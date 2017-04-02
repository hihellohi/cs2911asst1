import java.util.*;

/**
 * Represents a depot in the system
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 *
 * @inv name != null
 * @inv vans != null
 */
public class Depot implements Comparable<Depot> {

	String name;
	List<Caravan> vans;
	int order;

	/**
	 * class constructor
	 * 
	 * @param depotName name of the depot
	 * @param order how many depots have already been created
	 *
	 * @pre depotName != null
	 * @pre order &gt;= 0
	 */
	public Depot(String depotName, int order){
		name = depotName;
		vans = new ArrayList<Caravan>();
		this.order = order;
	}

	/**
	 * adds a van to the depot
	 * 
	 * @param vanName name of the van 
	 * @param isAuto whether the van is an automatic or manual transmission
	 *
	 * @pre vanName != null
	 */
	public void addVan(String vanName, boolean isAuto){
		vans.add(new Caravan(vanName, this, vans.size(), isAuto));		
	}

	/**
	 * attempts to make an order
	 *
	 * @param timeslot the requested timeslot of the order
	 * @param reqAutos the number of requested automatic vans
	 * @param reqManuals the number of requested manual vans
	 *
	 * @pre timeslot != null
	 * @pre reqAutos &gt;= 0
	 * @pre reqManuals &gt;= 0
	 *
	 * @post value != null
	 * @post (x.getVan() != y.getVan() || x == y) for each x, y value.getBookings()
	 *
	 * @return an Depot.Response object that contains the bookings that can be made
	 */
	public Response request(Interval timeslot, int reqAutos, int reqManuals){
		List<Booking> bookings = new ArrayList<Booking>();

		int nAutos = 0, nManuals = 0;
		Iterator<Caravan> it = vans.iterator();
		while(it.hasNext()){
			Caravan van = it.next();
			if((nAutos < reqAutos || !van.getIsAuto()) 
					&& (nManuals < reqManuals || van.getIsAuto()) 
					&& van.isAvailable(timeslot)){
				bookings.add(new Booking(van, timeslot));
				if(van.getIsAuto()){
					nAutos++;
				}
				else{
					nManuals++;
				}
			}
		}

		return new Response(nAutos, bookings);
	}

	/**
	 * compares a depot to another
	 *
	 * @param other the other depot
	 *
	 * @pre other != null
	 *
	 * @return negative if less than, 0 if equal to, positive if greater than
	 */
	public int compareTo(Depot other){
		return order - other.order;
	}

	/**
	 * gets a string representing all of the bookings associated with vans in this depot
	 *
	 * @post value != null
	 * @return a string representing all of the bookings associated with vans in this depot
	 */
	public String print(){
		StringBuilder out = new StringBuilder();

		vans.iterator().forEachRemaining(van -> {
			out.append(van.printBookings());
		});

		return out.toString();
	}

	/**
	 * gets the name of the depot
	 *
	 * @post value != null
	 * @return the name of the depot
	 */
	@Override public String toString(){
		return name;
	}

	/**
	 * class used as a return value for Depot.request
	 *
	 * @author	Kevin Ni
	 * @since	2017-03-30
	 *
	 * @inv autos &gt;= 0
	 * @inv autos &lt;= bookings.size()
	 * @inv bookings != null
	 */
	public static class Response{
		int autos;
		List<Booking> bookings;

		/**
		 * class constructor
		 *
		 * @param autos number of automatic vans that can be booked
		 * @param bookings list of vans that can be booked
		 *
		 * @pre auto &gt;= 0
		 * @pre auto &lt;= bookings.size()
		 * @pre bookings &gt;= 0
		 */
		Response(int autos, List<Booking> bookings){
			this.autos = autos;
			this.bookings = bookings;
		}

		/**
		 * returns number of autos
		 *
		 * @post value &gt;= 0
		 * @post value &lt;= getBookings().size()
		 * @return the number of autos
		 */
		public int getAutos(){
			return autos;
		}

		/**
		 * returns number of manuals
		 *
		 * @post value &gt;= 0
		 * @post value &lt;= getBookings().size()
		 * @return the number of manuals
		 */
		public int getManuals(){
			return bookings.size() - autos;
		}

		/**
		 * returns the list of vans that can be booked
		 *
		 * @post value != null
		 * @return the list of vans that can be booked
		 */
		public List<Booking> getBookings(){
			return bookings;
		}
	}
}
