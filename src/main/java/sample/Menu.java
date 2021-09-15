package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Menu {
	static Scene menuScene;
	static Game game;

	public static void init() throws IOException {
		menuScene = new Scene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/menu.fxml"))));
		menuScene.getStylesheets().add(Objects.requireNonNull(Menu.class.getResource("/style.css")).toExternalForm());
		rotateBackground(menuScene);
		Main.getFirstStage().setResizable(false);
		Main.getFirstStage().setTitle("Minesweeper");
		Main.getFirstStage().setScene(menuScene);
		Main.getFirstStage().sizeToScene();
		Main.getFirstStage().show();
	}

	public static void rotateBackground(Scene scene) {
		Circle background = (Circle) scene.lookup("#background");
		RotateTransition rotateTransition = new RotateTransition(Duration.millis(30000), background);
		rotateTransition.setByAngle(360);
		rotateTransition.setCycleCount(Animation.INDEFINITE);
		rotateTransition.setInterpolator(Interpolator.LINEAR);
		rotateTransition.play();
	}

	@FXML
	public void playOnClickEvent(MouseEvent event) {
		System.out.println(((Control) event.getSource()).getId());
		try {
			switch (((Control) event.getSource()).getId()) {
				case "playButton":
					game = new Game(100, 30, 30);
					Stage gameWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
					gameWindow.setScene(Game.getScene());
					break;
				case "accountButton":
					accountTab.init();
					break;
				case "howToPlayButton":
					break;
				case "settingsButton":
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
