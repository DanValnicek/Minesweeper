package servercomm;

import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sample.Launcher;
import sample.Main;
import sample.MultiplayerGame;
import sample.loginTab;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static servercomm.MessageTypes.*;

public class JsonMessageHandler {
	JSONObject jsonObject;
	List<String> message;
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
		}
		try {
			if (messageType != null) {
				message.set(0, String.join(",\n", (List<String>) jsonObject.get("message")));
			} else if (gameMessageType != null) {
				message = (List<String>) jsonObject.get("message");
			}
		} catch (Exception e) {
			message.set(0, jsonObject.get("message").toString());
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

	public void resolveCallback() throws InterruptedException, IOException {
		if (gameMessageType == null) {
			if (messageType.equals(q)) {
				if (message.get(0).startsWith("qConnect")) {
					try {
						loginTab.resolveLoginCallback(message.get(0));
					} catch (IOException | ConfigurationException ex) {
						ex.printStackTrace();
					}
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
				case s -> {
				}
				case p -> new MultiplayerGame(message.stream().map(Integer::valueOf).collect(Collectors.toList()));
				case t -> {
				}
				case n -> {
				}
				case e -> {
				}
				case f -> {
				}
			}
		}
	}


}
