package com.mgroschupf.dcl;

import java.io.BufferedReader;
import java.io.FileReader;

public class All {

	String filename = "C:/Users/Mike/workspace/DCLBaseball/src/All.txt";
	
	public void open() {
		try {
			BufferedReader br =
				new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] tokens = line.split("\\s+");
				if (tokens.length > 3) {
					String team = tokens[4];
					for(int i=5; i < tokens.length; i++) {
						team += " " + tokens[i];
					}
					// System.out.println(tokens[1] + " " + tokens[2] + " " + team);
					Player.addPlayer(tokens[1], tokens[2], team);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		All all = new All();
		all.open();
	}
}

