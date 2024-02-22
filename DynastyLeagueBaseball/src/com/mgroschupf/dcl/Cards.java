package com.mgroschupf.dcl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cards {
	/**
	 * Read the card data from the specified file
	 */
	void read(String filename, ArrayList<ArrayList<String>> records) {
		try {
			BufferedReader br =
				new BufferedReader(new FileReader(filename));
			String line = null, year = null, team = null;
			Pattern pattern = Pattern.compile("(\\d\\d\\d\\d) Season");
			while ((line = br.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
				    year = matcher.group(1);
				} else if (year != null && team == null) {
					team = line;
					System.out.println(year + " " + team);
					year = null;
					team = null;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Cards cards = new Cards();
		ArrayList<ArrayList<String>> records = new ArrayList();
		cards.read(DCL.ROOT_DIR + "Cards.txt", records);
    }
}