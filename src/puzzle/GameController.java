package puzzle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameController implements IController {
    private ScreenChangerService screenChanger;

    @FXML
    Label usernameLabel;

    @FXML
    ImageView imageView;

    private BufferedImage userImage;
    private String username;
    private String difficulty;

    private int cols;
    private int rows;

    public void setScreenChanger(ScreenChangerService screenChanger) {
        this.screenChanger = screenChanger;
    }

    public void setParameterBag(IParameterBag parameterBag) {
        this.userImage = ((GameParameterBag) parameterBag).getImage();
        this.username = ((GameParameterBag) parameterBag).getUsername();
        this.difficulty = ((GameParameterBag) parameterBag).getDifficulty();

        usernameLabel.setText(this.username + " (Difficulty: " + difficulty + ")");
        imageView.setImage(SwingFXUtils.toFXImage(this.userImage, null));

        calcFieldSize();
        createTiles();
    }

    private void calcFieldSize() {
        switch (difficulty) {
            case "Medium":
                this.cols = 4;
                this.rows = 3;
                break;

            case "Hard":
                this.cols = 5;
                this.rows = 4;
                break;

            default:
                // Easy
                this.cols = 3;
                this.rows = 2;
                break;
        }
    }

    private void createTiles() {
            int imageWidth = this.userImage.getWidth();
            int imageHeight = this.userImage.getHeight();

            //width and height of each piece
            int tileWidth = imageWidth / this.cols;
            int tileHeight = imageHeight / this.rows;

            int x = 0;
            int y = 0;

            for (int i = 0; i < rows; i++) {
                x = 0;
                for (int j = 0; j < cols; j++) {
                    try {
                        BufferedImage SubImgage = this.userImage.getSubimage(x, y, tileWidth, tileHeight);
                        File outputFile = new File("/tmp/puzzle" + i + j + ".jpg");
                        ImageIO.write(SubImgage, "jpg", outputFile);

                        x += tileWidth;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                y += tileHeight;
            }
    }

    @FXML
    private void goToScreen1(ActionEvent event) {
        screenChanger.setScreen("newGame.fxml");
    }

    @FXML
    private void goToScreen2(ActionEvent event) {
        screenChanger.setScreen("scoreboard.fxml");
    }
}
