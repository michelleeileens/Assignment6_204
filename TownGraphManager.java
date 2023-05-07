import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class TownGraphManager implements TownGraphManagerInterface{

	Graph graph;
	
	public TownGraphManager() {
		graph = new Graph();
	}

	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		Set<Town> towns = graph.vertexSet();
		Town town = null;
		Town town0 = null;
		for(Town t : towns) {
			if(t.getName().equalsIgnoreCase(town1)) 
				town = t;
			if(t.getName().equalsIgnoreCase(town2)) 
				town0 = t;
			if(town != null && town0 != null) 
				break;
		}
		Road r = graph.addEdge(town, town0, weight, roadName);
		if(r == null)
			return false;
		return true;
	}

	@Override
	public String getRoad(String town1, String town2) {
		Set<Town> towns = graph.vertexSet();
		Town town = null;
		Town town0 = null;
		for(Town t : towns) {
			if(t.getName().equalsIgnoreCase(town1)) 
				town = t;
			if(t.getName().equalsIgnoreCase(town2)) 
				town0 = t;
			if(town != null && town0 != null)
				break;
		}
		Road r = graph.getEdge(town, town0);
		if(r == null)
			return null;
		return r.getName();
	}

	@Override
	public boolean addTown(String v) {
		Town town = new Town(v);
		return graph.addVertex(town);
	}

	@Override
	public Town getTown(String name) {
		Set<Town> towns = new HashSet<Town>(graph.vertexSet());
		Iterator<Town> it = towns.iterator();
		while(it.hasNext()) {
			Town t = it.next();
			if(t.getName().equalsIgnoreCase(name))
				return t;
		}
		return null;
	}

	@Override
	public boolean containsTown(String v) {
		Town t = new Town(v);
		return graph.containsVertex(t);
	}

	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		Town town = new Town(town1);
		Town town0 = new Town(town2);
		return graph.containsEdge(town, town0);
	}

	@Override
	public ArrayList<String> allRoads() {
		Set<Road> roads = graph.edgeSet();
		ArrayList<String> allRoads = new ArrayList<String>();
		for(Road road : roads) {
			allRoads.add(road.getName());
		}
		Collections.sort(allRoads);
		return allRoads;
	}

	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		Road roadY;
		Town town = new Town(town1);
		Town town0 = new Town(town2);
		Road roadX = graph.getEdge(town, town0);
		roadY = graph.removeEdge(town, town0, roadX.getWeight(), road);
		if(roadY != null)
			return true;
		return false;
	}

	@Override
	public boolean deleteTown(String v) {
		Town town = new Town(v);
		return graph.removeVertex(town);
	}

	@Override
	public ArrayList<String> allTowns() {
		Set<Town> towns = graph.vertexSet();
		ArrayList<String> allTowns = new ArrayList<String>();
		for(Town town : towns) {
			allTowns.add(town.getName());
		}
		Collections.sort(allTowns);
		return allTowns;
	}

	@Override
	public ArrayList<String> getPath(String town1, String town2) {
		Set<Town> towns = graph.vertexSet();
		Set<Road> roads = graph.edgeSet();
		boolean foundSource = false;
		boolean foundDest = false;
		Town town = null;
		Town town0 = null;
		ArrayList<String> path = new ArrayList<String>();
		try {
			for(Town t : towns) {
				if(t.getName().equalsIgnoreCase(town1)) {
					town = t;
				}
				if(t.getName().equalsIgnoreCase(town2)) {
					town0 = t;
				}
				if(town != null && town0 != null) {
					break;
				}
			}
			for(Road r : roads) {
				if(r.getSource().compareTo(town) == 0 || r.getDestination().compareTo(town) == 0) {
					foundSource = true;
				}
				if(r.getSource().compareTo(town0) == 0 || r.getDestination().compareTo(town0) == 0) {
					foundDest = true;
				}
				if(foundSource == true && foundDest == true) {
					path = graph.shortestPath(town, town0);
					break;
				}
			}
		}
		catch(NullPointerException n) {	
		}
		
		return path;
	}

//	public void populateTownGraph(File selectedFile) throws FileNotFoundException, IOException{
//		Scanner in = new Scanner(selectedFile);
//		Town origin, destination;
//		String line = "";
//		String[] splitLine;
//		while(in.hasNext()) {
//			line = in.nextLine();
//			splitLine = line.split("[,;]");
//			destination = new Town(splitLine[3]);
//			origin = new Town(splitLine[2]);
//			graph.addVertex(origin);
//			graph.addVertex(destination);
//			graph.addEdge(origin, destination, Integer.parseInt(splitLine[1]), splitLine[0]);
//			
//		}
//		
//	}

}