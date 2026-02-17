package com.mgroschupf.dcl;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

import com.mgroschupf.flb.MVPs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class DCL extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final String ROOT_DIR = "C:\\Users\\GRO4525\\eclipse-workspace\\DynastyLeagueBaseball\\DynastyLeagueBaseball\\src\\";

	ArrayList<ArrayList<String>> pitcherData = new ArrayList<>();
	ArrayList<ArrayList<String>> hitterData = new ArrayList<>();
	Statistics stats = null;

	JPanel draft = new JPanel();
	JPanel mvp = new JPanel();
	
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

	public DCL() {
		draft.setLayout(new GridLayout(2,1));
		JScrollPane mvpScroll = new JScrollPane(mvp);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Draft", draft);
		tabbedPane.addTab("MVPs", mvpScroll);
		setLayout(new BorderLayout());
		add(tabbedPane, BorderLayout.CENTER);
	}
		
	public void setDraftStats(Statistics stats) {
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
		draft.add(new JScrollPane(pitchers));

		JTable hitters = new JTable(new HitterModel());
		hitters.setPreferredScrollableViewportSize(new Dimension(500, 70));
		hitters.setFillsViewportHeight(true);
		hitters.setAutoCreateColumnsFromModel(true);
		hitters.setAutoCreateRowSorter(true);
		draft.add(new JScrollPane(hitters));
		pack();
	}
	
	public void setMVPStats() {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		final PrintStream originalOut = System.out;
	    System.setOut(new PrintStream(outContent));
		// MVP standings
		MVPs mvps = new MVPs();
		mvps.cyYoung();
		mvps.rolaids();
		mvps.fielders();
		mvps.mvp();
	    System.setOut(originalOut);
	    
	    JTextArea textArea = new JTextArea(outContent.toString());
	    mvp.add(textArea, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		// Ranked players
		// All all = new All("C:\\Users\\Mike\\Documents\\github\\DynastyLeagueBaseball\\DynastyLeagueBaseball\\src\\All.txt");
		// all.open();
		// Players available to draft
		Available available = new Available(ROOT_DIR + "Available.txt");
		available.open();
		// Drafted players
		Selected selected = new Selected(ROOT_DIR + "Selected.txt");
		selected.open();
		// Hitter and Pitcher stats from https://www.rotowire.com/baseball/stats.php
		Statistics stats = new Statistics();
		stats.readHitting(ROOT_DIR + "mlb-player-stats-Batters.txt");
		stats.readPitching(ROOT_DIR + "mlb-player-stats-P.txt");
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
		DCL dcl = new DCL();
		dcl.setDraftStats(stats);
		dcl.setMVPStats();
		
		dcl.setVisible(true);
		dcl.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
