package sample;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class Launcher {
	private static Stage firstStage;
	private static SceneWithOverlay menuScene;
	private @Getter @Setter static TableView tableView;

	public static SceneWithOverlay getMenuScene() {
		return menuScene;
	}

	public static Stage getFirstStage() {
		return firstStage;
	}

	public static void start(Stage primaryStage, SceneWithOverlay sceneWithOverlay) {
		firstStage = primaryStage;
		menuScene = sceneWithOverlay;
		sceneWithOverlay.getRootScene().getStylesheets().add("/style.css");
		primaryStage.setScene(sceneWithOverlay.getRootScene());
		primaryStage.sizeToScene();
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Minesweeper");
		primaryStage.setOnCloseRequest(windowEvent -> {
			Main.client.disconnect();
			System.out.println("Shutting down");
			Platform.exit();
			System.exit(0);
		});
	}

	public static void newScene(String scenePath) throws IOException {
		menuScene = new SceneWithOverlay(scenePath);
		firstStage.setScene(menuScene.getRootScene());
		firstStage.sizeToScene();
		firstStage.setResizable(false);
		firstStage.setMinHeight(269);
	}

	public static void sceneSwitch(Node subScene) throws IOException {
		menuScene.setSubScene(subScene);
		menuScene.displayMessage("idk", 5);
	}

	public static void sceneSwitch(Node subScene, boolean resizable, double minWidth, double minHeight, boolean resize) {
		menuScene.setSubScene(subScene);
		if (resize) {
			firstStage.setResizable(resizable);
			firstStage.show();
			firstStage.sizeToScene();
			firstStage.setMaxHeight(firstStage.getHeight());
			firstStage.setMaxWidth(firstStage.getWidth());
			firstStage.setMinHeight(minHeight);
			firstStage.setMinWidth(minWidth);
		}
	}

	public static void previousScene() {
		System.out.println(Launcher.getFirstStage());
		System.out.println(menuScene);
		menuScene.getStackPane().getChildren().remove(menuScene.getStackPane().getChildren().size() - 1);
		firstStage.setResizable(false);
		firstStage.show();
		firstStage.sizeToScene();
		firstStage.setMinHeight(firstStage.getHeight());
		firstStage.setMinWidth(firstStage.getWidth());
	}
}
