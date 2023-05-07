import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Graph_STUDENT_Test {
	private GraphInterface<Town,Road> graph;
	private Town[] town;

	@Before
	public void setUp() throws Exception {
		 graph = new Graph();
		  town = new Town[12];
		  
		  for (int i = 1; i < 12; i++) {
			  town[i] = new Town("Town_" + i);
			  graph.addVertex(town[i]);
		  }
		  
		  graph.addEdge(town[1], town[2], 2, "Road_1");
		  graph.addEdge(town[1], town[3], 4, "Road_2");
		  graph.addEdge(town[1], town[5], 6, "Road_3");
		  graph.addEdge(town[3], town[7], 1, "Road_4");
		  graph.addEdge(town[3], town[8], 2, "Road_5");
		  graph.addEdge(town[4], town[8], 3, "Road_6");
		  graph.addEdge(town[6], town[9], 3, "Road_7");
		  graph.addEdge(town[9], town[10], 4, "Road_8");
		  graph.addEdge(town[8], town[10], 2, "Road_9");
		  graph.addEdge(town[5], town[10], 5, "Road_10");
		  graph.addEdge(town[10], town[11], 3, "Road_11");
		  graph.addEdge(town[2], town[11], 6, "Road_12");
	}

	@After
	public void tearDown() throws Exception {
		graph = null;
	}

	@Test
	public void testGetEdge() {
		assertEquals(new Road(town[4], town[8],3, "Road_6"), graph.getEdge(town[4], town[8]));
		assertEquals(new Road(town[5], town[10],5, "Road_10"), graph.getEdge(town[5], town[10]));
		assertEquals(new Road(town[6], town[9],3, "Road_7"), graph.getEdge(town[6], town[9]));
	}

	@Test
	public void testAddEdge() {
		assertEquals(false, graph.containsEdge(town[4], town[7]));
		graph.addEdge(town[4], town[7], 10, "Road_13");
		assertEquals(true, graph.containsEdge(town[4], town[7]));
		
		assertEquals(false, graph.containsEdge(town[9], town[11]));
		graph.addEdge(town[9], town[11], 7, "Road_14");
		assertEquals(true, graph.containsEdge(town[9], town[11]));
	}

	@Test
	public void testAddVertex() {
		Town newTown = new Town("Town_12");
		assertEquals(false, graph.containsVertex(newTown));
		graph.addVertex(newTown);
		assertEquals(true, graph.containsVertex(newTown));
		
		Town newTown1 = new Town("Town_13");
		assertEquals(false, graph.containsVertex(newTown1));
		graph.addVertex(newTown1);
		assertEquals(true, graph.containsVertex(newTown1));
	}

	@Test
	public void testContainsEdge() {
		assertEquals(true, graph.containsEdge(town[2], town[11]));
		assertEquals(false, graph.containsEdge(town[3], town[5]));
		assertEquals(true, graph.containsEdge(town[9], town[6]));
		assertEquals(false, graph.containsEdge(town[6], town[10]));
	}

	@Test
	public void testContainsVertex() {
		assertEquals(true, graph.containsVertex(new Town("Town_2")));
		assertEquals(false, graph.containsVertex(new Town("Town_12")));
		assertEquals(false, graph.containsVertex(new Town("McIntosh city")));
	}

	@Test
	public void testEdgeSet() {
		Set<Road> roads = graph.edgeSet();
		ArrayList<String> roadArrayList = new ArrayList<String>();
		for(Road road : roads)
			roadArrayList.add(road.getName());
		Collections.sort(roadArrayList);
		assertEquals("Road_1", roadArrayList.get(0));
		assertEquals("Road_10", roadArrayList.get(1));
		assertEquals("Road_11", roadArrayList.get(2));
		assertEquals("Road_12", roadArrayList.get(3));
		assertEquals("Road_2", roadArrayList.get(4));
		assertEquals("Road_8", roadArrayList.get(10));
		
		Graph graph1=new Graph();
		Road Road1=new Road(town[4],town[7],6,"Road1");
		Road Road2=new Road(town[6],town[10],7,"Road2");
		graph1.addVertex(town[4]);
		graph1.addVertex(town[7]);
		graph1.addVertex(town[10]);
		graph1.addVertex(town[6]);
		graph1.addEdge(town[4],town[7],6,"Road1");
		graph1.addEdge(town[6],town[10],7,"Road2");
		Set<Road>roadSet=graph1.edgeSet();
		ArrayList<String> roadsL=new ArrayList<String>();
		for(Road road : roadSet)
			roadsL.add(road.getName());
		Collections.sort(roadsL);
		assertEquals("Road1", roadsL.get(0));
		assertEquals("Road2", roadsL.get(1));

		
	}

	@Test
	public void testEdgesOf() {
		Set<Road> roads = graph.edgesOf(town[1]);
		ArrayList<String> roadArrayList = new ArrayList<String>();
		for(Road road : roads)
			roadArrayList.add(road.getName());
		Collections.sort(roadArrayList);
		assertEquals("Road_1", roadArrayList.get(0));
		assertEquals("Road_2", roadArrayList.get(1));
		assertEquals("Road_3", roadArrayList.get(2));
		
		Graph graph1=new Graph();
		graph1.addVertex(town[4]);
		graph1.addVertex(town[7]);
		graph1.addVertex(town[10]);
		graph1.addVertex(town[6]);
		graph1.addVertex(town[9]);
		graph1.addEdge(town[4],town[7],6,"Road1");
		graph1.addEdge(town[6],town[10],7,"Road2");
		graph1.addEdge(town[6],town[9],8,"Road3");
		Set<Road>roadSet=graph1.edgesOf(town[6]);
		ArrayList<String> roadsL=new ArrayList<String>();
		for(Road road : roadSet)
			roadsL.add(road.getName());
		Collections.sort(roadsL);
		assertEquals("Road2", roadsL.get(0));
		assertEquals("Road3", roadsL.get(1));
	}
	
	@Test
	public void testRemoveEdge() {
		assertEquals(true, graph.containsEdge(town[2], town[11]));
		graph.removeEdge(town[2], town[11], 6, "Road_12");
		assertEquals(false, graph.containsEdge(town[2], town[11]));
		
		Graph graph1=new Graph();
		graph1.addVertex(town[4]);
		graph1.addVertex(town[7]);
		graph1.addVertex(town[10]);
		graph1.addVertex(town[6]);
		graph1.addVertex(town[9]);
		graph1.addEdge(town[4],town[7],6,"Road1");
		graph1.addEdge(town[6],town[10],7,"Road2");
		graph1.addEdge(town[6],town[9],8,"Road3");
		
		assertEquals(true, graph1.containsEdge(town[4], town[7]));
		graph1.removeEdge(town[4], town[7], 6, "Road_1");
		assertEquals(false, graph1.containsEdge(town[4], town[7]));
	}
	
	@Test
	public void testRemoveVertex() {
		assertEquals(true, graph.containsVertex(town[2]));
		graph.removeVertex(town[2]);
		assertEquals(false, graph.containsVertex(town[2]));
		
		Graph graph1=new Graph();
		graph1.addVertex(town[4]);
		graph1.addVertex(town[7]);
		graph1.addVertex(town[10]);
		graph1.addVertex(town[6]);
		graph1.addVertex(town[9]);
		graph1.addEdge(town[4],town[7],6,"Road1");
		graph1.addEdge(town[6],town[10],7,"Road2");
		graph1.addEdge(town[6],town[9],8,"Road3");
		
		assertEquals(true, graph1.containsVertex(town[7]));
		graph1.removeVertex(town[7]);
		assertEquals(false, graph1.containsVertex(town[7]));
		
	}

	@Test
	public void testVertexSet() {
		Set<Town> towns = graph.vertexSet();
		assertEquals(true,towns.contains(town[1]));
		assertEquals(true, towns.contains(town[10]));
		assertEquals(true, towns.contains(town[11]));
		assertEquals(true, towns.contains(town[2]));
		assertEquals(true, towns.contains(town[3]));
		
		Graph graph1=new Graph();
		Town town1=new Town("Chicago");
		Town town2=new Town("Gotham");
		Town town3=new Town("Star City");
		graph1.addVertex(town1);
		graph1.addVertex(town2);
		graph1.addVertex(town3);
		Set<Town> townSet=graph1.vertexSet();
		assertEquals(true,townSet.contains(town1));
		assertEquals(true, townSet.contains(town2));
		assertEquals(true, townSet.contains(town3));
	}

	 @Test
	  public void testTown_1ToTown_11() {
		  String beginTown = "Town_1", endTown = "Town_11";
		  Town beginIndex=null, endIndex=null;
		  Set<Town> towns = graph.vertexSet();
		  Iterator<Town> iterator = towns.iterator();
		  while(iterator.hasNext())
		  {    	
			  Town town = iterator.next();
			  if(town.getName().equals(beginTown))
				  beginIndex = town;
			  if(town.getName().equals(endTown))
				  endIndex = town;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = graph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);
			  assertEquals("Town_1 via Road_1 to Town_2 2 mi",path.get(0).trim());
			  assertEquals("Town_2 via Road_12 to Town_11 6 mi",path.get(1).trim());
		  }
		  else
			  fail("Town names are not valid");

	  }
	  
	  
	  @Test
	  public void testTown1ToTown_10() {
		  String beginTown = "Town_1", endTown = "Town_10";
		  Town beginIndex=null, endIndex=null;
		  Set<Town> towns = graph.vertexSet();
		  Iterator<Town> iterator = towns.iterator();
		  while(iterator.hasNext())
		  {    	
			  Town town = iterator.next();
			  if(town.getName().equals(beginTown))
				  beginIndex = town;
			  if(town.getName().equals(endTown))
				  endIndex = town;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = graph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);
			  assertEquals("Town_1 via Road_2 to Town_3 4 mi",path.get(0).trim());
			  assertEquals("Town_3 via Road_5 to Town_8 2 mi",path.get(1).trim());
			  assertEquals("Town_8 via Road_9 to Town_10 2 mi",path.get(2).trim());
		  }
		  else
			  fail("Town names are not valid");

	  }
	  
	  @Test
	  public void testTown_4ToTown_11() {
		  String beginTown = "Town_4", endTown = "Town_11";
		  Town beginIndex=null, endIndex=null;
		  Set<Town> towns = graph.vertexSet();
		  Iterator<Town> iterator = towns.iterator();
		  while(iterator.hasNext())
		  {    	
			  Town town = iterator.next();
			  if(town.getName().equals(beginTown))
				  beginIndex = town;
			  if(town.getName().equals(endTown))
				  endIndex = town;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = graph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);
			  assertEquals("Town_4 via Road_6 to Town_8 3 mi",path.get(0).trim());
			  assertEquals("Town_8 via Road_9 to Town_10 2 mi",path.get(1).trim());
			  assertEquals("Town_10 via Road_11 to Town_11 3 mi",path.get(2).trim());
		  }
		  else
			  fail("Town names are not valid");

	  }
}
