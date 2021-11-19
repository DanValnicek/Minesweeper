package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Menu {
	static SubScene menuScene;

	public Menu() {

	}

	public static void init() throws IOException {
		menuScene = new SubScene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/menu.fxml"))), 525, 269);
		sceneInit(menuScene);
	}

	static EventHandler<? super MouseEvent> sceneInit(SubScene scene) {
		scene.setViewOrder(1);
		rotateBackground(scene);
		Main.getFirstStage().setResizable(false);
		Main.getFirstStage().setTitle("Minesweeper");
		Main.stackPane.getChildren().add(scene);

		return null;
	}

	public static void rotateBackground(SubScene scene) {
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
					sceneInit(new SubScene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/gameSettings.fxml"))), 525, 269));
					break;
				case "accountButton":
					sceneInit(new SubScene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/accountTab.fxml"))), 525, 269));
					break;
				case "howToPlayButton":
					break;
				case "settingsButton":
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
