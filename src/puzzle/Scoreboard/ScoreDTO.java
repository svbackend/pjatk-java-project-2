package puzzle.Scoreboard;

public final class ScoreDTO {
    public final String username;
    public final String difficulty;
    public final String spentTime;

    public ScoreDTO(String username, String difficulty, String spentTime) {
        this.username = username;
        this.difficulty = difficulty;
        this.spentTime = spentTime;
    }
}
