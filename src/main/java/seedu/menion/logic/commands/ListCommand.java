package seedu.menion.logic.commands;

import java.text.DateFormatSymbols;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ModelManager;
import seedu.menion.model.activity.Completed;

/**
 * Lists all tasks in the task manager to the user.
 */
//@@author A0139277U
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String LIST_BLANK = "";
    public static final String LIST_ALL = "all";
    public static final String LIST_COMPLETED = "completed";
    public static final String LIST_UNCOMPLETED = "uncompleted";
    public static final String LIST_MONTH = "month";
    public static final String LIST_DATE = "date";
    private static final Pattern VALID_DATE = Pattern
			.compile("(0?[0-1][0-9]-[0-3][0-9]-[0-2][0-9][0-9][0-9])");
    
    public static final String WRONG_ARGUMENT = "Wrong argument! Please input either list all, list DATE or list MONTH";
    public static final String MESSAGE_SUCCESS_ALL = "Listed all activities";
    public static final String MESSAGE_SUCCESS_DATE_MONTH = "Menion lists all activities that falls on ";
    public static final String MESSAGE_SUCCESS_LIST_KEYWORDS = "Menion has found these activities with the keyword : ";
    public static final String MESSAGE_SUCCESS_LIST_UNCOMPLETE_INITIAL = "You still have";
    public static final String MESSAGE_SUCCESS_LIST_UNCOMPLETE_FINAL = "uncompleted activities. Boo :(";
    public static final String MESSAGE_SUCCESS_LIST_COMPLETE_INITIAL = "You have";
    public static final String MESSAGE_SUCCESS_LIST_COMPLETE_FINAL = "completed activities. Yay!";
    
    private String listArgument;
    private String listType;
    private String monthToList;
    private String dateToList;
    private String keywordToList;
    private Set<String> argumentsToList;
    
    private static Matcher matcher;
    
    public ListCommand(String args){
        argumentsToList = new HashSet<String>();
        this.listArgument = args;  
    }

    /**
     * This method checks the argument input by the user.
     * @param args of command
     * @return type of Listing selected by user
     * @throws IllegalValueException
     */
    public String checkListType(String args){
    	
    	if (args.isEmpty()){
    		return LIST_BLANK;
    	}
    	
    	else if (args.toLowerCase().equals(LIST_ALL)) {
            return LIST_ALL;
        }
    	
    	else if (isMonth(args)){
    		return LIST_MONTH;
    	}
    	
    	else if (isDate(args)){
    		return LIST_DATE;
    	}
    	
    	else if (args.equals(LIST_COMPLETED)){
    		this.argumentsToList.add(Completed.COMPLETED_ACTIVITY);
    		return LIST_COMPLETED;
    	}
    	
    	else if (args.equals(LIST_UNCOMPLETED)){
    		this.argumentsToList.add(Completed.UNCOMPLETED_ACTIVITY);
    		return LIST_UNCOMPLETED;
    	}
    		// Invalid list parameter
    	else {
    		return WRONG_ARGUMENT;
    	}	
    }
    
    /**
     * This method checks if the arguments is in the format of a Date.
     * @param args
     * @return true if the arguments fit the format of a Date.
     */
    private Boolean isDate (String args){
    	matcher = VALID_DATE.matcher(args);
    	if (matcher.find()){
    		this.dateToList = args;
    		this.argumentsToList.add(args);
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
    private Boolean isMonth (String args){
    	
    	String monthString;
    	
    	for (Integer i = 0 ; i < 12; i ++){
    		monthString = new DateFormatSymbols().getMonths()[i];
    		if (args.toLowerCase().equals(monthString.toLowerCase())){
    			this.argumentsToList.add(monthString);
    			this.monthToList = monthString;
    			return true;
    		}
    	}	
    	return false;
    }
    
    public String getKeywordToList(){
    	return this.keywordToList;
    }
    
    public String getDateToList(){
    	return this.dateToList;
    }
    
    public String getMonthToList(){
    	return this.monthToList;
    }
    
	@Override
	public CommandResult execute() {
		assert model != null;
		
		model.updateRecentChangedActivity(null);
		
		this.listType = checkListType(this.listArgument);

		switch (this.listType) {

		case LIST_ALL:

			model.updateFilteredListToShowAll();
			return new CommandResult(MESSAGE_SUCCESS_ALL);
			
		case LIST_DATE:

			model.updateFilteredTaskList(this.argumentsToList, ModelManager.listDate);
			model.updateFilteredEventList(this.argumentsToList, ModelManager.listDate);
			model.updateFilteredFloatingTaskList(this.argumentsToList, ModelManager.listDate);
			return new CommandResult(MESSAGE_SUCCESS_DATE_MONTH + this.dateToList);

		case LIST_MONTH:

			model.updateFilteredTaskList(this.argumentsToList, ModelManager.listMonth);
			model.updateFilteredEventList(this.argumentsToList, ModelManager.listMonth);
			model.updateFilteredFloatingTaskList(this.argumentsToList, ModelManager.listMonth);
			return new CommandResult(MESSAGE_SUCCESS_DATE_MONTH + this.monthToList);
			
		case LIST_COMPLETED:
			
		    model.updateFilteredTaskList(this.argumentsToList, ModelManager.listCompleted);
            model.updateFilteredEventList(this.argumentsToList, ModelManager.listCompleted);
            model.updateFilteredFloatingTaskList(this.argumentsToList, ModelManager.listCompleted);
            return new CommandResult(MESSAGE_SUCCESS_LIST_COMPLETE_INITIAL + " " + (model.getFilteredEventList().size() + 
            							model.getFilteredFloatingTaskList().size() + model.getFilteredTaskList().size())
            							+ " " + MESSAGE_SUCCESS_LIST_COMPLETE_FINAL); 
            
		case LIST_UNCOMPLETED:
            
            model.updateFilteredTaskList(this.argumentsToList, ModelManager.listCompleted);
            model.updateFilteredEventList(this.argumentsToList, ModelManager.listCompleted);
            model.updateFilteredFloatingTaskList(this.argumentsToList, ModelManager.listCompleted);
            return new CommandResult(MESSAGE_SUCCESS_LIST_UNCOMPLETE_INITIAL + " " + (model.getFilteredEventList().size() + 
					model.getFilteredFloatingTaskList().size() + model.getFilteredTaskList().size()) + " " +
            		MESSAGE_SUCCESS_LIST_UNCOMPLETE_FINAL);
            
		default:
			return new CommandResult(WRONG_ARGUMENT);

		}
	}
}
