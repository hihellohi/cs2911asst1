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

	public boolean tryGetBooking(List<Caravan> vans){
		ArrayList<Booking> out = new ArrayList<Booking>();

		for(Iterator<Caravan> it = vans.iterator(); it.hasNext(); ){
			Caravan van = it.next();
			System.out.println(van);
		}
		
		return true;
	}
}
