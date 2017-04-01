import java.util.*;

/**
 * contains all caravans in the system
 *
 * @author  Kevin Ni
 * @since   2017-03-30
 */
public class Catalog{

	List<Depot> depotOrder;
	Map<String, Depot> allDepots;
	Map<String, Order> allOrders;

	public Catalog(){
		depotOrder = new ArrayList<Depot>();
		allDepots = new HashMap<String, Depot>();
		allOrders = new HashMap<String, Order>();
	}

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
		System.out.println(depotOrder.size());
	}

	public void makeOrder(String id, Interval interval, int autos, int manuals){
		Order order = new Order(interval, autos, manuals);

		if(order.tryGetBooking(depotOrder)){
			allOrders.put(id, order);
		}
	}
}
