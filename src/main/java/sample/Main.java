package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.configuration.ConfigurationException;
import servercomm.Client;

import java.util.concurrent.CompletableFuture;

public class Main extends Application {
	public static Image flag;
	public static Image mine;
	public static Client client;
	static ConfigurationHandler configurationHandler;
	public NotifOverlay overlayController;

	CompletableFuture<Void> voidCompletableFuture;
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			client.disconnect();
			client = null;
			System.out.println("Shutting down");
//			System.exit(0);

		}));
		launch(args);
	}

	public static ConfigurationHandler getConfigurationHandler() {
		return configurationHandler;
	}
@FXML
public void exitApplication(ActionEvent event){

}
	public static void saveConfig() throws ConfigurationException {
		System.out.println(new Exception().getStackTrace()[0].getLineNumber());
		System.out.println(configurationHandler.getConfiguration().toString());

		try {
			configurationHandler.getConfiguration().save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		System.out.println();
//		System.out.println(configurationHandler.getBuilder().);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		flag = new Image(getClass().getResource("/MonkaS.gif").toExternalForm(), true);
		mine = new Image(getClass().getResource("/jebaited.png").toExternalForm(), true);
		configurationHandler = new ConfigurationHandler();
		voidCompletableFuture = CompletableFuture.runAsync(() -> {
					if (client == null) {
						try {
							client = new Client("165.22.76.230", 56850);
							client.connect();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
		);
		Launcher.start(primaryStage, new SceneWithOverlay("/menuTab.fxml"));
	}
}
