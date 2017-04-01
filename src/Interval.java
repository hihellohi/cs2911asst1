import java.util.*;

/**
 * represents a period of time
 *
 * @author  Kevin Ni
 * @since   2017-03-30
 */
public class Interval implements Comparable<Interval>{
	Calendar start;
	Calendar end;

	public Interval(Calendar start, Calendar end){
		this.start = start;
		this.end = end;
	}

	public boolean intersects(Interval other){
		return !(start.after(other.getEnd()) || end.before(other.getStart()));
	}

	public int compareTo(Interval other){
		return start.compareTo(other.getStart());
	}

	public Calendar getStart(){
		return start;
	}

	public Calendar getEnd(){
		return end;
	}
}

