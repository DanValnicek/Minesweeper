package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Game {
    static VBox root = new VBox();
    static Scene scene = new Scene(root);
    final int numOfMines;
    final int numOfRows;
    final int numOfColumns;
    public GridPane gridPane;
    int numOfMarked;
    int[][] map;
    Timeline timeline;
    GameBar gameBar;
    long startTime;
    int[] minePositions;
    Stage stage;
    Square[][] squares;
    int emptySquares;




    public Game(int numOfMines, int numOfRows, int numOfColumns) throws IOException {

        emptySquares = numOfColumns * numOfRows - numOfMines;
        this.numOfMines = numOfMines;
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;

        gridPane = new GridPane();
        BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.3), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        gridPane.setBackground(background);
        gridPane.setHgap(.5);
        gridPane.setVgap(.5);
        minePositions = MapGenerator.randMinesGen(numOfRows * numOfColumns, numOfMines);
        map = MapGenerator.MapGen(numOfRows, numOfColumns, minePositions);
        gameBar = new GameBar(root, numOfMines);
        squares = new Square[numOfRows][numOfColumns];
        generateSquares(squares, numOfColumns, numOfRows, map);
        root.getChildren().addAll(gameBar.getAnchorPane(), gridPane);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            GameBar.setTimer((System.currentTimeMillis() - startTime) / 1000);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public static Scene getScene() {
        return scene;
    }

    private void generateSquares(Square[][] squares, int numOfColumns, int numOfRows, int[][] map) {
        for (int y = 0; y < numOfRows; y++) {
            for (int x = 0; x < numOfColumns; x++) {
                squares[y][x] = new Square(map[y][x]);
                squares[y][x].generateRectangle(x, y);
                gridPane.add(squares[y][x].rectangle, x, y);
            }
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public void startGame() {
        startTime = System.currentTimeMillis();
        timeline.play();
    }

    public void setNumOfMarked(boolean add) {
        if (add) {
            this.numOfMarked++;
        } else {
            this.numOfMarked--;
        }
        GameBar.mineCount.setText(Integer.toString(numOfMines - numOfMarked));
    }
}
