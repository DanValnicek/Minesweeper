package sample;

import javafx.application.Platform;
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
import servercomm.MessageTypes;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static servercomm.MessageTypes.e;
import static servercomm.MessageTypes.n;

public class NotifOverlay {
	static Map<MessageTypes, String> messageTypeColors = Map.of(
			e, "#ff0000",
			n, "#ffffff");
	private final Label notifText = new Label();
	private final Tooltip notifTooltip = new Tooltip();
	Pane pane = new Pane(notifText);
	private final Group group = new Group(pane);

	public NotifOverlay() {
		group.setMouseTransparent(false);
		group.setPickOnBounds(true);
		notifText.setWrapText(true);
		notifText.setTextFill(Color.RED);
//		notifText.setText("idkfadshdfhdafhad serhjrtheerfhdahr a");
		notifText.setTooltip(notifTooltip);
		notifText.setPadding(new Insets(2));
		notifText.setTextAlignment(TextAlignment.CENTER);
		notifText.setAlignment(Pos.CENTER);
		notifText.setPickOnBounds(true);
		notifText.setMaxSize(130, 50);
		notifText.setBackground(new Background(new BackgroundFill(
				Color.rgb(0, 0, 0, 0.5),
				CornerRadii.EMPTY, Insets.EMPTY)));
		group.setVisible(false);
	}

	public Group getGroup() {
		return group;
	}

	public void showMessage(MessageTypes messageType, String message, int timeout) {
		Platform.runLater(() -> {
//			System.out.println("from showMessage: " + message);
			notifText.setText(message);
			notifTooltip.setText(message);
			notifText.setStyle("-fx-text-fill: " + messageTypeColors.get(messageType));
			notifTooltip.setStyle("-fx-text-fill: " + messageTypeColors.get(messageType));

			group.setVisible(true);
			Launcher.getFirstStage().show();
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					group.setVisible(false);
					System.out.println("non Visible");
				}
			}, timeout * 1000L);
		});
	}

}
