package com.mgroschupf.daily;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SalaryDatabase {
	
	static String address = 
//	"https://sports.yahoo.com/dailyfantasy/contest/147341/setlineup";
//	"https://sports.yahoo.com/dailyfantasy/contest/161037/setlineup";
//	"https://sports.yahoo.com/dailyfantasy/contest/174344/setlineup";
//	"https://sports.yahoo.com/dailyfantasy/contest/214894/setlineup";
	"https://sports.yahoo.com/dailyfantasy/contest/240483/setlineup";

	List<Player> players = new ArrayList<Player>();
	
	public SalaryDatabase() {
	}

	public void getSalaries() {
		try {
	        // Create a URL for the desired page
	        URL url = new URL(address);       

	        // Read all the text returned by the server
	        BufferedReader in =
	        	new BufferedReader(new InputStreamReader(url.openStream()));
	        String str;
	        while ((str = in.readLine()) != null) {
	        	int index = 0;
	        	while ((index = str.indexOf("\"lastName\":", index + 1)) > -1) {
	        		int comma = str.indexOf(",", index);
        			String lastName = str.substring(index + 12, comma - 1);
        			index = str.indexOf("\"salary\":", index - 1);
        			comma = str.indexOf(",", index);
        			String salary = str.substring(index + 9, comma);
        			index = str.indexOf("\"primaryPosition\":", index + 1);
        			comma = str.indexOf(",", index);
        			String position = str.substring(index + 19, comma - 1);
        			index = str.indexOf("\"projectedPoints\":", index + 1);
        			comma = str.indexOf(",", index);
        			String points = str.substring(index + 18, comma);
        			index = str.indexOf("\"firstName\":", index + 1);
        			comma = str.indexOf(",", index);
        			String firstName = str.substring(index + 13, comma - 1);
        			// System.out.println(position + " " + firstName + " " + lastName + " $" + salary);
        			Player player = new Player();
        			player.setName(firstName + " " + lastName);
        			player.setPosition(position);
        			player.setSalary(Double.parseDouble(salary));
        			player.setValue(Double.parseDouble(points));
        			players.add(player);
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
		SalaryDatabase db = new SalaryDatabase();
		db.getSalaries();
	}
}