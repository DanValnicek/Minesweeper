package servercomm;

public enum MessageTypes {
	e("#ff0000"), //=> error
	n("#ffffff"), //=> notification
	q("#"), //=> query
	i("#"),
	g("#"),
	; //=> internal


	MessageTypes(String s) {

	}
}
