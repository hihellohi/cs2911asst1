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
	 *
	 * @return an Depot.Response object that contains the bookings that can be made
	 */
	public Response request(Interval timeslot, int reqAutos, int reqManuals){
		List<Caravan> caravans = new ArrayList<Caravan>();

		int nAutos = 0, nManuals = 0;
		for(Caravan van : vans){
			if((nAutos < reqAutos || !van.getIsAuto()) 
					&& (nManuals < reqManuals || van.getIsAuto()) 
					&& van.isAvailable(timeslot)){
				caravans.add(van);
				if(van.getIsAuto()){
					nAutos++;
				}
				else{
					nManuals++;
				}
			}
		}

		return new Response(nAutos, caravans);
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

		for(Caravan van: vans){
			out.append(van.printBookings());
		}

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
	 * @inv autos &lt;= caravans.size()
	 * @inv caravans != null
	 */
	public static class Response{
		int autos;
		List<Caravan> caravans;

		/**
		 * class constructor
		 *
		 * @param autos number of automatic vans that can be booked
		 * @param caravans list of vans that can be booked
		 *
		 * @pre auto &gt;= 0
		 * @pre auto &lt;= caravans.size()
		 * @pre caravans &gt;= 0
		 */
		Response(int autos, List<Caravan> caravans){
			this.autos = autos;
			this.caravans = caravans;
		}

		/**
		 * returns number of autos
		 *
		 * @post value &gt;= 0
		 * @post value &lt;= getCaravans().size()
		 * @return the number of autos
		 */
		public int getAutos(){
			return autos;
		}

		/**
		 * returns number of manuals
		 *
		 * @post value &gt;= 0
		 * @post value &lt;= getCaravans().size()
		 * @return the number of manuals
		 */
		public int getManuals(){
			return caravans.size() - autos;
		}

		/**
		 * returns the list of vans that can be booked
		 *
		 * @post value != null
		 * @return the list of vans that can be booked
		 */
		public List<Caravan> getCaravans(){
			return caravans;
		}
	}
}
