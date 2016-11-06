package seedu.menion.logic.parser;

import seedu.menion.logic.commands.Command;
import seedu.menion.logic.commands.FindCommand;
import seedu.menion.logic.commands.IncorrectCommand;
import seedu.menion.commons.core.Messages;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.commons.util.StringUtil;
import seedu.menion.logic.commands.*;
import seedu.menion.model.activity.Activity;

import static seedu.menion.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.menion.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.menion.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class ActivityParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern ACTIVITY_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    public ActivityParser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ListCommand.COMMAND_WORD:
        	return prepareList(arguments);
            
        case UndoCommand.COMMAND_WORD:
        	return new UndoCommand(arguments);
        
        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case RedoCommand.COMMAND_WORD:
        	return new RedoCommand(arguments);
        	//@@author: A0139164A
        case CompleteCommand.COMMAND_WORD:
            return prepareComplete(arguments);
            
        case UnCompleteCommand.COMMAND_WORD:
            return prepareUnComplete(arguments);
        
        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);
            
        case RemindCommand.COMMAND_WORD:
            return prepareRemind(arguments);
        
        case UnremindCommand.COMMAND_WORD:
            return prepareUnremind(arguments);
            
        case ModifyStoragePathCommand.COMMAND_WORD:
        	return new ModifyStoragePathCommand(arguments);
            
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
   //@@author A0139277U
    private Command prepareList(String args){
    	
    	args = args.trim();
    	return new ListCommand(args);
    
    }
    
    //@@author A0139164A
    private Command prepareUnremind(String args) {
        String[] splited = args.split("\\s+");
        
        // Should only contain a space. No other arguments.
        if (splited.length != 1) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnremindCommand.MESSAGE_USAGE));
        }
        
        return new UnremindCommand();
    }
    private Command prepareRemind(String args) {
        String[] splited = args.split("\\s+");
        
        // Should only contain a space, and Email of the user.
        if (splited.length != 2) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
        }
        // Checks that it is a valid email. Include regex here.
        
        return new RemindCommand(splited[1]);    
    }
    
    private Command prepareComplete(String args) {

        String[] splited = args.split("\\s+");
        
        // Should only contain a space, Activity Type and Index
        if (splited.length != 3) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
        
        // Checks that the activity type is of valid type
        boolean isValidType = false;
        String activityType = splited[1];

        if (activityType.equals(Activity.FLOATING_TASK_TYPE) || activityType.equals(Activity.TASK_TYPE) || activityType.equals(Activity.EVENT_TYPE)) {
            isValidType = true;
        }
        if (!isValidType) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = Optional.of(Integer.valueOf(splited[2]));
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
        
        return new CompleteCommand(splited);
    }
    
    private Command prepareUnComplete(String args) {

        String[] splited = args.split("\\s+");
        
        // Should only contain a space, Activity Type and Index
        if (splited.length != 3) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnCompleteCommand.MESSAGE_USAGE));
        }
        
        boolean isValidType = false; // Checks that the activity type is of valid type
        String activityType = splited[1];

        if (activityType.equals(Activity.FLOATING_TASK_TYPE) || activityType.equals(Activity.TASK_TYPE) || activityType.equals(Activity.EVENT_TYPE)) {
            isValidType = true;
        }
        if (!isValidType) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = Optional.of(Integer.valueOf(splited[2]));

        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }

        return new UnCompleteCommand(splited);
    }
    
    private Command prepareEdit(String args) {
        try {
            return new EditCommand(EditParser.parseEditCommand(args));
        } catch (IllegalValueException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, e.getMessage()));
        }
    }
    
    //@@author A0139515A
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
    	
		try {
			ArrayList<String> details = AddParser.parseCommand(args);
			if (details.isEmpty()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
			}

			return new AddCommand(details);

		} catch (IllegalValueException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, e.getMessage()));
		}
		
    }
    //@@author


    /**
     * Parses arguments in the context of the delete activity command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        String activityType = args.trim();
        if(activityType.isEmpty()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        String indexArray[] = activityType.split(" ");
        
        for (String index: indexArray){
            if(index == null){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }
        
        return new DeleteCommand(indexArray[0], indexArray);
    }
    
    
    /**
     * Parses arguments in the context of the find person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }


}