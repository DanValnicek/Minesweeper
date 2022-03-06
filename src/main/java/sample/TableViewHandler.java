package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public class TableViewHandler {
	JSONObject queryData;

	@Getter
	TableView<GameData> dataTableView = new TableView<>();
	@Getter TableView<PlayerData> playerDataTableView = new TableView<>();

	public TableViewHandler(JSONObject queryData) throws IOException {
		this.queryData = queryData;

		if (queryData.containsKey("size")) {
			JSONArray sizes = (JSONArray) queryData.get("size");
			JSONArray mineCounts = (JSONArray) queryData.get("mine_count");
			JSONArray durations = (JSONArray) queryData.get("duration_millis");
			TableColumn<GameData, String> durationColumn = new TableColumn<>("Čas");
			durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
			TableColumn<GameData, String> mapSizeColumn = new TableColumn<>("Veľkosť mapy");
			mapSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
			TableColumn<GameData, String> difficultyColumn = new TableColumn<>("Obťažnosť %");
			difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
			TableColumn<GameData, String> mineCountColumn = new TableColumn<>("Počet mín");
			mineCountColumn.setCellValueFactory(new PropertyValueFactory<>("mineCount"));
			dataTableView.getColumns().addAll(difficultyColumn, mapSizeColumn, mineCountColumn, durationColumn);
			GameData[] dataList = new GameData[sizes.size()];
			for (int i = 0; i < sizes.size(); i++) {
				dataList[i] = new GameData(
						(Long) sizes.get(i),
						(Long) mineCounts.get(i),
						(Long) durations.get(i));
			}
			ObservableList<GameData> items = FXCollections.observableArrayList(dataList);
			dataTableView.setItems(items);
//		Launcher.sceneSwitch(init("/accountTab.fxml"));
		} else if (queryData.containsKey("rank")) {

			JSONArray userName = (JSONArray) queryData.get("userName");
			JSONArray rank = (JSONArray) queryData.get("rank");
			JSONArray score = (JSONArray) queryData.get("score");
			JSONArray bestTime = (JSONArray) queryData.get("best_map_time");
			JSONArray highestDiff = (JSONArray) queryData.get("highest_difficulty");
			JSONArray mapsPlayed = (JSONArray) queryData.get("maps_played");
			TableColumn<PlayerData, String> userNameColumn = new TableColumn<>("Meno");
			userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
			TableColumn<PlayerData, String> bestTimeColumn = new TableColumn<>("Najlepší čas");
			bestTimeColumn.setCellValueFactory(new PropertyValueFactory<>("bestTime"));
			TableColumn<PlayerData, String> hardestMapColumn = new TableColumn<>("Najvyššia obťažnosť %");
			hardestMapColumn.setCellValueFactory(new PropertyValueFactory<>("hardestMap"));
			TableColumn<PlayerData, String> scoreColumn = new TableColumn<>("Skóre");
			scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
			TableColumn<PlayerData, String> rankColumn = new TableColumn<>("Poradie");
			rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
			TableColumn<PlayerData, String> mapsPlayedColumn = new TableColumn<>("Počet hraných máp");
			mapsPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("mapsPlayed"));
			playerDataTableView.getColumns().addAll(userNameColumn,rankColumn, scoreColumn, hardestMapColumn, bestTimeColumn, mapsPlayedColumn);
			PlayerData[] dataList = new PlayerData[rank.size()];
			for (int i = 0; i < rank.size(); i++) {
				dataList[i] = new PlayerData(
						(String) userName.get(i),
						(Long) rank.get(i),
						(Long) score.get(i),
						(Long) bestTime.get(i),
						(Long) highestDiff.get(i),
						(Long) mapsPlayed.get(i));
			}
			ObservableList<PlayerData> items = FXCollections.observableArrayList(dataList);
			playerDataTableView.setItems(items);
			playerDataTableView.getSortOrder().add(rankColumn);
//		Launcher.sceneSwitch(init("/accountTab.fxml"));
		}
		System.out.println(queryData);
	}

}
