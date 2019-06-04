package puzzle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class NewGameController implements Initializable , IController {

    private ScreenChangerService screenChangerService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setScreenParent(ScreenChangerService screenParent){
        screenChangerService = screenParent;
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       screenChangerService.setScreen("index.fxml");
    }
    
    @FXML
    private void goToScreen3(ActionEvent event){
       screenChangerService.setScreen("scoreboard.fxml");
    }
}
