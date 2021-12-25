package sample;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.StackPane;
import servercomm.MessageTypes;

import java.io.IOException;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class SceneWithOverlay implements Overlay {
	private static final String overlayPath = "/NotificationOverlay.fxml";
	private final SubScene subscene;
	private final StackPane stackPane;
	private final Scene rootScene;
	private final NotifOverlay overlay;

	public SceneWithOverlay(String scenePath) throws IOException {
		stackPane = new StackPane();
		subscene = AppSubScene.init(scenePath);
		System.out.println(getResource(overlayPath));
		overlay = new NotifOverlay();
		overlay.getGroup().setViewOrder(-1);
		stackPane.getChildren().addAll(subscene, overlay.getGroup());
		StackPane.setAlignment(overlay.getGroup(), Pos.BOTTOM_LEFT);
		rootScene = new Scene(stackPane);

	}

	public NotifOverlay getOverlay() {
		return overlay;
	}

	public Scene getRootScene() {
		return rootScene;
	}

	public SubScene getOverlayedScene() {
		return subscene;
	}

	public void setSubScene(Node subScene) {
//		subScene.setViewOrder(1);
		stackPane.getChildren().add(subScene);
	}

	@Override
	public void displayMessage(String message, int timeout) throws IOException {
		System.out.println("Message: " + message);
		Launcher.getFirstStage().show();
		System.out.println(stackPane.getChildren());
		overlay.showMessage(MessageTypes.n, message, timeout);
	}

	public StackPane getStackPane() {
		return stackPane;
	}
}


