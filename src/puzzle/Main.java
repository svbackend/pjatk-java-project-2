package puzzle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane content;
    @FXML
    Button newGameBtn;

    @FXML
    public void newGame() {
        newGameBtn.setText("Loading...");
        try {
            switchScene("newGameMenu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchScene(String newSceneName) throws Exception {
        AnchorPane newContent = FXMLLoader.load(getClass().getResource(newSceneName));
        root.getChildren().setAll(newContent);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        primaryStage.setTitle("Puzzle Game");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
