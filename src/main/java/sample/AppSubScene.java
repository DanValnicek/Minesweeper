package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
		rotateTransition.play();
	}

	protected static void init(String fxml) throws IOException {

		System.out.println(Main.stackPane.getChildren());
		System.out.println(fxml);
		scene = new javafx.scene.SubScene(FXMLLoader.load(Objects.requireNonNull(getResource(fxml))), 525, 269);
		scene.setViewOrder(1);
		rotateBackground();
		Main.getFirstStage().setResizable(false);
		Main.getFirstStage().setTitle("Minesweeper");
		Main.stackPane.getChildren().add(scene);
	}


	@FXML
	public void playOnClickEvent(MouseEvent event) throws IOException {
		String id = ((Control) event.getSource()).getId();
		System.out.println(id);
		if (id.endsWith("Tab")) {
			init("/" + id + ".fxml");
		} else if (id.startsWith("back")) {
			Main.stackPane.getChildren().remove(Main.stackPane.getChildren().size()-1);
		}
	}
}
