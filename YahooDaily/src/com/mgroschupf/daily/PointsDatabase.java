package com.mgroschupf.daily;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PointsDatabase {
	
	static String offenseAddress =
	// "http://football.fantasysports.yahoo.com/f1/1030995/players?&sort=PR&sdir=1&status=ALL&pos=O&stat1=S_PW_6&jsenabled=1";
	"http://football.fantasysports.yahoo.com/f1/1030995/players?&sort=PR&sdir=1&status=ALL&pos=O&stat1=S_PW_8&jsenabled=1";
	
	static String defenseAddress =
	"http://football.fantasysports.yahoo.com/f1/1030995/players?&sort=PR&sdir=1&status=ALL&pos=DEF&stat1=S_PW_8&jsenabled=1";
	
	List<Player> players = new ArrayList<Player>();
	
	public PointsDatabase() {
	}

	public void getPoints(String address) {
		try {
	        // Create a URL for the desired page
	        URL url = new URL(address);       

	        // Read all the text returned by the server
	        BufferedReader in =
	        	new BufferedReader(new InputStreamReader(url.openStream()));
	        String str;
	        int index;
	        Player player;
	        while ((str = in.readLine()) != null) {
	        	// System.out.println(str);
	        	if ((index = str.indexOf("<span class=\"Fz-xxs\">")) > -1) {
	        		index += 21;
	        		int endIndex = str.indexOf(" -", index);
	        		String teamName = str.substring(index, endIndex);
	        		player = new Player();
	        		// System.out.print(teamName);
	        	} else if ((index = str.indexOf("<span class=\"Fw-b\">")) > -1) {
	        		index += 19;
	        		int endIndex = str.indexOf("<", index);
	        		String points = str.substring(index, endIndex);
	        		System.out.println(" " + points);
	        	}
	        }
	        in.close();
	    } catch (MalformedURLException e) {
	    } catch (IOException e) {
	    }
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public static void main(String[] args) {
		PointsDatabase db = new PointsDatabase();
		db.getPoints(defenseAddress);
	}
}