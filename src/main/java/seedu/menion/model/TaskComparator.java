package seedu.menion.model;

import java.util.Comparator;
import javafx.collections.ObservableList;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;

//@@author A0146752B
/**
 * 
 * This class sorts a task list based on completion status,
 * then after sorting it based on date and time.
 * Completed tasks are pushed to the bottom of the list,
 * and tasks with earlier state dates are sorted in front.
 */
public class TaskComparator implements Comparator<Activity> {
    
    private int completeSortInt, dateSortInt, timeSortInt;
    private String date1, date2, time1, time2;
    
    @Override
    public int compare(Activity activityA, Activity activityB) {
        
        //sort by completion status 
        
        if (compareByCompletionStatus(activityA, activityB) != 0){
            return completeSortInt;
        }
        //if both tasks have same completion status we sort by start date
        else {
          //compares by start date
            if (compareByDate(activityA, activityB) != 0) {
                return compareByDate(activityA, activityB);
            }
            
            //if both events have same state date, we sort by start time
            else {
                return compareByTime(activityA, activityB);
            }
        }
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
    
    /**
     * This method compares two activities by their starting time as strings
     * @param activityA is the first activity
     * @param activityB is the second activity
     * @return the the two start time lexicographically
     */
    private int compareByTime(Activity activityA, Activity activityB) {
        time1 = activityA.getActivityStartTime().toString();
        time2 = activityB.getActivityStartTime().toString();

        timeSortInt = time1.compareTo(time2);

        return timeSortInt;
    }

    /**
     * This method compares two activities by their starting date as strings
     * @param activityA is the first activity
     * @param activityB is the second activity
     * @return the the two start dates lexicographically
     */
    private int compareByDate(Activity activityA, Activity activityB) {
        date1 = activityA.getActivityStartDate().toString();
        date2 = activityB.getActivityStartDate().toString();

        String[] valueOfDate1 = date1.split("-");
        String[] valueOfDate2 = date2.split("-");

        date1 = valueOfDate1[2] + valueOfDate1[1] + valueOfDate1[0];
        date2 = valueOfDate2[2] + valueOfDate2[1] + valueOfDate2[0];

        dateSortInt = date1.compareTo(date2);
        
        return dateSortInt;
    }
}
