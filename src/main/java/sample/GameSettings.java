package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameSettings implements Initializable {
	static Game game;
	@FXML
	private Spinner<Integer> percentOfMines;
	@FXML
	private ToggleGroup difficulty;
	@FXML
	private ToggleGroup size;
	@FXML
	private Spinner<Integer> width;
	@FXML
	private Spinner<Integer> height;
	@FXML
	private Spinner<Integer> mineCount;
	@FXML
	private ToggleButton custom;

	public static void newGame(int mineCount, int width, int height) throws IOException {
		game = new Game(mineCount, height, width);
	}

	@FXML
	private void selectDifficulty() {
		ToggleButton selectedButton = (ToggleButton) difficulty.getSelectedToggle();
		System.out.println(selectedButton.getId());
		switch (selectedButton.getId()) {
			case "easyDiff":
				mineCount.getValueFactory().setValue((int) Math.floor(width.getValue() * height.getValue() * 0.25));
				break;
			case "mediumDiff":
				mineCount.getValueFactory().setValue((int) Math.floor(width.getValue() * height.getValue() * 0.50));
				break;
			case "hardDiff":
				mineCount.getValueFactory().setValue((int) Math.floor(width.getValue() * height.getValue() * 0.75));
				break;
			case "custom":
				mineCount.getValueFactory().setValue((int) Math.floor(width.getValue() * height.getValue() * percentOfMines.getValue() / 100));
				System.out.println(percentOfMines.getValue());
				break;

		}
	}

	@FXML
	private void selectSize() {
		ToggleButton selectedButton = (ToggleButton) size.getSelectedToggle();
		System.out.println(selectedButton.getId());
		switch (selectedButton.getId()) {
			case "smallSize":
				width.getValueFactory().setValue(30);
				height.getValueFactory().setValue(30);
				break;
			case "mediumSize":
				width.getValueFactory().setValue(50);
				height.getValueFactory().setValue(50);
				break;
			case "bigSize":
				width.getValueFactory().setValue(70);
				height.getValueFactory().setValue(70);
				break;
		}
		selectDifficulty();
	}

	@FXML
	public void playOnClickEvent(MouseEvent event) throws IOException {
		System.out.println(((Control) event.getSource()).getId());
			switch (((Control) event.getSource()).getId()) {
				case "playButton":
					newGame(mineCount.getValue(), width.getValue(), height.getValue());
					break;
				case "backButton":
					Menu.init();
					break;
			}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		selectSize();
		difficulty.selectedToggleProperty().addListener(((observableValue, oldVal, newVal) -> {
			if (newVal == null)
				oldVal.setSelected(true);
		}));
		percentOfMines.getEditor().textProperty().addListener((observableValue, oldVal, newVal) -> {
			if (newVal != null) {
				custom.setSelected(true);
					percentOfMines.increment(0);
					selectDifficulty();
			}
		});
		width.getEditor().textProperty().addListener((observableValue, oldVal, newVal) -> {
			if (newVal.length() > 1) {
					width.increment(0);
					selectDifficulty();
			}
		});
		height.getEditor().textProperty().addListener((observableValue, oldVal, newVal) -> {
			System.out.println(newVal);
			if (newVal.length() > 1) {
					height.increment(0);
					selectDifficulty();
			}
		});
		mineCount.getEditor().textProperty().addListener((observableValue, oldVal, newVal) -> {
			if (newVal != null) {
					width.increment(0);
			}
		});
	}
}
