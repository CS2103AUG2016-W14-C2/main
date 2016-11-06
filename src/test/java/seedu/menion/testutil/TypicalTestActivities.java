package seedu.menion.testutil;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.activity.*;
//@@author A0139164A
/**
 *
 */
public class TypicalTestActivities {

    public static TestActivity floatingTask, floatingTask2, floatingTask3, floatingTask4, floatingTask5, floatingTask6, 
                                task, task2, task3, task4, task5, task6,
                                event, event2, event3, event4, event5, event6;

    public TypicalTestActivities() {
        try {
            floatingTask =  new Activitybuilder().withFloatingTask(Activity.FLOATING_TASK_TYPE
                    , new ActivityName("CS2103T Lab"), new Note("Bring roses")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            floatingTask2 = new Activitybuilder().withFloatingTask(Activity.FLOATING_TASK_TYPE
                    , new ActivityName("CS2103T Lab2"), new Note("Prepare questions")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            floatingTask3 = new Activitybuilder().withFloatingTask(Activity.FLOATING_TASK_TYPE
                    , new ActivityName("CS2103T Lab3"), new Note("FAIL")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            floatingTask4 = new Activitybuilder().withFloatingTask(Activity.FLOATING_TASK_TYPE
                    , new ActivityName("CS2103T Lab4"), new Note("Remove issues")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            floatingTask5 = new Activitybuilder().withFloatingTask(Activity.FLOATING_TASK_TYPE
                    , new ActivityName("CS2103T Lab5"), new Note("Prepare for demo")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            floatingTask6 = new Activitybuilder().withFloatingTask(Activity.FLOATING_TASK_TYPE
                    , new ActivityName("CS2103T Lab6"), new Note("Prepare for demo")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            task = new Activitybuilder().withTask(Activity.TASK_TYPE
                    , new ActivityName("CS2103T testing"), new Note("give roses")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1900")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            task2 = new Activitybuilder().withTask(Activity.TASK_TYPE
                    , new ActivityName("CS2103T testing2"), new Note("it is so hard!")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1900")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            task3 = new Activitybuilder().withTask(Activity.TASK_TYPE
                    , new ActivityName("CS2103T testing3"), new Note("it is so hard!")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1900")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            task4 = new Activitybuilder().withTask(Activity.TASK_TYPE
                    , new ActivityName("CS2103T testing4"), new Note("it is so hard!")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1900")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            task5 = new Activitybuilder().withTask(Activity.TASK_TYPE
                    , new ActivityName("CS2103T testing5"), new Note("it is so hard!")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1900")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            task6 = new Activitybuilder().withTask(Activity.TASK_TYPE
                    , new ActivityName("CS2103T testing6"), new Note("it is so hard!")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1900")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)).build();
            event = new Activitybuilder().withEvent(Activity.EVENT_TYPE
                    , new ActivityName("CS2103T tutorial"), new Note("Don't Sleep")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1500")
                    , new ActivityDate("10-12-2016"), new ActivityTime("1800")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)) .build();   
            event2 = new Activitybuilder().withEvent(Activity.EVENT_TYPE
                    , new ActivityName("CS2103T tutorial2"), new Note("Don't Sleep")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1500")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1800")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)) .build();
            event3 = new Activitybuilder().withEvent(Activity.EVENT_TYPE
                    , new ActivityName("CS2103T tutorial3"), new Note("Don't Sleep")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1500")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1800")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)) .build();
            event4 = new Activitybuilder().withEvent(Activity.EVENT_TYPE
                    , new ActivityName("CS2103T tutorial4"), new Note("Don't Sleep")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1500")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1800")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)) .build();
            event5 = new Activitybuilder().withEvent(Activity.EVENT_TYPE
                    , new ActivityName("CS2103T tutorial5"), new Note("Don't Sleep")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1500")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1800")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)) .build();
            event6 = new Activitybuilder().withEvent(Activity.EVENT_TYPE
                    , new ActivityName("CS2103T tutorial6"), new Note("Don't Sleep")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1500")
                    , new ActivityDate("10-08-2016"), new ActivityTime("1800")
                    , new Completed(Completed.UNCOMPLETED_ACTIVITY)) .build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadActivityManagerWithSampleData(ActivityManager ab) {

        try {
            ab.addFloatingTask(new Activity(floatingTask));
            ab.addFloatingTask(new Activity(floatingTask2));
            ab.addFloatingTask(new Activity(floatingTask3));
            ab.addFloatingTask(new Activity(floatingTask4));
            ab.addFloatingTask(new Activity(floatingTask5));
            ab.addTask(new Activity(task));
            ab.addTask(new Activity(task2));
            ab.addTask(new Activity(task3));
            ab.addTask(new Activity(task4));
            ab.addTask(new Activity(task5));
            ab.addEvent(new Activity(event));
            ab.addEvent(new Activity(event2));
            ab.addEvent(new Activity(event3));
            ab.addEvent(new Activity(event4));
            ab.addEvent(new Activity(event5));
        } catch (UniqueActivityList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestActivity[] getTypicalTask() {
        return new TestActivity[]{task, task2, task3, task4, task5};
    }   
    
    public TestActivity[] getTypicalFloatingTask() {
        return new TestActivity[]{floatingTask, floatingTask2, floatingTask3, floatingTask4, floatingTask5};
    }
    
    public TestActivity[] getTypicalEvent() {
        return new TestActivity[]{event, event2, event3, event4, event5};
    }

    public ActivityManager getTypicalActivityManager(){
        ActivityManager ab = new ActivityManager();
        loadActivityManagerWithSampleData(ab);
        return ab;
    }
}
