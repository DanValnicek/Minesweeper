package sample;

import org.json.simple.JSONObject;

public class TableViewHandler {
JSONObject queryData;

	public TableViewHandler(JSONObject queryData) {
		this.queryData = queryData;

		System.out.println(queryData);
	}
}
