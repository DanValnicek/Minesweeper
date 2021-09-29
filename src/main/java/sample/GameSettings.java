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
	private static Scene gameSettingsScene;
	private static Spinner customMinesPercentage;
	@FXML
	private ToggleGroup difficulty;

	public static void init() throws IOException {
		gameSettingsScene = new Scene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/gameSettings.fxml"))));
		Menu.sceneInit(gameSettingsScene);
		customMinesPercentage = (Spinner) gameSettingsScene.lookup("percentOfMines");
//customMinesPercentage.getValueFactory().setValue();
	}

	public static void newGame() throws IOException {
		game = new Game(100, 30, 30);

	}

	@FXML
	private void selectDifficulty() {
		ToggleButton selectedButton = (ToggleButton) difficulty.getSelectedToggle();
		System.out.println(selectedButton.getText());
	}

	@FXML
	public void playOnClickEvent(MouseEvent event) {
		System.out.println(((Control) event.getSource()).getId());
		try {
			switch (((Control) event.getSource()).getId()) {
				case "playButton":
					newGame();
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
