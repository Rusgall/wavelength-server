package server;

import controllers.PlayerManager;
import logic.GameLogic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static GameLogic gameLogic;

    private static List<PlayerManager> playerManagers = new ArrayList<>();

    public static void main(String[] args) {
        gameLogic = new GameLogic();
        try {
            try {
                server = new ServerSocket(6666);
                System.out.println("Server has started");

                while (true) {
                    Socket client = server.accept();
                    System.out.println("Socket " + client.getPort() + " accepted");
                    PlayerManager playerManager = new PlayerManager(client, gameLogic);
                    playerManager.start();
                    playerManagers.add(playerManager);
                    System.out.println("Socket " + client.getPort() + " connected");
                }
            }finally {
                server.close();
                playerManagers.forEach(PlayerManager::closeConnections);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<PlayerManager> getPlayerManagers() {
        return playerManagers;
    }

    public static void removePlayerManger(PlayerManager playerManager) {
        playerManagers.remove(playerManager);
        if (playerManagers.isEmpty()) {
            gameLogic = new GameLogic();
        }
    }
}
