package com.mgroschupf.flb;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MVPs {
	
	League league = null;
	List<Stats> allStats = new ArrayList<Stats>();
 	
	public MVPs() {
		league = new League();
		league.connect();
	}
	
	/**
	 * Load the league data from HTML files instead of online.
	 */
	public MVPs(List<File> leagueData) {
		league = new League(leagueData);
	}

	class Stats {
		private ArrayList<Object> statLine;
		
		public ArrayList<Object> getStatLine() {
			return statLine;
		}
		
		public Object get(int index) {
			return statLine.get(index);
		}
		
		public void addPoints(double points) {
			Double total = (Double) statLine.get(1) + points;
			statLine.set(1, total);
		}

		public Stats(ArrayList<Object> statLine) {
			this.statLine = statLine;
		}
	}

	class SortByColumn implements Comparator<Stats> {
		private int column;
		private boolean ascending;

		public SortByColumn(int column, boolean ascending) {
			this.column = column;
			this.ascending = ascending;
		}

		public int compare(Stats arg0, Stats arg1) {
			Object stat1 = arg0.get(column);
			Object stat2 = arg1.get(column);
			if (! ascending) {
				Object temp = stat2;
				stat2 = stat1;
				stat1 = temp;
			}
			if (stat1 instanceof Integer) {
				int val = ((Integer) stat1).compareTo((Integer) stat2);
				if (val != 0) {
					return val;
				}
				return ((Player) arg0.get(0)).getName().compareTo(((Player) arg1.get(0)).getName());
			}
			if (stat1 instanceof Double) {
				int val = ((Double) stat1).compareTo((Double) stat2);
				if (val != 0) {
					return val;
				}
				return ((Player) arg0.get(0)).getName().compareTo(((Player) arg1.get(0)).getName());
			}
			return 0;
		}
	}
	
	public void rank(ArrayList<Stats> array, int index, boolean ascending, double points) {
		Collections.sort(array, new SortByColumn(index, ascending));
		int counter = array.size();
		for (Iterator<Stats> s = array.iterator(); s.hasNext(); ) {
			Stats stats = s.next();
			stats.addPoints(points * counter);
			counter --;
		}
	}

	void printStats(List<Stats> stats, String label, boolean isCatcher) {
		System.out.println("\n" + label);
		String header = "Player                           Score      GS    RBIs      HR      SB     Avg  Fd Pct";
		if (isCatcher) {
			header += "  CS Pct";
		} else {
			header += "   Range";
		}
		System.out.println(header);
		for (Iterator<Stats> i = stats.iterator(); i.hasNext(); ) {
			Stats s = i.next();
			String line =
					String.format("%-30s %7.2f %7d %7d %7d %7d %7.3f %7.3f",
							s.get(0), s.get(1), s.get(2), s.get(3), s.get(4), s.get(5), s.get(6), s.get(7));
			if (isCatcher) {
				line += String.format(" %7.3f", s.get(9));
			} else {
				line += String.format(" %7.3f", s.get(8));
			}
			System.out.println(line);
		}
	}
	
	public void fielders() {
		List<Fielder> fielders = league.getFielders();
		
		ArrayList<Stats> catcher = new ArrayList<Stats>();
		ArrayList<Stats> firstbase = new ArrayList<Stats>();
		ArrayList<Stats> secondbase = new ArrayList<Stats>();
		ArrayList<Stats> thirdbase = new ArrayList<Stats>();
		ArrayList<Stats> shortstop = new ArrayList<Stats>();
		ArrayList<Stats> outfield = new ArrayList<Stats>();

		for (Iterator<Fielder> f = fielders.iterator(); f.hasNext(); ) {
			Fielder fielder = f.next();
			Batter batter = league.getBatter(fielder);
			if (batter == null) {
				System.out.println("Batter not found for " + fielder);
				continue;
			}
			ArrayList<Object> player = new ArrayList<Object>();
			player.add(fielder);
			int games = fielder.getGamesStarted();
			int rbi = batter.getRBIs();
			int hr = batter.getHomeRuns();
			int sb = batter.getStolenBases();
			double avg = batter.getBattingAvg();
			double fpct = fielder.getFieldingPct();
			double rf = fielder.getRangeFactor();
			double csPct = fielder.getCaughtStealingPct();

			double score =
					100.0 * (rbi / 100.0) + 50.0 * (hr / 40.0) + 50.0 * (sb / 40.0) + 75.0 * (avg / 0.300);
			
			if (fielder.getPosition() == Fielder.CATCHER) {
				score = score + 100.0 * (csPct / 0.5) + 50.0 * (fpct / 1.0);
			} else {
				score = score + 50.0 * (rf / 20.0) + 100.0 * (fpct / 1.0);
			}
			player.add(score);
			player.add(games);
			player.add(rbi);
			player.add(hr);
			player.add(sb);
			player.add(avg);
			player.add(fpct);
			player.add(rf);
			player.add(csPct);
			
			boolean ignore = false;
			Stats stats = new Stats(player);
			if (fielder.getPosition() == Fielder.CATCHER) {
				catcher.add(stats);
			} else if (fielder.getPosition() == Fielder.FIRST_BASE) {
				firstbase.add(stats);
			} else if (fielder.getPosition() == Fielder.SECOND_BASE) {
				secondbase.add(stats);
			} else if (fielder.getPosition() == Fielder.THIRD_BASE) {
				thirdbase.add(stats);
			} else if (fielder.getPosition() == Fielder.SHORT_STOP) {
				shortstop.add(stats);
			} else if (fielder.getPosition() == Fielder.LEFT_FIELD || fielder.getPosition() == Fielder.CENTER_FIELD || fielder.getPosition() == Fielder.RIGHT_FIELD){
				outfield.add(stats);
			} else {
				ignore = true;
			}
			
			if (!ignore) {
				allStats.add(stats);
			}
		}
		
		// Final rank
		rank(catcher, 1, false, 0.0);
		rank(firstbase, 1, false, 0.0);
		rank(secondbase, 1, false, 0.0);
		rank(thirdbase, 1, false, 0.0);
		rank(shortstop, 1, false, 0.0);
		rank(outfield, 1, false, 0.0);
		
		printStats(catcher, "Catcher (top 3)", true);
		printStats(firstbase, "First Base (top 3)", false);
		printStats(secondbase, "Second Base (top 3)", false);
		printStats(thirdbase, "Third Base (top 3)", false);
		printStats(shortstop, "Shortstop (top 3)", false);
		printStats(outfield, "Outfield (top 9)", false);
	}

	public void cyYoung() {
		List<Pitcher> pitchers = league.getPitchers();
		ArrayList<Stats> starters = new ArrayList<Stats>();

		for (Iterator<Pitcher> p = pitchers.iterator(); p.hasNext(); ) {
			Pitcher pitcher = p.next();
			ArrayList<Object> player = new ArrayList<Object>();
			player.add(pitcher);
			int wins = pitcher.getWins();
			int losses = pitcher.getLosses();
			double era = pitcher.getERA();
			int cg = pitcher.getCompleteGames();
			double whip = pitcher.getWHIP();
			int k = pitcher.getStrikeOuts();
			player.add(100.0 * (wins / 23.0) - 20.0 * (losses / 6.0) + 50.0 * (7.0 - era) / 7.0 + 50.0 * (cg / 5.0) +
					   40.0 * (3.0 - whip) / 3.0 + 40 * (k / 200.0));
			player.add(wins);
			player.add(losses);
			player.add(era);
			player.add(cg);
			player.add(whip);
			player.add(k);
			if (wins > 0 || losses > 0) {
				Stats stats = new Stats(player);
				starters.add(stats);
				allStats.add(stats);
			}
		}
		
		rank(starters, 1, false, 0.0); // Final rank
		
		System.out.println("\nCy Young Award (top 5)");
		System.out.println("Player                           Score    Wins  Losses    ERA       CG    WHIP      SO");
		for (Iterator<Stats> r = starters.iterator(); r.hasNext(); ) {
			Stats starter = r.next();
			String line =
					String.format("%-30s %7.2f %7d %7d %7.2f %7d %7.2f %7d",
							starter.get(0), starter.get(1), starter.get(2), starter.get(3), starter.get(4), starter.get(5),
							starter.get(6), starter.get(7));
			System.out.println(line);
		}
	}
	
	public void rolaids() {
		List<Pitcher> pitchers = league.getPitchers();
		ArrayList<Stats> relievers = new ArrayList<Stats>();

		for (Iterator<Pitcher> p = pitchers.iterator(); p.hasNext(); ) {
			Pitcher pitcher = p.next();
			ArrayList<Object> player = new ArrayList<Object>();
			player.add(pitcher);
			int saves = pitcher.getSaves();
			int blownSaves = pitcher.getBlownSaves();
			player.add(100.0 * (saves / 23.0) - 20.0 * (blownSaves / 6.0)); // Score
			player.add(saves);
			player.add(blownSaves);
			if (saves > 0 || blownSaves > 0) {
				Stats stats = new Stats(player);
				relievers.add(stats);
			}
		}
		
		//rank(relievers, 2, false, 1.0);
		//rank(relievers, 3, true, 0.5);
		rank(relievers, 1, false, 0.0); // Final rank
		
		System.out.println("\nRolaids Relief (top 3)");
		System.out.println("Player                           Score   Saves      BS");
		for (Iterator<Stats> r = relievers.iterator(); r.hasNext(); ) {
			Stats reliever = r.next();
			String line = String.format("%-30s %7.2f %7d %7d", reliever.get(0), reliever.get(1), reliever.get(2), reliever.get(3));
			System.out.println(line);
		}
	}
	
	public void mvp() {
		ArrayList<Stats> stats = (ArrayList) allStats;
		rank(stats, 1, false, 0.0);
		System.out.println("\nMVP (top 10)");
		System.out.println("Player                           Score");
		for (Iterator<Stats> s = stats.iterator(); s.hasNext(); ) {
			Stats stat = s.next();
			String line = String.format("%-30s %7.2f", stat.get(0), stat.get(1));
			System.out.println(line);
		}
	}

	public static void main(String[] args) {
		MVPs mvps = new MVPs();
		mvps.cyYoung();
		mvps.rolaids();
		mvps.fielders();
		mvps.mvp();
	}
}
