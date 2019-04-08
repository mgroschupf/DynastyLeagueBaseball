package com.mgroschupf.daily;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class YahooDaily {
	
	int maxTries = 50000000;
	double salaryCap = 0.0;
	List<Player> players = new ArrayList<Player>();
	List<Position> positions = new ArrayList<Position>();
	
	public Position findPosition(String positionName) {
		for (Iterator<Position> i = positions.iterator(); i.hasNext(); ) {
			Position p = i.next();
			if (p.getName().equalsIgnoreCase(positionName)) {
				return p;
			}
		}
		return null;
	}

	public void loadFile(String file) {
		InputStream in = YahooDaily.class.getResourceAsStream(file);
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(isr);
		String line;
		boolean header = true;
		try {
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("$")) {
					salaryCap = Double.parseDouble(line.substring(1));
				} else if ((line == null) || (line.trim().length() == 0)) {
					header = false;
				} else if (header) {
					String[] fields = line.split(",");
					Position position = new Position();
					position.setName(fields[0]);
					if (fields[1].matches("[0-9]+")) {
						position.setNumber(Integer.parseInt(fields[1]));
					} else {
						List<Position> posList = new ArrayList<Position>();
						for (int i = 1; i < fields.length - 1; i++) {
							String posName = fields[i];
							posList.add(findPosition(posName));
						}
						position.setNumber(Integer.parseInt(fields[fields.length - 1]));
						position.setPositions(posList);
					}
					positions.add(position);
				} else {
					Player player = new Player();
					String[] fields = line.split(",");
					player.setPosition(fields[0]);
					player.setName(fields[1]);
					player.setSalary(Double.parseDouble(fields[2].substring(1)));
					player.setValue(Double.parseDouble(fields[3]));
					players.add(player);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getBestRandomLineup() {
		double highValue = 0;
		for (int tries = 0; tries < maxTries; tries ++) {
			List<Player> lineup = new ArrayList<Player>();
			double payroll = 0;
			double value = 0;
			for (Iterator<Position> i = positions.iterator(); i.hasNext(); ) {
				Position position = i.next();
				int count = position.getNumber();
				long seed = System.nanoTime();
				Collections.shuffle(players, new Random(seed));
				for (Iterator<Player> j = players.iterator(); j.hasNext(); ) {
					Player player = j.next();
					if (count > 0) {
						if (position.getName().equals(player.getPosition())) {
							count --;
							lineup.add(player);
						} else if (position.getPositions().size() > 0) {
							for (Iterator<Position> k = position.getPositions().iterator(); k.hasNext(); ) {
								Position pos2 = k.next();
								if (pos2.getName().equals(player.getPosition())) {
									if (! lineup.contains(player)) {
										count --;
										lineup.add(player);
									}
								}
							}
						}
					}
				}
			}
			String output = "";
			for (Iterator<Player> i = lineup.iterator(); i.hasNext(); ) {
				Player player = i.next();
				payroll += player.getSalary();
				value += player.getValue();
				output += "\n" + player.getPosition() + " " + player.getName() + " " + player.getValue() + " $" + player.getSalary();
			}
			if ((payroll <= salaryCap) && (value > highValue)) {
				System.out.println(output);
				System.out.println("Payroll = $" + payroll);
				System.out.println("Value = " + value);
				highValue = value;
			}
		}
	}

	public boolean positionFilled(Position position, List<Player> lineup) {
		int count = position.getNumber();
		for (Iterator<Player> i = lineup.iterator(); i.hasNext(); ) {
			Player player = i.next();
			if (player.getPosition().equals(position.getName())) {
				count --;
			}
		}
		if (count == 0) {
			return true;
		}
		return false;
	}
	
	public double getBestLineup(List<Player> currentLineup, double highValue) {
		boolean playerAdded = false;
		List<Player> lineup = new ArrayList<Player>();
		lineup.addAll(currentLineup);
		for (Iterator<Position> i = positions.iterator(); i.hasNext() && ! playerAdded; ) {
			Position position = i.next();
			if (! positionFilled(position, lineup)) {
				for (Iterator<Player> j = players.iterator(); j.hasNext(); ) {
					Player player = j.next();
					if ((player.getValue() > 0) && !lineup.contains(player) && position.getName().equals(player.getPosition())) {
						lineup.add(player);
						playerAdded = true;
						highValue = getBestLineup(lineup, highValue);
						String output = "";
						double payroll = 0, value = 0;
						for (Iterator<Player> l = lineup.iterator(); l.hasNext(); ) {
							Player p = l.next();
							payroll += p.getSalary();
							value += p.getValue();
							output += "\n" + p.getPosition() + " " + p.getName() + " " + p.getValue() + " $" + p.getSalary();
						}
						if ((payroll <= salaryCap) && (value > highValue)) {
							System.out.println(output);
							System.out.println("Payroll = $" + payroll);
							System.out.println("Value = " + value);
							highValue = value;
						}
						lineup.remove(player);
					}
				}
			}
		}
		return highValue;
	}
	
	public void getBestLineup() {
		getBestLineup(new ArrayList<Player>(), 0.0);
	}

	public static void main(String[] args) {
		YahooDaily yd = new YahooDaily();
		// yd.loadFile("stats.txt");
		yd.salaryCap = 200;
		Position pos;
		List<Position> positionList = new ArrayList<Position>();
		yd.positions.add(new Position("QB", 1));
		yd.positions.add(pos = new Position("RB", 2));
		positionList.add(pos);
		yd.positions.add(pos = new Position("WR", 3));
		positionList.add(pos);
		yd.positions.add(pos = new Position("TE", 1));
		positionList.add(pos);
		yd.positions.add(pos = new Position("FLEX", 1));
		pos.setPositions(positionList);
		yd.positions.add(new Position("DEF", 1));

		SalaryDatabase db = new SalaryDatabase();
		db.getSalaries();
		yd.players = db.getPlayers();
		yd.getBestRandomLineup();
		// yd.getBestLineup();
	}
}
