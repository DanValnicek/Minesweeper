package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationOverlay {
	@FXML
	public static SubScene notifOverlay;
	@FXML
	private Tooltip messageTextTooltip;
	@FXML
	private Label messageText;
	@FXML
	private Pane messagePane;

	public NotificationOverlay() {

	}

	public NotificationOverlay(SubScene notifOverlay) {
		this.notifOverlay = notifOverlay;
	}

	public static void init() throws IOException {
		notifOverlay = new SubScene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/NotificationOverlay.fxml"))), 525, 269);
	}
	public void showMessage(String message, int timeout){
		messageText.setText(message);
		messageTextTooltip.setText(message);
		messagePane.setVisible(true);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {

		messagePane.setVisible(false);
			}
		},timeout* 1000);
	}
}
