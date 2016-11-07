package seedu.menion.testutil;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.*;

/**
 * Builds a test activity
 */
public class Activitybuilder {

    private TestActivity activity;
    
    public Activitybuilder() {
        
    }
    public Activitybuilder(ReadOnlyActivity newActivity ) {
        this.activity = new TestActivity(newActivity);
    }

    public Activitybuilder withFloatingTask(String type, ActivityName name, Note note, Completed status) throws IllegalValueException {
        
        this.activity = new TestActivity(type, name, note, status);
        return this;
    }
    
    public Activitybuilder withTask(String type, ActivityName name, Note note, ActivityDate startDate, ActivityTime startTime, Completed status) throws IllegalValueException {
       
        this.activity = new TestActivity(type, name, note, startDate, startTime, status, null, null);
        return this;
    }

    public Activitybuilder withEvent(String type, ActivityName name, Note note, ActivityDate startDate, 
            ActivityTime startTime, ActivityDate endDate, ActivityTime endTime, Completed status) throws IllegalValueException {
        
        this.activity = new TestActivity(type, name, note, startDate, startTime, endDate, endTime, status, null, null);
        return this;
    }

    public TestActivity build() {
        return this.activity;
    }

}
