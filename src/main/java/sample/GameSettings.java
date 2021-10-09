package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class GameSettings {
	static Game game;
	@FXML
	private static Spinner<Double> percentOfMines;
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

	public GameSettings(int width, int height, int mineCount) throws IOException {


		this.width.getValueFactory().setValue(width);
		this.height.getValueFactory().setValue(height);
		this.mineCount.getValueFactory().setValue(mineCount);
		percentOfMines.getValueFactory().setValue(55.0);

	}

	public static void init() throws IOException {
	}

	public static void newGame(int mineCount, int width, int height) throws IOException {
		game = new Game(mineCount, height, width);
	}

	@FXML
	private void selectDifficulty() throws Exception {
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
				System.out.println(percentOfMines.getValue());
				break;

		}
	}

	@FXML
	private void selectSize() throws Exception {
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
	}

	@FXML
	public void playOnClickEvent(MouseEvent event) {
		System.out.println(((Control) event.getSource()).getId());
		try {
			switch (((Control) event.getSource()).getId()) {
				case "playButton":
					newGame(mineCount.getValue(), width.getValue(), height.getValue());
					break;
				case "backButton":
					Menu.init();
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
