package logic;

import data.GameState;
import data.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static logic.Questions.getQuestions;

public class GameLogic {

    List<GameState> gameStates = new ArrayList<>();
    private List<String> connectedPlayersNames = new ArrayList<>();
    private List<String> playersAnswers = new ArrayList<>();
    private String currentQuestion;
    private Player activePlayer;
    private Random random = new Random();
    private int correctNumber = 0;
    private List<String> disconnectedPlayers = new ArrayList<>();

    public void createNewPlayer(GameState gameState, String name, int id) {
        Player player = new Player(name, id);
        if (gameStates.stream().noneMatch(it -> it.getPlayer() != null)){
            player.setFirst(true);
            activePlayer = player;
        }
        gameState.setPlayer(player);
        connectedPlayersNames.add(player.getName());
    }

    public GameState createGameState() {
        GameState gameState = new GameState(connectedPlayersNames, playersAnswers);
        gameStates.add(gameState);
        return gameState;
    }

    public List<Player> getPlayers() {
        return gameStates.stream().map(GameState::getPlayer).collect(Collectors.toList());
    }

    public GameState getGameStateForPlayer(Player player) {
        return gameStates.stream().filter(gameState -> gameState.getPlayer().equals(player)).findFirst().get();
    }

    public List<GameState> getGameStates() {
        return gameStates;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<Player> getOtherPlayers() {
        return getPlayers().stream().filter(it -> !it.equals(activePlayer)).collect(Collectors.toList());
    }

    public String getQuestion() {
        return currentQuestion;
    }

    public int getNumber() {
        return correctNumber;
    }

    public void playerReady(GameState gameState) {
        playersAnswers.clear();
        gameState.getPlayer().getPlayerState().setReadyButtonEnabled(false);
        gameState.getPlayer().setReady(true);
    }

    public boolean allReady() {
        return getPlayers().stream().allMatch(Player::isReady);
    }

    public void startTurn() {
        playersAnswers.clear();
        newActivePlayer();
        if(getQuestions().isEmpty()){
            currentQuestion = "End or Game";
        } else {
            currentQuestion = getQuestions().get(random.nextInt(getQuestions().size()));
            getQuestions().remove(currentQuestion);
            correctNumber = random.nextInt(10) + 1;
        }
        getGameStates().forEach(it -> {
            it.getPlayer().setNewScore(0);
            it.getPlayer().setSentAnswer(false);
            it.getPlayer().setReady(false);
            it.getPlayer().setCurrentAnswer(0);
            it.setFirstQuestion(currentQuestion.split(" or ")[0]);
            it.setSecondQuestion(currentQuestion.split(" or ")[1]);
            it.setActiveName(activePlayer.getName());
            it.getPlayer().getPlayerState().setShowAnswerButtonEnabled(false);
            if (it.getPlayer().isActive()) {
                it.setCorrectNumber(String.valueOf(correctNumber));
                it.getPlayer().getPlayerState().setAnswerButtonEnabled(false);
                it.getPlayer().getPlayerState().setAnswerSpinnerEnabled(false);
            } else {
                it.setCorrectNumber("-");
                it.getPlayer().getPlayerState().setAnswerButtonEnabled(true);
                it.getPlayer().getPlayerState().setAnswerSpinnerEnabled(true);
            }
        });
    }

    public void playerSentAnswer(GameState gameState, int answer) {
        Player player = gameState.getPlayer();
        player.setCurrentAnswer(answer);
        player.setSentAnswer(true);
        player.getPlayerState().setAnswerSpinnerEnabled(false);
        player.getPlayerState().setAnswerButtonEnabled(false);
        playersAnswers.add(player.getName() + ": " + answer);
        if (allPlayersSentAnswer()) {
            activePlayer.getPlayerState().setShowAnswerButtonEnabled(true);
        }
    }

    public void startGame() {
        gameStates.forEach(it -> {
            it.getPlayer().getPlayerState().setStartGameActivity(true);
            it.getPlayer().getPlayerState().setReadyButtonEnabled(true);
        });
    }

    public boolean allPlayersSentAnswer() {
        return getOtherPlayers().stream().allMatch(Player::isSentAnswer);
    }

    public void calculateScores() {
        getOtherPlayers().forEach(player -> {
            int guessAnswer = player.getCurrentAnswer();
            int prizeScore;
            int diff = Math.abs(correctNumber - guessAnswer);
            switch (diff) {
                case 0:
                    prizeScore = 4;
                    break;
                case 1:
                    prizeScore = 3;
                    break;
                case 2:
                    prizeScore = 2;
                    break;
                default:
                    prizeScore = 0;
            }
            player.setNewScore(prizeScore);
            player.addScore(player.getNewScore());
        });
        int newScoreForActivePlayer = getOtherPlayers().stream().mapToInt(Player::getNewScore).max().getAsInt();
        activePlayer.setNewScore(newScoreForActivePlayer);
        activePlayer.addScore(activePlayer.getNewScore());
        activePlayer.getPlayerState().setShowAnswerButtonEnabled(false);
        gameStates.forEach(it -> it.getPlayer().getPlayerState().setReadyButtonEnabled(true));
        updateAnswerWithScores();
    }

    private void updateAnswerWithScores() {
        playersAnswers.clear();
        getOtherPlayers().forEach(player -> playersAnswers.add(
                player.getName() + ": " + player.getCurrentAnswer() + " (+" + player.getNewScore() + ")"
        ));
    }

    private void newActivePlayer() {
        activePlayer.setActive(false);
        int index = getPlayers().indexOf(activePlayer);
        index++;
        if (index >= getPlayers().size()) {
            index = 0;
        }
        activePlayer = getPlayers().get(index);
        activePlayer.setActive(true);
    }

    public void addDisconnectPlayer(Player player) {
        disconnectedPlayers.add(player.getName());
    }

    public List<String> getDisconnectPlayers() {
        return disconnectedPlayers;
    }

    public void removeDisconnectPlayer(Player player) {
        disconnectedPlayers.remove(player.getName());
    }
}
