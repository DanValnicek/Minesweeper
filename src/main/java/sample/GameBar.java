package sample;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Objects;

public class GameBar {
	static Label mineCount = new Label();
	public Label timer = new Label();
	AnchorPane anchorPane = new AnchorPane();

	public GameBar(int bombCount) throws IOException {
		Label reset = new Label("Reset");
		reset.setFont(Font.font("Impact", 15));
		reset.setTextFill(Color.RED);
		reset.setOnMouseClicked(mouseEvent -> {
			try {
				Game.restart();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		AnchorPane.setTopAnchor(reset, 5.0);
		AnchorPane.setRightAnchor(reset, 40.0);
		Label backButton = new Label("Back");
		backButton.setFont(Font.font("Impact", 15));
		backButton.setTextFill(Color.RED);
		backButton.setOnMouseClicked(mouseEvent -> {
			try {
				Main.getFirstStage().setScene(Main.rootScene);
				Menu.sceneInit(new SubScene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/gameSettings.fxml"))),525,269));
				System.gc();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		AnchorPane.setTopAnchor(backButton, 5.0);
		AnchorPane.setLeftAnchor(backButton, 40.0);
		BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.2), CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(backgroundFill);
		anchorPane.setBackground(background);
		mineCount.setFont(Font.font("Impact", 15));
		mineCount.setText(Integer.toString(bombCount));
		mineCount.setTextFill(Color.YELLOW);
		AnchorPane.setLeftAnchor(mineCount, 5.0);
		AnchorPane.setTopAnchor(mineCount, 5.0);
		timer.setText("0:00");
		timer.setFont(Font.font("Impact", 15));
		timer.setTextFill(Color.YELLOW);
		AnchorPane.setRightAnchor(timer, 5.0);
		AnchorPane.setTopAnchor(timer, 5.0);
		AnchorPane.setBottomAnchor(timer, 3.0);
		anchorPane.getChildren().addAll(mineCount, timer, reset, backButton);
	}

	//TODO: finish gamebar

	public void setTimer(long currentTime) {
		timer.setText(currentTime / 60 + ":" + currentTime % 60);
	}

	public AnchorPane getAnchorPane() {
		return anchorPane;
	}
}
