package com.mgroschupf.dcl2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

public class Appearances {

	String filename = "C:/Users/Mike/Desktop/Appearances.csv";
	String YEAR = "2016";
	
	public void open() {
		try {
			BufferedReader br =
				new BufferedReader(new FileReader(filename));
			String line = br.readLine(); // header
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] tokens = line.split(",");
				if (tokens.length > 10) {
					String year = tokens[0];
					// System.out.println(tokens[1] + " " + tokens[2] + " " + team);
					if (YEAR.equals(year)) {
						int p = Integer.parseInt(tokens[8]);
						int c = Integer.parseInt(tokens[9]);
						int b1 = Integer.parseInt(tokens[10]);
						int b2 = Integer.parseInt(tokens[11]);
						int b3 = Integer.parseInt(tokens[12]);
						int ss = Integer.parseInt(tokens[13]);
						int lf = Integer.parseInt(tokens[14]);
						int cf = Integer.parseInt(tokens[15]);
						int rf = Integer.parseInt(tokens[16]);
						int of = Integer.parseInt(tokens[17]);
						int dh = Integer.parseInt(tokens[18]);

						String playerID = tokens[3];
						List<Player> players = Player.findPlayers(playerID);
						for (Iterator<Player> i = players.iterator(); i.hasNext(); ) {
							Player player = i.next();
							if (p > 9) {
								player.addPosition("P");
							}
							if (c > 9) {
								player.addPosition("C");
							}
							if (b1 > 9) {
								player.addPosition("1B");
							}
							if (b2 > 9) {
								player.addPosition("2B");
							}
							if (b3 > 9) {
								player.addPosition("3B");
							}
							if (ss > 9) {
								player.addPosition("SS");
							}
							if (lf > 9) {
								player.addPosition("LF");
							}
							if (cf > 9) {
								player.addPosition("CF");
							}
							if (rf > 9) {
								player.addPosition("RF");
							}
							if (of > 9) {
								player.addPosition("OF");
							}
							if (dh > 9) {
								player.addPosition("DH");
							}
						}
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Appearances all = new Appearances();
		all.open();
	}
}

