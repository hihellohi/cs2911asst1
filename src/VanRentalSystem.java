import java.io.*;
import java.util.*;

/**
 * VanRentalSystem implements a prototype system that serves as the "back end" of a campervan rental system
 *
 * @author	Kevin Ni
 * @since	2017-03-29
 */
public class VanRentalSystem{

	String fin;
	SimpleDateFormat formatter;

	/**
	 * This is the entry point of the program
	 * 
	 * @param    args input file
	 */
	public static void main(String[] args){
		new VanRentalSystem(args[0]).run();
	}

	private VanRentalSystem(String fin){
		this.fin = fin;
		formatter = new SimpleDateFormat("hh MMM dd");
	}

	private void run(){
		Scanner sc = null;
		try{
			sc = new Scanner(new FileReader(fin));
			Catalog catalog = new Catalog();

			while(sc.hasNextLine()){
				String[] input = digest(sc.nextLine());
				if(input.length == 0) continue;

				switch (input[0]) {
					case "Location":
						catalog.addVan(input[1], input[2], input[3].equals("Automatic"));						
					case "Request":
						
				}
			}
		}
		catch(FileNotFoundException e)
		{
			System.err.println(String.format("File %s not found", fin));
		}
		finally{
			if (sc != null){
				sc.close();
			}
		}
	}

	Calendar stringsToCalendar(String hour, String month, String Date){
		Calendar cal = Calendar.getInstance();
	}

	private String[] digest(String s){
		String decommented = s.split("#", 2)[0];
		return decommented.split("\\s+");
	}
}
