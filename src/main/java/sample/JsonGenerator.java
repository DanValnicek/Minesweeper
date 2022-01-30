package sample;

import org.json.simple.JSONObject;

import java.util.List;

public class JsonGenerator {

	//e = error
	//n = notification
	//q = query
	//i = internal
	public static JSONObject generateRequest(String operation, Object args) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("operation", operation);
		jsonObject.put("args", args);
		return jsonObject;
	}

	public static JSONObject generateRequest(String operation) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("operation", operation);
		return jsonObject;
	}

	public static String saveMap(List numbers) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("operation", "iSaveMap");
		jsonObject.put("mapInfo", numbers);
		return jsonObject.toJSONString();
	}
}
