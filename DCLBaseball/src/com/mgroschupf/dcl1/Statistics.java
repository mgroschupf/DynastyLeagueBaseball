package com.mgroschupf.dcl1;

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
		int headerCount = 0;
		if (player.isPitcher()) {
			for (Iterator<ArrayList<String>> i=pitchingRecords.iterator(); i.hasNext(); ) {
				ArrayList<String> record = i.next();
				if (record.get(0).equals(name)) {
					if (player.getRank() == 0) {
						rankPitcher(player, record);
					}
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
		record.set(0, player.getFirstName() + " " + player.getLastName());
		record.set(1, player.getLastTeam());
		if (! player.isPitcher()) {
			record.set(2, player.getPositions().get(0));
		}
		return record;
	}
	
	void rankPitcher(Player player, ArrayList<String> record) {
		float ip = Float.parseFloat(getStatistic("IP", pitchingRecords.get(0), record));
		int k = Integer.parseInt(getStatistic("K", pitchingRecords.get(0), record));
		player.setRank((int) (0.5 * (300 - ip) + 0.5 * (200 - k)));
		record.set(record.size() - 1, "" + player.getRank());
	}
	
	void rankHitter(Player player, ArrayList<String> record) {
		float ab = Float.parseFloat(getStatistic("AB", hittingRecords.get(0), record));
		int rbi = Integer.parseInt(getStatistic("RBI", hittingRecords.get(0), record));
		player.setRank((int) (0.5 * (650 - ab) + 0.5 * (150 - rbi)));
		record.set(record.size() - 1, "" + player.getRank());
	}
	
	public void readHitting(String filename) {
		hittingRecords = new ArrayList<>();
		read(filename, hittingRecords);
	}
	
	public void readPitching(String filename) {
		pitchingRecords = new ArrayList<>();
		read(filename, pitchingRecords);
	}

	void read(String filename, ArrayList<ArrayList<String>> records) {
		try {
			BufferedReader br =
				new BufferedReader(new FileReader(filename));
			String line;
			ArrayList<String> headers = null;
			boolean headerFound = false;
			while ((line = br.readLine()) != null) {
				String [] tokens = line.split("\\t+");
				if (line.startsWith("Player")) {
					// Read headers
					headers = new ArrayList<>(Arrays.asList(tokens));
					// Add a ranking
					headers.add("Rank");
					records.add(headers);
					headerFound = true;
				} else if (line.length() > 0 && headerFound && tokens.length > 1) {
					// Read data
					ArrayList<String> data = new ArrayList<>(Arrays.asList(tokens));
					// Add ranking
					data.add("0");
					records.add(data);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
