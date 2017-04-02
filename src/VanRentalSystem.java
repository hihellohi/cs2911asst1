import java.io.*;
import java.util.*;
import java.text.*;

/**
 * Program to handle the backend of a campervan rental system
 *
 * @author Kevin Ni
 * @since 2017-03-29
 * @inv catalog != null
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
	 * @param args 
	 * command line arguments. args[0] should contain the name of the input file.
	 *
	 * @pre args.length == 1
	 * @pre args[0] != null
	 */
	public static void main(String[] args){
		new VanRentalSystem().run(args[0]);
	}


	Catalog catalog;

	/**
	 * class constructor
	 */
	VanRentalSystem(){
		catalog = new Catalog();
	}

	/**
	 * runs the rental system on an input file
	 *
	 * @param fin input file
	 * @pre fin != null
	 */
	void run(String fin){
		Scanner sc = null;
		try{
			sc = new Scanner(new FileReader(fin));

			while(sc.hasNextLine()){
				executeLine(sc.nextLine());
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

	/**
	 * parses and executes one line of input
	 *
	 * @param line
	 * the line of input to be parsed and executed
	 *
	 * @pre line != null
	 * @pre line.length &gt; 0
	 * @pre if(line begins with LOCATION) line.length == 4
	 * @pre if(line begins with REQUEST or line begins with CHANGE) 
	 *     (line.length == 10 || line.length == 12 &amp;&amp; line[10] can be parsed as an int) &amp;&amp; line[8] can be parsed as an int
	 * @pre if(line begins with CANCEL or line begins with PRINT) line.length == 2
	 */
	void executeLine(String line){
		String[] input = digest(line);
		if(input.length == 0) return;

		switch (input[0]) {
			case LOCATION:
				catalog.addVan(input[1], input[2], input[3].equals(AUTOMATIC));						
				break;

			case REQUEST:
			case CHANGE:

				Calendar start, end;
				try{
					start = stringsToCalendar(input[2], input[3], input[4]);
					end = stringsToCalendar(input[5], input[6], input[7]);
				}
				catch(ParseException e){
					System.err.println(String.format("Cannot parse date string, skipping..."));
					return;
				}

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
					System.out.print("Booking " + catalog.makeOrder(input[1], interval, auto, manual) + "\n");
				}
				else{
					System.out.print("Change " + catalog.changeOrder(input[1], interval, auto, manual) + "\n");
				}
				break;

			case CANCEL:
				System.out.print("Cancel " + catalog.cancelOrder(input[1]) + "\n");
				break;

			case PRINT:
				System.out.print(catalog.print(input[1]));
				break;
		}
	}

	/**
	 * converts from string to a calendar
	 *
	 * @param hour the hour of the day 
	 * @param month the month of the year
	 * @param date the date of the month
	 *
	 * @pre hour != null
	 * @pre month != null
	 * @pre date != null
	 *
	 * @post value != null
	 *
	 * @throws ParseException 
	 * if hour + " " + month + " " + date is not a valid date in the form HH MMM dd
	 * 
	 * @return Calendar representing the input date
	 */
	Calendar stringsToCalendar(String hour, String month, String date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		String dateString = hour + " " + month + " " + date;
		cal.setTime(formatter.parse(dateString));

		return cal;
	}

	/**
	 * removes comments from a line and splits the string on whitespaces
	 *
	 * @param s the line to be processed
	 *
	 * @pre s != null
	 *
	 * @post value != null
	 * @post value.containsAll(x -&gt; {x != null})
	 *
	 * @return s with comments removed and splitted on whitespace
	 */
	String[] digest(String s){
		String decommented = s.split("#", 2)[0];
		return decommented.split("\\s+");
	}
}
