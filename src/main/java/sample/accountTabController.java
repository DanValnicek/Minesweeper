package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class accountTabController extends AppSubScene implements Initializable {
	@FXML
	Label usernameLabel;
	@FXML
	Label logOutLabel;

	public static void init() throws IOException {
		init("/resources/accountTab.fxml");

	}

	public void playOnClickEvent(MouseEvent event) throws IOException, InterruptedException {
		super.playOnClickEvent(event);

	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		usernameLabel.setText("Logged in as " + Main.getConfigurationHandler().getConfiguration().getString("username"));
	}
}
