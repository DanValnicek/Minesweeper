package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

public class Game {
	private static final AudioClip explosionSound = new AudioClip(new File(Main.getConfigurationHandler().getConfiguration().getString("explosion_effect_path")).toURI().toString());
	private static final AudioClip defuseSound = new AudioClip(new File(Main.getConfigurationHandler().getConfiguration().getString("defusion_effect_path")).toURI().toString());
	static boolean isRunning = false;
	private @Getter
	static int numOfMines;
	private static int numOfRows;
	private static int numOfColumns;
	public GridPane gridPane;
	VBox root = new VBox();
	int numOfMarked;
	int[][] map;

	GameBar gameBar;
	long startTime;
	int[] minePositions;
	int emptySquares;
	private static Timeline timeline;
	private @Getter
	static Square[][] squares;

	public Game(int numOfMines, int numOfRows, int numOfColumns, boolean resize) throws IOException {

		Game.numOfMines = numOfMines;
		Game.numOfRows = numOfRows;
		Game.numOfColumns = numOfColumns;

		emptySquares = numOfColumns * numOfRows - numOfMines;
		ScrollPane scrollPane = new ScrollPane();
		gridPane = new GridPane();
		scrollPane.setContent(gridPane);
		scrollPane.pannableProperty().set(true);
		scrollPane.fitToHeightProperty().set(true);
		scrollPane.fitToWidthProperty().set(true);
		scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.4), CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(backgroundFill);
		gridPane.setBackground(background);
		gridPane.setHgap(.5);
		gridPane.setVgap(.5);
		minePositions = MapGenerator.randMinesGen(numOfRows * numOfColumns, numOfMines, -1, numOfColumns);
		map = MapGenerator.MapGen(numOfRows, numOfColumns, minePositions);
		gameBar = new GameBar(numOfMines);
		squares = new Square[numOfRows][numOfColumns];
		generateSquares(squares, numOfColumns, numOfRows, map);
		root.getChildren().addAll(gameBar.getAnchorPane(), scrollPane);
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			gameBar.setTimer((System.currentTimeMillis() - startTime) / 1000);
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		explosionSound.setVolume(Main.getConfigurationHandler().getConfiguration().getDouble("Volume"));
		defuseSound.setVolume(Main.getConfigurationHandler().getConfiguration().getDouble("Volume"));
		Launcher.sceneSwitch(root, true, 291, 340, resize);
	}

	public static void gameOver() {
		isRunning = false;
		timeline.stop();
		squares = null;
		System.gc();
	}

	public static void restart() throws IOException {
		gameOver();
		Launcher.getMenuScene().getStackPane().getChildren().remove(Launcher.getMenuScene().getStackPane().getChildren().size() - 1);
		GameSettings.newGame(numOfMines, numOfColumns, numOfRows, false);
	}

	public VBox getRoot() {
		return root;
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
				squares[y][x].value = map[y][x];
			}
		}
	}

	public GridPane getGridPane() {
		return gridPane;
	}

	public void startGame() {
		startTime = System.currentTimeMillis();
		timeline.play();
		isRunning = true;
	}

	public void setNumOfMarked(boolean add) {
		if (add) {
			this.numOfMarked++;
		} else {
			this.numOfMarked--;
		}
		GameBar.mineCount.setText(Integer.toString(numOfMines - numOfMarked));
	}

	public static void playDefuseSound() {
		defuseSound.play();
	}

	public static void playExplosionSound() {
		explosionSound.play();
	}
}

