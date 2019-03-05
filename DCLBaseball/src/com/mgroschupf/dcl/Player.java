package com.mgroschupf.dcl;

import java.util.ArrayList;
import java.util.List;

public class Player {
	String lastName;
	String firstName;
	String teamName;
	boolean selected = false;

	static ArrayList<Player> players = new ArrayList<Player>();
	
	public static List<Player> getPlayers() {
		return players;
	}
	
	public static void addPlayer(String firstName, String lastName, String teamName) {
		Player player = new Player(firstName, lastName, teamName);
		players.add(player);
	}
	
	public String toString() {
		return lastName + ", " + firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Player)) {
			return false;
		}
		Player player = (Player) obj;
		if (lastName.equalsIgnoreCase(player.getLastName())) {
			return true;
		}
		return false;
	}
	
	public static Player findPlayer(Player player) {
		int i = players.indexOf(player);
		if (i < 0) {
			return null;
		}
		return players.get(i);
	}

	public Player(String firstName, String lastName, String teamName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.teamName = teamName;
	}
}
