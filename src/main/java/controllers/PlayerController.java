package controllers;

import data.GameState;
import data.Player;
import sockets.PlayerSocket;

import java.io.IOException;
import java.net.Socket;

public class PlayerController extends Thread{

    private PlayerSocket playerSocket;
    private GameState gameState;

    public PlayerController(Socket socket) throws IOException {
        playerSocket = new PlayerSocket(socket);
    }

    public String readMessage() {
        try {
            return playerSocket.getIn().readLine();
        } catch (IOException e) {
            e.printStackTrace();
            closeConnections();
        }
        return "Error";
    }

    public int getPlayerId() {
        return playerSocket.getPlayerSocket().getPort();
    }

    public void sendMessage(String message) {
        try {
            playerSocket.getOut().write(message + "\n");
            playerSocket.getOut().flush();
            System.out.println(gameState.getPlayer() + ": Reply sent " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnections() {
        try {
            playerSocket.getPlayerSocket().close();
            playerSocket.getIn().close();
            playerSocket.getOut().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
