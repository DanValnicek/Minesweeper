package sample;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;
import servercomm.MessageTypes;

import java.io.IOException;
import java.util.List;

public class loginTab extends AppSubScene {
	private static Scene accountScene;
	@FXML
	private VBox loginBox;
	@FXML
	private VBox registerBox;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField regUsername;
	@FXML
	private PasswordField password1;
	@FXML
	private PasswordField password2;


	@FXML
	public void playOnClickEvent(MouseEvent event) {
		System.out.println(((Control) event.getSource()).getId());
		try {
			switch (((Control) event.getSource()).getId()) {
				case "backButton":
					if (loginBox.isVisible()) {
						Launcher.previousScene();
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
				case "register":
					register();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void keyPress(KeyEvent keyEvent) throws InterruptedException {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	public static void resolveLoginCallback(String callback) throws IOException {
		if (callback.equals("qLogin-success")) {
			AppSubScene.init("/accountTab.fxml");
		} else {
			Launcher.getMenuScene().getOverlay().showMessage(MessageTypes.e, "Username or password is incorrect!", 10);
		}
	}

	@FXML
	private void login() throws InterruptedException {
		System.out.println(usernameField.getText());
		System.out.println(passwordField.getText());
		if (!usernameField.getText().equals("") && !passwordField.getText().equals("")) {
			Main.client.sendMessage(JsonGenerator.generateRequest("qLogin", List.of(passwordField.getText(), usernameField.getText())).toJSONString());
		}
	}

	@FXML
	private void register() throws InterruptedException {
		if (!regUsername.getText().equals("") && !password1.getText().equals("")) {
			if (password1.getText().equals(password2.getText())) {
				JSONObject message = JsonGenerator.generateRequest("uRegister", List.of(regUsername.getText(), password2.getText()));
				Main.client.sendMessage(message.toJSONString());
				System.out.println("register");
			} else {
				Launcher.getMenuScene().getOverlay().showMessage(MessageTypes.e, "Passwords don't match!", 10);
			}
		} else {
			Launcher.getMenuScene().getOverlay().showMessage(MessageTypes.e, "Fields are blank!", 10);
		}
	}
}