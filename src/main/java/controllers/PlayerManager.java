package controllers;

import com.google.gson.Gson;
import data.GameState;
import data.Player;
import logic.GameLogic;
import server.App;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PlayerManager extends Thread{

    private PlayerController playerController;
    private GameLogic gameLogic;
    private Gson gson = new Gson();

    public PlayerManager(Socket socket, GameLogic gameLogic) {
        try {
            this.playerController = new PlayerController(socket);
            this.gameLogic = gameLogic;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        playerController.setGameState(gameLogic.createGameState());
        playerController.getGameState().setConnected(true);
        playerController.getGameState().setGameStarted(gameLogic.isGameStarted());
        updateGameState();

        while (true) {
            String message = playerController.readMessage();
            if (playerController.getGameState() != null) {
                System.out.println(playerController.getGameState().getPlayer() + ": Got message " + message);
            } else {
                System.out.println("Unknown: Got message " + message);
            }

            if(message == null || message.equals("Error")) {
                System.out.println("ERROR in read message");
                App.removePlayerManger(this);
                gameLogic.addDisconnectPlayer(playerController.getGameState().getPlayer());
                break;
            }

            checkCommand(message);
        }
    }

    private void checkCommand(String message) {
        if (message.startsWith("Login:")) {
            String name = message.replace("Login:", "");
            gameLogic.createNewPlayer(playerController.getGameState(), name, playerController.getPlayerId());
            System.out.println("Player " + playerController.getGameState().getPlayer().toString() + " has been added");
            updateAllGameStates();
        } else if(message.startsWith("GetDisconnectedPlayers:")) {
            playerController.sendMessage("DisconnectedPlayers:" + gameLogic.getDisconnectPlayers());
        } else if(message.startsWith("Reconnect:")) {
            String name = message.replace("Reconnect:", "");
            Optional<GameState> gameState = gameLogic.getGameStates().stream().filter(it -> it.getPlayer().getName().equals(name)).findFirst();
            if (gameState.isPresent()) {
                gameLogic.getGameStates().remove(playerController.getGameState());
                playerController.setGameState(gameState.get());
                gameState.get().getPlayer().setId(playerController.getPlayerId());
                playerController.sendMessage("ReconnectResponse:Success");
                updateGameState();
                gameLogic.removeDisconnectPlayer(gameState.get().getPlayer());
            } else {
                playerController.sendMessage("ReconnectResponse:Fail");
            }
        } else if(message.startsWith("StartGame:")) {
            gameLogic.startGame();
            updateAllGameStates();
        } else if(message.startsWith("GameReady:")) {
            gameLogic.playerReady(playerController.getGameState());
            if (gameLogic.allReady()) {
                gameLogic.startTurn();
                updateAllGameStates();
            } else {
                updateGameState();
            }
        } else if(message.startsWith("SentAnswer:")) {
            String answer = message.replace("SentAnswer:", "");
            gameLogic.playerSentAnswer(playerController.getGameState(), Integer.parseInt(answer));
            updateAllGameStates();
        } else if(message.startsWith("ShowAnswers:")) {
            gameLogic.calculateScores();
            updateAllGameStates();
        }
    }

    private void updateAllGameStates() {
        App.getPlayerManagers().forEach(it -> {
            GameState gameState = it.playerController.getGameState();
            if (gameState != null) {
                String json = gson.toJson(gameState);
                it.playerController.sendMessage("GameState:" + json);
            }
        });
    }

    private void updateGameState() {
        GameState gameState = playerController.getGameState();
        if (gameState != null) {
            String json = gson.toJson(gameState);
            playerController.sendMessage("GameState:" + json);
        }
    }

    private void sendMessageActivePlayer(String message) {
        PlayerManager manager = App.getPlayerManagers().stream().filter(it -> it.playerController.getGameState().getPlayer().equals(gameLogic.getActivePlayer())).findFirst().get();
        manager.playerController.sendMessage(message);
    }

    public void closeConnections() {
        playerController.closeConnections();
    }
}
