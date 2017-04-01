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

	static final String REJECTED = "rejected";

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
	}

	public String makeOrder(String id, Interval interval, int autos, int manuals){
		Order order = new Order(interval, autos, manuals, depotOrder);

		if(order.isValid()){
			allOrders.put(id, order);
			return id + " " + order.toString();
		}
		else{
			return REJECTED;
		}
	}

	public String changeOrder(String id, Interval interval, int autos, int manuals){

		Order oldOrder = cancelOrderPrivate(id);

		String result = makeOrder(id, interval, autos, manuals);
		if(result.equals(REJECTED)){
			oldOrder.lockIn();
			allOrders.put(id, oldOrder);
			return REJECTED;
		}
		else{
			return result;
		}
	}

	public String print(String depotName){
		return allDepots.get(depotName).print();
	}

	public String cancelOrder(String id){
		return cancelOrderPrivate(id) == null ? REJECTED : id; 
	}

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
