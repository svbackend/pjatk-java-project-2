package puzzle.Index;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import puzzle.Interfaces.IController;
import puzzle.Interfaces.IParameterBag;
import puzzle.NewGame.NewGameController;
import puzzle.Scoreboard.ScoreboardController;
import puzzle.ScreenChangerService;

public class IndexController implements Initializable, IController {
    private ScreenChangerService screenChangerService;

    public static final String SCREEN = "Index/index.fxml";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setScreenChanger(ScreenChangerService screenParent){
        screenChangerService = screenParent;
    }

    @FXML
    private void newGame(ActionEvent event){
       screenChangerService.setScreen(NewGameController.SCREEN);
    }
    
    @FXML
    private void showScoreboard(ActionEvent event){
       screenChangerService.setScreen(ScoreboardController.SCREEN);
    }

    @Override
    public void setParameterBag(IParameterBag parameterBag) {

    }
}
