package com.mgroschupf.dcl;

import java.util.List;
import java.util.Iterator;

public class DCL {

	public static void main(String[] args) {
		// Ranked players
		All all = new All("C:\\Users\\GRO4525\\Documents\\github\\DynastyLeagueBaseball\\DCLBaseball\\src\\All.txt");
		all.open();
		// Top 300 players available to draft
		Available available = new Available("C:\\Users\\GRO4525\\Documents\\github\\DynastyLeagueBaseball\\DCLBaseball\\src\\Available.txt");
		available.open();
		// Drafted players
		Selected selected = new Selected("C:\\Users\\GRO4525\\Documents\\github\\DynastyLeagueBaseball\\DCLBaseball\\src\\Selected.txt");
		selected.open();
		// Loop through the players and find those that are available
		List<Player> players = Player.getPlayers();
		for (Iterator<Player> i = players.iterator(); i.hasNext(); ) {
			Player p = i.next();
			if (p.isAvailable() && ! p.isSelected()) {
				System.out.println(p);
			}
		}
	}
}
