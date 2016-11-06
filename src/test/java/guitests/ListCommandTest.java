package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.commands.ListCommand;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.ActivityDate;
import seedu.menion.model.activity.ActivityName;
import seedu.menion.model.activity.ActivityTime;
import seedu.menion.model.activity.Note;
import seedu.menion.testutil.ActivityManagerBuilder;
import seedu.menion.testutil.Activitybuilder;
import seedu.menion.testutil.TestActivity;
import seedu.menion.testutil.TestUtil;
import seedu.menion.testutil.TypicalTestActivities;
import seedu.menion.model.activity.*;

//@@author A0139277U
public class ListCommandTest extends ActivityManagerGuiTest {
	
	private TestActivity testTask1, testTask2, testEvent1, testEvent2, testFloating1, testFloating2;

	@Test
	public void list() {

		commandBox.runCommand("clear");
		ActivityManager testListActivities = new ActivityManager();
		generateActivitiesForList(testListActivities);
		
		// To test for list all
		assertListSuccess();	
	}

	private void assertListSuccess(){
	
		// Test list all
		commandBox.runCommand("list");
		TypicalTestActivities typicalActivities = new TypicalTestActivities();
		TestActivity[] expectedTaskList = new TestActivity[2];
		expectedTaskList[0] = testTask1;
		expectedTaskList[1] = testTask2;
		TestActivity[] expectedEventList = new TestActivity[2];
		expectedEventList[0] = testEvent1;
		expectedEventList[1] = testEvent2;
		TestActivity[] expectedFloatingList = new TestActivity[2];
		expectedFloatingList[0] = testFloating1;
		expectedFloatingList[1] = testFloating2;
		assertTrue(activityListPanel.isTaskListMatching(expectedTaskList));
		assertTrue(activityListPanel.isEventListMatching(expectedEventList));
		assertTrue(activityListPanel.isFloatingTaskListMatching(expectedFloatingList));
		
		commandBox.runCommand("list all");
		assertTrue(activityListPanel.isTaskListMatching(expectedTaskList));
		assertTrue(activityListPanel.isEventListMatching(expectedEventList));
		assertTrue(activityListPanel.isFloatingTaskListMatching(expectedFloatingList));
		
		// Test list date
		commandBox.runCommand("list 18-08-2016");
		TestActivity[] expectedTaskListDate1 = new TestActivity[1];
		expectedTaskListDate1[0] = testTask1;
		TestActivity[] expectedEventListDate1 = new TestActivity[1];
		expectedEventListDate1[0] = testEvent1;
		assertTrue(activityListPanel.isTaskListMatching(expectedTaskListDate1));
		assertTrue(activityListPanel.isEventListMatching(expectedEventListDate1));
		assertTrue(activityListPanel.isFloatingTaskListMatching());
		
		commandBox.runCommand("list 19-08-2016");
		assertTrue(activityListPanel.isTaskListMatching());
		assertTrue(activityListPanel.isEventListMatching());
		assertTrue(activityListPanel.isFloatingTaskListMatching());
		
		// Test list month
		commandBox.runCommand("list october");
		TestActivity[] expectedTaskListMonth1 = new TestActivity[1];
		expectedTaskListMonth1[0] = testTask2;
		TestActivity[] expectedEventListMonth1 = new TestActivity[1];
		expectedEventListMonth1[0] = testEvent2;
		assertTrue(activityListPanel.isTaskListMatching(expectedTaskListMonth1));
		assertTrue(activityListPanel.isEventListMatching(expectedEventListMonth1));
		assertTrue(activityListPanel.isFloatingTaskListMatching());
		
		// Test wrong command
		commandBox.runCommand("list oktober");
		assertResultMessage(ListCommand.WRONG_ARGUMENT);
		
		commandBox.runCommand("list all");
		commandBox.runCommand("complete task 1");
		commandBox.runCommand("list completed");
		TestActivity[] expectedKeywordTaskListCompleted = new TestActivity[1];
		expectedKeywordTaskListCompleted[0] = testTask1;
		assertResultMessage(ListCommand.MESSAGE_SUCCESS_LIST_COMPLETE_INITIAL + " " + 1 + " " + ListCommand.MESSAGE_SUCCESS_LIST_COMPLETE_FINAL);
		assertTrue(activityListPanel.isTaskListMatching(expectedKeywordTaskListCompleted));
		assertTrue(activityListPanel.isEventListMatching());
		assertTrue(activityListPanel.isFloatingTaskListMatching());
		
	}

	private void generateActivitiesForList(ActivityManager testActivityManager) {
		
		try {
			testTask1 = new TestActivity(Activity.TASK_TYPE, new ActivityName("cs2103t"), new Note("This is difficult"),
					new ActivityDate("18-08-2016"), new ActivityTime("1900"),
					new Completed(Completed.UNCOMPLETED_ACTIVITY), null, null);
			
			testTask2 = new TestActivity(Activity.TASK_TYPE, new ActivityName("cs2106"), new Note("This is even more difficult"),
					new ActivityDate("20-10-2016"), new ActivityTime("1900"),
					new Completed(Completed.UNCOMPLETED_ACTIVITY), null, null);
			
			testEvent1 = new TestActivity(Activity.EVENT_TYPE, new ActivityName("eat dinner"), new Note("bring money"),
					new ActivityDate("18-08-2016"), new ActivityTime("1900"), new ActivityDate("18-08-2016"),
					new ActivityTime("2015"), new Completed(Completed.UNCOMPLETED_ACTIVITY), null, null);
			
			testEvent2 = new TestActivity(Activity.EVENT_TYPE, new ActivityName("meet prof"), new Note("be prepared"),
					new ActivityDate("20-10-2016"), new ActivityTime("1900"), new ActivityDate("24-10-2016"),
					new ActivityTime("2015"), new Completed(Completed.UNCOMPLETED_ACTIVITY), null, null);
			
			testFloating1 = new TestActivity(Activity.FLOATING_TASK_TYPE, new ActivityName("Must do cs2103t"),
					new Note("Very important"), new Completed(Completed.UNCOMPLETED_ACTIVITY));
			
			testFloating2 = new TestActivity(Activity.FLOATING_TASK_TYPE, new ActivityName("Print card"),
					new Note("with color"), new Completed(Completed.UNCOMPLETED_ACTIVITY));
			
			commandBox.runCommand(testTask1.getAddCommand());
			commandBox.runCommand(testTask2.getAddCommand());
			commandBox.runCommand(testEvent1.getAddCommand());
			commandBox.runCommand(testEvent2.getAddCommand());
			commandBox.runCommand(testFloating1.getAddCommand());
			commandBox.runCommand(testFloating2.getAddCommand());

			
			
		} catch (IllegalValueException e) {
			System.out.println("Not possible");
		}
	}
}
