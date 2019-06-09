package puzzle;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Timer;

public class GameController implements IController, IListener, IStopable {
    private ScreenChangerService screenChanger;

    @FXML
    Label usernameLabel;
    @FXML
    Label timeLabel;

    @FXML
    GridPane tiles;

    private BufferedImage userImage;
    private String username;
    private String difficulty;

    private int cols;
    private int rows;

    // Position of user's tile
    private int currentRow;
    private int currentCol;

    private Timer timer;
    private SpentTime spentTime;

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
        initTimer();
    }

    private void initTimer() {
        this.spentTime = new SpentTime();

        this.timer = new Timer("Timer");
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    System.out.println("hello");
                    timeLabel.setText(spentTime.toString());
                });
            }
        }, 1000, 1000);

    }

    private void calcFieldSize() {
        switch (difficulty) {
            default:
            case "Easy":
                this.cols = 2;
                this.rows = 2;
                break;

            case "Medium":
                this.cols = 3;
                this.rows = 3;
                break;

            case "Hard":
                this.cols = 4;
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
                    imageView.setId(idx(i, j));

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

        this.currentRow = this.rows - 1;
        this.currentCol = this.cols - 1;

        getCurrent().setOpacity(0.6);

        shuffleTiles();

        while (!isSolvable() || isPuzzleResolved()) {
            shuffleTiles();
        }

        renderTiles();
    }

    private void renderTiles() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.tiles.add(this.puzzles.get(idx(i, j)), j, i);
            }
        }
    }

    private void shuffleTiles() {
        Random rand = new Random();

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                int newRow = rand.nextInt(this.rows);
                int newCol = rand.nextInt(this.cols);

                ImageView tmp = this.puzzles.get(idx(i, j));
                this.puzzles.put(idx(i, j), this.puzzles.get(idx(newRow, newCol)));
                this.puzzles.put(idx(newRow, newCol), tmp);

                if (this.currentRow == newRow && this.currentCol == newCol) {
                    this.currentRow = i;
                    this.currentCol = j;
                } else if (i == this.currentRow && j == this.currentCol) {
                    this.currentRow = newRow;
                    this.currentCol = newCol;
                }
            }
        }
    }

    private int getTileNumber(String id) {
        String[] rowAndCol = id.split("x");
        int row = Integer.parseInt(rowAndCol[0]) + 1;
        int col = Integer.parseInt(rowAndCol[1]) + 1;

        return (row * this.cols) - this.cols + col;
    }

    private int getInversionsCount(int[][] arr) {
        int invCount = 0;

        for (int i = 0; i < cols - 1; i++) {
            for (int j = i + 1; j < rows; j++) {
                if (arr[j][i] > 0 && arr[j][i] > arr[i][j]) {
                    invCount++;
                }
            }
        }

        return invCount;
    }

    /**
     * If the grid width is odd, then the number of inversions in a solvable situation is even.
     * If the grid width is even, and the blank is on an even row counting from the bottom (second-last, fourth-last etc), then the number of inversions in a solvable situation is odd.
     * If the grid width is even, and the blank is on an odd row counting from the bottom (last, third-last, fifth-last etc) then the number of inversions in a solvable situation is even.
     * @link https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
     */
    private boolean isSolvable() {
        int[][] puzzle = new int[rows][cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                puzzle[i][j] = getTileNumber(puzzles.get(idx(i, j)).getId());

                if (currentRow == i && currentCol == j) {
                    puzzle[i][j] = 0;
                }
            }
        }

        int invCount = getInversionsCount(puzzle);

        if (cols % 2 == 0) {
            return (invCount % 2 != 0);
        } else {
            return (invCount % 2 == 0);
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

    private String idx(int row, int col) {
        return row + "x" + col;
    }

    private ImageView getCurrent() {
        return this.puzzles.get(idx(this.currentRow, this.currentCol));
    }

    private void moveCurrentTileUp() {
        if (this.currentRow == 0) {
            return;
        }

        swapCurrentTile(currentRow - 1, currentCol);
    }

    private void moveCurrentTileDown() {
        if (this.currentRow == this.rows - 1) {
            return;
        }

        swapCurrentTile(currentRow + 1, currentCol);
    }

    private void moveCurrentTileLeft() {
        if (this.currentCol == 0) {
            return;
        }

        swapCurrentTile(currentRow, currentCol - 1);
    }

    private void moveCurrentTileRight() {
        if (this.currentCol == this.cols - 1) {
            return;
        }

        swapCurrentTile(currentRow, currentCol + 1);
    }

    private void swapCurrentTile(int newRow, int newCol) {
        String currentPosition = idx(currentRow, currentCol);
        String newPosition = idx(newRow, newCol);

        ImageView currentView = this.getCurrent();
        ImageView newView = this.puzzles.get(newPosition);

        this.puzzles.replace(currentPosition, newView);
        this.puzzles.replace(newPosition, currentView);

        this.tiles.getChildren().removeAll(currentView, newView);

        this.tiles.add(newView, currentCol, currentRow);
        this.tiles.add(currentView, newCol, newRow);

        this.currentCol = newCol;
        this.currentRow = newRow;
    }

    public void onKeyEvent(KeyEvent event) {
        if (event.getEventType() != KeyEvent.KEY_PRESSED) {
            return;
        }

        switch (event.getCode()) {
            case UP:
                moveCurrentTileUp();
                break;
            case DOWN:
                moveCurrentTileDown();
                break;
            case LEFT:
                moveCurrentTileLeft();
                break;
            case RIGHT:
                moveCurrentTileRight();
                break;
        }

        if (isPuzzleResolved()) {
            System.out.println("You won!");
        }
    }

    @Override
    public void stop() {
        spentTime.stop();
        timer.cancel();
    }
}
