package seedu.menion.model;

import javafx.collections.transformation.FilteredList;
import seedu.menion.commons.core.ComponentManager;
import seedu.menion.commons.core.LogsCenter;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.commons.events.model.ActivityManagerChangedEvent;
import seedu.menion.commons.util.StringUtil;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.activity.UniqueActivityList.ActivityNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the activity manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ActivityManager activityManager;
    private final FilteredList<Activity> filteredTasks;

    /**
     * Initializes a ModelManager with the given Activity Manager
     * ActivityManager and its variables should not be null
     */
    public ModelManager(ActivityManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with activity manager: " + src + " and user prefs " + userPrefs);

        activityManager = new ActivityManager(src);
        filteredTasks = new FilteredList<>(activityManager.getTasks());
    }

    public ModelManager() {
        this(new ActivityManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyActivityManager initialData, UserPrefs userPrefs) {
        activityManager = new ActivityManager(initialData);
        filteredTasks = new FilteredList<>(activityManager.getTasks());
    }

    @Override
    public void resetData(ReadOnlyActivityManager newData) {
        activityManager.resetData(newData);
        indicateActivityManagerChanged();
    }

    @Override
    public ReadOnlyActivityManager getActivityManager() {
        return activityManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateActivityManagerChanged() {
        raise(new ActivityManagerChangedEvent(activityManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyActivity target) throws ActivityNotFoundException {
        activityManager.removeTask(target);
        indicateActivityManagerChanged();
    }

    @Override
    public synchronized void addTask(Activity task) throws UniqueActivityList.DuplicateTaskException {
        activityManager.addTask(task);
        updateFilteredListToShowAll();
        indicateActivityManagerChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyActivity task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyActivity task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyActivity task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyActivity task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
