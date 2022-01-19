package sample;

import java.io.IOException;
import java.util.List;

public class MultiplayerGame extends Game{
	public MultiplayerGame(List<Integer> args) throws IOException {
		super(args.get(0),args.get(1),args.get(2),true);
	}

	public void joinToGame() throws IOException, InterruptedException {
		if(!Main.client.isLoggedIn()){
		Launcher.newScene("/loginTab.fxml");
		return;
		}
		Main.client.sendMessage(JsonGenerator.generateRequest("iJoinToGame",null).toString());
	}

	public MultiplayerGame(int numOfMines, int numOfRows, int numOfColumns, boolean resize) throws IOException {
		super(numOfMines, numOfRows, numOfColumns, resize);
	}
//	public MultiplayerGame( boolean resize) throws IOException {

//		super(/*numOfMines, numOfRows, numOfColumns, */ resize);
//	}
	//TODO: join method
	//TODO: leave method
}
