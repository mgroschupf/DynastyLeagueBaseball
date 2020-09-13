package com.mgroschupf.dcl;

import java.io.BufferedReader;
import java.io.FileReader;

public class Selected {

	String filename = null;
	
	public Selected(String name) {
		filename = name;
	}

	public void open() {
		try {
			BufferedReader br =
				new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				String[] tokens = line.split("\\s+");
				if (tokens.length > 0) {
					String lastName = tokens[0];
					if (lastName.length() > 2) {
						// Might be a last name
						if (Character.isUpperCase(lastName.charAt(0))) {
							if (Character.isLowerCase(lastName.charAt(1))) {
								// System.out.println(lastName);
								Player player = new Player(tokens[1], lastName, 0, null, null);
								Player p = Player.findPlayer(player);
								if (p != null) {
									p.setSelected(true);
								} else {
									System.out.println("Couldn't find: " + player);
								}
							}
						}
					} else if (lastName.length() == 2) {
						if (lastName.charAt(1) == '.') {
							// lastName was the first initial
							String firstInitial = lastName.substring(0, 1);
							if (tokens.length > 1) {
								lastName = tokens[1];
								// System.out.println(lastName);
								Player player = new Player(firstInitial, lastName, 0, null, null);
								Player p = Player.findPlayer(player);
								if (p != null) {
									p.setSelected(true);
								} else {
									System.out.println("Couldn't find: " + player);
								}
							}
						}
					}
				}
			}
			br.close();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Selected selected = new Selected("C:\\Users\\Mike\\Documents\\GitHub\\DynastyLeagueBaseball\\DynastyLeagueBaseball\\src\\Selected.txt");
		selected.open();
	}
}

