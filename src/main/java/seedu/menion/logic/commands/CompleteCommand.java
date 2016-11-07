package seedu.menion.logic.commands;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.activity.UniqueActivityList.ActivityNotFoundException;
import seedu.menion.model.activity.UniqueActivityList.DuplicateTaskException;

/**
 * Completes an activity given the index and it's activtyType
 */
//@@author A0139164A
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Completes an activity using their type and index: "
            + "\n" + "Parameters: [Activity_Type] + [Activity_Index] \n"
            + "Example: complete " + Activity.TASK_TYPE + " 1";
    public static final String MESSAGE_COMPLETED_ACTIVITY_SUCCESS = "Completed Activity: %1$s";
    public static final String MESSAGE_ALREADY_COMPLETED = "Menion has already completed this activity!";
    public static final String MESSAGE_EVENT_CANT_BE_COMPLETED = "Events do not need to be completed!";
    
    public final int targetIndex;
    public final String targetType;
    ReadOnlyActivity activityToComplete;

    public CompleteCommand(String[] splited) {
        this.targetType = splited[1];
        this.targetIndex = Integer.valueOf(splited[2]) - 1;
    }

    @Override
    public CommandResult execute() {
    	assert model != null;
    	
    	model.storePreviousState(new ActivityManager(model.getActivityManager()));
    	
        UnmodifiableObservableList<ReadOnlyActivity> lastShownList;
        
        try {
            if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
                lastShownList = model.getFilteredFloatingTaskList();
                activityToComplete = lastShownList.get(targetIndex);
            } else if (targetType.equals(Activity.TASK_TYPE)) {
                lastShownList = model.getFilteredTaskList();
                activityToComplete = lastShownList.get(targetIndex);
            } else {
                return new CommandResult(String.format(MESSAGE_EVENT_CANT_BE_COMPLETED, activityToComplete));
            }
        } catch (IndexOutOfBoundsException e) {
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }
        
        if (lastShownList.size() <= targetIndex || targetIndex < 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }
        
        if (activityToComplete.getActivityStatus().status) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_ALREADY_COMPLETED);
        }
        
        callCompleteActivity(targetType, activityToComplete); // Calls the correct method depending on type of activity.
        model.updateRecentChangedActivity(activityToComplete);
        return new CommandResult(String.format(MESSAGE_COMPLETED_ACTIVITY_SUCCESS, activityToComplete));
    }

    private void callCompleteActivity(String targetType, ReadOnlyActivity activityToComplete) {
        assert model != null;
        
        try {
            if (targetType.equals(Activity.FLOATING_TASK_TYPE)) {
                model.completeFloatingTask(activityToComplete);
            } else if (targetType.equals(Activity.TASK_TYPE)){
                model.completeTask(activityToComplete);
            } else {
                assert false: "Events do not need to be completed";
            }
        }catch (ActivityNotFoundException pnfe) {
            assert false : "The target activity cannot be missing";
        }
        
    }
}
