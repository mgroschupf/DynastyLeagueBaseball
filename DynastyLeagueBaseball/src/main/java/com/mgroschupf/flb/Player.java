package com.mgroschupf.flb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public abstract class Player {
	
	public static int BATTER = 0;
	public static int PITCHER = 1;
	public static int FIELDER = 2;
	
	public static String NAME = "Name";
	public static String NAME_LINK = "NameLink";
	
	Hashtable<String, String> stats = new Hashtable<String, String>();
	
	int getInt(String key) {
		if (stats.get(key) == null) {
			return 0;
		}
		return Integer.parseInt(stats.get(key));
	}
	
	double getDouble(String key) {
		if (stats.get(key) == null) {
			return 0.0;
		}
		return Double.parseDouble(stats.get(key));
	}
	
	public String toString() {
		return getName();
	}
	
	public String getNum() {
		String idNum = null;
		String nameLink = stats.get(NAME_LINK);
		int beginIndex = nameLink.indexOf("PlayerID=");
		int endIndex = nameLink.indexOf("&", beginIndex);
		if (beginIndex > -1 && endIndex > -1) {
			idNum = nameLink.substring(beginIndex + 9, endIndex);
		}
		return idNum;
	}
	
	public Set<String> getKeys() {
		return stats.keySet();
	}
	
	public String getStat(String key) {
		return stats.get(key);
	}
	
	public String getName() {
		return getStat(NAME);
	}

	static String[] splitOnCommas(String input) {
		List<String> result = new ArrayList<String>();
		int start = 0;
		boolean inQuotes = false;
		for (int current = 0; current < input.length(); current++) {
		    if (input.charAt(current) == '\"') inQuotes = !inQuotes; // toggle state
		    boolean atLastChar = (current == input.length() - 1);
		    if(atLastChar) result.add(input.substring(start));
		    else if (input.charAt(current) == ',' && !inQuotes) {
		        result.add(input.substring(start, current));
		        start = current + 1;
		    }
		}
		return result.toArray(new String[0]);
	}
	
	String trim(String input) {
		String output = input.trim();

		if (output.startsWith("'") || output.startsWith("\"")) {
			output = output.substring(1);
			if (output.endsWith("'") || output.endsWith("\"")) {
				output = output.substring(0, output.length() - 1);
			}
		}
		return output;
	}
	
	public Player(String playerData) {
		String[] tuples = splitOnCommas(playerData);
		for (int t = 0; t < tuples.length; t++) {
			String[] tuple = tuples[t].split(":");
			if (tuple.length == 2) {
				String key = trim(tuple[0]);
				String value = trim(tuple[1]);
				stats.put(key, value);
			}
		}
	}
}
