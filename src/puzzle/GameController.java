package puzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable, IController {
    ScreenChangerService screenChanger;

    static String call = "1";

    @FXML
    Label usernameLabel;

    @FXML
    ImageView imageView;

    Image userImage;
    String username;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usernameLabel.setText("Loading..." + call);
        call = "2";
    }
    
    public void setScreenChanger(ScreenChangerService screenChanger){
        this.screenChanger = screenChanger;
    }

    public void setParameterBag(IParameterBag parameterBag) {
        this.userImage = ((GameParameterBag) parameterBag).getImage();
        this.username = ((GameParameterBag) parameterBag).getUsername();

        usernameLabel.setText(this.username);
        imageView.setImage(this.userImage);
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       screenChanger.setScreen("newGame.fxml");
    }
    
    @FXML
    private void goToScreen2(ActionEvent event){
       screenChanger.setScreen("scoreboard.fxml");
    }
}
