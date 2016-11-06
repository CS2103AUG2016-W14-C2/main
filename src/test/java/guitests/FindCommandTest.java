package guitests;

import org.junit.Test;
import seedu.menion.commons.core.Messages;
import seedu.menion.model.activity.Activity;
import seedu.menion.testutil.TestActivity;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class FindCommandTest extends ActivityManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find CS2106"); //no results
        assertFindResult("find roses", td.floatingTask, td.task); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete floating 1");
        assertFindResult("find roses",td.task);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find CS2103T"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findCS2103T");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    //@@author A0146752B
    private void assertFindResult(String command, TestActivity... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " activities listed!");
        
        ArrayList<TestActivity> expectedTask = new ArrayList<TestActivity>();
        ArrayList<TestActivity> expectedFloating = new ArrayList<TestActivity>();
        ArrayList<TestActivity> expectedEvent = new ArrayList<TestActivity>();
        
        //splitting by acitivtyType
        for (int i = 0; i < expectedHits.length; i++){
            if (expectedHits[i].getActivityType().equals(Activity.TASK_TYPE)) {
                expectedTask.add(expectedHits[i]);
            }
            else if (expectedHits[i].getActivityType().equals(Activity.FLOATING_TASK_TYPE)) {
                expectedFloating.add(expectedHits[i]);
            }
            else if (expectedHits[i].getActivityType().equals(Activity.EVENT_TYPE)) {
                expectedEvent.add(expectedHits[i]);
            }
        }
        assertTrue(activityListPanel.isTaskListMatching(expectedTask.toArray(new TestActivity[expectedTask.size()])));
        assertTrue(activityListPanel.isFloatingTaskListMatching(expectedFloating.toArray(new TestActivity[expectedFloating.size()])));
        assertTrue(activityListPanel.isEventListMatching(expectedEvent.toArray(new TestActivity[expectedEvent.size()])));
    }

}
