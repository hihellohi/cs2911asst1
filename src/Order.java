import java.util.*;

/**
 * Represents one particular order
 * Responsible for checking validity of orders
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 */
public class Order{

	Interval timeslot;
	int autos;
	int manuals;
	List<Booking> bookings;

	public Order(Interval timeslot, int autos, int manuals){
		this.timeslot = timeslot;
		this.autos = autos;
		this.manuals = manuals;
		bookings = new ArrayList<Booking>();
	}

	public String tryGetBooking(List<Depot> depots){
		ArrayList<Booking> out = new ArrayList<Booking>();

		int countAutos = autos, countManuals = manuals;
		Iterator<Depot> itDepot = depots.iterator();
		while(itDepot.hasNext() && (countAutos > 0 || countManuals > 0)){
			Depot depot = itDepot.next();

			Response response = depot.request(timeslot, countAutos, countManuals);

			countAutos -= response.getAutos();
			countManuals -= response.getManuals();
			out.addAll(response.getBookings());
		}
		
		StringBuilder result = new StringBuilder();
		Depot last = null;

		Iterator<Booking> itBooking = out.iterator();
		while(itBooking.hasNext()){
			Booking booking = itBooking.next();					

			if(last == booking.getDepot()){
				result.append(", ");
			}
			else{ 
				if(last == null){
					result.append(booking.depotString() + " ");			
				}
				else {
					result.append("; " + booking.depotString() + " ");			
				}
				last = booking.getDepot();
			}
			result.append(booking.vanString());
		}
		
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

		public int getManuals(){
			return bookings.size() - autos;
		}

		public List<Booking> getBookings(){
			return bookings;
		}
	}
}
