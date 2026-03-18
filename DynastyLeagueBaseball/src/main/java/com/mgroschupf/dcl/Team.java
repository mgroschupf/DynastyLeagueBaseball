package com.mgroschupf.dcl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

/**
 * The players on my team.
 */
public class Team {

	String filename = null;
	
	public Team(String name) {
		filename = name;
	}

	public void open() {
		try {
			InputStream in = this.getClass().getResourceAsStream("/" + filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
            String position = "";
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] tokens = line.split("\\t+");
				
                if (tokens.length == 1) {
                    position = tokens[0];
                    if (position.startsWith("RH")) {
                        position = "RHP";
                    } else if (position.startsWith("LH")) {
                        position = "LHP";
                    } else if (position.startsWith("C")) {
                        position = "C";
                    } else if (position.startsWith("Out")) {
                        position = "OF";
                    } else if (position.startsWith("In")) {
                        position = "INF";
                    }
                } else if ((tokens.length == 4) && ! tokens[0].startsWith("Player")) {
					Player p = null;
                    String note = tokens[3];
                    String[] names = tokens[0].split(" ");
                    p = Player.addPlayer(names[0], names[1], 0, tokens[2], position, note);
                    p.setLastTeam(tokens[1]);
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
		Team team = new Team("Team.txt");
		team.open();
		List<Player> players = Player.getPlayers();
		for (Iterator<Player> i=players.iterator(); i.hasNext(); )
		{
			Player p = i.next();
			System.out.println(p);
		}
	}
}
