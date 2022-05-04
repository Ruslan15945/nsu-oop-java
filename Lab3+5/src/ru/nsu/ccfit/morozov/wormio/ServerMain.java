package ru.nsu.ccfit.morozov.wormio;

import ru.nsu.ccfit.morozov.wormio.client.Bot;
import ru.nsu.ccfit.morozov.wormio.server.Server;
import ru.nsu.ccfit.morozov.wormio.server.model.Player;

import java.io.IOException;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {

        int port = args.length < 1 ? 54353 : Integer.parseInt(args[0]);
        try{

            Server server = new Server(port);
            Thread thread = new Thread(server);
            thread.start();


            Scanner in = new Scanner(System.in);
            while(true){
                String cmd = in.nextLine();
                String[] parsedCmd = cmd.split(" ");
                if (parsedCmd[0].equals("exit")){
                    server.shutdown();
                    break;
                }
                else if (parsedCmd[0].equals("disconnect")){
                    if (parsedCmd.length<2)
                        printPlayers(server);
                    else
                        server.getController().disconnect(Integer.parseInt(parsedCmd[1]));
                }
                else if (parsedCmd[0].equals("spawn")){
                    int count = parsedCmd.length > 1 ? Integer.parseInt(parsedCmd[1]):1;
                    for (int i = 0; i < count; ++i){
                        try{
                            new Bot("localhost",54353).init();
                        }
                        catch (IOException ignore){}
                    }
                }
                else if (parsedCmd[0].equals("playing")){
                    printPlayers(server);
                }
            }
            in.close();
        }
        catch (IOException e){
            System.out.println("Cannot start a server");
        }
    }

    public static void printPlayers(Server server) {
        for (Player player: server.getController().getPlayers()){
            if (player != null)
                System.out.println(player.getId() + " from " + player.getSocket().getRemoteSocketAddress());
        }
    }
}
