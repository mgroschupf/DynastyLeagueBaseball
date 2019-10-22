package com.mgroschupf.flb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Team {
	
	String name;
	String number;
	String owner;
	League league;
	ArrayList<Player> players = new ArrayList<Player>();

	public void setName(String teamName) {
		this.name = teamName;
	}

	public void setNumber(String teamNum) {
		this.number = teamNum;
	}

	public void setOwner(String teamOwner) {
		this.owner = teamOwner;
	}
	
	public void setLeague(League league) {
		this.league = league;
	}
	
	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public String getOwner() {
		return owner;
	}
	
	public League getLeague() {
		return league;
	}
	
	public String getURL() {
		return league.getBaseUrl() + "Team.aspx?OrgID=" + league.getLeagueNum() + "&TeamID=" + getNumber();
	}
	
	public String getStatsUrl(String category) {
		return league.getBaseUrl() + "TeamStats.aspx?OrgID=" + league.getLeagueNum() + "&TeamID=" + getNumber() + "&Report=" + category + "&GameType=0&Splits=0";
	}
	
	public String getBattingStatsUrl() {
		return getStatsUrl("0");
	}
	
	public String getPitchingStatsUrl() {
		return getStatsUrl("1");
	}
	
	public String getFieldingStatsUrl() {
		return getStatsUrl("2");
	}
	
	public File getStatsFile(String stat, List<File> leagueData) {
		String searchString = "team" + getNumber() + stat + ".htm";
		for (Iterator<File> i=leagueData.iterator(); i.hasNext(); ) {
			File file = i.next();
			if (searchString.equals(file.getName())) {
				return file;
			}
		}
		return null;
	}
	
	public File getBattingStatsFile(List<File> leagueData) {
		return getStatsFile("batting", leagueData);
	}
	
	public File getPitchingStatsFile(List<File> leagueData) {
		return getStatsFile("pitching", leagueData);
	}
	
	public File getFieldingStatsFile(List<File> leagueData) {
		return getStatsFile("fielding", leagueData);
	}
	
	void addPlayers(Document doc, int scriptIndex, int playerType, int position) throws IOException {
		Elements elements = doc.select("script");
		// System.out.println(elements.size() + " elements.");
		Element e = elements.get(scriptIndex);
		String script = e.toString();
		// System.out.println(script);
		String data = script.split("\\[")[1].split("\\]")[0];
		// System.out.println(data);
		String[] playerData = data.split("\\{");
		for (int i=0; i < playerData.length; i ++) {
			Player player = null;
			if (playerType == Player.BATTER) {
				player = new Batter(playerData[i]);
			} else if (playerType == Player.PITCHER) {
				player = new Pitcher(playerData[i]);
			} else if (playerType == Player.FIELDER) {
				player = new Fielder(playerData[i], position);
			}
			String name = player.getName();
			if (name != null && ! name.equals("TEAM TOTALS")) {
				players.add(player);
			}
		}		
	}
	
	void addPlayers(String url, int scriptIndex, int playerType) throws IOException {
		Document doc = Jsoup.connect(url).get();
		addPlayers(doc, scriptIndex, playerType, Fielder.NONE);
	}
	
	void addPlayers(String url, int[] scriptIndices, int playerType) throws IOException {
		Document doc = Jsoup.connect(url).get();
		for (int index = 0; index < scriptIndices.length; index++) {
			addPlayers(doc, scriptIndices[index], playerType, index + 1);
		}
	}

	private void addPlayers(File in, int scriptIndex, int playerType) {
		try {
			Document doc = Jsoup.parse(in, null);
			addPlayers(doc, scriptIndex, playerType, Fielder.NONE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addPlayers(File in, int[] scriptIndices, int playerType) {
		try {
			Document doc = Jsoup.parse(in, null);
			for (int index = 0; index < scriptIndices.length; index++) {
				addPlayers(doc, scriptIndices[index], playerType, index + 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public void connect() {
		try {
			//if (number.equals("12")) {
				// BATTERS
				addPlayers(getBattingStatsUrl(), 10, Player.BATTER);
				
				// PITCHERS
				addPlayers(getPitchingStatsUrl(), 10, Player.PITCHER);

				// FIELDERS
				int[] indices = new int[] {10, 11, 12, 13, 14, 15, 16, 17, 18};
				addPlayers(getFieldingStatsUrl(), indices, Player.FIELDER);
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readLeagueData(List<File> leagueData) {
		// BATTERS
		addPlayers(getBattingStatsFile(leagueData), 10, Player.BATTER);
		// PITCHERS
		addPlayers(getPitchingStatsFile(leagueData), 10, Player.PITCHER);
		// FIELDERS
		int[] indices = new int[] {10, 11, 12, 13, 14, 15, 16, 17, 18};
		addPlayers(getFieldingStatsFile(leagueData), indices, Player.FIELDER);
	}
}
