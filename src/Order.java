import java.util.*;

/**
 * Represents one particular order
 * Checks validity of itself
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 *
 * @inv autos &gt;= 0
 * @inv manuals &gt;= 0
 * @inv timeslot != null
 */
public class Order{

	Interval timeslot;
	int autos;
	int manuals;
	List<Caravan> caravans;

	/**
	 * class constructor
	 *
	 * @param timeslot requested timeslot
	 * @param autos number of requested autos
	 * @param manuals number of requested manuals
	 * @param depots all available depots in sorted order
	 *
	 * @pre timeslot != null
	 * @pre autos &gt;= 0;
	 * @pre manuals &gt;= 0;
	 * @pre depots != null
	 * @pre depots is sorted
	 */
	public Order(Interval timeslot, int autos, int manuals, List<Depot> depots){
		this.timeslot = timeslot;
		this.autos = autos;
		this.manuals = manuals;
		caravans = new ArrayList<Caravan>();
		requestBookings(depots);
	}

	/**
	 * gets the string that represents this order
	 *
	 * @pre isValid()
	 * @post value != null
	 *
	 * @return string that represents this order
	 */
	@Override public String toString(){
		StringBuilder result = new StringBuilder();
		Depot last = null;

		for(Caravan caravan: caravans){
			if(last == caravan.getDepot()){
				result.append(", ");
			}
			else{ 
				if(last == null){
					result.append(caravan.depotString() + " ");			
				}
				else {
					result.append("; " + caravan.depotString() + " ");			
				}
				last = caravan.getDepot();
			}
			result.append(caravan.toString());
		}
		return result.toString();
	}

	/**
	 * indicates whether this order is valid
	 *
	 * @return true iff this booking has been accepted
	 */
	public boolean isValid(){
		return caravans != null;
	}

	/**
	 * uncommits all of the bookings associated with this order
	 *
	 * @pre isValid()
	 * @pre lockIn() has been called at least once since the last call of delete() and object creation
	 */
	public void delete(){
		for(Caravan van: caravans){
			van.removeBooking(timeslot);
		}
	}

	/**
	 * commits all of the bookings associated with this order
	 *
	 * @pre isValid()
	 */
	public void lockIn(){
		for(Caravan van: caravans){
			van.addBooking(timeslot);
		}
	}

	/**
	 * creates and requests all of the bookings needed
	 *
	 * @pre isValid()
	 * @pre depots != null
	 * @pre depots is sorted
	 */
	void requestBookings(List<Depot> depots){

		int countAutos = autos, countManuals = manuals;
		Iterator<Depot> itDepot = depots.iterator();
		while(itDepot.hasNext() && (countAutos > 0 || countManuals > 0)){
			Depot depot = itDepot.next();

			Depot.Response response = depot.request(timeslot, countAutos, countManuals);

			countAutos -= response.getAutos();
			countManuals -= response.getManuals();
			caravans.addAll(response.getCaravans());
		}

		if(countAutos + countManuals > 0){
			caravans = null;
		}
		else{
			lockIn();
		}
	}
}
