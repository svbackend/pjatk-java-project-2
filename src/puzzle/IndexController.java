package puzzle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class IndexController implements Initializable, IController {
    private ScreenChangerService screenChangerService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setScreenChanger(ScreenChangerService screenParent){
        screenChangerService = screenParent;
    }

    @FXML
    private void newGame(ActionEvent event){
       screenChangerService.setScreen("newGame.fxml");
    }
    
    @FXML
    private void showScoreboard(ActionEvent event){
       screenChangerService.setScreen("scoreboard.fxml");
    }

    @Override
    public void setParameterBag(IParameterBag parameterBag) {

    }
}
