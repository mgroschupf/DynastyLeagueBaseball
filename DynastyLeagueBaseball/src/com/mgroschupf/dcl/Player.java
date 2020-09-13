package com.mgroschupf.dcl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Player {
	String lastName;
	String firstName;
	String teamName;
	String lastTeam;
	ArrayList<String> positions = new ArrayList<String>();
	int rank;
	boolean selected = false;
	boolean available = false;

	static ArrayList<Player> players = new ArrayList<Player>();
	
	public List<String> getPositions() {
		return positions;
	}
	
	public class PlayerComparator implements Comparator<Player>
	{
		public int compare(Player a, Player b) {
			if (a == b) {
				return 0;
			}
			if (a == null) {
				return -1;
			}
			if (b == null) {
				return 1;
			}
			if (a.getRank() == b.getRank()) {
				return 0;
			}
			if (b.getRank() == 0) {
				return -1;
			}
			if (a.getRank() == 0) {
				return 1;
			}
			return a.getRank() - b.getRank();
		}
	}

	/**
	 * Return players, sorted by rank.
	 * Unranked players (rank 0) go to the end of the list.
	 * @return
	 */
	public static List<Player> getPlayers() {
		if (! players.isEmpty()) {
			Player player = players.get(0);
			Collections.sort(players, player.new PlayerComparator());
		}
		return players;
	}
	
	/**
	 * Create a new player if they don't already exist.
	 * @param firstName
	 * @param lastName
	 * @param rank
	 * @param teamName
	 * @return
	 */
	public static Player addPlayer(String firstName, String lastName, int rank, String teamName, String position) {
		Player newPlayer = new Player(firstName, lastName, rank, teamName, position);
		Player player = Player.findPlayer(newPlayer);
		if (player == null) {
			players.add(newPlayer);
			return newPlayer;
		} else {
			List<String> positions = player.getPositions();
			if (! positions.contains(position)) {
				player.getPositions().add(position);
			}
		}
		return player;
	}
	
	public String toString() {
		return rank + ") " + lastName + ", " + firstName + " - " + positions + " (" + teamName + " / " + lastTeam + ")";
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public boolean isSelected() {
		return selected;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	/**
	 * Return the rank of the player, starting with 1 as
	 * highest ranked.  A rank of 0 means they are unranked.
	 * @return
	 */
	public int getRank() {
		return rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public boolean isPitcher() {
		String position = positions.get(0);
		if (position.startsWith("P") || position.startsWith("SP") || position.startsWith("RP")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Last year's team with card.
	 * @param team
	 */
	public void setLastTeam(String team) {
		lastTeam = team;
	}
	
	public String getLastTeam() {
		return lastTeam;
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Player)) {
			return false;
		}
		Player player = (Player) obj;
		if (lastName.equalsIgnoreCase(player.getLastName()) && firstName.equalsIgnoreCase(player.getFirstName())) {
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

	public Player(String firstName, String lastName, int rank, String teamName, String position) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.rank = rank;
		this.teamName = teamName;
		this.positions.add(position);
	}
}
