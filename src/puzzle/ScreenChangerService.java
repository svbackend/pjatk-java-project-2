package puzzle;

import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import puzzle.Interfaces.IController;
import puzzle.Interfaces.IListener;
import puzzle.Interfaces.IParameterBag;
import puzzle.Interfaces.IStopable;

public class ScreenChangerService extends StackPane {
    private HashMap<String, Node> screens = new HashMap<>();
    private HashMap<String, IController> controllers = new HashMap<>();
    private String currentScreen;

    private EventHandler<KeyEvent> keyListener = (event) -> {
        IController controller = controllers.get(currentScreen);

        if (controller instanceof IListener) {
            ((IListener) controller).onKeyEvent(event);
        }
    };

    public Node getScreen(String name) {
        return screens.get(name);
    }

    public boolean loadScreen(String fxmlFilename) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(fxmlFilename));
            Parent loadScreen = myLoader.load();
            IController controller = myLoader.getController();
            controller.setScreenChanger(this);
            screens.put(fxmlFilename, loadScreen);
            controllers.put(fxmlFilename, controller);
            return true;
        } catch (Exception e) {
            System.out.println("Screen not loaded: " + fxmlFilename);
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void setScreen(final String fxmlFilename, IParameterBag parameterBag) {
        IController controller = controllers.get(fxmlFilename);
        controller.setParameterBag(parameterBag);
        setScreen(fxmlFilename);
    }

    public boolean setScreen(final String fxmlFilename) {
        if (screens.get(fxmlFilename) != null) {
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(400), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                getChildren().remove(0);
                                getChildren().add(0, screens.get(fxmlFilename));
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(400), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(fxmlFilename));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(400), new KeyValue(opacity, 1.0))
                );
                fadeIn.play();
            }

            this.currentScreen = fxmlFilename;

            return true;
        } else {
            System.out.println(fxmlFilename + "screen hasn't been loaded!!! \n");
            return false;
        }
    }

    public boolean reloadScreen(String name) {
        stop(name);
        screens.remove(name);
        return loadScreen(name);
    }

    public EventHandler<KeyEvent> getKeyListener() {
        return keyListener;
    }

    public void stop() {
        stop(currentScreen);
    }

    public void stop(String name) {
        IController controller = controllers.get(name);

        if (controller instanceof IStopable) {
            ((IStopable) controller).stop();
        }
    }
}

