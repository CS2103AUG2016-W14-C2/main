package seedu.menion.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.commands.EditCommand;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;

//@@author A0139164A
public class EditParser {

    public static final String NAME_PARAM = "name:";
    public static final String NOTE_PARAM = "n:";
    public static final String TASK_DEADLINE_PARAM = "by:";
    public static final String EVENT_FROM_PARAM = "from:";
    public static final String EVENT_TO_PARAM = "to:";
    public static final String SEPARATOR = "/ ";
    public static final String MESSAGE_INVALID_PARAMETER = "Menion detected an invalid parameter for the current type! \n" +
            "Please make sure it is, for: \n" + 
            Activity.FLOATING_TASK_TYPE + ": "  + NAME_PARAM + SEPARATOR + NOTE_PARAM + SEPARATOR + TASK_DEADLINE_PARAM + "\n" +
            Activity.TASK_TYPE + ": " + NAME_PARAM + SEPARATOR + NOTE_PARAM + SEPARATOR + TASK_DEADLINE_PARAM + "\n" +
            Activity.EVENT_TYPE + ": " + NAME_PARAM + SEPARATOR + NOTE_PARAM + SEPARATOR + EVENT_FROM_PARAM + SEPARATOR + EVENT_TO_PARAM;

    public static final String MESSAGE_USAGE = EditCommand.COMMAND_WORD
            + ": edit an activity using their type, index, [Parameters to change] and new changes: " + "\n"
            + "Parameters: [Activity_Type] + [Activity_Index] [ '"+NAME_PARAM+"', '"+NOTE_PARAM+"', '"+TASK_DEADLINE_PARAM+" (Date & Time)', '"+EVENT_FROM_PARAM+" (Date & Time)' '"+EVENT_TO_PARAM+" (Date & Time)' ] + [Changes]\n"
            + "Example: " + EditCommand.COMMAND_WORD + " task 1 by: 10-10-2016 1900 \n" 
            + "Example: " + EditCommand.COMMAND_WORD+ " task 1 n: write in red ink \n" 
            + "Example: " + EditCommand.COMMAND_WORD + " event 1 name: ORD";

            
    UnmodifiableObservableList<ReadOnlyActivity> lastShownList; // List of all current activities.
    public static String[] editDetails = new String[6];
    private static ArrayList<String> fromNatty = new ArrayList<String>();
    public static boolean taskToFloating;
    
    public static String[] parseEditCommand(String details) throws IllegalValueException {
        
        editDetails = details.split("\\s+");
        
        // Checks for valid number of parameters.
        // Must be 5 and above. [Command] + [Type] + [index] + [parameter] + [changes]
        if (editDetails.length <= 4) {
            throw new IllegalValueException(MESSAGE_USAGE);
        }

        // Checks for valid activityType
        String activityType = editDetails[1];
        validActivityType(activityType);

        // Checks for presence of index 
        int targetIndex = Integer.valueOf(editDetails[2]) - 1;
        validIndex(targetIndex);
        editDetails[2] = String.valueOf(targetIndex); // replace with verified index
        
        // Checks for paramToChange
        String indexOfParam = String.valueOf(checkParam(editDetails[3], activityType));
        editDetails[3] = indexOfParam; // Replace with an index for editCommand to check.
        editDetails[4] = arrayToString(Arrays.copyOfRange(editDetails, 4, editDetails.length));
        
        // Parse the changes with natty, if required to
        // From indexOfParam 2 -> 5. It requires Natty to parse Date & Time. 0, and 1 is simply Name and note.
        taskToFloating = changeTaskToFloating(activityType, indexOfParam);
        if (Integer.valueOf(indexOfParam) >= 2 && !taskToFloating) {
            NattyDateParser.parseDate(editDetails[4], fromNatty);
            editDetails[4] = fromNatty.get(0);
            editDetails[5] = fromNatty.get(1);
        } else {
            // Do nothing. No changes to be made.
        }
        // If indexOfParam is >= 2. Implies user is changing date & Time.
        return editDetails;
    }

    private static void validActivityType(String typeToCheck) throws IllegalValueException {
        
        if (typeToCheck.equals(Activity.FLOATING_TASK_TYPE) || typeToCheck.equals(Activity.TASK_TYPE) 
                || typeToCheck.equals(Activity.EVENT_TYPE)) {  
            return;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_PARAMETER);
        }
    }
    
    private static void validIndex(int targetIndex) throws IllegalValueException {
        
        Optional<Integer> index = Optional.of(targetIndex);
        if (index.isPresent()){
            return;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_PARAMETER);
        }
    }
    
    /**
     * 
     * @param paramToChange
     * @return an integer to match with the param to change, refer below for index:
     *         0 = name (For all) 
     *         1 = note (For all) 
     *         2 = by (For Tasks only, and floating Task) 
     *         3 = from (For Event's Start Date & Time)
     *         4 = to (For Event's End Date & Time)
     */
    private static int checkParam(String paramToChange, String activityType) throws IllegalValueException {

        if (paramToChange.equals(NAME_PARAM)) {
            return 0;
        } else if (paramToChange.equals(NOTE_PARAM)) {
            return 1;
        } else if (paramToChange.equals(TASK_DEADLINE_PARAM) && (activityType.equals(Activity.TASK_TYPE) || (activityType.equals(Activity.FLOATING_TASK_TYPE)))) {
            return 2;
        } else if (paramToChange.equals(EVENT_FROM_PARAM) && activityType.equals(Activity.EVENT_TYPE)) {
            return 3;
        } else if (paramToChange.equals(EVENT_TO_PARAM) && (activityType.equals(Activity.EVENT_TYPE) || activityType.equals(Activity.TASK_TYPE))) {
            return 4;
        }
        throw new IllegalValueException(MESSAGE_INVALID_PARAMETER);
    }

    private static String arrayToString(String[] from) {
        StringBuilder build = new StringBuilder();
        
        for (int i = 0; i < from.length; i++) {
            build.append(from[i]);
            build.append(" ");
        }
        return build.toString();
    }
    
    /**
     *  This is a unique case of edit. Where we change Task -> FloatingTask. 
     *  returns true if Activity type is: "Task" & indexOfParam is "to:" 
     */
    private static boolean changeTaskToFloating(String activityType, String indexOfParam) {
        if (activityType.equals(Activity.TASK_TYPE) && Integer.valueOf(indexOfParam) == 4) {
            return true;
        } else {
            return false;
        }
    }
}
