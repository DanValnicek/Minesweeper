package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

public class Game {
	static int numOfMines;
	static int numOfRows;
	static int numOfColumns;
	static Timeline timeline;
	static Square[][] squares;
	public GridPane gridPane;
	VBox root = new VBox();
	Scene scene = new Scene(root);
	int numOfMarked;
	int[][] map;
	GameBar gameBar;
	long startTime;
	int[] minePositions;
	int emptySquares;

	public Game(int numOfMines, int numOfRows, int numOfColumns) throws IOException {


		Game.numOfMines = numOfMines;
		Game.numOfRows = numOfRows;
		Game.numOfColumns = numOfColumns;

		emptySquares = numOfColumns * numOfRows - numOfMines;
		gridPane = new GridPane();
		BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.3), CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(backgroundFill);
		gridPane.setBackground(background);
		gridPane.setHgap(.5);
		gridPane.setVgap(.5);
		minePositions = MapGenerator.randMinesGen(numOfRows * numOfColumns, numOfMines, -1, numOfColumns);
		map = MapGenerator.MapGen(numOfRows, numOfColumns, minePositions);
		gameBar = new GameBar(numOfMines);
		squares = new Square[numOfRows][numOfColumns];
		generateSquares(squares, numOfColumns, numOfRows, map);
		root.getChildren().addAll(gameBar.getAnchorPane(), gridPane);
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			gameBar.setTimer((System.currentTimeMillis() - startTime) / 1000);
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		Main.getFirstStage().setScene(scene);
	}

	public static void gameOver() {
		timeline.stop();
		squares = null;
		System.gc();

	}

	public static void restart() throws IOException {
		gameOver();

		GameSettings.newGame(numOfMines, numOfColumns, numOfRows);
	}

	public VBox getRoot() {
		return root;
	}

	public Scene getScene() {
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

	public void reGenerateSquares(int forbiddenX, int forbiddenY) {
		minePositions = MapGenerator.randMinesGen(numOfRows * numOfColumns, numOfMines, ((forbiddenY) * numOfColumns + forbiddenX), numOfColumns);
		map = MapGenerator.MapGen(numOfRows, numOfColumns, minePositions);
		for (int y = 0; y < numOfRows; y++) {
			for (int x = 0; x < numOfColumns; x++) {
				squares[y][x].changeValues(map[y][x]);
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

