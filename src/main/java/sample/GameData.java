package sample;

import lombok.Getter;
import lombok.Setter;

public class GameData {
	private @Getter @Setter Integer size;
	private @Getter @Setter Integer mineCount;
	private @Getter @Setter Long duration;
	private @Getter @Setter Double difficulty;

	public GameData(Integer size, Integer mineCount, Long duration) {
		this.size = size;
		this.mineCount = mineCount;
		this.duration = duration;
		this.difficulty = Double.valueOf(mineCount) / size * 100;
	}
}
