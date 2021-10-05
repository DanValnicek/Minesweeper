package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;

import static sample.GameSettings.game;

public class GameBar {
	public Label timer = new Label();
	static Label mineCount = new Label();
	AnchorPane anchorPane = new AnchorPane();

	public GameBar( int bombCount) {
		Label reset = new Label("Reset");
		reset.setFont(Font.font("Impact", 15));
		reset.setTextFill(Color.RED);
		reset.setOnMouseClicked(mouseEvent -> {
			try {
				Game.gameOver();
				GameSettings.newGame();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        anchorPane.setTopAnchor(reset,5.0);
        anchorPane.setRightAnchor(reset, 40.0);
		BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.2), CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(backgroundFill);
		anchorPane.setBackground(background);
		mineCount.setFont(Font.font("Impact", 15));
		mineCount.setText(Integer.toString(bombCount));
		mineCount.setTextFill(Color.YELLOW);
		anchorPane.setLeftAnchor(mineCount, 5.0);
		anchorPane.setTopAnchor(mineCount, 5.0);
		timer.setText("0:00");
		timer.setFont(Font.font("Impact", 15));
		timer.setTextFill(Color.YELLOW);
		anchorPane.setRightAnchor(timer, 5.0);
		anchorPane.setTopAnchor(timer, 5.0);
		anchorPane.setBottomAnchor(timer, 3.0);
		anchorPane.getChildren().addAll(mineCount, timer,reset);
	}

	//TODO: finish gamebar

	public void setTimer(long currentTime) {
		timer.setText(currentTime / 60 + ":" + currentTime % 60);
	}

	public AnchorPane getAnchorPane() {
		return anchorPane;
	}
}
