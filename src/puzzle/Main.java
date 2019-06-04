package puzzle;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        ScreenChangerService mainContainer = new ScreenChangerService();
        mainContainer.loadScreen("newGame.fxml");
        mainContainer.loadScreen("scoreboard.fxml");
        mainContainer.loadScreen("game.fxml");
        mainContainer.loadScreen("index.fxml");

        mainContainer.setScreen("index.fxml");
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
