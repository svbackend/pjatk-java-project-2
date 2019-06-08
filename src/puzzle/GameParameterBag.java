package puzzle;

import javafx.scene.image.Image;

class GameParameterBag implements IParameterBag {
    private final Image image;
    private final String username;

    GameParameterBag(Image image, String username) {
        this.image = image;
        this.username = username;
    }

    Image getImage() {
        return image;
    }

    String getUsername() {
        return username;
    }
}
