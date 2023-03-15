package com.mgroschupf.dcl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Statistics {

	ArrayList<ArrayList<String>> hittingRecords = null;
	ArrayList<ArrayList<String>> pitchingRecords = null;

	public ArrayList<String> getPitchingHeader() {
		return pitchingRecords.get(0);
	}
	
	public ArrayList<String> getHittingHeader() {
		return hittingRecords.get(0);
	}
	
	String getStatistic(String header, ArrayList<String> headers, ArrayList<String> record) {
		int index = 0;
		for (Iterator<String> i = headers.iterator(); i.hasNext(); ) {
			String category = i.next();
			if (category.equals(header)) {
				return record.get(index);
			}
			index ++;
		}
		return null;
	}
	
	public ArrayList<String> getPlayerStats(Player player) {
		String name = player.getFirstName() + " " + player.getLastName();
		String team = player.getLastTeam();
		if (!team.equals(player.getTeam())) {
			team += " / " + player.getTeam();
		}
		// String sortableName = player.getLastName() + ", " + player.getFirstName();
		int headerCount = 0;
		if (player.isPitcher()) {
			for (Iterator<ArrayList<String>> i=pitchingRecords.iterator(); i.hasNext(); ) {
				ArrayList<String> record = i.next();
				if (record.get(0).equals(name)) {
					if (player.getRank() == 0) {
						rankPitcher(player, record);
					}
					// record.set(0, sortableName);
					record.set(1, team);
					return record;
				}
			}
			headerCount = getPitchingHeader().size();
		} else {
			for (Iterator<ArrayList<String>> i=hittingRecords.iterator(); i.hasNext(); ) {
				ArrayList<String> record = i.next();
				if (record.get(0).equals(name)) {
					if (player.getRank() == 0) {
						rankHitter(player, record);
					}
					// record.set(0, sortableName);
					record.set(1, team);
					return record;
				}
			}
			headerCount = getHittingHeader().size();
		}
		// Didn't find the player
		ArrayList<String> record = new ArrayList<String>();
		for (int i=0; i < headerCount; i++) {
			record.add(null);
		}
		record.set(0, name);
		record.set(1, team);
		if (! player.isPitcher()) {
			record.set(2, player.getPositions().get(0));
		}
		return record;
	}
	
	void rankPitcher(Player player, ArrayList<String> record) {
		float ip = Float.parseFloat(getStatistic("IP", pitchingRecords.get(0), record));
		int k = Integer.parseInt(getStatistic("K", pitchingRecords.get(0), record));
		int w = Integer.parseInt(getStatistic("W", pitchingRecords.get(0), record));
		int sv = Integer.parseInt(getStatistic("SV", pitchingRecords.get(0), record));
		player.setRank((int) (0.1 * (300 - ip) + 0.25 * (200 - k) + 0.25 * (25 - w) + 0.25 * (40 - sv)));
		record.set(record.size() - 1, "" + player.getRank());
	}
	
	void rankHitter(Player player, ArrayList<String> record) {
		int ab = Integer.parseInt(getStatistic("AB", hittingRecords.get(0), record));
		int r = Integer.parseInt(getStatistic("R", hittingRecords.get(0), record));
		int rbi = Integer.parseInt(getStatistic("RBI", hittingRecords.get(0), record));
		int hr = Integer.parseInt(getStatistic("HR", hittingRecords.get(0), record));
		int sb = Integer.parseInt(getStatistic("SB", hittingRecords.get(0), record));
		player.setRank((int) (0.1 * (650 - ab) + 0.15 * (120 - r) + 0.25 * (150 - rbi) + 0.25 * (50 - hr) + 0.25 * (60 - sb)));
		record.set(record.size() - 1, "" + player.getRank());
	}
	
	public void readHitting(String filename) {
		hittingRecords = new ArrayList<>();
		read(false, filename, hittingRecords);
	}

	public void readHitting(boolean isMLB, String filename) {
		hittingRecords = new ArrayList<>();
		read(isMLB, filename, hittingRecords);
	}
	
	public void readPitching(String filename) {
		pitchingRecords = new ArrayList<>();
		read(false, filename, pitchingRecords);
	}

	void read(boolean isMLB, String filename, ArrayList<ArrayList<String>> records) {
		try {
			BufferedReader br =
				new BufferedReader(new FileReader(filename));
			String line;
			ArrayList<String> headers = null;
			boolean headerFound = false;
			String rank = null, name = null, position = null;
			while ((line = br.readLine()) != null) {
				String [] tokens = line.split("\\t+");
				if (line.startsWith("Player")) {
					// Read headers
					headers = new ArrayList<>(Arrays.asList(tokens));
					// Add a ranking
					headers.add("Rank");
					records.add(headers);
					headerFound = true;
				} else if (line.length() > 0 && headerFound && tokens.length > 0) {
					if (tokens.length == 1) {
						// Rank, name (FirstLast) and position
						if (rank == null) {
							rank = tokens[0]; // Ignore
						} else if (name == null) {
							name = tokens[0];
						} else if (position == null) {
							position = tokens[0];
						}
					} else if (tokens.length > 1) {
						// Read data
						ArrayList<String> data = new ArrayList<>(Arrays.asList(tokens));
						// Add ranking
						data.add("0");
						// Check for name and team
						if (name != null) {
							data.add(0, position);
							data.add(0, parseName(name));
						}
						records.add(data);
						rank = null;
						name = null;
						position = null;
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Avoid class cast exceptions by converting integers to floats
		// if there is data in the column with "." characters.
		boolean[] hasFloat = new boolean[records.get(0).size()];
		for (int col=0; col < hasFloat.length; col++) {
			hasFloat[col] = false;
		}
		for (int row=1; row < records.size(); row++) {
			ArrayList<String> record = records.get(row);
			for (int col=1; (col < record.size()) && !hasFloat[col]; col++) {
				String data = record.get(col);
				if ((data != null) && (data instanceof String) && (data.indexOf('.') > -1)) {
					try {
						Float.parseFloat(data);
						hasFloat[col] = true;
					} catch (Exception ex) {
					}
				}
			}
		}
		for (int row=1; row < records.size(); row++) {
			ArrayList<String> record = records.get(row);
			for (int col=1; col < record.size(); col++) {
				String data = record.get(col);
				if (hasFloat[col] && (data != null) && (data instanceof String) && (data.indexOf('.') < 0)) {
					record.set(col, data + ".0");
				}
			}
		}
	}
	
	String parseName(String name) {
		String newName = "";
		for (int i=0; i < name.length(); i++) {
			if (Character.isUpperCase(name.charAt(i))) {
				newName += " ";
			}
			newName += name.charAt(i);
		}
		return newName.trim();
	}
}
