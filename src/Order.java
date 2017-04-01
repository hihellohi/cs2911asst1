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

	public String tryGetBooking(List<Depot> depots){
		ArrayList<Booking> out = new ArrayList<Booking>();

		int countAutos = autos, countManuals = manuals;
		Iterator<Depot> it = depots.iterator();
		while(it.hasNext() && countAutos > 0 && countManuals > 0){
			Depot depot = it.next();

			Response response = depot.request(countAutos, countManuals);

			countAutos -= response.getAutos();
			countManuals -= response.getBookings().size() - response.getAutos();
			out.addAll(response.getBookings());
		}
		
		StringBuilder result = new StringBuilder();
		Depot last = null;
		out.iterator().forEachRemaining(booking -> {
			if(last == null){
				result.append(booking.depotString() + " ");			
			}
			else if(last == booking.getDepot()){
				result.append("; " + booking.depotString() + " ");			
			}
			else{
				result.append(", ");
			}

			result.append(booking.vanString());
		});
		
		return result.toString();
	}

	public static class Response{
		int autos;
		List<Booking> bookings;

		public Response(int autos, List<Booking> bookings){
			this.autos = autos;
			this.bookings = bookings;
		}

		public int getAutos(){
			return autos;
		}

		public List<Booking> getBookings(){
			return bookings;
		}
	}
}
