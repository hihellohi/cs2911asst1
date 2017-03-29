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
	}

	private void run(){
		Scanner sc = null;
		try{
			sc = new Scanner(new FileReader(fin));

			while(sc.hasNextLine()){
				String[] input = digest(sc.nextLine());
				if(input.length == 0) continue;
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

	private String[] digest(String s){
		String decommented = s.split("#", 2)[0];
		return decommented.split(" ");
	}
}
