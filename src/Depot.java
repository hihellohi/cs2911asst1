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

	public Order.Response request(Interval timeslot, int reqAutos, int reqManuals){
		List<Booking> bookings = new ArrayList<Booking>();

		int nAutos = 0, nManuals = 0;
		Iterator<Caravan> it = vans.iterator();
		while(it.hasNext()){
			Caravan van = it.next();
			if((nAutos < reqAutos || !van.getIsAuto()) 
					&& (nManuals < reqManuals || van.getIsAuto()) 
					&& van.isAvailable(timeslot)){
				bookings.add(new Booking(van, timeslot));
				if(van.getIsAuto()){
					nAutos++;
				}
				else{
					nManuals++;
				}
			}
		}

		return new Order.Response(nAutos, bookings);
	}

	public int compareTo(Depot other){
		return order - other.order;
	}

	public String print(){
		StringBuilder out = new StringBuilder();

		return out.toString();
	}

	@Override public String toString(){
		return name;
	}
}
