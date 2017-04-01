/**
 * Represents a booking for one caravan
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 */

public class Booking {

	Caravan van;
	Interval timeslot;

	public Booking(Caravan van, Interval timeslot){
		this.van = van;
		this.timeslot = timeslot;
	}

	public void commit(){
		van.addBooking(timeslot);
	}

	public void uncommit(){
		van.removeBooking(timeslot);
	}

	public String vanString(){
		return van.toString();
	}

	public String depotString(){
		return van.depotString();
	}

	public Depot getDepot(){
		return van.getDepot();
	}
}
