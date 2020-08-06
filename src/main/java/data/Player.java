package data;

public class Player {
    private int id;
    private boolean isFirst = false;
    private boolean isReady = false;
    private boolean isActive = false;
    private boolean isSentAnswer = false;
    private int score = 0;
    private int newScore = 0;
    private int currentAnswer = 0;
    private String name = "stub";
    private PlayerState playerState;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        playerState = new PlayerState();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public int getCurrentAnswer() {
        return currentAnswer;
    }

    public void setCurrentAnswer(int currentAnswer) {
        this.currentAnswer = currentAnswer;
    }

    public int getNewScore() {
        return newScore;
    }

    public void setNewScore(int newScore) {
        this.newScore = newScore;
    }

    public void addScore(int addScore) {
        score += addScore;
    }

    public boolean isSentAnswer() {
        return isSentAnswer;
    }

    public void setSentAnswer(boolean sentAnswer) {
        isSentAnswer = sentAnswer;
    }

    public boolean isActive() {
        return isActive;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", isFirst=" + isFirst +
                ", isReady=" + isReady +
                ", isActive=" + isActive +
                ", isSentAnswer=" + isSentAnswer +
                ", score=" + score +
                ", newScore=" + newScore +
                ", currentAnswer=" + currentAnswer +
                ", name='" + name + '\'' +
                ", playerState=" + playerState +
                '}';
    }
}
