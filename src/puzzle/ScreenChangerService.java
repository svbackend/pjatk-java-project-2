package puzzle;

import java.io.IOException;
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
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ScreenChangerService extends StackPane {
    private HashMap<String, Node> screens = new HashMap<>();
    private HashMap<String, IController> controllers = new HashMap<>();


    public ScreenChangerService() {
        super();
    }


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

    void setScreen(final String fxmlFilename, IParameterBag parameterBag) {
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
            return true;
        } else {
            System.out.println(fxmlFilename + "screen hasn't been loaded!!! \n");
            return false;
        }


        /*Node screenToRemove;
         if(screens.get(fxmlFilename) != null){   //screen loaded
         if(!getChildren().isEmpty()){    //if there is more than one screen
         getChildren().add(0, screens.get(fxmlFilename));     //add the screen
         screenToRemove = getChildren().get(1);
         getChildren().remove(1);                    //remove the displayed screen
         }else{
         getChildren().add(screens.get(fxmlFilename));       //no one else been displayed, then just show
         }
         return true;
         }else {
         System.out.println("screen hasn't been loaded!!! \n");
         return false;
         }*/
    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }
}

