package com.mgroschupf.flb;

public class Fielder extends Player {
	
	public static int NONE = 0;
	public static int CATCHER = 1;
	public static int FIRST_BASE = 2;
	public static int SECOND_BASE = 3;
	public static int THIRD_BASE = 4;
	public static int SHORT_STOP = 5;
	public static int LEFT_FIELD = 6;
	public static int CENTER_FIELD = 7;
	public static int RIGHT_FIELD = 8;
	public static int PITCHER = 9;
	
	public static String GAMES = "G";
	public static String GAMES_STARTED = "GS";
	public static String INNINGS = "INN";
	public static String TOTAL_CHANCES = "TC";
	public static String PUT_OUTS = "PO";
	public static String ASSISTS = "A";
	public static String ERRORS = "E";
	public static String DOUBLE_PLAYS = "DP";
	public static String FIELDING_PCT = "FPCT";
	public static String RANGE_FACTOR = "RF";
	public static String PASSED_BALLS = "PB";
	public static String STOLEN_BASES = "SB";
	public static String CAUGHT_STEALING = "CS";
	public static String CAUGHT_STEALING_PCT = "CSP";
	
	int position = NONE;
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}

	public int getGames() {
		return getInt(GAMES);
	}
	
	public int getGamesStarted() {
		return getInt(GAMES_STARTED);
	}
	
	public int getInnings() {
		return getInt(INNINGS);
	}
	
	public int getTotalChances() {
		return getInt(TOTAL_CHANCES);
	}
	
	public int getPutOuts() {
		return getInt(PUT_OUTS);
	}
	
	public int getAssists() {
		return getInt(ASSISTS);
	}
	
	public int getErrors() {
		return getInt(ERRORS);
	}
	
	public int getDoublePlays() {
		return getInt(DOUBLE_PLAYS);
	}
	
	public double getFieldingPct() {
		return getDouble(FIELDING_PCT);
	}
	
	public double getRangeFactor() {
		return getDouble(RANGE_FACTOR);
	}
	
	public int getPassedBalls() {
		return getInt(PASSED_BALLS);
	}
	
	public int getStolenBases() {
		return getInt(STOLEN_BASES);
	}
	
	public int getCaughtStealing() {
		return getInt(CAUGHT_STEALING);
	}
	
	public double getCaughtStealingPct() {
		return getDouble(CAUGHT_STEALING_PCT);
	}
	
	public Fielder(String fielderData, int position) {
		super(fielderData);
		setPosition(position);
	}
}
