package com.mgroschupf.dcl;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

public class DCL extends JFrame {

	private static final long serialVersionUID = 1L;
	
	ArrayList<ArrayList<String>> pitcherData = new ArrayList<>();
	ArrayList<ArrayList<String>> hitterData = new ArrayList<>();
	Statistics stats = null;
	
	public Object getValue(String value) {
		if (value != null && value.length() > 0) {
			if (Character.isDigit(value.charAt(0)) && Character.isDigit(value.charAt(value.length() - 1))) {
				if (value.contains(".")) {
					return Float.parseFloat(value);
				} else {
					return Integer.parseInt(value);
				}
			}
		}
		return value;
	}
	
	class PitcherModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		public int getColumnCount() {
			return pitcherData.get(0).size();
		}

		public int getRowCount() {
			return pitcherData.size();
		}

		public Object getValueAt(int arg0, int arg1) {
			try {
				pitcherData.get(arg0).get(arg1);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("arg0: " + arg0 + ", arg1: " + arg1);
				System.out.println(pitcherData.get(arg0));
				System.out.println("Pitcher data size: " + pitcherData.size());
				System.out.println("Pitcher data width: " + pitcherData.get(arg0).size());
			}
			return getValue(pitcherData.get(arg0).get(arg1));
		}
		
		public String getColumnName(int col) {
			return stats.getPitchingHeader().get(col);
		}
		
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
	}
	
	class HitterModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		public int getColumnCount() {
			return hitterData.get(0).size();
		}

		public int getRowCount() {
			return hitterData.size();
		}

		public Object getValueAt(int arg0, int arg1) {
			return getValue(hitterData.get(arg0).get(arg1));
		}

		public String getColumnName(int col) {
			return stats.getHittingHeader().get(col);
		}
		
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
	}

	public DCL(Statistics stats) {
		setLayout(new GridLayout(2,1));
		
		this.stats = stats;
		List<Player> players = Player.getPlayers();
		for (Iterator<Player> i = players.iterator(); i.hasNext(); ) {
			Player p = i.next();
			if (p.isAvailable()) {
				if (! p.isSelected()) {
					if (p.isPitcher()) {
						pitcherData.add(stats.getPlayerStats(p));
					} else {
						hitterData.add(stats.getPlayerStats(p));
					}
				}
			}
		}
		
		JTable pitchers = new JTable(new PitcherModel());
		pitchers.setPreferredScrollableViewportSize(new Dimension(500, 70));
		pitchers.setFillsViewportHeight(true);
		pitchers.setAutoCreateColumnsFromModel(true);
		pitchers.setAutoCreateRowSorter(true);
		add(new JScrollPane(pitchers));

		JTable hitters = new JTable(new HitterModel());
		hitters.setPreferredScrollableViewportSize(new Dimension(500, 70));
		hitters.setFillsViewportHeight(true);
		hitters.setAutoCreateColumnsFromModel(true);
		hitters.setAutoCreateRowSorter(true);
		add(new JScrollPane(hitters));
		pack();
	}

	public static void main(String[] args) {
		// Ranked players
		All all = new All("C:\\Users\\GRO4525\\Documents\\github\\DynastyLeagueBaseball\\DCLBaseball\\src\\All.txt");
		// All all = new All("C:\\Users\\Mike\\git\\DynastyLeagueBaseball\\DCLBaseball\\src\\All.txt");
		all.open();
		// Top 300 players available to draft
		Available available = new Available("C:\\Users\\GRO4525\\Documents\\github\\DynastyLeagueBaseball\\DCLBaseball\\src\\Available.txt");
		// Available available = new Available("C:\\Users\\Mike\\git\\DynastyLeagueBaseball\\DCLBaseball\\src\\Available.txt");
		available.open();
		// Drafted players
		Selected selected = new Selected("C:\\Users\\GRO4525\\Documents\\github\\DynastyLeagueBaseball\\DCLBaseball\\src\\Selected.txt");
		// Selected selected = new Selected("C:\\Users\\Mike\\git\\DynastyLeagueBaseball\\DCLBaseball\\src\\Selected.txt");
		selected.open();
		// Hitter and Pitcher stats from https://www.rotowire.com/baseball/stats.php
		Statistics stats = new Statistics();
		stats.readHitting("C:\\Users\\GRO4525\\Documents\\github\\DynastyLeagueBaseball\\DCLBaseball\\src\\mlb-player-stats-Batters.csv");
		stats.readPitching("C:\\Users\\GRO4525\\Documents\\github\\DynastyLeagueBaseball\\DCLBaseball\\src\\mlb-player-stats-P.csv");
		// Loop through the players and rank those that are available
		List<Player> players = Player.getPlayers();
		for (Iterator<Player> i = players.iterator(); i.hasNext(); ) {
			Player p = i.next();
			if (p.isAvailable()) {
				if (! p.isSelected()) {
					stats.getPlayerStats(p);
				}
			}
		}
		// Print the available players, now sorted by rank
		players = Player.getPlayers();
		for (Iterator<Player> i = players.iterator(); i.hasNext(); ) {
			Player p = i.next();
			if (p.isAvailable()) {
				if (! p.isSelected()) {
					System.out.println(p + " " + stats.getPlayerStats(p));
				} else {
					// System.out.println("***" + p);
				}
			}
		}
		// Display
		DCL dcl = new DCL(stats);
		dcl.setVisible(true);
		dcl.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
