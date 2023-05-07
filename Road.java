
public class Road implements Comparable<Road>{
	
	private Town origin;
	private Town destination;
	private int weight;
	private String name;
	
	public Road(Town source, Town destination, int degrees, String name) {
		origin = source;
		this.destination = destination;
		weight = degrees;
		this.name = name;
	}

	public Road(Town source, Town destination, String name) {
		this(source,destination,1,name);
	}

	public Town getSource() {
		return origin;
	}
	
	public Town getDestination() {
		return destination;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean contains(Town town) {
		return ((town.getName().equalsIgnoreCase(origin.getName())) || (town.getName().equalsIgnoreCase(destination.getName())));
	}
	
	@Override
	public int compareTo(Road o) {
		return name.compareToIgnoreCase(o.getName());
	}
	
	@Override
	public boolean equals(Object r) {
		if(!(r instanceof Road)) 
			return false;
		else {
			String rOrigin = ((Road)r).getSource().getName();
			String rDestination = ((Road)r).getDestination().getName();
			if((origin.getName().equalsIgnoreCase(rOrigin) || origin.getName().equalsIgnoreCase(rDestination)) 
					&& (destination.getName().equalsIgnoreCase(rOrigin)
					|| destination.getName().equalsIgnoreCase(rDestination)))
				return true;
			return false;
		}
	}
	
	@Override
	public String toString() {
		return name;
	}

}