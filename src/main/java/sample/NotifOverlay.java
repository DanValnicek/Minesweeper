package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Timer;
import java.util.TimerTask;

public class NotifOverlay {
	private final Label notifText = new Label();
	private final Tooltip notifTooltip = new Tooltip();
	Pane pane = new Pane(notifText);
	private final Group group = new Group(pane);

	public NotifOverlay() {
		group.setMouseTransparent(false);
		group.setPickOnBounds(true);
		notifText.setWrapText(true);
		notifText.setTextFill(Color.RED);
		notifText.setText("idkfadshdfhdafhad serhjrtheerfhdahr a");
		notifText.setTooltip(notifTooltip);
		notifText.setPadding(new Insets(2));
		notifText.setTextAlignment(TextAlignment.CENTER);
		notifText.setAlignment(Pos.CENTER);
		notifText.setPickOnBounds(true);
		notifText.setMaxSize(130, 50);
		notifText.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public Group getGroup() {
		return group;
	}

	public void showMessage(String message, int timeout) {
//		Platform.runLater(() -> {
		System.out.println("message from controller" + message);
		System.out.println("messageText: " + notifText);

		notifText.setText(message);
		notifTooltip.setText(message);
		group.setVisible(true);
		Launcher.getFirstStage().show();
		System.out.println(message);
		System.out.println("messageText: " + notifText);
//		});
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				group.setVisible(false);
				System.out.println("non Visible");
			}
		}, timeout * 1000L);
	}

}
