import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TownGraphManager_STUDENT_Test {
	private TownGraphManager graph;
	private String[] town;
	File inputFile;
	  
	@Before
	public void setUp() throws Exception {
		  graph = new TownGraphManager();
		  town = new String[12];
		  
		  for (int i = 1; i < 12; i++) {
			  town[i] = "Town_" + i;
			  graph.addTown(town[i]);
		  }
		  
		  graph.addRoad(town[1], town[2], 2, "Road_1");
		  graph.addRoad(town[1], town[3], 4, "Road_2");
		  graph.addRoad(town[1], town[5], 6, "Road_3");
		  graph.addRoad(town[3], town[7], 1, "Road_4");
		  graph.addRoad(town[3], town[8], 2, "Road_5");
		  graph.addRoad(town[4], town[8], 3, "Road_6");
		  graph.addRoad(town[6], town[9], 3, "Road_7");
		  graph.addRoad(town[9], town[10], 4, "Road_8");
		  graph.addRoad(town[8], town[10], 2, "Road_9");
		  graph.addRoad(town[5], town[10], 5, "Road_10");
		  graph.addRoad(town[10], town[11], 3, "Road_11");
		  graph.addRoad(town[2], town[11], 6, "Road_12");
		 
	}

	@After
	public void tearDown() throws Exception {
		graph = null;
	}

	@Test
	public void testAddRoad() {
		graph.addRoad(town[4], town[11], 1,"Road_13");
		ArrayList<String> roads = graph.allRoads();
		roads.contains(graph.getRoad("town_4", "town_11"));
		
	}

	@Test
	public void testGetRoad() {
		assertEquals("Road_1", graph.getRoad(town[2], town[1]));
		assertEquals("Road_9", graph.getRoad(town[8], town[10]));
		assertEquals("Road_5", graph.getRoad(town[3], town[8]));
	}

	@Test
	public void testAddTown() {
		assertEquals(false, graph.containsTown("Town_12"));
		graph.addTown("Town_12");
		assertEquals(true, graph.containsTown("Town_12"));
		
		assertEquals(false, graph.containsTown("Town_13"));
		graph.addTown("Town_13");
		assertEquals(true, graph.containsTown("Town_13"));
		
	}
	
	@Test
	public void testDisjointGraph() {
		assertEquals(false, graph.containsTown("Town_12"));
		graph.addTown("Town_12");
		ArrayList<String> path = graph.getPath(town[1],"Town_12");
		assertFalse(path.size()> 0);
	}

	@Test
	public void testContainsTown() {
		assertEquals(true, graph.containsTown("Town_2"));
		assertEquals(true, graph.containsTown("Town_7"));
		assertEquals(false, graph.containsTown("Town_12"));
		assertEquals(false, graph.containsTown("Town_13"));
	}

	@Test
	public void testContainsRoadConnection() {
		assertEquals(true, graph.containsRoadConnection(town[2], town[11]));
		assertEquals(false, graph.containsRoadConnection(town[3], town[5]));
		
		assertEquals(false, graph.containsRoadConnection(town[4], town[11]));
		graph.addRoad(town[4], town[11], 1,"Road_13");
		assertEquals(true, graph.containsRoadConnection(town[4], town[11]));
		
	}

	@Test
	public void testAllRoads() {
		ArrayList<String> roads = graph.allRoads();
		assertEquals(12,roads.size());
	}

	@Test
	public void testDeleteRoadConnection() {
		assertEquals(true, graph.containsRoadConnection(town[2], town[11]));
		graph.deleteRoadConnection(town[2], town[11], "Road_12");
		assertEquals(false, graph.containsRoadConnection(town[2], town[11]));
		
		graph.addRoad(town[4], town[11], 1,"Road_13");
		assertEquals(true, graph.containsRoadConnection(town[4], town[11]));
		graph.deleteRoadConnection(town[4], town[11],"Road_13");
		assertEquals(false, graph.containsRoadConnection(town[4], town[11]));
	}

	@Test
	public void testDeleteTown() {
		assertEquals(true, graph.containsTown("Town_2"));
		graph.deleteTown(town[2]);
		assertEquals(false, graph.containsTown("Town_2"));
		
		assertEquals(true, graph.containsTown("Town_9"));
		graph.deleteTown(town[9]);
		assertEquals(false, graph.containsTown("Town_9"));
	}
	
	
	@Test
	public void testAllTowns() {
		ArrayList<String> towns = graph.allTowns();
		assertEquals(11,towns.size());
	}

	@Test
	public void testGetPath() {
		ArrayList<String> path = graph.getPath(town[1],town[11]);
		  assertNotNull(path);
		  assertTrue(path.size() > 0);
		  assertEquals("Town_1 via Road_1 to Town_2 2 mi",path.get(0).trim());
		  assertEquals("Town_2 via Road_12 to Town_11 6 mi",path.get(1).trim());

	}
	
	@Test
	public void testGetPathA() {
		ArrayList<String> path = graph.getPath(town[1],town[10]);
		  assertNotNull(path);
		  assertTrue(path.size() > 0);
		  assertEquals("Town_1 via Road_2 to Town_3 4 mi",path.get(0).trim());
		  assertEquals("Town_3 via Road_5 to Town_8 2 mi",path.get(1).trim());
		  assertEquals("Town_8 via Road_9 to Town_10 2 mi",path.get(2).trim());
	}
	
	@Test
	public void testGetPathB() {
		ArrayList<String> path = graph.getPath(town[1],town[6]);
		  assertNotNull(path);
		  assertTrue(path.size() > 0);
		  assertEquals("Town_1 via Road_2 to Town_3 4 mi",path.get(0).trim());
		  assertEquals("Town_3 via Road_5 to Town_8 2 mi",path.get(1).trim());
		  assertEquals("Town_8 via Road_9 to Town_10 2 mi",path.get(2).trim());
		  assertEquals("Town_10 via Road_8 to Town_9 4 mi",path.get(3).trim());
		  assertEquals("Town_9 via Road_7 to Town_6 3 mi",path.get(4).trim());
	}
	
	public void getFile(String in) throws FileNotFoundException {		
		JFileChooser chooser = new JFileChooser();
		int status;

		chooser.setDialogTitle("Select Input File - " + in);
		status = chooser.showOpenDialog(null);

		if(status == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				inputFile = chooser.getSelectedFile();
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "There is a problem with this file", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}