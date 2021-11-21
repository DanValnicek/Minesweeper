package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import servercomm.Client;

import java.util.concurrent.CompletableFuture;

public class Main extends Application {
//	public static Image flag = new Image("/MonkaS.gif", true);
	public static Image flag;


	//	public static Image flag = new Image("C:\\Users\\danva\\IdeaProjects\\Minesweeper\\src\\main\\resources\\MonkaS.gif", true);
//	public static Image mine = new Image("/jebaited.png", true);
	public static Image mine;
	//	public static Image mine = new Image("C:\\Users\\danva\\IdeaProjects\\Minesweeper\\src\\main\\resources\\jebaited.png", true);
	public static Stage firstStage;
	public static Client client;
	public static StackPane stackPane;
	public static Scene rootScene;

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
		NotificationOverlay.init();
		Menu.init();
		stackPane.getChildren().add(NotificationOverlay.notifOverlay);
		rootScene = new Scene(stackPane);
		rootScene.getStylesheets().add("/style.css");
		primaryStage.setScene(rootScene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
}
