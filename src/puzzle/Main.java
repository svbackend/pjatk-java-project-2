package puzzle;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        ScreenChangerService screenChanger = new ScreenChangerService();
        screenChanger.loadScreen("newGame.fxml");
        screenChanger.loadScreen("scoreboard.fxml");
        screenChanger.loadScreen("game.fxml");
        screenChanger.loadScreen("index.fxml");

        screenChanger.setScreen("index.fxml");
        
        Group root = new Group();
        root.getChildren().addAll(screenChanger);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(768);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
