package data;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<String> connectedPlayers;
    private List<String> playersAnswers;
    private Player player;
    private String activeName = "stub";
    private String correctNumber = "-";
    private String firstQuestion = "first question";
    private String secondQuestion = "second question";
    private Boolean isConnected = false;
    private Boolean isGameStarted = false;

    public GameState(List<String> connectedPlayers, List<String> playersAnswers) {
        this.connectedPlayers = connectedPlayers;
        this.playersAnswers = playersAnswers;
    }

    public List<String> getConnectedPlayers() {
        return connectedPlayers;
    }

    public void setConnectedPlayers(List<String> connectedPlayers) {
        this.connectedPlayers = connectedPlayers;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getCorrectNumber() {
        return correctNumber;
    }

    public void setCorrectNumber(String correctNumber) {
        this.correctNumber = correctNumber;
    }

    public String getFirstQuestion() {
        return firstQuestion;
    }

    public void setFirstQuestion(String firstQuestion) {
        this.firstQuestion = firstQuestion;
    }

    public String getSecondQuestion() {
        return secondQuestion;
    }

    public void setSecondQuestion(String secondQuestion) {
        this.secondQuestion = secondQuestion;
    }

    public List<String> getPlayersAnswers() {
        return playersAnswers;
    }

    public void setPlayersAnswers(List<String> playersAnswers) {
        this.playersAnswers = playersAnswers;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

    public Boolean getGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(Boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "connectedPlayers=" + connectedPlayers +
                ", playersAnswers=" + playersAnswers +
                ", player=" + player +
                ", activeName='" + activeName + '\'' +
                ", correctNumber='" + correctNumber + '\'' +
                ", firstQuestion='" + firstQuestion + '\'' +
                ", secondQuestion='" + secondQuestion + '\'' +
                ", isConnected=" + isConnected +
                ", isGameStarted=" + isGameStarted +
                '}';
    }
}
