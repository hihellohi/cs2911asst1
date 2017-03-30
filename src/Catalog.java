import java.util.*;

/**
 * contains all caravans in the system
 * responsible for checking availability
 *
 * @author  Kevin Ni
 * @since   2017-03-30
 */
public class Catalog{

	List<Caravan> allVans;
	Map<String, Depot> allDepots;

	public Catalog(){
		allVans = new ArrayList<Caravan>();
		allDepots = new HashMap<String, Depot>();
	}

	public void addVan(String depotName, String vanName, boolean isAuto){

		Depot depot = allDepots.putIfAbsent(depotName, new Depot(depotName));

		Caravan van = new Caravan(vanName, depot, allDepots.size(), isAuto); 
		System.out.println(allDepots.size());
		allVans.add(van);
	}

	public void makeOrder(Interval interval, int autos, int manuals){
	}
}
