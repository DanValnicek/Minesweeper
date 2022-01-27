package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import servercomm.MessageTypes;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MultiplayerGame extends Game {
	protected static UUID uuid;
	Timeline countDown;

	@SneakyThrows
	public MultiplayerGame(JSONArray minePositions, int rowCount, int columnCount, UUID uuid) throws IOException {
		if (Launcher.getMenuScene().getStackPane().getChildren().size() > 3) {
			Launcher.getMenuScene().getStackPane().getChildren().remove(Launcher.getMenuScene().getStackPane().getChildren().size() - 1);
		}
		MultiplayerGame.uuid = uuid;
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
		Main.client.sendMessage(JsonGenerator.generateRequest("iSendPlayerCount", List.of(uuid.toString())).toJSONString());
	}

	public static void joinToGame() throws IOException, InterruptedException {
		if (!Main.client.isLoggedIn()) {
			Launcher.sceneSwitch(AppSubScene.init("/loginTab.fxml"));
			return;
		}
		Main.client.sendMessage(JsonGenerator.generateRequest("iJoinToGame").toString());
	}

	@SneakyThrows
	public void gameOver() {
		super.gameOver();
		if (emptySquares == 0) {
			Main.client.sendMessage(JsonGenerator.generateRequest("iReportFinishedMap", List.of(uuid.toString())).toJSONString());
		} else {
			Main.client.sendMessage(JsonGenerator.generateRequest("iLostGame", List.of(uuid.toString())).toJSONString());
		}
		gridPane.setMouseTransparent(true);
			uuid = null;
		Main.game = null;
	}

	public void startCountDown(int duration) {
		long endTime = System.currentTimeMillis() + duration * 1000L;
		countDown = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			MultiplayerGameBar.setDeathCounter((endTime - System.currentTimeMillis()) / 1000);
		}));
		countDown.setCycleCount(duration);
		countDown.play();
	}

	public void startGame() {
		gridPane.setMouseTransparent(false);
		super.startGame();
	}

	public void eliminated(String message) {
		if (countDown != null) {
			countDown.stop();
		}
		Launcher.getMenuScene().getOverlay().showMessage(MessageTypes.g, message, 10);
		Game.isRunning = false;
		System.gc();
	}

}
