import java.util.*;

/**
 * Represents a depot in the system
 *
 * @author	Kevin Ni
 * @since	2017-03-30
 */
public class Depot{

	String name;

	public Depot(String depotName){
		name = depotName;
	}

	@Override public String toString(){
		return name;
	}
}
