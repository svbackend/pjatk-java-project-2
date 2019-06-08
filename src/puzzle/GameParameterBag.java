package puzzle;

import java.awt.image.BufferedImage;

class GameParameterBag implements IParameterBag {
    private final BufferedImage image;
    private final String username;
    private final String difficulty;

    GameParameterBag(BufferedImage image, String username, String difficulty) {
        this.image = image;
        this.username = username;
        this.difficulty = difficulty;
    }

    BufferedImage getImage() {
        return image;
    }

    String getUsername() {
        return username;
    }

    String getDifficulty() {
        return difficulty;
    }
}
