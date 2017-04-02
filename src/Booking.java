/**
 * Represents a booking for one caravan
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 *
 * @inv van != null
 * @inv timeslot != null
 */

public class Booking {

	Caravan van;
	Interval timeslot;

	/**
	 * class contructor
	 *
	 * @param van the van that this booking is requesting
	 * @param timeslot the timeslot that this booking is requesting
	 *
	 * @pre van != null
	 * @pre timeslot != null
	 * @pre van.isAvailable(timeslot)
	 */
	public Booking(Caravan van, Interval timeslot){
		this.van = van;
		this.timeslot = timeslot;
	}

	/**
	 * locks in this booking 
	 *
	 * @pre no other Booking with the same van has called commit() since the last call of uncommit() or object creation
	 */
	public void commit(){
		van.addBooking(timeslot);
	}

	/**
	 * undoes this booking 
	 *
	 * @pre commit() has been called at least once since the last call of uncommit() or object creation
	 */
	public void uncommit(){
		van.removeBooking(timeslot);
	}

	/**
	 * gets the name of the booked caravan 
	 *
	 * @post value != null
	 * @return the name of the booked caravan 
	 */
	public String vanString(){
		return van.toString();
	}

	/**
	 * gets the booked caravan 
	 *
	 * @post value != null
	 * @return the booked caravan 
	 */
	public Caravan getVan(){
		return van;
	}

	/**
	 * gets the name of the booked depot 
	 *
	 * @post value != null
	 * @return the name of the booked depot
	 */
	public String depotString(){
		return van.depotString();
	}

	/**
	 * gets the booked depot 
	 *
	 * @post value != null
	 * @return the booked depot
	 */
	public Depot getDepot(){
		return van.getDepot();
	}
}
