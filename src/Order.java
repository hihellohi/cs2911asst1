import java.util.*;

/**
 * Represents one particular order
 * Responsible for checking validity of orders
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 */

public class Order{

	Interval interval;
	int autos;
	int manuals;

	List<Booking> bookings;

	public Order(Interval interval, int autos, int manuals){
		this.interval = interval;
		this.autos = autos;
		this.manuals = manuals;
		bookings = new ArrayList<Booking>();
	}

	public ArrayList<Booking> getBookings(List<Caravan> vans){
		//for(Iterator<Caravan> i = vans.iterator(); i < vans.length; i++){
		//}
		
		vans.iterator().forEachRemaining(van -> {
			System.out.println(van);
		});
	}
}
