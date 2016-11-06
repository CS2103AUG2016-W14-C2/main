package guitests;

import org.junit.Test;

import seedu.menion.model.activity.Activity;
import seedu.menion.testutil.TestActivity;
import seedu.menion.testutil.TestUtil;
import seedu.menion.model.activity.Activity;

import static org.junit.Assert.assertTrue;
import static seedu.menion.logic.commands.DeleteCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS;

public class DeleteCommandTest extends ActivityManagerGuiTest {

    @Test
    public void delete() {
        //@@author A0146752B
        //delete the first floating task in the list
        TestActivity[] currentList = td.getTypicalFloatingTask();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList, Activity.FLOATING_TASK_TYPE);
        
        //delete the last event in the list
        currentList = td.getTypicalEvent();
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList, Activity.EVENT_TYPE);
        
        //delete a task from the middle of the list
        currentList = td.getTypicalTask();
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList, Activity.TASK_TYPE);
        
        //delete multiple task in the list
        currentList = TestUtil.removeActivityFromList(currentList, targetIndex);
        String targetIndexString = "1 3 4";
        assertDeleteMultipleSuccess(targetIndexString, currentList, Activity.TASK_TYPE);
        
        //invalid index
        commandBox.runCommand("delete task " + currentList.length + 1);
        assertResultMessage("The activity index provided is invalid");
    }

    /**
     * Runs the delete command to delete the activity at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first activity in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of activities (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestActivity[] currentList, String activityType) {
        TestActivity activityToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestActivity[] expectedRemainder = TestUtil.removeActivityFromList(currentList, targetIndexOneIndexed);
        
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            commandBox.runCommand("delete floating " + targetIndexOneIndexed);
            //confirm the list now contains all previous activities except the deleted activity
            assertTrue(activityListPanel.isFloatingTaskListMatching(expectedRemainder));
        }
        else if (activityType.equals(Activity.EVENT_TYPE)) {
            commandBox.runCommand("delete event " + targetIndexOneIndexed);
            //confirm the list now contains all previous activities except the deleted activity
            assertTrue(activityListPanel.isEventListMatching(expectedRemainder));
        }
        else if (activityType.equals(Activity.TASK_TYPE)) {
            commandBox.runCommand("delete task " + targetIndexOneIndexed);
            //confirm the list now contains all previous activities except the deleted activity
            assertTrue(activityListPanel.isTaskListMatching(expectedRemainder));
        }
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDelete));
    }
    
    /**
     * Runs the delete command to delete the activity at all specified index and confirms the result is correct.
     * @param targetIndexString e.g. to delete the activity index at 1, 3, 5 in the list, "1 3 5" should be given as the string.
     * @param currentList A copy of the current list of activities (before deletion).
     */
    private void assertDeleteMultipleSuccess(String targetIndexString, final TestActivity[] currentList, String activityType) {
        String[] targetIndexArr = targetIndexString.split(" ");
        TestActivity[] activityToDeleteArr = new TestActivity[targetIndexArr.length];
        TestActivity[] expectedRemainder = new TestActivity[targetIndexArr.length];
        StringBuilder activityToDeleteMessage = new StringBuilder();
        
        for (int i = 0; i < targetIndexArr.length; i++) {
            TestActivity activityToDelete = currentList[Integer.valueOf(targetIndexArr[i]) - 1]; //-1 because array uses zero indexing
            activityToDeleteArr[i] = activityToDelete;
        }
        
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            expectedRemainder = TestUtil.removeMultipleActivityFromList(currentList, targetIndexArr);
            commandBox.runCommand("delete floating " + targetIndexString);

            //confirm the list now contains all previous activities except the deleted activity
            assertTrue(activityListPanel.isFloatingTaskListMatching(expectedRemainder));
        }
        else if (activityType.equals(Activity.EVENT_TYPE)) {
            expectedRemainder = TestUtil.removeMultipleActivityFromList(currentList, targetIndexArr);
            commandBox.runCommand("delete event " + targetIndexString);

            //confirm the list now contains all previous activities except the deleted activity
            assertTrue(activityListPanel.isEventListMatching(expectedRemainder));
        }
        else if (activityType.equals(Activity.TASK_TYPE)) {
            expectedRemainder = TestUtil.removeMultipleActivityFromList(currentList, targetIndexArr);
            commandBox.runCommand("delete task " + targetIndexString);

            //confirm the list now contains all previous activities except the deleted activity
            assertTrue(activityListPanel.isTaskListMatching(expectedRemainder));
        }
        
        for (int i = 0; i < activityToDeleteArr.length; i++) {
            activityToDeleteMessage.append(activityToDeleteArr[i]);
            if (i == activityToDeleteArr.length - 1) {
                break;
            }
            activityToDeleteMessage.append("\n");
            activityToDeleteMessage.append("Deleted Activity: ");
        }
        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, activityToDeleteMessage.toString()));
    }
}
