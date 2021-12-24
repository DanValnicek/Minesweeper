package servercomm;

public enum MessageTypes {
	e("#ff0000"), //=> error
	n("#ffffff"), //=> notification
	q("#"), //=> query
	i("#"),
	; //=> internal

	MessageTypes(String s) {

	}
}
