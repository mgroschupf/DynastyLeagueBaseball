package com.mgroschupf.dcl2;

import java.io.BufferedReader;
import java.io.FileReader;

public class Batting {

	String filename = "C:/Users/Mike/Desktop/Batting.csv";
	String YEAR = "2016";
	
	public void open() {
		try {
			BufferedReader br =
				new BufferedReader(new FileReader(filename));
			String line = br.readLine(); // header
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] tokens = line.split(",");
				if (tokens.length > 16) {
					String year = tokens[1];
					// System.out.println(tokens[1] + " " + tokens[2] + " " + team);
					if (YEAR.equals(year)) {
						Player.addPlayer(tokens[0], tokens[3], tokens[5], tokens[6], tokens[7], tokens[8], tokens[11], tokens[12], tokens[13]);
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Batting all = new Batting();
		all.open();
	}
}

