import java.util.*;

/**
 * Represents a caravan in the system
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 *
 * @inv name != null
 * @inv order &gt;= 0
 * @inv depot != null
 * @inv bookings != null
 * @inv (x == y || !x.intersects(y)) for each x,y in bookings
 */
public class Caravan implements Comparable<Caravan> {

	String name;
	int order;
	Depot depot;
	boolean isAuto;
	SortedSet<Interval> bookings;

	/**
	 * class constructor
	 * 
	 * @param caravanName name of the caravan
	 * @param depot depot that this caravan is in
	 * @param order how many caravan in this depot already
	 * @param isAuto whether or not this caravan is automatic
	 *
	 * @pre caravanName != null
	 * @pre depot != null
	 * @pre order &gt;= 0
	 */
	public Caravan(String caravanName, Depot depot, int order, boolean isAuto){
		name = caravanName;
		this.order = order;
		this.depot = depot;
		this.isAuto = isAuto;
		bookings = new TreeSet<Interval>();
	}
	
	/**
	 * checks if a timeslot is available on this carvan
	 *
	 * @param timeslot != null
	 *
	 * @return true iff timeslot is available
	 */
	public boolean isAvailable(Interval timeslot){
		SortedSet<Interval> head = bookings.headSet(timeslot);
		if(head.size() > 0 && timeslot.intersects(head.first())){
			return false;
		}
		
		SortedSet<Interval> tail = bookings.tailSet(timeslot);
		return tail.size() == 0 || !timeslot.intersects(tail.first());
	}

	/**
	 * books a timeslot on this caravan
	 *
	 * @param timeslot timeslot to be booked
	 * 
	 * @pre timeslot != null
	 * @pre isAvailable(timeslot)
	 */
	public void addBooking(Interval timeslot){
		bookings.add(timeslot);
	}

	/**
	 * unbooks a timeslot on this caravan
	 *
	 * @param timeslot timeslot to be unbooked
	 * 
	 * @pre timeslot != null
	 * @pre bookings.contains(timeslot)
	 */
	public void removeBooking(Interval timeslot){
		bookings.remove(timeslot);
	}

	/**
	 * gets a string representing all bookings in this caravan
	 *
	 * @post value != null
	 * @return string representing all bookings in this caravan
	 */
	public String printBookings(){
		StringBuilder out = new StringBuilder();

		bookings.iterator().forEachRemaining(booking -> {
			out.append(depotString() + " " + toString() + " " + booking.toString());
			out.append('\n');
		});

		return out.toString();
	}

	/**
	 * compares a caravan to another
	 *
	 * @param other the other caravan 
	 *
	 * @pre other != null
	 *
	 * @return negative if less than, 0 if equal to, positive if greater than
	 */
	public int compareTo(Caravan other){
		int depotCmp = depot.compareTo(other.getDepot());

		return depotCmp != 0 ? depotCmp : order - other.order;
	}

	/**
	 * gets the name of the caravan 
	 *
	 * @post value != null
	 * @return the name of the caravan 
	 */
	@Override public String toString(){
		return name;
	}
	
	/**
	 * returns true if the van is auto
	 * @return true if the van is auto
	 */
	public boolean getIsAuto(){
		return isAuto;
	}

	/**
	 * gets the name of the depot this caravan is in 
	 *
	 * @post value != null
	 * @return the name of the depot this caravan is in 
	 */
	public String depotString(){
		return depot.toString();
	}

	/**
	 * gets the depot this caravan is in 
	 *
	 * @post value != null
	 * @return the depot this caravan is in 
	 */
	public Depot getDepot(){
		return depot;
	}
}
