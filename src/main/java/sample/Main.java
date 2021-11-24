package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import servercomm.Client;

import java.util.concurrent.CompletableFuture;

public class Main extends Application {
	public static Image flag;
	public static Image mine ;
	public static Stage firstStage;
	public static Client client;
	public static StackPane stackPane;
	public static Scene rootScene;
	public  NotificationOverlay overlay;

	public static void main(String[] args) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		launch(args);
	}

	public static Stage getFirstStage() {
		return firstStage;
	}

	public static void setFirstStage(Stage firstStage) {
		Main.firstStage = firstStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		flag = new Image(getClass().getResource("/MonkaS.gif").toExternalForm(),true);
		mine = new Image(getClass().getResource("/jebaited.png").toExternalForm(),true);
		CompletableFuture.runAsync(() -> {
					if (client == null) {
						try {
							client = new Client("165.22.76.230", 56850);
							client.run();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
		);
		firstStage = primaryStage;
		stackPane = new StackPane();
		overlay = new NotificationOverlay();
		NotificationOverlay.init();
		Menu.init();
//		sample.Scene.init("/menuTab.fxml");
		stackPane.getChildren().add(NotificationOverlay.notifOverlay);
		rootScene = new Scene(stackPane);
		rootScene.getStylesheets().add("/style.css");
		primaryStage.setScene(rootScene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

}
