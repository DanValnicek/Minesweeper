package sample;


import javafx.fxml.FXML;
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

public class accountTab extends AppSubScene {
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