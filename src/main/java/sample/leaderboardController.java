package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class leaderboardController extends AppSubScene implements Initializable {
	@FXML VBox contentVbox;

	@Override public void initialize(URL url, ResourceBundle resourceBundle) {
		contentVbox.getChildren().addAll(Launcher.getTableView());
	}
}
