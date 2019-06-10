package puzzle.Won;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import puzzle.Interfaces.IController;
import puzzle.Interfaces.IParameterBag;
import puzzle.NewGame.NewGameController;
import puzzle.Scoreboard.ScoreboardController;
import puzzle.ScreenChangerService;
import puzzle.Game.SpentTime;

import java.net.URL;
import java.util.ResourceBundle;

public class WonController implements Initializable, IController {
    public static final String SCREEN = "Won/won.fxml";

    private ScreenChangerService screenChangerService;

    @FXML
    Label resultsLabel;

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
        String username = ((ResultsParameterBag) parameterBag).getUsername();
        String difficulty = ((ResultsParameterBag) parameterBag).getDifficulty();
        SpentTime spentTime = ((ResultsParameterBag) parameterBag).getSpentTime();
        resultsLabel.setText(username + " won on " + difficulty + " level with time: " + spentTime.toString());
    }
}
