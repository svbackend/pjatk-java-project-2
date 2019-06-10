package puzzle.Won;

import puzzle.Game.SpentTime;
import puzzle.Interfaces.IParameterBag;

public class ResultsParameterBag implements IParameterBag {
    private final String username;
    private final String difficulty;
    private final SpentTime spentTime;

    public ResultsParameterBag(SpentTime spentTime, String username, String difficulty) {
        this.spentTime = spentTime;
        this.username = username;
        this.difficulty = difficulty;
    }

    public SpentTime getSpentTime() {
        return spentTime;
    }

    public String getUsername() {
        return username;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
