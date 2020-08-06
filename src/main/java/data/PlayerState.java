package data;

public class PlayerState {

    private boolean startGameActivity = false;
    private boolean isReadyButtonEnabled = false;
    private boolean isShowAnswerButtonEnabled = false;
    private boolean isAnswerButtonEnabled = false;
    private boolean isAnswerSpinnerEnabled = false;
    private boolean isSeekBarAnswerVisible = false;

    public boolean isStartGameActivity() {
        return startGameActivity;
    }

    public void setStartGameActivity(boolean startGameActivity) {
        this.startGameActivity = startGameActivity;
    }

    public boolean isReadyButtonEnabled() {
        return isReadyButtonEnabled;
    }

    public void setReadyButtonEnabled(boolean readyButtonEnabled) {
        isReadyButtonEnabled = readyButtonEnabled;
    }

    public boolean isShowAnswerButtonEnabled() {
        return isShowAnswerButtonEnabled;
    }

    public void setShowAnswerButtonEnabled(boolean showAnswerButtonEnabled) {
        isShowAnswerButtonEnabled = showAnswerButtonEnabled;
    }

    public boolean isAnswerButtonEnabled() {
        return isAnswerButtonEnabled;
    }

    public void setAnswerButtonEnabled(boolean answerButtonEnabled) {
        isAnswerButtonEnabled = answerButtonEnabled;
    }

    public boolean isAnswerSpinnerEnabled() {
        return isAnswerSpinnerEnabled;
    }

    public void setAnswerSpinnerEnabled(boolean answerSpinnerEnabled) {
        isAnswerSpinnerEnabled = answerSpinnerEnabled;
    }

    public boolean isSeekBarAnswerVisible() {
        return isSeekBarAnswerVisible;
    }

    public void setSeekBarAnswerVisible(boolean seekBarAnswerVisible) {
        isSeekBarAnswerVisible = seekBarAnswerVisible;
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "startGameActivity=" + startGameActivity +
                ", isReadyButtonEnabled=" + isReadyButtonEnabled +
                ", isShowAnswerButtonEnabled=" + isShowAnswerButtonEnabled +
                ", isAnswerButtonEnabled=" + isAnswerButtonEnabled +
                ", isAnswerSpinnerEnabled=" + isAnswerSpinnerEnabled +
                ", isSeekBarAnswerVisible=" + isSeekBarAnswerVisible +
                '}';
    }
}
