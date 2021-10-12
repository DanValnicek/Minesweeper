package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Menu {
	static Scene menuScene;

public Menu(){

}
	public static void init() throws IOException {
		menuScene = new Scene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/menu.fxml"))));
		sceneInit(menuScene);
	}

	static void sceneInit(Scene scene) {
		scene.getStylesheets().add(Menu.class.getResource("/style.css").toExternalForm());
		rotateBackground(scene);
		Main.getFirstStage().setResizable(false);
		Main.getFirstStage().setTitle("Minesweeper");
		Main.getFirstStage().setScene(scene);
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
					sceneInit(new Scene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/gameSettings.fxml")))));
//					new GameSettingsInitializer(30,30,225);
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
