package com.mgroschupf.flb;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MVPsTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private String baseline = "";
	private String filePath = "dcl2019.txt";
	File folder = new File(getClass().getClassLoader().getResource("resources").getFile());

	@Before
	public void setUp() throws Exception {
		// Redirect standard output 
	    //System.setOut(new PrintStream(outContent));
	    
	    // Read baseline output
		try 
		{
			baseline = new String(Files.readAllBytes(Paths.get(folder.getAbsolutePath(), filePath)));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		// Reset standard output
	    System.setOut(originalOut);
	}

	@Test
	public void testMain() {
		ArrayList<File> leagueData = new ArrayList<File>();
		// Add league page
		leagueData.add(new File(folder, "league.htm"));
		// Testing 12 teams in 2019 league
		for (int i=1; i < 13; i++) {
			String teamName = "team" + i + ".htm";
			String battingStats = "team" + i + "batting.htm";
			String pitchingStats = "team" + i + "pitching.htm";
			String fieldingStats = "team" + i + "fielding.htm";
			leagueData.add(new File(folder, teamName));
			leagueData.add(new File(folder, battingStats));
			leagueData.add(new File(folder, pitchingStats));
			leagueData.add(new File(folder, fieldingStats));
		}
		MVPs mvps = new MVPs(leagueData);
		mvps.cyYoung();
		mvps.rolaids();
		mvps.fielders();

	    assertEquals(baseline, outContent.toString());
	}
}
