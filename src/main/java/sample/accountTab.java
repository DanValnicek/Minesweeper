package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class accountTab {
	private static Scene accountScene;
	@FXML
	private VBox loginBox;
	@FXML
	private VBox registerBox;

	@FXML
	public static void init() throws IOException {
		accountScene = new Scene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/accountTab.fxml"))));
		Menu.sceneInit(accountScene);
//		loginBox = (VBox) accountScene.lookup("#loginBox");
//		registerBox = (VBox) accountScene.lookup("#registerBox");
	}

	@FXML
	public void playOnClickEvent(MouseEvent event) {
		System.out.println(((Control) event.getSource()).getId());
		try {
			switch (((Control) event.getSource()).getId()) {
				case "backButton":
					if (loginBox.isVisible()) {
						Menu.init();
					} else {
						loginBox.setVisible(true);
						registerBox.setVisible(false);
					}
					break;
				case "login":
					login();
					break;
				case "noAccount":
					loginBox.setVisible(false);
					registerBox.setVisible(true);
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void keyPress(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	@FXML
	private void login() {
		TextField username = (TextField) accountScene.lookup("#usernaneField");
		PasswordField password = (PasswordField) accountScene.lookup("#passwordField");
		System.out.println(username.getText());
		System.out.println(password.getText());
	}

	private void register() {

	}
}