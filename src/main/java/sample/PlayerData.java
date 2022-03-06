package sample;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

public class PlayerData {
	private @Getter final Long hardestMap;
	private @Getter final String userName;
	private @Getter final Long rank;
	private @Getter final Long score;
	private @Getter final String bestTime;
	private @Getter final Long mapsPlayed;

	public PlayerData(String userName, Long rank, Long score, Long bestTime, Long hardestMap, Long mapsPlayed) {
		this.userName = userName;
		this.rank = rank;
		this.score = score;
		this.bestTime = String.format("%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes(bestTime),
				TimeUnit.MILLISECONDS.toSeconds(bestTime) -
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(bestTime))
		);
		this.hardestMap = hardestMap;
		this.mapsPlayed = mapsPlayed;
	}

}
