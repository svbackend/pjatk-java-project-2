package puzzle;

class ResultsParameterBag implements IParameterBag {
    private final String username;
    private final String difficulty;
    private final SpentTime spentTime;

    ResultsParameterBag(SpentTime spentTime, String username, String difficulty) {
        this.spentTime = spentTime;
        this.username = username;
        this.difficulty = difficulty;
    }

    public SpentTime getSpentTime() {
        return spentTime;
    }

    String getUsername() {
        return username;
    }

    String getDifficulty() {
        return difficulty;
    }
}
