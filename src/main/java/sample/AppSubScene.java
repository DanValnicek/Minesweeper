package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
@FXML
public void exitApplication(ActionEvent event){
	Main.client.disconnect();
	Platform.exit();
}
public void stop(){
	System.out.println("disconnecting");
	Main.client.disconnect();
}
	public static void rotateBackground() {
		Circle background = (Circle) scene.lookup("#background");
		RotateTransition rotateTransition = new RotateTransition(Duration.millis(30000), background);
		rotateTransition.setByAngle(360);
		rotateTransition.setCycleCount(Animation.INDEFINITE);
		rotateTransition.setInterpolator(Interpolator.LINEAR);
		Platform.runLater(rotateTransition::play);
	}

	protected static SubScene init(String fxml) throws IOException {
		scene = new SubScene(FXMLLoader.load(Objects.requireNonNull(getResource(fxml))), 525, 269);
		scene.setViewOrder(1);
		rotateBackground();
		System.out.println(scene);
		return scene;
	}

	@FXML
	public void playOnClickEvent(MouseEvent event) throws IOException {
		String id = ((Control) event.getSource()).getId();
		if (id.endsWith("Tab")) {
			Launcher.sceneSwitch(init("/" + id + ".fxml"));
		} else if (id.startsWith("back")) {
			Launcher.previousScene();
		}
	}
}
