package seedu.menion.logic.parser;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.commands.ListCommand;
import seedu.menion.model.activity.Completed;

public class ListParser {

	
private static final Pattern VALID_DATE = Pattern
		.compile("(0?[0-1][0-9]-[0-3][0-9]-[0-2][0-9][0-9][0-9])");
	
private static Matcher matcher;
	
    /**
     * This method checks the argument input by the user.
     * @param args of command
     * @return type of Listing selected by user
     * @throws IllegalValueException
     */
    public static String checkListType(String args, Set<String>argumentsToList){
    	
    	if (args.isEmpty()){
    		return ListCommand.LIST_BLANK;
    	}
    	
    	else if (args.toLowerCase().equals(ListCommand.LIST_ALL)) {
            return ListCommand.LIST_ALL;
        }
    	
    	else if (isMonth(args, argumentsToList)){
    		return ListCommand.LIST_MONTH;
    	}
    	
    	else if (isDate(args, argumentsToList)){
    		return ListCommand.LIST_DATE;
    	}
    	
    	else if (args.equals(ListCommand.LIST_COMPLETED)){
    		argumentsToList.add(Completed.COMPLETED_ACTIVITY);
    		return ListCommand.LIST_COMPLETED;
    	}
    	
    	else if (args.equals(ListCommand.LIST_UNCOMPLETED)){
    		argumentsToList.add(Completed.UNCOMPLETED_ACTIVITY);
    		return ListCommand.LIST_UNCOMPLETED;
    	}
    		// Invalid list parameter
    	else {
    		return ListCommand.WRONG_ARGUMENT;
    	}	
    }
    
    /**
     * This method checks if the arguments is in the format of a Date.
     * @param args
     * @return true if the arguments fit the format of a Date.
     */
    private static Boolean isDate (String args, Set<String> argumentsToList){
    	matcher = VALID_DATE.matcher(args);
    	if (matcher.find()){
    		argumentsToList.add(args);
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    

    /**
     * This method checks if the arguments is a month
     * @param args of command
     * @return true if the arguments fit the format of a Month.
     */
    private static Boolean isMonth (String args, Set<String> argumentsToList){
    	
    	String monthString;
    	
    	for (Integer i = 0 ; i < 12; i ++){
    		monthString = new DateFormatSymbols().getMonths()[i];
    		if (args.toLowerCase().equals(monthString.toLowerCase())){
    			argumentsToList.add(monthString);
    			return true;
    		}
    	}	
    	return false;
    }
    /*
    public static String getKeywordToList(){
    	return this.keywordToList;
    }
    
    public static String getDateToList(){
    	return this.dateToList;
    }
    
    public static String getMonthToList(){
    	return this.monthToList;
    }
    */
    
}
