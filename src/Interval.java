import java.util.*;
import java.text.*;

/**
 * represents a period of time
 *
 * @author  Kevin Ni
 * @since   2017-03-30
 *
 * @inv start != null
 * @inv end != null
 * @inv start.before(end)
 */
public class Interval implements Comparable<Interval>{

	static final String DATE_FORMAT = "HH:mm MMM dd";
	static SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

	Calendar start;
	Calendar end;

	/**
	 * class constructor
	 *
	 * @param start start of the interval
	 * @param end end of the interval
	 *
	 * @pre start != null
	 * @pre end != null
	 * @pre start.before(end)
	 */
	public Interval(Calendar start, Calendar end){
		this.start = start;
		this.end = end;
	}

	/**
	 * checks if this interval overlaps with another
	 *
	 * @param other other interval to check against
	 *
	 * @pre other != null
	 * @return true if the two intervals overlap
	 */
	public boolean intersects(Interval other){
		return !(start.after(other.getEnd()) || end.before(other.getStart()));
	}

	/**
	 * orders intervals by start time
	 *
	 * @param other other interval to compare against
	 *
	 * @pre other != null
	 *
	 * @return negative if less than, 0 if equal to, positive if greater than
	 */
	public int compareTo(Interval other){
		return start.compareTo(other.getStart());
	}

	/**
	 * gets the start of the interval
	 *
	 * @post value != null
	 *
	 * @return the start of the interval
	 */
	public Calendar getStart(){
		return start;
	}

	/**
	 * gets the end of the interval
	 *
	 * @post value != null
	 *
	 * @return the end of the interval
	 */
	public Calendar getEnd(){
		return end;
	}

	/**
	 * get the string that represents this interval
	 *
	 * @post String != null
	 *
	 * @return the string that represents this interval
	 */
	@Override public String toString(){
		return formatter.format(start.getTime()) + " " + formatter.format(end.getTime());
	}
}

