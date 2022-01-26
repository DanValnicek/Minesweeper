package sample;


import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.List;

public class MultiplayerGameBar extends GameBar {

	private static final Label deathCounter = new Label(("N/A"));
	private final Label userCount = new Label("1");

	public MultiplayerGameBar(int bombCount) throws IOException {
		super(bombCount, false);
		backButton.setOnMouseClicked(mouseEvent -> {
			try {
				Main.client.sendMessage(JsonGenerator.generateRequest("iLeaveGame", List.of(MultiplayerGame.uuid)).toJSONString());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Game.isRunning = false;
			Launcher.previousScene();
			System.gc();
		});
		userCount.setFont(Font.font("Impact", 15));
		userCount.setTextFill(Color.YELLOW);
		AnchorPane.setLeftAnchor(userCount, 85.0);
		AnchorPane.setTopAnchor(userCount, 5.0);
		deathCounter.setTextFill(Color.ORANGE);
		deathCounter.setFont(Font.font("Impact", 15));
		AnchorPane.setTopAnchor(deathCounter, 5.0);
		AnchorPane.setRightAnchor(deathCounter, 60.0);
		getAnchorPane().getChildren().addAll(deathCounter, userCount);
	}

	public static void setDeathCounter(long currentTime) {
		if (currentTime < 0) {
			((MultiplayerGame) Main.game).countDown.stop();
			deathCounter.setText("N/A");
			return;
		}
		deathCounter.setText(currentTime / 60 + ((currentTime % 60) < 10 ? ":0" : ":") + currentTime % 60);
	}

	public void setUserCount(int userCounter) {
		userCount.setText(Integer.toString(userCounter));
	}
}
