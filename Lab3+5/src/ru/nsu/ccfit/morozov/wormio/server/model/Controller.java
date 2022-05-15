package ru.nsu.ccfit.morozov.wormio.server.model;

import ru.nsu.ccfit.morozov.wormio.model.Action;
import ru.nsu.ccfit.morozov.wormio.model.PlayerView;

import java.io.IOException;
import java.net.Socket;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Controller extends TimerTask {

    ThreadPoolExecutor threadPool;
    private Game game;
    private Timer timer;
    Player[] players;
    int playersCount;
    Stack<Integer> availableIds;
    int maxPlayers;


    public Controller(int maxPlayers){
        this.maxPlayers = maxPlayers;
        threadPool = new ThreadPoolExecutor(maxPlayers,maxPlayers,0L, TimeUnit.SECONDS, new SynchronousQueue<>());
        game = new Game(this);
        players = new Player[maxPlayers];
        availableIds = new Stack<>();
        for (int i = maxPlayers-1; i>=0; --i)
            availableIds.push(i);
    }

    @Override
    public synchronized void run() {
        synchronized (this){
            game.tick();
            for (int i = 0; i<maxPlayers;++i){
                if (players[i] == null)
                    continue;

                else if (game.getPlayer(i).alive == false){
                    disconnect(i);
                    continue;
                }
                PlayerView view = game.getPlayerView(i);

                players[i].setPlayerView(view);
                threadPool.execute(players[i].getSender());
            }
        }
    }

    public void init(){
        game.init(maxPlayers);
        playersCount = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(this, 30, 30);
    }

    public void stop(){
        timer.cancel();
        threadPool.shutdown();
        for(Player player : players){
            if (player == null)
                continue;
            player.disconnect();
        }
    }
    public void register(Socket socket) {
        if (availableIds.empty()){
            System.out.println("The server is full");
            try {
                socket.close();
            } catch (IOException ignore) {
            }
            return;
        }
        int id = availableIds.pop();
        synchronized (this){
            players[id] = new Player(this, socket, id);
            game.addPlayer(id);
            playersCount++;
            System.out.println("Player" + id + " joined. Total: " + playersCount + " players.");
        }
    }

    public void movePlayer( int id, Action action) {
        Player player = players[id];
        if (player.getToken().equals(action.getToken())){
            game.changeVelocity(id, action.getDirection(), action.getSpeed());
        }
    }



    public synchronized void disconnect(int id) {
        if (id<0 || id>=maxPlayers)
            return;
        if(players[id]==null){
            return;
        }

        players[id].disconnect();
        players[id]=null;
        --playersCount;
        game.killPlayer(id);
        System.out.println("Player" + id + " disconnected. Total: " + playersCount + " players.");
        availableIds.push(id);
    }

    public Player[] getPlayers(){
        return players;
    }
}
