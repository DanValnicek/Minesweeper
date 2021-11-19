package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;
import servercomm.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class accountTab implements Initializable, SceneInterface {
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
	private Label errorBox;


	@Override
	public void addOverlay() {

	}

	public void initialize(URL url, ResourceBundle resourceBundle) {

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
				case "register":
					register();
			}
		} catch (IOException | InterruptedException e) {
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
		System.out.println(usernameField.getText());
		System.out.println(passwordField.getText());
	}

	@FXML
	private void register() throws InterruptedException {
		if (regUsername.getText() != null && password1.getText().equals(password2.getText())) {
			JSONObject message = new JSONObject();
			message.put("queryType", "update");
			message.put("operation", "register");
			message.put(0, regUsername.getText());
			message.put(1, password1.getText());
			Main.client.sendMessage(message.toJSONString());
			System.out.println("register");
		}
	}
}