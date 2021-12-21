package servercomm;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sample.Launcher;

import java.util.List;
import java.util.Objects;

public class JsonMessageHandler {
	JSONObject jsonObject;
	String message;
	String messageType;

	public JsonMessageHandler(JSONObject jsonObject) throws ParseException {
		this.jsonObject = jsonObject;
		System.out.println(jsonObject);
		try {
			message = String.join(",\n",(List<String>) jsonObject.get("message"));
		} catch (Exception e) {
			message = jsonObject.get("message").toString();
		}
		System.out.println(message);
		messageType = jsonObject.get("messageType").toString();
	}

	//e = error
	//n = notification
	//q = query
	//i = internal
	public void showNotification() {
		if (Objects.equals(messageType, "e") || Objects.equals(messageType, "n")) {
//			System.out.println("showNotification: " + message);
			Launcher.getMenuScene().getOverlay().showMessage(messageType, message, 30);
		} else {
			System.out.println(messageType + ": " + message);
		}

	}
}
