package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public abstract class AppSubScene {

	private static javafx.scene.SubScene scene;

	public static void rotateBackground() {
		Circle background = (Circle) scene.lookup("#background");
		RotateTransition rotateTransition = new RotateTransition(Duration.millis(30000), background);
		rotateTransition.setByAngle(360);
		rotateTransition.setCycleCount(Animation.INDEFINITE);
		rotateTransition.setInterpolator(Interpolator.LINEAR);
		Platform.runLater(rotateTransition::play);
	}

	public static SubScene init(String fxml) throws IOException {
		scene = new SubScene(FXMLLoader.load(Objects.requireNonNull(getResource(fxml))), 525, 269);
		scene.setViewOrder(1);
		rotateBackground();
		System.out.println(scene);
		return scene;
	}

	@FXML
	public void playOnClickEvent(MouseEvent event) throws IOException, InterruptedException {
		String id = ((Control) event.getSource()).getId();
		if (id.equals("multiplayerGameTab")) {
			MultiplayerGame.joinToGame();
		} else if (id.endsWith("Tab")) {
			if (id.startsWith("menu")) {
				Launcher.newScene("/menuTab.fxml");
			}
			Launcher.sceneSwitch(init("/" + id + ".fxml"));
		} else if (id.startsWith("back")) {
			Launcher.previousScene();
		}
	}
}
