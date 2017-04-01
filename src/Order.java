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

	public Order(Interval timeslot, int autos, int manuals, List<Depot> depots){
		this.timeslot = timeslot;
		this.autos = autos;
		this.manuals = manuals;
		bookings = new ArrayList<Booking>();
		requestBookings(depots);
	}

	@Override public String toString(){

		StringBuilder result = new StringBuilder();
		Depot last = null;

		Iterator<Booking> itBooking = bookings.iterator();
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

	public boolean isValid(){
		return bookings != null;
	}

	public void delete(){
		bookings.iterator().forEachRemaining(booking -> {
			booking.uncommit();
		});
	}

	public void lockIn(){
		bookings.iterator().forEachRemaining(booking -> {
			booking.commit();
		});
	}

	void requestBookings(List<Depot> depots){

		int countAutos = autos, countManuals = manuals;
		Iterator<Depot> itDepot = depots.iterator();
		while(itDepot.hasNext() && (countAutos > 0 || countManuals > 0)){
			Depot depot = itDepot.next();

			Response response = depot.request(timeslot, countAutos, countManuals);

			countAutos -= response.getAutos();
			countManuals -= response.getManuals();
			bookings.addAll(response.getBookings());
		}

		if(countAutos + countManuals > 0){
			bookings = null;
		}
		else{
			lockIn();
		}
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
