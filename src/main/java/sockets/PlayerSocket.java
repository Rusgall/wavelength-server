package sockets;

import java.io.*;
import java.net.Socket;

public class PlayerSocket {

    private Socket playerSocket;
    private BufferedReader in;
    private BufferedWriter out;

    public PlayerSocket(Socket socket) throws IOException {
        playerSocket = socket;
        in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(playerSocket.getOutputStream()));
    }

    public Socket getPlayerSocket() {
        return playerSocket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }
}
