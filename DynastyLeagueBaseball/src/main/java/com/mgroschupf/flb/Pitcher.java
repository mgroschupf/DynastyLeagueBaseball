package com.mgroschupf.flb;

public class Pitcher extends Player {
	
	static String GAMES = "G";
	static String GAMES_STARTED = "GS";
	static String COMPLETE_GAMES = "CG";
	static String WINS = "W";
	static String LOSSES = "L";
	static String SAVES = "SV";
	static String BLOWN_SAVES = "BS";
	static String INNINGS_PITCHED = "IP";
	static String HITS = "H";
	static String RUNS = "R";
	static String EARNED_RUNS = "ER";
	static String HOME_RUNS = "HR";
	static String WALKS = "BB";
	static String STRIKE_OUTS = "SO";
	static String BATTING_AVG = "BA";
	static String WHIP = "WHIP";
	static String ERA = "ERA";
	
	public int getGames() {
		return getInt(GAMES);
	}
	
	public int getGamesStarted() {
		return getInt(GAMES_STARTED);
	}
	
	public int getCompleteGames() {
		return getInt(COMPLETE_GAMES);
	}
	
	public int getWins() {
		return getInt(WINS);
	}
	
	public int getLosses() {
		return getInt(LOSSES);
	}
	
	public int getSaves() {
		return getInt(SAVES);
	}
	
	public int getBlownSaves() {
		return getInt(BLOWN_SAVES);
	}
	
	public int getInningsPitched() {
		return getInt(INNINGS_PITCHED);
	}
	
	public int getHits() {
		return getInt(HITS);
	}
	
	public int getRuns() {
		return getInt(RUNS);
	}
	
	public int getEarnedRuns() {
		return getInt(EARNED_RUNS);
	}
	
	public int getHomeRuns() {
		return getInt(HOME_RUNS);
	}
	
	public int getWalks() {
		return getInt(WALKS);
	}
	
	public int getStrikeOuts() {
		return getInt(STRIKE_OUTS);
	}
	
	public double getBattingAverage() {
		return getDouble(BATTING_AVG);
	}
	
	public double getWHIP() {
		return getDouble(WHIP);
	}
	
	public double getERA() {
		return getDouble(ERA);
	}

	public Pitcher(String pitcherData) {
		super(pitcherData);
	}
}
