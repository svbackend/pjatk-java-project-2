package puzzle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class Screen3Controller implements Initializable, IController {

    ScreenChangerService myController;

    @FXML
    ImageView img;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.img.setImage();
    }    
    
    public void setScreenChanger(ScreenChangerService screenParent) {
        myController = screenParent;
    }

    @FXML
    private void goToScreen1(ActionEvent event) {
       myController.setScreen("index.fxml");
    }
    
    @FXML
    private void goToScreen2(ActionEvent event) {
       myController.setScreen("newGame.fxml");
    }

    @Override
    public void setParameterBag(IParameterBag parameterBag) {

    }
}
