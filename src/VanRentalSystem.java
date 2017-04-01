import java.io.*;
import java.util.*;
import java.text.*;

/**
 * VanRentalSystem implements a prototype system that serves as the "back end" of a campervan rental system
 *
 * @author	Kevin Ni
 * @since	2017-03-29
 */
public class VanRentalSystem{


	static final String DATE_FORMAT = "HH MMM dd";
	static final String LOCATION = "Location";
	static final String REQUEST = "Request";
	static final String CHANGE = "Change";
	static final String CANCEL = "Cancel";
	static final String PRINT = "Print";
	static final String AUTOMATIC = "Automatic";
	static final String MANUAL = "Manual";

	static SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

	/**
	 * This is the entry point of the program
	 * 
	 * @param    args input file
	 */
	public static void main(String[] args){
		new VanRentalSystem(args[0]).run();
	}

	String fin;

	VanRentalSystem(String fin){
		this.fin = fin;
	}

	void run(){
		Scanner sc = null;
		try{
			sc = new Scanner(new FileReader(fin));
			Catalog catalog = new Catalog();

			while(sc.hasNextLine()){
				executeLine(sc.nextLine(), catalog);
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

	void executeLine(String line, Catalog catalog){
		String[] input = digest(line);
		if(input.length == 0) return;

		switch (input[0]) {
			case LOCATION:
				catalog.addVan(input[1], input[2], input[3].equals(AUTOMATIC));						
				break;

			case REQUEST:
			case CHANGE:
				Calendar start = stringsToCalendar(input[2], input[3], input[4]);
				Calendar end = stringsToCalendar(input[5], input[6], input[7]);
				Interval interval = new Interval(start, end);
				
				int auto, manual;
				int tmpa = Integer.parseInt(input[8]);
				int tmpb = input.length == 12 ? Integer.parseInt(input[10]) : 0;
				if(input[9].equals(AUTOMATIC)){
					auto = tmpa;
					manual = tmpb;
				}
				else{
					auto = tmpb;
					manual = tmpa;
				}

				if(input[0].equals(REQUEST)){
					System.out.println("Booking " + catalog.makeOrder(input[1], interval, auto, manual));
				}
				else{
					System.out.println("Change " + catalog.changeOrder(input[1], interval, auto, manual));
				}
				break;

			case CANCEL:
				System.out.println("Cancel " + catalog.cancelOrder(input[1]));
				break;

			case PRINT:
				System.out.print(catalog.print(input[1]));
				break;
		}
	}

	Calendar stringsToCalendar(String hour, String month, String date){
		Calendar cal = Calendar.getInstance();
		String dateString = hour + " " + month + " " + date;
		try{
			cal.setTime(formatter.parse(dateString));
		}
		catch(ParseException e){
			System.err.println(String.format("Cannot parse date string %s", dateString));
		}
		return cal;
	}

	String[] digest(String s){
		String decommented = s.split("#", 2)[0];
		return decommented.split("\\s+");
	}
}
