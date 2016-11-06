package seedu.menion.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.parser.EditParser;
import seedu.menion.logic.parser.NattyDateParser;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList.ActivityNotFoundException;

//@@author A0139164A
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String INDEX_NOT_FOUND = "Menion cannot find the target activity in our lists, please use a valid index.";
    public static final String MESSAGE_EDITED_ACTIVITY_SUCCESS = "Menion edited your Activity to: %1$s";
    
    private final int targetIndex;
    private final String targetType;
    private final String changes;
    private final String newDate;
    private final String newTime;
    public final int paramToChange;
    
    ReadOnlyActivity activityToEdit;

    public EditCommand(String[] splited) {
        
        this.targetType = splited[1];
        this.targetIndex = Integer.valueOf(splited[2]);
        this.paramToChange = Integer.valueOf(splited[3]);
        if (paramToChange >= 2 && !EditParser.taskToFloating) {
            // Only gets here, if User is editting Date and/or Time
            this.changes = null;
            this.newDate = splited[4];
            this.newTime = splited[5];
        }
        else {
            this.changes = splited[4];
            this.newDate = null;
            this.newTime = null;
        }
    }

    @Override
    public CommandResult execute() {
        
    	assert model != null;
        UnmodifiableObservableList<ReadOnlyActivity> lastShownList;
    	storePreviousState(); // For undo
        
        try {
            if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
                lastShownList = model.getFilteredFloatingTaskList();
                activityToEdit = lastShownList.get(targetIndex);
                floatingTaskEdit(activityToEdit, this.paramToChange);
            } else if (targetType.equals(Activity.TASK_TYPE)) {
                lastShownList = model.getFilteredTaskList();
                activityToEdit = lastShownList.get(targetIndex);
                taskEdit(activityToEdit, this.paramToChange);
            } else {
                lastShownList = model.getFilteredEventList();
                activityToEdit = lastShownList.get(targetIndex);
                eventEdit(activityToEdit, this.paramToChange);
            }
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        } catch (ActivityNotFoundException pnfe) {
            return new CommandResult(INDEX_NOT_FOUND);
        }
        
        // Validates valid index is an index of an activity in the correct list
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        model.updateRecentChangedActivity(activityToEdit);
        return new CommandResult(String.format(MESSAGE_EDITED_ACTIVITY_SUCCESS, activityToEdit));
    }
    

    private void floatingTaskEdit(ReadOnlyActivity floatingTaskToEdit, int paramToChange) throws IllegalValueException, ActivityNotFoundException{

        switch (paramToChange) {

        case 0:
            String newName = this.changes;
            model.editFloatingTaskName(floatingTaskToEdit, newName);
            break;
        case 1:
            String newNote = this.changes;
            model.editFloatingTaskNote(floatingTaskToEdit, newNote);
            break;
        case 2:
            model.editTaskDateTime(floatingTaskToEdit, this.newDate, this.newTime);
            break;
        }

    }

    private void taskEdit(ReadOnlyActivity taskToEdit, int paramToChange) throws IllegalValueException, ActivityNotFoundException {
        
        switch (paramToChange) {

        case 0:
            String newName = this.changes;
            System.out.println("This is the newName: " + newName);
            model.editTaskName(taskToEdit, newName);
            break;
        case 1:
            String newNote = this.changes;
            model.editTaskNote(taskToEdit, newNote);
            break;
        case 2:
            model.editTaskDateTime(taskToEdit, this.newDate, this.newTime);
            break;
        case 4:
            if (this.changes.contains(Activity.FLOATING_TASK_TYPE)) {
                   model.editTaskToFloating(taskToEdit);
            } else {
                throw new IllegalValueException("Menion can only accept changing task to: " + Activity.FLOATING_TASK_TYPE);
            }
        }
    }
    
    private void eventEdit(ReadOnlyActivity eventToEdit, int paramToChange) throws IllegalValueException , ActivityNotFoundException{

        switch (paramToChange) {

        case 0:
            String newName = this.changes;
            model.editEventName(eventToEdit, newName);
            break;
        case 1:
            String newNote = this.changes;
            model.editEventNote(eventToEdit, newNote);
            break;
        case 3: 
            model.editEventStartDateTime(eventToEdit, this.newDate, this.newTime);
            break;
        case 4:
            model.editEventEndDateTime(eventToEdit, this.newDate, this.newTime);
            break;
        }
    }

    
    //@@author A0139515A
    /**
     * Edit command will store previous activity manager to support undo command
     * 
     */
    public void storePreviousState() {
        
        assert model != null;
        ReadOnlyActivityManager beforeState = new ActivityManager(model.getActivityManager());
    	model.addStateToUndoStack(beforeState);
    }
}
