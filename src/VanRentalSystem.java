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

	String fin;
	SimpleDateFormat formatter;

	private static final String DATE_FORMAT = "hh MMM dd";
	private static final String LOCATION = "Location";
	private static final String REQUEST = "Request";
	private static final String CHANGE = "Change";
	private static final String AUTOMATIC = "Automatic";
	private static final String MANUAL = "Manual";

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
		formatter = new SimpleDateFormat(DATE_FORMAT);
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

	private String[] digest(String s){
		String decommented = s.split("#", 2)[0];
		return decommented.split("\\s+");
	}
}
