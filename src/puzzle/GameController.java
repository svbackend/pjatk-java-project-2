package puzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable, IController {
    ScreenChangerService myController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setScreenParent(ScreenChangerService screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       myController.setScreen("newGame.fxml");
    }
    
    @FXML
    private void goToScreen2(ActionEvent event){
       myController.setScreen("scoreboard.fxml");
    }
}
