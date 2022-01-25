package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import servercomm.MessageTypes;

import java.io.IOException;
import java.util.List;

public class MultiplayerGame extends Game {
	//	public MultiplayerGame(int[] args) throws IOException {
////		super(args.get(0),args.get(1),args.get(2),true);
//		super(args[0], args[1], args[2], true);
//	}
	protected static long serverStartTime;
	Timeline countDown;

	public MultiplayerGame(JSONArray minePositions, int rowCount, int columnCount) throws IOException {
		int[] mines = new int[minePositions.size()];
		for (int i = 0; i < minePositions.size(); i++) {
			mines[i] = ((Long) minePositions.get(i)).intValue();
		}
		numOfMines = minePositions.size();
		numOfColumns = columnCount;
		numOfRows = rowCount;
		gameBar = new MultiplayerGameBar(numOfMines);
		setupScene();
		emptySquares = numOfColumns * numOfRows - numOfMines;
		map = MapGenerator.MapGen(numOfRows, numOfColumns, mines);
		squares = new Square[numOfRows][numOfColumns];
		generateSquares(squares, numOfColumns, numOfRows, map);
		gridPane.setMouseTransparent(true);
		Launcher.sceneSwitch(root, true, 277, 329, true);
	}

	public static void joinToGame() throws IOException, InterruptedException {
		if (!Main.client.isLoggedIn()) {
			Launcher.sceneSwitch(AppSubScene.init("/loginTab.fxml"));
			return;
		}
		Main.client.sendMessage(JsonGenerator.generateRequest("iJoinToGame").toString());
	}

	@SneakyThrows
	public static void gameOver() {
		Game.gameOver();
		Main.client.sendMessage(JsonGenerator.generateRequest("iReportFinishedMap",List.of(Long.toString(serverStartTime))).toJSONString());
	}

	public void startCountDown(int duration) {
		long endTime = System.currentTimeMillis() + duration * 1000L;
		countDown = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			MultiplayerGameBar.setDeathCounter((endTime - System.currentTimeMillis()) / 1000);
		}));
		countDown.setCycleCount(duration);
		countDown.play();
	}

	public void startGame(long serverStartTime) {
		this.serverStartTime = serverStartTime;
		gridPane.setMouseTransparent(false);
		super.startGame();
	}

	public void eliminated(String message) {
		countDown.stop();
		Launcher.getMenuScene().getOverlay().showMessage(MessageTypes.g, message, 10);
		Game.isRunning = false;
		Launcher.previousScene();
		System.gc();
	}

}
