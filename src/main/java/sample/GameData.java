package sample;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

public class GameData {
	private @Getter
	@Setter
	Long size;
	private @Getter
	@Setter
	Long mineCount;
	private @Getter
	@Setter
	String duration;
	private @Getter
	@Setter
	Double difficulty;

	public GameData(Long size, Long mineCount, Long duration) {
		this.size = size;
		this.mineCount = mineCount;
		this.difficulty = Double.valueOf(mineCount) / Double.valueOf(size) * 100;
		this.duration = String.format("%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes(duration),
				TimeUnit.MILLISECONDS.toSeconds(duration) -
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
		);
	}

}
