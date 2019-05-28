package puzzle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application implements IController {
    @FXML
    AnchorPane root;

    @Override
    public void start(Stage primaryStage) {
        IndexController indexController = new IndexController();

        boolean isScreenLoaded = indexController.loadScreen("mainMenu.fxml");

        if (isScreenLoaded == false) {
            System.out.println("Hello!");
            return;
        }

        Group root = new Group();
        root.getChildren().addAll(indexController.getScreen("mainMenu.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void onNewGame() {
        System.out.println("Hello222!");
    }

    @Override
    public void setScreenParent(IController screenPage) {

    }
}
