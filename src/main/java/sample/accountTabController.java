package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class accountTabController extends AppSubScene implements Initializable {
	@FXML Label usernameLabel;
	@FXML TableView playersGamesTable = Launcher.getTableView();
	@FXML TableColumn durationColumn;
	@FXML TableColumn mapSizeColumn;
	@FXML TableColumn difficultyColumn;
	@FXML TableColumn mineCountColumn;

	public static void init() throws IOException {
		init("/resources/accountTab.fxml");

	}

	@Override
	public void playOnClickEvent(MouseEvent event) throws IOException, InterruptedException {

		String id = ((Control) event.getSource()).getId();
		if (id.equals("logOutButton")) {
			Main.client.logOut();
			Launcher.previousScene();
			return;
		}
		super.playOnClickEvent(event);
	}


	@SneakyThrows @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		usernameLabel.setText("Ahoj " + Main.getConfigurationHandler().getConfiguration().getString("username"));
		Main.client.sendMessage(JsonGenerator.generateRequest("iReturnGameHistory").toJSONString());
	}
}
