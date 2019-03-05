package com.mgroschupf.dcl;

import java.util.List;
import java.util.Iterator;

public class DCL {

	public static void main(String[] args) {
		All all = new All();
		all.open();
		Selected selected = new Selected();
		selected.open();
		
		List<Player> players = Player.getPlayers();
		for (Iterator<Player> i = players.iterator(); i.hasNext(); ) {
			Player p = i.next();
			if (! p.isSelected()) {
				System.out.println("Not selected: " + p);
			}
		}
	}
}
