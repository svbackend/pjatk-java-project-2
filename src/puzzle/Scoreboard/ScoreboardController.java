package puzzle.Scoreboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import puzzle.Index.IndexController;
import puzzle.Interfaces.IController;
import puzzle.Interfaces.IListener;
import puzzle.Interfaces.IParameterBag;
import puzzle.ScreenChangerService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScoreboardController implements Initializable, IController, IListener {
    public static final String SCREEN = "Scoreboard/scoreboard.fxml";

    private ScreenChangerService screenChanger;

    @FXML
    VBox box;

    public void setScreenChanger(ScreenChangerService screenChanger) {
        this.screenChanger = screenChanger;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<ScoreDTO> scores = ScoreboardService.getAll();

        for (ScoreDTO score : scores) {
            Label username = new Label();
            username.setText(score.username);
            username.getStyleClass().addAll("column", "username");

            Label difficulty = new Label();
            difficulty.setText(score.difficulty);
            difficulty.getStyleClass().addAll("column", "difficulty");

            Label spentTime = new Label();
            spentTime.setText(score.spentTime);
            spentTime.getStyleClass().addAll("column", "time");

            HBox row = new HBox();
            row.getChildren().addAll(username, difficulty, spentTime);

            this.box.getChildren().add(row);
        }
    }

    public void onKeyEvent(KeyEvent event) {
        if (event.getEventType() != KeyEvent.KEY_PRESSED) {
            return;
        }

        if (event.getCode() == KeyCode.BACK_SPACE) {
            screenChanger.setScreen(IndexController.SCREEN);
        }
    }

    public void setParameterBag(IParameterBag parameterBag) {

    }
}
