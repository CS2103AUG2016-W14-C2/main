package seedu.menion.testutil;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;

/**
 * A utility class to help with building activity manager objects.
 * Example usage: <br>
 *     {@code taskManager ab = new MenionBuilder().withActivity("buy milk").build();}
 */
public class MenionBuilder {

    private ActivityManager taskManager;

    public MenionBuilder(ActivityManager taskManager){
        this.taskManager = taskManager;
    }

    public MenionBuilder withActivity(Activity testActivity) throws UniqueActivityList.DuplicateTaskException {
        taskManager.addTask(testActivity);
        return this;
    }
    
    public ActivityManager build(){
        return taskManager;
    }
}
