package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class accountTabController extends AppSubScene implements Initializable {
	@FXML Label usernameLabel;
	@FXML TableView playersGamesTable;
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


	private void setupTable(JSONObject data) {
		TableColumn<Map, String> mapSizeColumn = new TableColumn<>("firstName");
		mapSizeColumn.setCellValueFactory(new MapValueFactory<>("firstName"));

		TableColumn<Map, String> lastNameColumn = new TableColumn<>("lastName");
		lastNameColumn.setCellValueFactory(new MapValueFactory<>("lastName"));

		playersGamesTable.getColumns().add(mapSizeColumn);
		playersGamesTable.getColumns().add(lastNameColumn);


		ObservableList<Map<String, Object>> items =
				FXCollections.observableArrayList();

		Map<String, Object> item1 = new HashMap<>();
		item1.put("firstName", "Randall");
		item1.put("lastName", "Kovic");

		items.add(item1);

		Map<String, Object> item2 = new HashMap<>();
		item2.put("firstName", "Irmelin");
		item2.put("lastName", "Satoshi");

		items.add(item2);

		playersGamesTable.getItems().addAll(items);
	}

	@SneakyThrows @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		usernameLabel.setText("Ahoj " + Main.getConfigurationHandler().getConfiguration().getString("username"));
		Main.client.sendMessage(JsonGenerator.generateRequest("iReturnGameHistory").toJSONString());
	}
}
