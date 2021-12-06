package sample;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import servercomm.Client;

import java.util.concurrent.CompletableFuture;

public class Main extends Application {
	public static Image flag;
	public static Image mine;

	public static Client client;
	public NotifOverlay overlayController;

	public static void main(String[] args) {
		launch(args);
	}



	@Override
	public void start(Stage primaryStage) throws Exception {
		flag = new Image(getClass().getResource("/MonkaS.gif").toExternalForm(), true);
		mine = new Image(getClass().getResource("/jebaited.png").toExternalForm(), true);
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
		Launcher.start(primaryStage, new SceneWithOverlay("/menuTab.fxml"));
	}

}
