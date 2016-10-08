package seedu.menion.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This Class parses a given Add command argument.
 * 
 * @author lowjiansheng
 *
 */
public class AddParser {
	
	
	public AddParser(){};
	
	private static final Pattern REGULAR_TASK_REGEX = 
			Pattern.compile("by: (0?[0-3][0-9]-[0-1][0-9]-[0-2][0-9][0-9][0-9]) (0?[0-2][0-9][0-6][0-9])");
	private static final Pattern EVENTS_REGEX = 
			Pattern.compile("from: (0?[0-3][0-9]-[0-1][0-9]-[0-2][0-9][0-9][0-9]) (0?[0-2][0-9][0-6][0-9]) "
					+ "to: (0?[0-3][0-9]-[0-1][0-9]-[0-2][0-9][0-9][0-9]) (0?[0-2][0-9][0-6][0-9])");
	private static final String REGULAR_TASK = "task";
	private static final String EVENTS = "event";
	private static final String FLOATING_TASK = "floatingTask";
	
	private static Matcher matcher;
	private static String[] parsedArguments;
	
	
	/**
	 * This method parses the input command and will check the type of add command.
	 * @param args
	 * @return An array of parsed commands
	 */
	public static String[] parseCommand(String args){
		
		checkActivityType(args);
		
		return parsedArguments;
	}
	
	public static void checkActivityType(String args){
		
		if (isTask(args)){
			parsedArguments[0] = REGULAR_TASK;
		}
		
		else if (isEvents(args)){
			parsedArguments[0] = EVENTS;
		}
		
		else {
			parsedArguments[0] = FLOATING_TASK;
		}
		
	}
	
	/**
	 * This method checks if the input arguments satisfy the requirements to be a Task.
	 * @return
	 */
	public static Boolean isTask(String args){
		matcher = REGULAR_TASK_REGEX.matcher(args);
		
		if (matcher.find()){
			return true;
		}
		
		return false;
		
	}

	/**
	 * This method checks if the input arguments satisfy the requirements to be a Event.
	 * @return
	 */
	public static Boolean isEvents(String args){
		matcher = EVENTS_REGEX.matcher(args);
		
		if (matcher.find()){
			return true;
		}
		
		return false;
	
	}
	
	/**
	 * This method checks if the input arguments satisfy the requirements to be a Floating Task.
	 * @return
	 */
	public static Boolean isFloatingTask(String args){
		return true;
	}
}
