import java.util.ArrayList;


public class Town implements Comparable <Town>{

	private String name;
	private ArrayList<Town> adjTowns;
	
	public Town(String name) {
		this.name = name;
		adjTowns = new ArrayList<Town>();
	}
	
	public Town(Town templateTown) {
		this(templateTown.getName());
		setAdjacentTowns(templateTown.getAdjacentTowns());
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		Town t = (Town)obj;
		return this.name.equals(t.getName());
	}

	public void setAdjacentTowns(ArrayList<Town> towns) {
		for(int i = 0; i < towns.size(); i++) {
			adjTowns.add(towns.get(i));
		}
	}
	
	public void addAdjacentTowns(Town town) {
		adjTowns.add(town);
	}
	
	public ArrayList<Town> getAdjacentTowns(){
		return adjTowns;
	}
	
	@Override
	public int compareTo(Town o) {
		if(o.getName().equalsIgnoreCase(name))
			return 0;
		return name.compareToIgnoreCase(o.getName());
	}
	
	public int hashCode() {
		return name.hashCode();
	}

	public String toString() {
		return this.name;
	}

}