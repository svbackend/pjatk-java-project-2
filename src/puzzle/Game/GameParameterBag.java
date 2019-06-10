package puzzle.Game;

import puzzle.Interfaces.IParameterBag;

import java.awt.image.BufferedImage;

public class GameParameterBag implements IParameterBag {
    private final BufferedImage image;
    private final String username;
    private final String difficulty;

    public GameParameterBag(BufferedImage image, String username, String difficulty) {
        this.image = image;
        this.username = username;
        this.difficulty = difficulty;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
