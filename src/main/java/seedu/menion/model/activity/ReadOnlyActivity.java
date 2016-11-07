package seedu.menion.model.activity;

import java.util.ArrayList;

import seedu.menion.commons.exceptions.IllegalValueException;


/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyActivity {
    
    
    // Returns activity
    Activity get();
    
    // Floating Task parameters
    ActivityName getActivityName();   
    Note getNote();

    // Task additional parameters, on top of Floating Task
    ActivityDate getActivityStartDate();
    ActivityTime getActivityStartTime();
    
    // Event additional parameters, on top of Task
    ActivityDate getActivityEndDate();
    ActivityTime getActivityEndTime();
    
    // Finding Activity Type; Can be a Floating Task, Task, or Event
    String getActivityType();

    
    // Gets the state of completion of an activity.
    Completed getActivityStatus();
    
    // An arrayList having parameters of an activity.
    ArrayList<String> getActivityDetails();
    
    void setActivityDetails();
    
    Boolean isEmailSent();
    Boolean isTimePassed();
    Boolean isEventOngoing();
    
    //@@author A0139164A
    /**
     * Methods to set the Activity's Param 
     * @param the String of the new change to make.
     */
    void setCompleted();
    void setUncompleted();
    void setActivityType(String newType) throws IllegalValueException;
    void setActivityName(String newName) throws IllegalValueException;
    void setActivityNote(String newNote) throws IllegalValueException;
    void setActivityStartDateTime(String newDate, String newTime) throws IllegalValueException;
    void setActivityEndDateTime(String newDate, String newTime) throws IllegalValueException;
    void setEmailSent(Boolean sentStatus);
    void setTimePassed(Boolean timePassed);
    void setEventOngoing(Boolean eventOngoing);
    
    /**
     * For Floating Task
     * Only checks for: Name, Note
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isFloatingTaskSameStateAs(ReadOnlyActivity other) {
        
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getActivityName().equals(this.getActivityName())); // state checks here onwards
    }

    /**
     * For Task
     * Only checks for: Name, StartDate, StartTime & Note.
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isTaskSameStateAs(ReadOnlyActivity other) {
        
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getActivityName().equals(this.getActivityName()) // state checks here onwards
                && other.getActivityStartDate().equals(this.getActivityStartDate())
                && other.getActivityStartTime().equals(this.getActivityStartTime()));
    }

    /**
     * For Event
     * Only checks for: Name, StartDate, StartTime, EndDate, EndTime & Note.
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isEventSameStateAs(ReadOnlyActivity other) {
        
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getActivityName().equals(this.getActivityName()) // state checks here onwards
                && other.getActivityStartDate().equals(this.getActivityStartDate())
                && other.getActivityStartTime().equals(this.getActivityStartTime())
                && other.getActivityEndDate().equals(this.getActivityEndDate())
                && other.getActivityEndTime().equals(this.getActivityEndTime()));
    }
    
    /**
     * For Floating Task
     * Formats the Activity as text, showing all activity details.
     */
    default String getFloatingTaskAsText() {
        
        final StringBuilder builder = new StringBuilder();
        if (getNote().equals(null)){
        	builder.append(getActivityName())
        			.append("\nCompletion status: ")
        			.append(getActivityStatus().toString());
        	return builder.toString();
        }
		else {
			builder.append(getActivityName())
					.append("\nNote: ")
					.append(getNote())
					.append("\nCompletion status: ")
					.append(getActivityStatus().toString());
			return builder.toString();
		}
    }
    
    /**
     * For Task
     * Formats the Activity as text, showing all activity details.
     */
    default String getTaskAsText() {
        
        final StringBuilder builder = new StringBuilder();
        builder.append(getActivityName())
                .append("\nDeadline: ")
                .append(getActivityStartDate().toFormattedDateString());
        if (!getActivityStartTime().toString().equals(ActivityTime.INFERRED_TIME)){
        	builder.append(", ")
        			.append(getActivityStartTime());
        }
        if (getNote().toString() != null){
        	builder.append("\nNote: ")
        			.append(getNote());
        }
       builder.append("\nCompletion status: ")
       			.append(getActivityStatus().toString());

        return builder.toString();
    }
    
    /**
     * For Event
     * Formats the Activity as text, showing all activity details.
     */
    default String getEventAsText() {
        
        final StringBuilder builder = new StringBuilder();
        
        builder.append(getActivityName())
        		.append("\nStarting from: ")
        		.append(getActivityStartDate().toFormattedDateString());
        
        if (!getActivityStartTime().toString().equals(ActivityTime.INFERRED_TIME)){
        	builder.append(", ")
			.append(getActivityStartTime());
        }
        
        builder.append("\nEnding at: ")
        		.append(getActivityEndDate().toFormattedDateString());
        
        if (!getActivityStartTime().toString().equals(ActivityTime.INFERRED_TIME)){
        	builder.append(",  ")
			.append(getActivityEndTime());
        }
        
        if (getNote().toString() != null){
        	builder.append("\nNote: ")
        			.append(getNote());
        }
        
        builder.append("\nCompletion status: ")
        		.append(getActivityStatus().toString());
        
        return builder.toString();
    }
}
