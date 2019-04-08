package com.mgroschupf.dcl2;

import java.io.BufferedReader;
import java.io.FileReader;

public class Available {

	String filename = "C:/Users/Mike/Desktop/Available.csv";
	
	public void open() {
		try {
			BufferedReader br =
				new BufferedReader(new FileReader(filename));
			String line = br.readLine(); // header
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] tokens = line.split(",");
				if (tokens.length > 3) {
					String team = tokens[2];
					String currentTeam = tokens[3];
					// System.out.println(tokens[1] + " " + tokens[2] + " " + team);
					Player.addPlayer(tokens[1], tokens[0], team, currentTeam);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Available all = new Available();
		all.open();
	}
}

