package seedu.menion.logic.parser;

import java.util.regex.Pattern;

/**
 * This Class parses a given Add command argument.
 * 
 * @author lowjiansheng
 *
 */
public class AddParser {
	
	
	public AddParser(){};
	
	private static final Pattern REGULAR_TASK = Pattern.compile("by:");
	private static final Pattern EVENTS = Pattern.compile("from: ");
	
	
	/**
	 * This method parses the input command and will check the type of add command.
	 * @param args
	 * @return An array of parsed commands
	 */
	public static String[] parseCommand(String args){
		
		
		
		
		return null;	
	}
	
	/**
	 * This method checks if the input arguments satisfy the requirements to be a Task.
	 * @return
	 */
	public static Boolean isTask(String args){
		return false;
	}

	/**
	 * This method checks if the input arguments satisfy the requirements to be a Event.
	 * @return
	 */
	private static Boolean isEvents(String args){
		return false;
	}
	
	/**
	 * This method checks if the input arguments satisfy the requirements to be a Floating Task.
	 * @return
	 */
	private static Boolean isFloatingTask(String args){
		return false;
	}
}
