package seedu.menion.logic.commands;

import java.text.DateFormatSymbols;
import java.util.HashSet;
import java.util.Iterator;
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

    public static final String WRONG_ARGUMENT = "Wrong argument! Please input either list all, list DATE or list MONTH";
    public static final String MESSAGE_SUCCESS_ALL = "Listed all activities";
    public static final String MESSAGE_SUCCESS_DATE_MONTH = "Menion lists all activities that falls on ";
    public static final String MESSAGE_SUCCESS_LIST_KEYWORDS = "Menion has found these activities with the keyword : ";
    public static final String MESSAGE_SUCCESS_LIST_UNCOMPLETE_INITIAL = "You still have";
    public static final String MESSAGE_SUCCESS_LIST_UNCOMPLETE_FINAL = "uncompleted activities. Boo :(";
    public static final String MESSAGE_SUCCESS_LIST_COMPLETE_INITIAL = "You have";
    public static final String MESSAGE_SUCCESS_LIST_COMPLETE_FINAL = "completed activities. Yay!";
    
    private String listType;
    private String monthToList;
    private String dateToList;
    private String keywordToList;
    private String argumentListed;
    
    private Set<String> argumentsToList;
    
    
    public ListCommand(String args, Set<String> argumentsToList, String listType){
        this.argumentsToList = argumentsToList;
        this.listType = listType;
    }

	@Override
	public CommandResult execute() {
		assert model != null;
		
		model.updateRecentChangedActivity(null);
		
		switch (this.listType) {

		case LIST_ALL:

			model.updateFilteredListToShowAll();
			return new CommandResult(MESSAGE_SUCCESS_ALL);
			
		case LIST_DATE:

			assert (this.argumentsToList.size() == 1);
		
			Iterator<String> setIteratorDate = this.argumentsToList.iterator();
			while (setIteratorDate.hasNext()){
				this.dateToList = setIteratorDate.next();
			}
			
			model.updateFilteredTaskList(this.argumentsToList, ModelManager.listDate);
			model.updateFilteredEventList(this.argumentsToList, ModelManager.listDate);
			model.updateFilteredFloatingTaskList(this.argumentsToList, ModelManager.listDate);
			return new CommandResult(MESSAGE_SUCCESS_DATE_MONTH + this.dateToList);

		case LIST_MONTH:

			Iterator<String> setIteratorMonth = this.argumentsToList.iterator();
			while (setIteratorMonth.hasNext()){
				this.dateToList = setIteratorMonth.next();
			}
			
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
