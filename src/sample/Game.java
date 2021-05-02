package sample;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {
    int numOfMines;
    int numOfRows;
    int numOfColumns;
    int numOfMarked;
    int map[][];
    public Game game;
    public Game(int numOfMines, int numOfRows, int numOfColumns, VBox root, Stage primaryStage) {
        this.numOfMines = numOfMines;
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
        map = MapGenerator.MapGen(numOfRows, numOfColumns, MapGenerator.randMinesGen(numOfRows * numOfColumns, numOfMines));
        GameBar gameBar = new GameBar(root, numOfMines);
        Square.generateSquares(primaryStage, root, numOfColumns, numOfRows, map);
    }

    public void setNumOfMarked(int numOfMarked) {
        this.numOfMarked = numOfMarked;
        GameBar.mineCount.setText(Integer.toString(numOfMarked));
    }
}
