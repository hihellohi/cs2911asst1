import java.util.*;

/**
 * Represents a caravan in the system
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 */
public class Caravan implements Comparable<Caravan> {

	String name;
	int order;
	Depot depot;
	boolean isAuto;

	public Caravan(String caravanname, Depot depot, int order, boolean isAuto){
		name = caravanname;
		this.order = order;
		this.depot = depot;
		this.isAuto = isAuto;
	}

	@Override public String toString(){
		return name;
	}

	public int compareTo(Caravan other){
		return order - other.order;
	}
	
	public boolean getIsAuto(){
		return isAuto;
	}
}
