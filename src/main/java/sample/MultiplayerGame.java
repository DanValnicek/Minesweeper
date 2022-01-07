package sample;

import java.io.IOException;

public class MultiplayerGame extends Game{
	public MultiplayerGame(int numOfMines, int numOfRows, int numOfColumns, boolean resize) throws IOException {
		super(numOfMines, numOfRows, numOfColumns, resize);
	}
	//TODO: join method
	//TODO: leave method
}
