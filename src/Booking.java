/**
 * Represents a booking for one caravan
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 */

public class Booking {

	Caravan van;

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
