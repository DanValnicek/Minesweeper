package servercomm;

import javafx.application.Platform;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sample.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static servercomm.MessageTypes.*;

public class JsonMessageHandler {
	JSONObject jsonObject;
	ArrayList<String> message = new ArrayList<>();
	MessageTypes messageType;
	GameMessageTypes gameMessageType;

	public JsonMessageHandler(JSONObject jsonObject) throws ParseException {
		this.jsonObject = jsonObject;
		System.out.println(jsonObject);
		if (jsonObject.containsKey("messageType")) {
			messageType = MessageTypes.valueOf(jsonObject.get("messageType").toString());
		}
		if (jsonObject.containsKey("gameMessageType")) {
			gameMessageType = GameMessageTypes.valueOf(jsonObject.get("gameMessageType").toString());
			if (gameMessageType == GameMessageTypes.p) {
				return;
			}
		}
		if (!jsonObject.containsKey("message")) return;
		try {
			if (messageType != null) {
				message.add(0, String.join(",\n", (ArrayList<String>) jsonObject.get("message")));
			} else if (gameMessageType != null) {
				message = (ArrayList<String>) jsonObject.get("message");
			}
		} catch (Exception e) {
			message.add(0, jsonObject.get("message").toString());
			System.out.println(message);
		}
	}


	public void showNotification() {
		if (Objects.equals(messageType, "e") || Objects.equals(messageType, "n")) {
//			System.out.println("showNotification: " + message);
		} else {
			System.out.println(messageType + ": " + message);
		}
	}

	public void resolveCallback() throws InterruptedException, IOException, ConfigurationException {
		if (gameMessageType == null) {
			if (messageType.equals(q)) {
				if (message.get(0).startsWith("qConnect")) {
					try {
						loginTab.resolveLoginCallback(message.get(0));
					} catch (IOException | ConfigurationException ex) {
						ex.printStackTrace();
					}
				} else if (message.get(0).startsWith("qReturnGameHistory")) {
					new TableViewHandler(jsonObject);
				}


			} else if (messageType.equals(i)) {
				if (message.get(0).equals("ping")) {
					Main.client.sendMessage("pong");
				} else if (message.get(0).startsWith("uRegister")) {
				}
			} else if (messageType.equals(e) || messageType.equals(n)) {
				if (messageType.equals(e) && message.get(0).startsWith("java.sql")) {
					message.set(0, message.get(0).split(": ")[1]);
				}
				Launcher.getMenuScene().getOverlay().showMessage(messageType, message.get(0), 30);
			}
		} else {
			switch (gameMessageType) {
				case s -> Main.game.startGame();
				case p -> Platform.runLater(() -> {
					try {
						Main.game = new MultiplayerGame((JSONArray) jsonObject.get("minePositions"),
								((Long) jsonObject.get("rowCount")).intValue(),
								((Long) jsonObject.get("columnCount")).intValue(),
								UUID.fromString((String) jsonObject.get("uuid")));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				});
				case t -> ((MultiplayerGame) Main.game).eliminated(message.get(0));
				case n -> Launcher.getMenuScene().getOverlay().showMessage(n, message.get(0), 10);
				case e -> Launcher.getMenuScene().getOverlay().showMessage(e, message.get(0), 10);
				case f -> {
					Launcher.getMenuScene().getOverlay().showMessage(n, message.get(1), 10);
					((MultiplayerGame) Main.game).startCountDown(Integer.parseInt(message.get(0)));
				}
				case w -> {
					Launcher.getMenuScene().getOverlay().showMessage(n, message.get(0), 10);
					((MultiplayerGame) Main.game).won = true;
					Main.game.gameOver();
				}
				case pcc -> {
					Launcher.getMenuScene().getOverlay().showMessage(n, message.get(0), 10);
					CompletableFuture.supplyAsync(() -> {
						while (Main.game.getGameBar() == null) {
						}
						System.out.println(Main.game.getGameBar());
						Platform.runLater(() -> {
							((MultiplayerGameBar) Main.game.getGameBar()).setUserCount(
									Integer.parseInt(message.get(1)));
						});
						return null;
					});
				}
			}
		}

	}
}
