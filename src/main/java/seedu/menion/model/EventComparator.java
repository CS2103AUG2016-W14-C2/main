
package seedu.menion.model;

import java.util.Comparator;
import javafx.collections.ObservableList;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;

//@@author A0146752B
/**
 * 
 * This class sorts a event list based on date and time
 * Events with earlier state dates are sorted in front.
 */
public class EventComparator implements Comparator<Activity> {
    
    private int dateSortInt, timeSortInt;
    private String date1, date2, time1, time2;
    
    @Override
    public int compare(Activity activityA, Activity activityB) {
        
        //compares by start date
        if (compareByDate(activityA, activityB) != 0) {
            return compareByDate(activityA, activityB);
        }
        
        //if both events have same state date, we sort by start time
        else {
            return compareByTime(activityA, activityB);
        }
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
