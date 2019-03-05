package com.mgroschupf.dcl2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
	private boolean available = false;

	private String lastName;
	private String firstName;
	private String teamName;
	private String currentTeam;

	private String playerID;
	private String teamID;
	private String games = "0";
	private String atBats = "0";
	private String runs = "0";
	private String hits = "0";
	private String homeRuns = "0";
	private String rbis = "0";
	private String stolenBases = "0";
	
	private String wins = "0";
	private String losses = "0";
	// String games;
	private String gamesStarted = "0";
	private String completeGames = "0";
	private String shutOuts = "0";
	private String saves = "0";
	private String ipOuts = "0";
	// String hits;
	private String earnedRuns = "0";
	// String homeRuns;
	private String walks = "0";
	private String strikeOuts = "0";
	private String opposingBA = "0";
	private String era = "0";
	
	private List<String> positions = new ArrayList<String>();

	static ArrayList<Player> players = new ArrayList<Player>();
	
	public static List<Player> getPlayers() {
		return players;
	}
	
	public static void addPlayer(String firstName, String lastName, String teamName, String currentTeam) {
		Player player = new Player(firstName, lastName, teamName, currentTeam);
		players.add(player);
		player.setAvailable(true);
	}

	public static void addPlayer(String playerID, String teamID, String games, String atBats, String runs,
			String hits, String homeRuns, String rbis, String stolenBases) {
		Player player = new Player(playerID, teamID, games, atBats, runs, hits, homeRuns, rbis, stolenBases);
		List<Player> list = findPlayers(playerID);
		for (Iterator<Player> i = list.iterator(); i.hasNext(); ) {
			Player p = i.next();
			if ((p.getAtBats() != null) && !p.getAtBats().equals("0")) {
				// Add stats to existing player
				p.games = "" + (Integer.parseInt(p.games) + Integer.parseInt(games));
				p.atBats = "" + (Integer.parseInt(p.atBats) + Integer.parseInt(atBats));
				p.runs = "" + (Integer.parseInt(p.runs) + Integer.parseInt(runs));
				p.hits = "" + (Integer.parseInt(p.hits) + Integer.parseInt(hits));
				p.homeRuns = "" + (Integer.parseInt(p.homeRuns) + Integer.parseInt(homeRuns));
				p.rbis = "" + (Integer.parseInt(p.rbis) + Integer.parseInt(rbis));
				p.stolenBases = "" + (Integer.parseInt(p.stolenBases) + Integer.parseInt(stolenBases));
				return;
			}
		}
		players.add(player);
	}

	public static void addPlayer(String playerID, String teamID, String wins, String losses, String games,
			String gamesStarted, String completeGames, String shutOuts, String saves, String ipOuts,
			String hits, String earnedRuns, String homeRuns, String walks, String strikeOuts,
			String opposingBA, String era) {
		Player player = new Player(playerID, teamID, wins, losses, games, gamesStarted, completeGames,
				shutOuts, saves, ipOuts, hits, earnedRuns, homeRuns, walks, strikeOuts, opposingBA, era);
		List<Player> list = findPlayers(playerID);
		for (Iterator<Player> i = list.iterator(); i.hasNext(); ) {
			Player p = i.next();
			if ((p.getInningsPitched() != null) && !p.getInningsPitched().equals("0")) {
				// Add stats to existing player
				p.wins = "" + (Integer.parseInt(p.wins) + Integer.parseInt(wins));
				p.losses = "" + (Integer.parseInt(p.losses) + Integer.parseInt(losses));
				p.games = "" + (Integer.parseInt(p.games) + Integer.parseInt(games));
				p.gamesStarted = "" + (Integer.parseInt(p.gamesStarted) + Integer.parseInt(gamesStarted));
				p.completeGames = "" + (Integer.parseInt(p.completeGames) + Integer.parseInt(completeGames));
				p.shutOuts = "" + (Integer.parseInt(p.shutOuts) + Integer.parseInt(shutOuts));
				p.saves = "" + (Integer.parseInt(p.saves) + Integer.parseInt(saves));
				p.ipOuts = "" + (Integer.parseInt(p.ipOuts) + Integer.parseInt(ipOuts));
				p.hits = "" + (Integer.parseInt(p.hits) + Integer.parseInt(hits));
				p.earnedRuns = "" + (Integer.parseInt(p.earnedRuns) + Integer.parseInt(earnedRuns));
				p.homeRuns = "" + (Integer.parseInt(p.homeRuns) + Integer.parseInt(homeRuns));
				p.walks = "" + (Integer.parseInt(p.walks) + Integer.parseInt(walks));
				p.strikeOuts = "" + (Integer.parseInt(p.strikeOuts) + Integer.parseInt(strikeOuts));
				//p.opposingBA = "" + Integer.parseInt(p.shutOuts) + Integer.parseInt(shutOuts);
				//p.era = "" + Integer.parseInt(p.shutOuts) + Integer.parseInt(shutOuts);
				
				return;
			}
		}
		players.add(player);
	}

	public String toString() {
		return lastName + ", " + firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public String getPlayerID() {
		return playerID;
	}
	
	// Scale from 0 to 100
	public int getScore() {
		int score = 0;
		if (playerID == null) {
			return score;
		}
		if ((atBats != null) && !(atBats.equals("0"))) {
			// HR, RBI, SB, R, H - each 20%
			int hr = Integer.parseInt(homeRuns);
			int rbi = Integer.parseInt(rbis);
			int sb = Integer.parseInt(stolenBases);
			int r = Integer.parseInt(runs);
			int h = Integer.parseInt(hits);
			int ab = Integer.parseInt(atBats);
			int avg = (1000 * h) / ab;
			score = (int) (0.2 * (100 * hr / 60) + 0.2 * (100 * rbi / 150) + 0.2 * (100 * sb / 100) + 0.2 * (100 * r / 150) + 0.2 * (100 * h / 250));
		} else {
			// W, SV, SO - each 33%
			int w = Integer.parseInt(wins);
			int sv = Integer.parseInt(saves);
			int so = Integer.parseInt(strikeOuts);
			score = (int) (0.33 * (100 * w / 25) + 0.33 * (100 * sv / 50) + 0.33 * (100 * so / 300));
		}
		return score;
	}
	
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Player)) {
			return false;
		}
		Player player = (Player) obj;
		if (lastName.equalsIgnoreCase(player.getLastName())) {
			return true;
		}
		return false;
	}
	
	public static List<Player> findStats(Player player) {
		ArrayList<Player> l = new ArrayList<Player>();
		if (player.getLastName() == null) {
			l.add(player);
			return l;
		}
		int endIndex = player.getLastName().length();
		if (endIndex > 5) {
			endIndex = 5;
		}
		String id =
			player.getLastName().replace(" ", "").replace("'", "").toLowerCase().substring(0, endIndex) +
			player.getFirstName().replace(".", "").toLowerCase().substring(0, 2);
		for (Iterator<Player> i = players.iterator(); i.hasNext(); ) {
			Player p = i.next();
			String playerID = p.getPlayerID();
			if (playerID != null) {
				if (playerID.startsWith(id)) {
					l.add(p);
				}
			}
		}
		return l;
	}
	
	public static List<Player> findPlayers(String playerID) {
		List<Player> list = new ArrayList<Player>();
		for (Iterator<Player> p = players.iterator(); p.hasNext(); ) {
			Player player = p.next();
			String id = player.getPlayerID();
			if ((id != null) && id.startsWith(playerID)) {
				list.add(player);
			}
		}
		return list;
	}

	public void addPosition(String position) {
		if (! positions.contains(position)) {
			positions.add(position);
		}
	}
	
	public List<String> getPositions() {
		return positions;
	}
	
	public String getPositionString() {
		String text = "";
		for (Iterator<String> i = positions.iterator(); i.hasNext(); ) {
			String pos = i.next();
			if (text.isEmpty()) {
				text = pos;
			} else {
				text += "," + pos;
			}
		}
		return text;
	}

	public Player(String firstName, String lastName, String teamName, String currentTeam) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.teamName = teamName;
		this.currentTeam = currentTeam;
	}

	public Player(String playerID, String teamID, String games, String atBats, String runs, String hits,
			String homeRuns, String rbis, String stolenBases) {
		this.playerID = playerID;
		this.teamID = teamID;
		this.games = games;
		this.atBats = atBats;
		this.runs = runs;
		this.hits = hits;
		this.homeRuns = homeRuns;
		this.rbis = rbis;
		this.stolenBases = stolenBases;
	}
	
	public Player(String playerID, String teamID, String wins, String losses, String games,
			String gamesStarted, String completeGames, String shutOuts, String saves, String ipOuts,
			String hits, String earnedRuns, String homeRuns, String walks, String strikeOuts,
			String opposingBA, String era) {
		this.playerID = playerID;
		this.teamID = teamID;
		this.wins = wins;
		this.losses = losses;
		this.games = games;
		this.gamesStarted = gamesStarted;
		this.completeGames = completeGames;
		this.shutOuts = shutOuts;
		this.saves = saves;
		this.ipOuts = ipOuts;
		this.hits = hits;
		this.earnedRuns = earnedRuns;
		this.homeRuns = homeRuns;
		this.walks = walks;
		this.strikeOuts = strikeOuts;
		this.opposingBA = opposingBA;
		this.era = era;
	}

	public String getTeamName() {
		return teamName;
	}
	
	public String getCurrentTeam() {
		return currentTeam;
	}
	
	public String getInningsPitched() {
		int ipo = Integer.parseInt(this.ipOuts);
		return "" + (ipo / 3);
	}

	public String getERA() {
		return this.era;
	}
	
	public String getWins() {
		return this.wins;
	}
	
	public String getSaves() {
		return this.saves;
	}
	
	public String getStrikeOuts() {
		return this.strikeOuts;
	}
	
	public String getAtBats() {
		return this.atBats;
	}

	public String getAverage() {
		double ab = Integer.parseInt(this.atBats);
		double h = Integer.parseInt(this.hits);
		if (ab == 0) return ".000";
		String avg = "" + h / ab;
		if (avg.startsWith("0.")) {
			avg = avg.substring(1, avg.length());
		}
		if (avg.length() > 4) {
			avg = avg.substring(0, 4);
		}
		return avg;
	}
	
	public String getRBIs() {
		return this.rbis;
	}
	
	public String getHomeRuns() {
		return this.homeRuns;
	}
	
	public String getStolenBases() {
		return this.stolenBases;
	}
}
