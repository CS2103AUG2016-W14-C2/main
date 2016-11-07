
package seedu.menion.model;

import java.util.Comparator;
import javafx.collections.ObservableList;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;

//@@author A0146752B
/**
 * 
 * This class sorts a floating task list based on completion status,
 * Completed floating tasks are pushed to the bottom of the list,
 */
public class FloatingTaskComparator implements Comparator<Activity> {
    
    
    
    @Override
    public int compare(Activity activityA, Activity activityB) {
        
        //sort by completion status 
        return compareByCompletionStatus(activityA, activityB);
    }
    
    /**
     * This method compares two activities by their completion status
     * @param activityA is the first activity
     * @param activityB is the second activity
     * @return returns 0 if both have same completion status
     *          returns 1 if activityA is completed and activityB is uncompleted
     *          returns -1 if activityB is completed and activityA is uncompleted
     */
    private int compareByCompletionStatus(Activity activityA, Activity activityB) {
        int completeSortInt;
        if (activityA.getActivityStatus().toString().equals(activityB.getActivityStatus().toString())) {
            completeSortInt = 0;
        }
        else if (activityA.getActivityStatus().toString().equals("Completed")
                && activityB.getActivityStatus().toString().equals("Uncompleted")) {
            completeSortInt = 1;
        }
        else {
            completeSortInt = -1;
        }
        return completeSortInt;
    }
}
