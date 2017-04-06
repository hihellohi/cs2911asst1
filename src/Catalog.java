import java.util.*;

/**
 * contains and manages all depots in the system
 * creates, executes, changes and deletes orders
 *
 * @author Kevin Ni
 * @since 2017-03-30
 *
 * @inv depotOrder != null
 * @inv allDepots != null
 * @inv allOrders != null
 * @inv order.isValid() for each order in allOrders.Values
 */
public class Catalog{

	List<Depot> depotOrder;
	Map<String, Depot> allDepots;
	Map<String, Order> allOrders;

	static final String REJECTED = "rejected";

	/**
	 * class constructor
	 */
	public Catalog(){
		depotOrder = new ArrayList<Depot>();
		allDepots = new HashMap<String, Depot>();
		allOrders = new HashMap<String, Order>();
	}

	/**
	 * adds a van to the catalog or updates it if it already exists
	 *
	 * @param depotName name of the depot the van is in
	 * @param vanName name of the van you are trying to add
	 * @param isAuto whether or not the van has an automatic transmission
	 *
	 * @pre depotName != null
	 * @pre vanName != null
	 */
	public void addVan(String depotName, String vanName, boolean isAuto){
		Depot depot;

		if(allDepots.containsKey(depotName)){
			depot = allDepots.get(depotName);
		}
		else{
			depot = new Depot(depotName, allDepots.size());
			allDepots.put(depotName, depot);
			depotOrder.add(depot);
		}

		depot.addVan(vanName, isAuto);
	}

	/**
	 * creates and attempts to execute an order
	 *
	 * @param id the order's id
	 * @param interval the timeslot that the order is trying to book
	 * @param autos the number of automatic vans the order is requesting
	 * @param manuals the number of manual vans the order is requesting
	 *
	 * @pre id != null
	 * @pre interval != null
	 * @pre autos &gt;= 0
	 * @pre manuals &gt;= 0
	 *
	 * @post value != null
	 *
	 * @return the output string of the order if accepted, REJECTED otherwise
	 */
	public String makeOrder(String id, Interval interval, int autos, int manuals){
		if(!allOrders.containsKey(id)){
			Order order = new Order(interval, autos, manuals, depotOrder);

			if(order.isValid()){
				allOrders.put(id, order);
				return id + " " + order.toString();
			}
		}
		return REJECTED;
	}

	/**
	 * attempts to change an existing order
	 *
	 * @param id the order's id
	 * @param interval the timeslot that the order is trying to change to 
	 * @param autos the new number of automatic vans the order is requesting
	 * @param manuals the new number of manual vans the order is requesting
	 *
	 * @pre id != null
	 * @pre interval != null
	 * @pre autos &gt;= 0
	 * @pre manuals &gt;= 0
	 *
	 * @post value != null
	 *
	 * @return the output string of the order if accepted, REJECTED otherwise
	 */
	public String changeOrder(String id, Interval interval, int autos, int manuals){

		Order oldOrder = cancelOrderPrivate(id);
		if(oldOrder == null) { 
			return REJECTED;
		}

		String result = makeOrder(id, interval, autos, manuals);
		if(result.equals(REJECTED)){
			oldOrder.lockIn();
			allOrders.put(id, oldOrder);
			return REJECTED;
		}
		return result;
	}

	/**
	 * prints out all of the bookings of a particular depot
	 *
	 * @param depotName name of the depot
	 *
	 * @pre depotName != null
	 * @pre addVan has been previously called with depotName as the first argument
	 *
	 * @post value != null
	 *
	 * @return a string containing all of the bookings in the requested depot
	 */
	public String print(String depotName){
		return allDepots.get(depotName).print();
	}

	/**
	 * cancels an order
	 *
	 * @param id the id of the order to be deleted
	 *
	 * @pre id != null
	 * @post value != null
	 *
	 * @return id if successful, otherwise REJECTED
	 */
	public String cancelOrder(String id){
		return cancelOrderPrivate(id) == null ? REJECTED : id; 
	}

	/**
	 * cancels an order
	 *
	 * @param id the id of the order to be deleted
	 *
	 * @pre id != null
	 * @post value != null
	 * @post value.isValid()
	 *
	 * @return the deleted order if successful, otherwise null
	 */
	Order cancelOrderPrivate(String id){
		Order order = allOrders.getOrDefault(id, null);
		if(order == null){
			return null;
		}
		else{
			order.delete();
			allOrders.remove(id);
			return order;
		}
	}
}
