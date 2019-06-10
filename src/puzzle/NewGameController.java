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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

import javafx.util.Duration;

public class NewGameController implements Initializable , IController, IListener {

    private ScreenChangerService screenChanger;

    @FXML
    ImageView imageView;

    @FXML
    Button selectImgBtn;

    @FXML
    TextField username;

    @FXML
    ChoiceBox<String> difficulty;

    private BufferedImage userImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setScreenChanger(ScreenChangerService screenParent){
        this.screenChanger = screenParent;
    }

    @FXML
    private void startGame(ActionEvent event) {
        this.screenChanger.setScreen("game.fxml", new GameParameterBag(userImage, username.getText(), difficulty.getValue()));
    }

    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("jpg files", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("png files", "*.png");
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);

        File file = fileChooser.showOpenDialog(null);

        try {
            this.userImage = ImageIO.read(file);
            this.imageView.setImage(SwingFXUtils.toFXImage(this.userImage, null));
            this.selectImgBtn.setText("Change image");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Timeline displayImageAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(this.imageView.fitHeightProperty(), 0.0)),
                new KeyFrame(new Duration(400), new KeyValue(this.imageView.fitHeightProperty(), 150.0))
        );

        displayImageAnimation.play();
    }

    public void onKeyEvent(KeyEvent event) {
        if (event.getEventType() != KeyEvent.KEY_PRESSED) {
            return;
        }

        if (event.getCode() != KeyCode.BACK_SPACE) {
            return;
        }

        screenChanger.setScreen("index.fxml");
    }

    public void setParameterBag(IParameterBag parameterBag) {
        // todo move to base abstract controller class in order to avoid copypaste such empty methods in each controller
    }
}
