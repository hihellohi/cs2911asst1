import java.util.*;

/**
 * Represents a depot in the system
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 */
public class Depot implements Comparable<Depot> {

	String name;
	List<Caravan> vans;
	int order;

	public Depot(String depotName, int order){
		name = depotName;
		vans = new ArrayList<Caravan>();
		this.order = order;
	}

	public void addVan(String vanName, boolean isAuto){
		vans.add(new Caravan(vanName, this, vans.size(), isAuto));		
	}

	public Order.Response request(int reqAutos, int reqManuals){
		List<Booking> bookings = new ArrayList<Booking>();
		return new Order.Response(0, bookings);
	}

	public int compareTo(Depot other){
		return order - other.order;
	}

	@Override public String toString(){
		return name;
	}
}
