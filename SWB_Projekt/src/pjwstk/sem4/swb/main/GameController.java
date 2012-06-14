package pjwstk.sem4.swb.main;

import java.io.IOException;

public class GameController {

	private Integer gameScore = 0;
	
	int getGameScore() {
		return gameScore;
	}
	
	private synchronized void updateScore() {
		try {
			SerialComm.out.write(gameScore.toString().getBytes());
		} catch (IOException e) {
			// TODO: Zerwane po³¹czenie?
			e.printStackTrace();
		}
	}

	synchronized int scorePC() {
		++gameScore;
		updateScore();
		return getGameScore();
	}

	synchronized int scoreCOM() {
		--gameScore;
		updateScore();
		return getGameScore();
	}

	int getTimerValue() {
		// TODO: Obs³uga zegara 
		return 0;
	}
	
	
}
