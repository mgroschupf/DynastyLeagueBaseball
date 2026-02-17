package com.mgroschupf.dcl1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

/**
 * The players available to draft.
 */
public class Available {

	String filename = null;
	
	public Available(String name) {
		filename = name;
	}

	public void open() {
		try {
			BufferedReader br =
				new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String [] tokens = null;
				// If line starts with a digit: Rank. First Last - Team
				// Otherwise: Last First Team Team Position
				if (Character.isDigit(line.charAt(0))) {
					tokens = line.split("\\s+");
				}
				else {
					tokens = line.split("\\t+");
				}
				
				if (tokens.length > 3) {
					Player p = null;
					if (Character.isDigit(tokens[0].charAt(0))) {
						String team = tokens[4];
						for(int i=5; i < tokens.length; i++) {
							team += " " + tokens[i];
						}
						// System.out.println(tokens[1] + " " + tokens[2] + " " + team);
						p = Player.addPlayer(tokens[1], tokens[2], 0, team, null);
						p.setLastTeam(team);
					} else {
						p = Player.addPlayer(tokens[1], tokens[0], 0, tokens[2], tokens[4]);
						p.setLastTeam(tokens[2]);
					}
					if (p != null) {
						p.setAvailable(true);
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Available available = new Available("C:\\Users\\GRO4525\\Documents\\github\\DynastyLeagueBaseball\\DCLBaseball\\src\\Available.txt");
		available.open();
		List<Player> players = Player.getPlayers();
		for (Iterator<Player> i=players.iterator(); i.hasNext(); )
		{
			Player p = i.next();
			System.out.println(p);
		}
	}
}

