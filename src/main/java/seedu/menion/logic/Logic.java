package seedu.menion.logic;

import javafx.collections.ObservableList;
import seedu.menion.logic.commands.CommandResult;
import seedu.menion.model.activity.ReadOnlyActivity;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of activities */
    ObservableList<ReadOnlyActivity> getFilteredActivityList();

}
