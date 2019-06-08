package puzzle;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.IntStream;

public class GameController implements IController {
    private ScreenChangerService screenChanger;

    @FXML
    Label usernameLabel;

    @FXML
    GridPane tiles;

    private BufferedImage userImage;
    private String username;
    private String difficulty;

    private int cols;
    private int rows;

    private HashMap<String, ImageView> puzzles = new HashMap<>();

    public void setScreenChanger(ScreenChangerService screenChanger) {
        this.screenChanger = screenChanger;
    }

    public void setParameterBag(IParameterBag parameterBag) {
        this.userImage = ((GameParameterBag) parameterBag).getImage();
        this.username = ((GameParameterBag) parameterBag).getUsername();
        this.difficulty = ((GameParameterBag) parameterBag).getDifficulty();

        usernameLabel.setText(this.username + " (Difficulty: " + difficulty + ")");

        calcFieldSize();
        createTiles();
    }

    private void calcFieldSize() {
        switch (difficulty) {
            default:
            case "Easy":
                this.cols = 3;
                this.rows = 2;
                break;

            case "Medium":
                this.cols = 4;
                this.rows = 3;
                break;

            case "Hard":
                this.cols = 5;
                this.rows = 4;
                break;
        }
    }

    private void createTiles() {
        int imageWidth = this.userImage.getWidth();
        int imageHeight = this.userImage.getHeight();

        int tileWidth = imageWidth / this.cols;
        int tileHeight = imageHeight / this.rows;

        final int AVAILABLE_WIDTH_SPACE = 994;
        final int AVAILABLE_HEIGHT_SPACE = 620;
        final int TILE_FIT_WIDTH = AVAILABLE_WIDTH_SPACE / this.cols;
        final int TILE_FIT_HEIGHT = AVAILABLE_HEIGHT_SPACE / this.rows;
        boolean useFitWidth = true;

        if (imageHeight >= imageWidth) {
            useFitWidth = false;
        }

        int x = 0;
        int y = 0;

        for (int i = 0; i < rows; i++) {
            x = 0;
            for (int j = 0; j < cols; j++) {
                try {
                    BufferedImage tileImage = this.userImage.getSubimage(x, y, tileWidth, tileHeight);
                    ImageView imageView = new ImageView();
                    imageView.setId(i + "x" + j);

                    if (useFitWidth) {
                        imageView.setFitWidth(TILE_FIT_WIDTH);
                    } else {
                        imageView.setFitHeight(TILE_FIT_HEIGHT);
                    }

                    imageView.setImage(SwingFXUtils.toFXImage(tileImage, null));
                    imageView.setPreserveRatio(true);
                    this.puzzles.put(imageView.getId(), imageView);

                    x += tileWidth;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            y += tileHeight;
        }

        renderTiles();

        boolean isWin = isPuzzleResolved();

        System.out.println(isWin);
    }

    private void renderTiles() {
        // java, are you kidding me?
        Integer[] rows = IntStream.rangeClosed(0, this.rows-1).boxed().toArray(Integer[]::new);
        Integer[] cols = IntStream.rangeClosed(0, this.cols-1).boxed().toArray(Integer[]::new);

        Collections.shuffle(Arrays.asList(rows));
        Collections.shuffle(Arrays.asList(cols));

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                ImageView tmp = this.puzzles.get(i + "x" + j);
                this.puzzles.put(i + "x" + j, this.puzzles.get(rows[i] + "x" + cols[j]));
                this.puzzles.put(rows[i] + "x" + cols[j], tmp);
                //this.tiles.add(this.puzzles.get(i + "x" + j), j, i);
                //this.tiles.add(this.puzzles.get(rows[i] + "x" + cols[j]), cols[j], rows[i]);
                //this.tiles.getChildren().set
            }
        }

    }

    private boolean isPuzzleResolved() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (!this.puzzles.get(i + "x" + j).getId().equals(i + "x" + j)) {
                    return false;
                }
            }
        }

        return true;
    }
}
