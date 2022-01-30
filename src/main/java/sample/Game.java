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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Game {
	private static final AudioClip explosionSound = new AudioClip(new File(Main.getConfigurationHandler().getConfiguration().getString("explosion_effect_path")).toURI().toString());
	private static final AudioClip defuseSound = new AudioClip(new File(Main.getConfigurationHandler().getConfiguration().getString("defusion_effect_path")).toURI().toString());
	protected @Getter
	static int numOfMines;
	protected static int numOfRows;
	protected static int numOfColumns;
	protected static Timeline timeline;
	protected @Getter
	static Square[][] squares;
	static boolean isRunning = false;
	public GridPane gridPane;
	VBox root = new VBox();
	int numOfMarked;
	int[][] map;
	@Getter GameBar gameBar;
	long startTime;
	int[] minePositions;
	int emptySquares;

	public Game(int numOfMines, int numOfRows, int numOfColumns, boolean resize) throws IOException {
		Game.numOfMines = numOfMines;
		Game.numOfRows = numOfRows;
		Game.numOfColumns = numOfColumns;

		gameBar = new GameBar(numOfMines, true);
		setupScene();
		emptySquares = numOfColumns * numOfRows - numOfMines;
		minePositions = MapGenerator.randMinesGen(numOfRows * numOfColumns, numOfMines, -1, numOfColumns);
		map = MapGenerator.MapGen(numOfRows, numOfColumns, minePositions);
		squares = new Square[numOfRows][numOfColumns];
		generateSquares(squares, numOfColumns, numOfRows, map);
		Launcher.sceneSwitch(root, true, 277, 329, resize);
	}

	public Game() {
	}

	public static void restart() throws IOException {
		Main.game.gameOver();
		Launcher.getMenuScene().getStackPane().getChildren().remove(Launcher.getMenuScene().getStackPane().getChildren().size() - 1);
		GameSettings.newGame(numOfMines, numOfColumns, numOfRows, false);
	}

	public static void playDefuseSound() {
		defuseSound.play();
	}

	public static void playExplosionSound() {
		explosionSound.play();
	}

	public void gameOver() {

		isRunning = false;
		timeline.stop();
		squares = null;
		if (emptySquares == 0) {
			try {
				Main.client.sendMessage(JsonGenerator.generateRequest("iSaveMap",
						List.of(numOfColumns * numOfRows, System.currentTimeMillis() - startTime,
								numOfMines, Arrays.stream(minePositions).boxed().collect(Collectors.toList()))
								.stream().map(Objects::toString).collect(Collectors.toList())).toJSONString());
				//map size,game time, number of mines, json of map
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.gc();
	}

	protected void setupScene() throws IOException {
		explosionSound.setVolume(Main.getConfigurationHandler().getConfiguration().getDouble("Volume"));
		defuseSound.setVolume(Main.getConfigurationHandler().getConfiguration().getDouble("Volume"));
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
		root.getChildren().addAll(gameBar.getAnchorPane(), scrollPane);
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			gameBar.setTimer((System.currentTimeMillis() - startTime) / 1000);
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
	}

	public VBox getRoot() {
		return root;
	}

	protected void generateSquares(Square[][] squares, int numOfColumns, int numOfRows, int[][] map) {
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
		GameBar.setMineCount(numOfMines - numOfMarked);
	}
}

