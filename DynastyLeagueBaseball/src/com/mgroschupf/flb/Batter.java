package com.mgroschupf.flb;

public class Batter extends Player {

	static String GAMES = "G";
	static String AT_BATS = "AB";
	static String RUNS = "R";
	static String HITS = "H";
	static String DOUBLES = "2B";
	static String TRIPLES = "3B";
	static String HOME_RUNS = "HR";
	static String RBIS = "RBI";
	static String WALKS = "BB";
	static String STRIKE_OUTS = "SO";
	static String STOLEN_BASES = "SB";
	static String CAUGHT_STEALING = "CS";
	static String BATTING_AVG = "BA";
	static String ON_BASE_PCT = "OBP";
	static String SLUGGING_PCT = "SLG";
	static String OPS = "OPS";
	
	public int getGames() {
		return getInt(GAMES);
	}
	
	public int getAtBats() {
		return getInt(AT_BATS);
	}
	
	public int getRuns() {
		return getInt(RUNS);
	}
	
	public int getHits() {
		return getInt(HITS);
	}
	
	public int getDoubles() {
		return getInt(DOUBLES);
	}
	
	public int getTriples() {
		return getInt(TRIPLES);
	}
	
	public int getHomeRuns() {
		return getInt(HOME_RUNS);
	}
	
	public int getRBIs() {
		return getInt(RBIS);
	}
	
	public int getWalks() {
		return getInt(WALKS);
	}
	
	public int getStrikeOuts() {
		return getInt(STRIKE_OUTS);
	}
	
	public int getStolenBases() {
		return getInt(STOLEN_BASES);
	}
	
	public int getCaughtStealing() {
		return getInt(CAUGHT_STEALING);
	}
	
	public double getBattingAvg() {
		return getDouble(BATTING_AVG);
	}
	
	public double getOnBasePct() {
		return getDouble(ON_BASE_PCT);
	}
	
	public double getSluggingPct() {
		return getDouble(SLUGGING_PCT);
	}
	
	public double getOPS() {
		return getDouble(OPS);
	}
	
	public Batter(String batterData) {
		super(batterData);
	}
}
