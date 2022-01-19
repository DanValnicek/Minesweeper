package servercomm;

import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sample.Launcher;
import sample.Main;
import sample.loginTab;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static servercomm.MessageTypes.*;

public class JsonMessageHandler {
	JSONObject jsonObject;
	String message;
	MessageTypes messageType;
	GameMessageTypes gameMessageType;

	public JsonMessageHandler(JSONObject jsonObject) throws ParseException {
		this.jsonObject = jsonObject;
		System.out.println(jsonObject);
		messageType = MessageTypes.valueOf(jsonObject.get("messageType").toString());
		gameMessageType = GameMessageTypes.valueOf(jsonObject.get("gameMessageType").toString());
		try {
			if (messageType != null) {
				message = String.join(",\n", (List<String>) jsonObject.get("message"));
			} else if (gameMessageType != null) {
				message = String.valueOf(jsonObject.get("message"));
			}
		} catch (Exception e) {
			message = jsonObject.get("message").toString();
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
				if (message.startsWith("qConnect")) {
					try {
						loginTab.resolveLoginCallback(message);
					} catch (IOException | ConfigurationException ex) {
						ex.printStackTrace();
					}
				}
			} else if (messageType.equals(i)) {
				if (message.equals("ping")) {
					Main.client.sendMessage("pong");
				} else if (message.startsWith("uRegister")) {

				}
			} else if (messageType.equals(e) || messageType.equals(n)) {
				if (messageType.equals(e) && message.startsWith("java.sql")) {
					message = message.split(": ")[1];
				}
				Launcher.getMenuScene().getOverlay().showMessage(messageType, message, 30);
			}
		} else {
			switch (gameMessageType) {
				case s -> {
				}
//				case p -> new MultiplayerGame();
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
