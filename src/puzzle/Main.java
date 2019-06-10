package puzzle;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import puzzle.Game.GameController;
import puzzle.Index.IndexController;
import puzzle.NewGame.NewGameController;
import puzzle.Scoreboard.ScoreboardController;

public class Main extends Application {

    private ScreenChangerService screenChanger;

    @Override
    public void start(Stage primaryStage) {
        this.screenChanger = new ScreenChangerService();
        this.screenChanger.loadScreen(NewGameController.SCREEN);
        this.screenChanger.loadScreen(ScoreboardController.SCREEN);
        this.screenChanger.loadScreen(GameController.SCREEN);
        this.screenChanger.loadScreen(IndexController.SCREEN);

        this.screenChanger.setScreen(IndexController.SCREEN);
        
        Group root = new Group(this.screenChanger);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(this.screenChanger.getKeyListener());

        final int WIDTH = 1024;
        final int HEIGHT = 768;
        final String TITLE = "Puzzle Game";

        primaryStage.setScene(scene);
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setMinWidth(WIDTH);
        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setMaxWidth(WIDTH);
        primaryStage.setMaxHeight(HEIGHT);
        primaryStage.setTitle(TITLE);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop(){
        this.screenChanger.stop();
    }
}
