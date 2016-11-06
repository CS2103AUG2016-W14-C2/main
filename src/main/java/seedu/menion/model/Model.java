package seedu.menion.model;

import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.activity.UniqueActivityList.ActivityNotFoundException;

import java.util.Set;
import java.util.Stack;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyActivityManager newData);

    /** Returns the Activity Manager */
    ReadOnlyActivityManager getActivityManager();

    /** Deletes the given task */
    void deleteTask(ReadOnlyActivity target) throws UniqueActivityList.ActivityNotFoundException;
    
    //@@author A0139164A
    /** Completes the given Activity, given it's index. */
    void completeFloatingTask(ReadOnlyActivity activityToComplete) throws ActivityNotFoundException;
    void completeTask(ReadOnlyActivity activityToComplete) throws ActivityNotFoundException;
    
    /** Uncompletes the given Activity, given it's index. */
    void UncompleteFloatingTask(ReadOnlyActivity activityToUncomplete) throws ActivityNotFoundException;
    void UncompleteTask(ReadOnlyActivity activityToUncomplete) throws ActivityNotFoundException;
 
    /** 
     * Edits the name of the given Activity, given it's index. 
     * @throws IllegalValueException 
     */
    void editFloatingTaskName(ReadOnlyActivity floatingTaskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException;
    void editTaskName(ReadOnlyActivity taskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException;
    void editEventName(ReadOnlyActivity eventToEdit, String changes) throws IllegalValueException, ActivityNotFoundException;
    
    /**
     * Edits the note of the given Activity, given it's index. 
     * @throws IllegalValueException 
     */
    void editFloatingTaskNote(ReadOnlyActivity floatingTaskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException;
    void editTaskNote(ReadOnlyActivity taskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException;
    void editEventNote(ReadOnlyActivity eventToEdit, String changes) throws IllegalValueException, ActivityNotFoundException;
    
    /**
     * Edits the Start Date & Time of the given Task/Event, given it's index. 
     * @throws IllegalValueException 
     */
    void editTaskToFloating(ReadOnlyActivity taskToEdit) throws IllegalValueException, ActivityNotFoundException;
    void editTaskDateTime(ReadOnlyActivity taskToEdit, String newDate, String newTime) throws IllegalValueException, ActivityNotFoundException;
    void editEventEndDateTime(ReadOnlyActivity eventToEdit, String newDate, String newTime) throws IllegalValueException, ActivityNotFoundException;
    void editEventStartDateTime(ReadOnlyActivity eventToEdit, String newDate, String newTime) throws IllegalValueException, ActivityNotFoundException;
    
    
    //@@author A0139515A
    /**
     * Methods for undo 
     * 
     */
    
    /** add an activity manager state into undo state stack */
    void addStateToUndoStack(ReadOnlyActivityManager activityManager);
    
    /** retrieve previous activity manager from undo state stack */
    ReadOnlyActivityManager retrievePreviousStateFromUndoStack();
    
    /** check if there is any previous activity manager in undo state stack */
    boolean checkStatesInUndoStack();
    
    /** add a file path into undo stack */
    void addStoragePathToUndoStack(String filePath);
    
    /** retrieve previous file path from undo file path stack */
    String retrievePreviouStoragePathFromUndoStack();
    
    /** check if there is any previous file path in undo file path stack */
    boolean checkStoragePathInUndoStack();
    
    /**
     * Methods for redo
     * 
     */
    

    /** add an activity manager state into redo state stack */
    void addStateToRedoStack(ReadOnlyActivityManager activityManager);
    
    /** retrieve previous activity manager from redo state stack */
    ReadOnlyActivityManager retrievePreviousStateFromRedoStack();
  
    /** check if there is any previous activity manager in redo state stack */
    boolean checkStatesInRedoStack();
    
    /** add a file path into redo stack */
    void addStoragePathToRedoStack(String filePath);
    
    /** retrieve previous file path from redo file path stack */
    String retrievePreviouStoragePathFromRedoStack();
    
    /** check if there is any previous file path in redo file path stack */
    boolean checkStoragePathInRedoStack();
    
    /**
     * Methods for recently changed activity
     */
    
    ReadOnlyActivity getMostRecentUpdatedActivity();
    void updateRecentChangedActivity(ReadOnlyActivity activity);
    
    //@@author A0146752B
    /** Adds the given task */
    void addTask(Activity task) throws UniqueActivityList.DuplicateTaskException;
    
    /** Deletes the given floating task */
    void deleteFloatingTask(ReadOnlyActivity target) throws UniqueActivityList.ActivityNotFoundException;

    /** Adds the given floating task */
    void addFloatingTask(Activity task) throws UniqueActivityList.DuplicateTaskException;
    
    /** Deletes the given event */
    void deleteEvent(ReadOnlyActivity target) throws UniqueActivityList.ActivityNotFoundException;

    /** Adds the given event */
    void addEvent(Activity task) throws UniqueActivityList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredTaskList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyFloatingTask>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredFloatingTaskList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyEvent>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredEventList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords
     * @param parameterToSearch TODO*/
    void updateFilteredTaskList(Set<String> keywords, String parameterToSearch);
    
    /** Updates the filter of the filtered task list to filter by the given keywords
     * @param parameterToSearch TODO*/
    void updateFilteredFloatingTaskList(Set<String> keywords, String parameterToSearch);
    
    /** Updates the filter of the filtered task list to filter by the given keywords
     * @param parameterToSearch TODO*/
    void updateFilteredEventList(Set<String> keywords, String parameterToSearch);


}
