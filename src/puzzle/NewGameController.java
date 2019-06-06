package puzzle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class NewGameController implements Initializable , IController {

    private ScreenChangerService screenChangerService;

    @FXML
    ImageView imageView;

    @FXML
    Button selectImgBtn;

    private Image userImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setScreenChanger(ScreenChangerService screenParent){
        this.screenChangerService = screenParent;
    }

    public Image getUserImage() {
        return this.userImage;
    }

    @FXML
    private void startGame(ActionEvent event) {
        this.screenChangerService.setScreen("game.fxml");
    }

    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("jpg files", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("png files", "*.png");
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);

        File file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            this.userImage = SwingFXUtils.toFXImage(bufferedImage, null);
            this.imageView.setImage(this.userImage);
            this.selectImgBtn.setText("Change image");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Timeline displayImageAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(this.imageView.fitHeightProperty(), 0.0)),
                new KeyFrame(new Duration(400), new KeyValue(this.imageView.fitHeightProperty(), 150.0))
        );

        displayImageAnimation.play();
    }
}
