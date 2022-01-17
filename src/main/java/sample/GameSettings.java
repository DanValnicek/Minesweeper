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
import java.util.Objects;
import java.util.ResourceBundle;

public class GameSettings extends AppSubScene implements Initializable {
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


	public static void newGame(int mineCount, int width, int height, boolean resize) throws IOException {
		game = new Game(mineCount, height, width, resize);
	}

	@FXML
	private void selectDifficulty() {
		if (difficulty.getSelectedToggle() != null) {
			ToggleButton selectedButton = (ToggleButton) difficulty.getSelectedToggle();
			switch (selectedButton.getId()) {
				case "easyDiff" -> mineCount.getValueFactory().setValue((int) Math.floor(width.getValue() * height.getValue() * 0.15));
				case "mediumDiff" -> mineCount.getValueFactory().setValue((int) Math.floor(width.getValue() * height.getValue() * 0.25));
				case "hardDiff" -> mineCount.getValueFactory().setValue((int) Math.floor(width.getValue() * height.getValue() * 0.35));
				case "custom" -> mineCount.getValueFactory().setValue((int) Math.floor(width.getValue() * height.getValue() * percentOfMines.getValue() / 100));
			}
		}
	}

	@FXML
	private void selectSize() {
		ToggleButton selectedButton = (ToggleButton) size.getSelectedToggle();
		switch (selectedButton.getId()) {
			case "smallSize" -> {
				width.getValueFactory().setValue(30);
				height.getValueFactory().setValue(30);
			}
			case "mediumSize" -> {
				width.getValueFactory().setValue(50);
				height.getValueFactory().setValue(50);
			}
			case "bigSize" -> {
				width.getValueFactory().setValue(70);
				height.getValueFactory().setValue(70);
			}
		}
		selectDifficulty();
	}

	@Override
	@FXML
	public void playOnClickEvent(MouseEvent event) throws IOException {
		switch (((Control) event.getSource()).getId()) {
			case "playButton":
				newGame(mineCount.getValue(), width.getValue(), height.getValue(), true);
				break;
			case "backButton":
				Launcher.previousScene();
				break;
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		selectSize();
		percentOfMines.getEditor().textProperty().addListener((observableValue, oldVal, newVal) -> {
			if (newVal != null) {
				custom.setSelected(true);
				percentOfMines.increment(0);
				selectDifficulty();
			}
		});
		width.getEditor().textProperty().addListener((observableValue, oldVal, newVal) -> {
			if (newVal.length() > 1 && width.isFocused()) {
				if (size.getSelectedToggle() != null) {
					size.getSelectedToggle().setSelected(false);
				}
				width.increment(0);
				selectDifficulty();
			}
		});
		height.getEditor().textProperty().addListener((observableValue, oldVal, newVal) -> {
			System.out.println(newVal);
			if (newVal.length() > 1 && height.isFocused()) {
				if (size.getSelectedToggle() != null) {
					size.getSelectedToggle().setSelected(false);
				}
				height.increment(0);
				selectDifficulty();
			}
		});
		mineCount.getEditor().textProperty().addListener((observableValue, oldVal, newVal) -> {
			if (!Objects.equals(newVal, oldVal)) {
				if (difficulty.getSelectedToggle() != null && mineCount.isFocused()) {
					difficulty.getSelectedToggle().setSelected(false);
				}
				System.out.println(difficulty.getSelectedToggle());
				mineCount.increment(0);
				if (Integer.parseInt(newVal) > height.getValue() * width.getValue() - 9) {
					mineCount.getValueFactory().setValue(height.getValue() * width.getValue() - 9);
				}
			}
		});
	}
}
