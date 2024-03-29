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

public class League {
	
	// https://www.dynastyleaguebaseball.com/League.aspx?OrgID=114243
	String leagueNum = "146332"; // Changes every year
	String baseUrl = "https://www.dynastyleaguebaseball.com/";
	String url = baseUrl + "League.aspx?OrgID=" + leagueNum;
	Document doc = null;
	ArrayList<Team> teams = new ArrayList<Team>();
	
	public String getBaseUrl() {
		return baseUrl;
	}
	
	public ArrayList<Team> getTeams() {
		return teams;
	}
	
	public void connect() {
		try {
			// Login to https://www.dynastyleaguebaseball.com/
			// Goto league page
			// (In chrome) Click on lock and select Certificate, save to file dst.cer
			// keytool -importcert -file dst.cer -keystore dsk.jks -alias DynastyLeagueBaseball
			System.setProperty("javax.net.ssl.trustStore", "C:/Users/Mike/Downloads/dsk.jks");
			// System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			doc = Jsoup.connect(url).get();
			// System.out.println(doc.toString());
			Elements elements = doc.select("a[href]");
			// System.out.println(elements.size() + " elements.");
			for (Iterator<Element> i = elements.iterator(); i.hasNext(); ) {
				Element e = i.next();
				String link = e.toString();
				if (link.indexOf("Team.aspx") > -1) {
					// Team names
					String[] tokens = link.split(">");
					if (tokens.length > 2) {
						String teamName = tokens[2].split("<")[0];
						// Team number
						tokens = link.split("=");
						String teamNum = tokens[3].split("\"")[0];
						// System.out.println(teamNum + ") " + teamName);
						// Now get the owners
						Element ne = e.nextElementSibling();
						String span = ne.toString();
						tokens = span.split(">");
						String teamOwner = tokens[1].split("<")[0];
						// System.out.println(teamOwner);
						Team team = new Team();
						team.setName(teamName);
						team.setNumber(teamNum);
						team.setOwner(teamOwner);
						team.setLeague(this);
						teams.add(team);
						team.connect();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getLeagueNum() {
		return leagueNum;
	}

	public List<Player> getPlayers() {
		ArrayList<Player> list = new ArrayList<Player>();
		for (Iterator<Team> t = teams.iterator(); t.hasNext(); ) {
			Team team = t.next();
			for (Iterator<Player> p = team.getPlayers().iterator(); p.hasNext(); ) {
				Player player = p.next();
				list.add((Batter) player);
			}
		}
		return list;
	}

	public List<Batter> getBatters() {
		ArrayList<Batter> list = new ArrayList<Batter>();
		for (Iterator<Team> t = teams.iterator(); t.hasNext(); ) {
			Team team = t.next();
			for (Iterator<Player> p = team.getPlayers().iterator(); p.hasNext(); ) {
				Player player = p.next();
				if (player instanceof Batter) {
					list.add((Batter) player);
				}
			}
		}
		return list;
	}

	public List<Fielder> getFielders() {
		ArrayList<Fielder> list = new ArrayList<Fielder>();
		for (Iterator<Team> t = teams.iterator(); t.hasNext(); ) {
			Team team = t.next();
			for (Iterator<Player> p = team.getPlayers().iterator(); p.hasNext(); ) {
				Player player = p.next();
				if (player instanceof Fielder) {
					list.add((Fielder) player);
				}
			}
		}
		return list;
	}

	public List<Pitcher> getPitchers() {
		ArrayList<Pitcher> list = new ArrayList<Pitcher>();
		for (Iterator<Team> t = teams.iterator(); t.hasNext(); ) {
			Team team = t.next();
			for (Iterator<Player> p = team.getPlayers().iterator(); p.hasNext(); ) {
				Player player = p.next();
				if (player instanceof Pitcher) {
					list.add((Pitcher) player);
				}
			}
		}
		return list;
	}
	
	public Batter getBatter(Player player) {
		String playerID = player.getNum();
		List<Batter> batters = getBatters();
		for (Iterator<Batter> i = batters.iterator(); i.hasNext(); ) {
			Batter batter = i.next();
			if (playerID.equals(batter.getNum())) {
				return batter;
			}
		}
		return null;
	}

	public League() {		
	}
	
	/**
	 * Load the league from HTML files
	 * @param leagueData
	 */
	public League(List<File> leagueData) {
		File league = leagueData.get(0);
		try {
			doc = Jsoup.parse(league, null);
			// System.out.println(doc.toString());
			Elements elements = doc.select("a[href]");
			// System.out.println(elements.size() + " elements.");
			for (Iterator<Element> i = elements.iterator(); i.hasNext(); ) {
				Element e = i.next();
				String link = e.toString();
				if (link.indexOf("Team.aspx") > -1) {
					// Team names
					String[] tokens = link.split(">");
					String teamName = tokens[2].split("<")[0];
					// Team number
					tokens = link.split("=");
					String teamNum = tokens[3].split("\"")[0];
					// System.out.println(teamNum + ") " + teamName);
					// Now get the owners
					Element ne = e.nextElementSibling();
					String span = ne.toString();
					tokens = span.split(">");
					String teamOwner = tokens[1].split("<")[0];
					// System.out.println(teamOwner);
					Team team = new Team();
					team.setName(teamName);
					team.setNumber(teamNum);
					team.setOwner(teamOwner);
					team.setLeague(this);
					teams.add(team);
					team.readLeagueData(leagueData);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public static void main(String[] args) {
		League league = new League();
		league.connect();
	}	
}
