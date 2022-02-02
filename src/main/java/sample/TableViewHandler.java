package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TableViewHandler {
	JSONObject queryData;

	@Getter TableView<GameData> dataTableView = new TableView<>();

	public TableViewHandler(JSONObject queryData, TableType tableType) {
		this.queryData = queryData;
//		if (tableType == TableType.gameHistory) {
		JSONArray sizes = (JSONArray) queryData.get("size");
		JSONArray mineCounts = (JSONArray) queryData.get("mine_count");
		JSONArray durations = (JSONArray) queryData.get("duration_milis");
		TableColumn<GameData, String> mapSizeColumn = new TableColumn<>("Čas");
		mapSizeColumn.setCellValueFactory(new PropertyValueFactory<>("duration_milis"));

		TableColumn<GameData, String> mineCountColumn = new TableColumn<>("Veľkosť mapy");
		mineCountColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

		TableColumn<GameData, String> difficultyColumn = new TableColumn<>("Obtiažnosť %");
		difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

		TableColumn<GameData, String> durationColumn = new TableColumn<>("Čas");
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		dataTableView.getColumns().addAll(difficultyColumn, mapSizeColumn, mineCountColumn, durationColumn);
		GameData[] dataList = new GameData[sizes.size()];
		for (int i = 0; i < sizes.size(); i++) {
			dataList[i].setSize(Math.toIntExact((Long) sizes.get(i)));
			dataList[i].setDuration((Long) durations.get(i));
			dataList[i].setMineCount(Math.toIntExact((Long) mineCounts.get(i)));
		}
		ObservableList<GameData> items = FXCollections.observableArrayList(dataList);
		dataTableView.setItems(items);
//		}
		System.out.println(queryData);
	}

	public enum TableType {
		gameHistory,
		leaderBoard
	}
}
