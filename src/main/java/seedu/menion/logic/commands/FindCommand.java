package seedu.menion.logic.commands;
import java.util.Set;

import seedu.menion.model.ModelManager;

/**
 * Finds and lists all activities in menion whose name/note contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose names/notes contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " buy milk chocolate";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(this.keywords, ModelManager.listKeyword);
        model.updateFilteredEventList(this.keywords, ModelManager.listKeyword);
        model.updateFilteredFloatingTaskList(this.keywords, ModelManager.listKeyword);
        return new CommandResult(getMessageForActivityListShownSummary(model.getFilteredTaskList().size() 
                                                                    + model.getFilteredFloatingTaskList().size()
                                                                    + model.getFilteredEventList().size()));
    }

}