import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Comparator;

public class Graph implements GraphInterface<Town,Road>{
	ArrayList<Town> towns;
	static ArrayList<LinkedList> edges;
	Map<Town,Town> previousNode = new HashMap<>();
	
	public Graph() {
		towns = new ArrayList<Town>();
		edges = new ArrayList<LinkedList>();
	}
	
	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		Road r = new Road(sourceVertex, destinationVertex,"r");
		int sourceIndex = 0;
		int destinationIndex = 0;
		boolean foundSource = false;
		boolean foundDest = false;
		if(sourceVertex == null || destinationVertex == null) 
			return null;
		
		for(int i = 0; i < towns.size(); i++) {
			if(towns.get(i).compareTo(sourceVertex) == 0) {
				sourceIndex = i;
				foundSource = true;
			}
			if(towns.get(i).compareTo(destinationVertex) == 0) {
				destinationIndex = i;
				foundDest = true;
			}
			if(foundSource == true && foundDest == true)
				break;
		}

		ListIterator<Road> it = edges.get(sourceIndex).listIterator(0);
		while(it.hasNext()) {
			Road road = it.next();
			if(r.equals(road))
				return road;
		}		
		return null;
	}

	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		int sourceIndex = 0;
		int destinationIndex = 0;
		boolean foundSource = false;
		boolean foundDest = false;
		
		if(sourceVertex == null || destinationVertex == null) {
			throw new NullPointerException();
		}
		Road edge = new Road(sourceVertex, destinationVertex,weight,description);
		
		for(int i = 0; i < towns.size(); i++) {
			if(towns.get(i).compareTo(sourceVertex) == 0) {
				sourceIndex = i;
				foundSource = true;
			}
			if(towns.get(i).compareTo(destinationVertex) == 0) {
				destinationIndex = i;
				foundDest = true;
			}
			if(foundSource == true && foundDest == true) {
				break;
			}
		}
		
		if(foundSource == false || foundDest == false) {
			throw new IllegalArgumentException();
		}
		else {
			if(!edges.get(destinationIndex).contains(edge) && !edges.get(sourceIndex).contains(edge)) {
				edges.get(sourceIndex).add(edge);
				edges.get(destinationIndex).add(edge);
				sourceVertex.addAdjacentTowns(destinationVertex);
				destinationVertex.addAdjacentTowns(sourceVertex);
				return edge;
			}
		}
		return null;
	}

	@Override
	public boolean addVertex(Town v) {
		for(int i = 0; i < towns.size(); i++) {
			if(towns.get(i).compareTo(v) == 0) {
				return false;
			}
		}
		towns.add(v);
		if(edges.size() == 0) {
			edges.add(new LinkedList<Road>());
		}
		else {
			edges.add(new LinkedList<Road>());
		}
		return true;
	}

	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		int sourceIndex = 0;
		int destinationIndex = 0;
		boolean foundSource = false;
		boolean foundDest = false;
		for(int i = 0; i < towns.size(); i++) {
			if(towns.get(i).compareTo(sourceVertex) == 0) {
				sourceIndex = i;
				foundSource = true;
			}
			if(towns.get(i).compareTo(destinationVertex) == 0) {
				destinationIndex = i;
				foundDest = true;
			}
			if(foundSource == true && foundDest == true) {
				break;
			}
		}
		
		if(foundSource == true && foundDest == true) {
			ListIterator<Road> it = edges.get(sourceIndex).listIterator(0);
			while(it.hasNext()) {
				Road r = it.next();
				if(r.contains(sourceVertex) && r.contains(destinationVertex)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean containsVertex(Town v) {
		for(int i = 0; i < towns.size(); i++) {
			if(towns.get(i).compareTo(v) == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Road> edgeSet() {
		Set<Road> roads = new HashSet<Road>();
		for(int i = 0; i < edges.size(); i++) {
			ListIterator<Road> it = edges.get(i).listIterator(0);
			while(it.hasNext()) {
				Road r = it.next();
				if(!roads.contains(r)) {
					roads.add(r);
				}
			}
		}
		return roads;
	}

	@Override
	public Set<Road> edgesOf(Town vertex) {
		int index = 0;
		Set<Road> roads = new HashSet<Road>();
		for(int i = 0; i < towns.size(); i++) {
			if(towns.get(i).compareTo(vertex)==0) {
				index = i;
			}
		}
		ListIterator<Road> it = edges.get(index).listIterator(0);
		while(it.hasNext()) {
			Road r = it.next();
			if(!roads.contains(r)) {
				roads.add(r);
			}
		}
		return roads;
	}

	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		int sourceIndex = 0, destinationIndex = 0;
		Road road = new Road(sourceVertex, destinationVertex,weight,description);
		if(containsEdge(sourceVertex,destinationVertex)) {
			for(int i = 0; i < towns.size(); i++) {
				boolean foundSource = false;
				if(towns.get(i).compareTo(sourceVertex) == 0) {
					sourceIndex = i;
					foundSource = true;
				}
				boolean foundDest = false;
				if(towns.get(i).compareTo(destinationVertex) == 0) {
					destinationIndex = i;
					foundDest = true;
				}
				if(foundSource == true && foundDest == true) {
					break;
				}
			}
			
			ListIterator<Road> it = edges.get(sourceIndex).listIterator(0);
			ListIterator<Road> it2 = edges.get(destinationIndex).listIterator(0);
			while(it.hasNext()) {
				Road road2 = it.next();
				if(road2.equals(road)) {
					if(edges.get(sourceIndex).remove(road2)) {
						break;
					}
				}
			}
			
			while(it2.hasNext()) {
				Road road2 = it2.next();
				if(road2.equals(road)) {
					if(edges.get(destinationIndex).remove(road2)) {
						break;
					}
				}
			}
			return road;
		}
		return null;
	}

	@Override
	public boolean removeVertex(Town v) {
		int townIndex = 0;
		if(containsVertex(v)) {
			for(int i = 0; i < towns.size(); i++) {
				if(towns.get(i).compareTo(v) == 0) {
					townIndex = i;
					break;
				}
			}
			for(int i = 0; i < v.getAdjacentTowns().size(); i++) {
				Road r = getEdge(v.getAdjacentTowns().get(i),v);
				if(containsEdge(v.getAdjacentTowns().get(i),v)) {
					removeEdge(v.getAdjacentTowns().get(i),v,r.getWeight(),r.getName());
				}
			}
			towns.remove(townIndex);
			edges.remove(townIndex);
			return true;
		}
		return false;
	}

	@Override
	public Set<Town> vertexSet() {
		Set<Town> town = new HashSet<Town>();
		for(int i = 0; i < towns.size(); i++) {
			if(!town.contains(towns.get(i))) {
				town.add(towns.get(i));
			}
		}
		return town;
	}

	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		ArrayList<String> path = new ArrayList<String>();
		String edgeName;
		String path1 = "";
		dijkstraShortestPath(sourceVertex);
		Town currentTown = destinationVertex;
		Town previousTown = previousNode.get(destinationVertex);
		while(currentTown.compareTo(sourceVertex)!=0) {
			edgeName = getEdge(currentTown,previousTown).getName();
			path1 = previousTown.getName() + " via " + edgeName+ " to " + currentTown.getName()+
					" " + getEdge(currentTown,previousTown).getWeight() + " mi";
			path.add(0,path1);
			currentTown = previousTown;
			previousTown = previousNode.get(currentTown);
		}
		return path;
	}

	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		Map<Town, Integer>pathWeight = new HashMap<>();
		Set<Town> visitedTown = new HashSet<>();
		Comparator<Road> comparator = new Comparator<Road>() {	
		@Override
		public int compare(Road r1, Road r2) {
			return r1.getWeight() - r2.getWeight();	
		}
		};
		PriorityQueue<Road> minPQ = new PriorityQueue<>(comparator);
		int edgeDist,newDist;
		
		minPQ.addAll(sortEdges(sourceVertex)); 
		visitedTown.add(sourceVertex);
		pathWeight.put(sourceVertex, 0);
		
		while(!minPQ.isEmpty()) {
			Town town1,town2;
			Road smallR = minPQ.remove();
			town1 = smallR.getSource();
			town2 = smallR.getDestination();
			if(!visitedTown.contains(town1)) {
				visitedTown.add(town1);
				for(Road r:sortEdges(town1)) {
					if(!minPQ.contains(r)) {
						minPQ.add(r);
					}
				}
				previousNode.put(town1, town2);
				edgeDist = pathWeight.get(town2);
				newDist = edgeDist+smallR.getWeight();
				pathWeight.put(town1, newDist);
			}
			if(!visitedTown.contains(town2)){
				visitedTown.add(town2);
				for(Road r : sortEdges(town2)) {
					if(!minPQ.contains(r)) {
						minPQ.add(r);
					}
				}
				previousNode.put(town2, town1);
				edgeDist = pathWeight.get(town1);
				newDist = edgeDist + smallR.getWeight();
				pathWeight.put(town2, newDist);
			}
			if(visitedTown.contains(town1)) {
				edgeDist = pathWeight.get(town1);
				newDist = pathWeight.get(town2) + smallR.getWeight();
				if(newDist < edgeDist) {
					pathWeight.put(town1, newDist);
					previousNode.put(town1, town2);
				}
			}
			if(visitedTown.contains(town2)) {
				edgeDist = pathWeight.get(town2);
				newDist = pathWeight.get(town1) + smallR.getWeight();
				if(newDist < edgeDist) {
					pathWeight.put(town2, newDist);
					previousNode.put(town2, town1);
				}
			}
			else {
				continue;
			}
		}
	}
	
	public ArrayList<Road> sortEdges(Town sourceVertex){
		ArrayList<Road> sort = new ArrayList<Road>();
		Road r = null;
		int townIndex = 0;
		for(int i = 0; i < towns.size(); i++) {
			if(towns.get(i).compareTo(sourceVertex)==0) {
				townIndex = i;
				break;
			}
		}
		Iterator<Road> it = edges.get(townIndex).iterator();
		while(it.hasNext()) {
			if(sort.size() == 0) {
				sort.add(it.next());
			}
			else {
				r = it.next();
				sort.add(r);
			}
		}
		return sort;
	}
	
	

}