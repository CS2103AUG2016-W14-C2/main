package seedu.menion.logic.commands;

import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;

/**
 * Clears the activity manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Menion has been cleared!";
    
    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        
        model.storePreviousState(new ActivityManager(model.getActivityManager()));
        
        model.updateRecentChangedActivity(null);
        
        model.resetData(ActivityManager.getEmptyActivityManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
