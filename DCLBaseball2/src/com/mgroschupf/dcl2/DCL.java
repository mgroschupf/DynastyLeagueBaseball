package com.mgroschupf.dcl2;

import java.util.List;
import java.util.Iterator;

public class DCL {

	public static void main(String[] args) {
		Available avail = new Available();
		avail.open();
		Batting batting = new Batting();
		batting.open();
		Pitching pitching = new Pitching();
		pitching.open();
		Appearances appearances = new Appearances();
		appearances.open();
		
		//System.out.println("Player\tID\tPositions\tCard Team\tCurrent Team\tScore\tAB/IP\tAvg/ERA" +
		//		"\tRBI/W\tHR/SV\tSB/SO");
		System.out.println("Player\tID\tPositions\tCard Team\tCurrent Team\tScore\t" +
				"IP\tERA\tW\tSV\tSO\t" + "AB\tAvg\tRBI\tHR\tSB");
		for (Iterator<Player> p = Player.getPlayers().iterator(); p.hasNext(); ) {
			Player player = p.next();
			if (player.isAvailable()) {
				List<Player> l = Player.findStats(player);
				for (Iterator<Player> s = l.iterator(); s.hasNext(); ) {
					Player stats = s.next();
					System.out.print(player + "\t" + stats.getPlayerID() + "\t" + stats.getPositionString() + "\t"
							+ player.getTeamName() + "\t" + player.getCurrentTeam() + "\t" + stats.getScore());
					//if ("P".equals(stats.getPositionString())) {
						System.out.print("\t" + stats.getInningsPitched());
						System.out.print("\t" + stats.getERA());
						System.out.print("\t" + stats.getWins());
						System.out.print("\t" + stats.getSaves());
						System.out.print("\t" + stats.getStrikeOuts());
					//} else {
						System.out.print("\t" + stats.getAtBats());
						System.out.print("\t" + stats.getAverage());
						System.out.print("\t" + stats.getRBIs());
						System.out.print("\t" + stats.getHomeRuns());
						System.out.print("\t" + stats.getStolenBases());
					//}
					System.out.println();
				}
			}
		}
	}
}
