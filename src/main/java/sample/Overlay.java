package sample;

import javafx.scene.Node;

import java.io.IOException;

public interface Overlay {
	void setSubScene(Node subScene);

	void displayMessage(String message, int timeout) throws IOException;
}
