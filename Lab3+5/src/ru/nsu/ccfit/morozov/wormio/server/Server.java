package ru.nsu.ccfit.morozov.wormio.server;

import ru.nsu.ccfit.morozov.wormio.server.model.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private Controller controller;
    private boolean keepRunning;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        controller = new Controller(10);
    }

    public synchronized void shutdown(){
        if (serverSocket != null){
            try{
                keepRunning = false;
                controller.stop();
                serverSocket.close();
            }catch (IOException ignore){

            }
            finally {
                serverSocket = null;
            }
        }

    }


    @Override
    public void run() {
        controller.init();
        keepRunning = true;
        while (keepRunning){
            try {
            Socket connection = serverSocket.accept();
            connection.setTcpNoDelay(true);
            controller.register(connection);
            } catch (IOException e) {
            }
        }
    }

    public Controller getController(){
        return controller;
    }

}
