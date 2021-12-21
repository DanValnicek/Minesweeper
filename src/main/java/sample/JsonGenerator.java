package sample;
import org.json.simple.JSONObject;

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
}
