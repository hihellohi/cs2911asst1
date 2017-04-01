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
	SortedSet<Interval> bookings;

	public Caravan(String caravanname, Depot depot, int order, boolean isAuto){
		name = caravanname;
		this.order = order;
		this.depot = depot;
		this.isAuto = isAuto;
		bookings = new TreeSet<Interval>();
	}
	
	public boolean isAvailable(Interval timeslot){
		SortedSet<Interval> head = bookings.headSet(timeslot);
		if(head.size() > 0 && timeslot.intersects(head.first())){
			return false;
		}
		
		SortedSet<Interval> tail = bookings.tailSet(timeslot);
		return tail.size() == 0 || !timeslot.intersects(tail.first());
	}

	public void addBooking(Interval timeslot){
		bookings.add(timeslot);
	}

	public void removeBooking(Interval timeslot){
		bookings.remove(timeslot);
	}

	public int compareTo(Caravan other){
		int depotCmp = depot.compareTo(other.getDepot());

		return depotCmp != 0 ? depotCmp : order - other.order;
	}

	@Override public String toString(){
		return name;
	}
	
	public boolean getIsAuto(){
		return isAuto;
	}

	public String depotString(){
		return depot.toString();
	}

	public Depot getDepot(){
		return depot;
	}
}
