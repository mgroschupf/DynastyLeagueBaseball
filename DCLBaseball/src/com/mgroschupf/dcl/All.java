package com.mgroschupf.dcl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

/**
 * All of the players with a ranking, not necessarily available
 * to draft.  Once loaded, the players are available from the
 * Player class.
 */
public class All {

	String filename = null;
	
	public All(String name) {
		filename = name;
	}

	public void open() {
		try {
			BufferedReader br =
				new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				// Rank First Last Team Position
				String [] tokens = line.split("\\t+");
				String rankString = tokens[0];
				int rank = Integer.parseInt(rankString);
				String team = tokens[2];
				String position = tokens[3];
				String [] name = tokens[1].trim().split("\\s+");
				// System.out.println(tokens[0] + "|" + tokens[1] + "|" + tokens[2] + "|" + tokens[3]);
				Player.addPlayer(name[0], name[1], rank, team, position);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		All all = new All("C:\\Users\\GRO4525\\Documents\\github\\DynastyLeagueBaseball\\DCLBaseball\\src\\All.txt");
		all.open();
		List<Player> players = Player.getPlayers();
		for (Iterator<Player> i=players.iterator(); i.hasNext(); )
		{
			Player p = i.next();
			System.out.println(p);
		}
	}
}

