package seedu.menion.testutil;

import java.util.ArrayList;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.commons.util.DateChecker;
import seedu.menion.logic.commands.CompleteCommand;
import seedu.menion.logic.commands.EditCommand;
import seedu.menion.logic.commands.UnCompleteCommand;
import seedu.menion.logic.parser.EditParser;
import seedu.menion.model.activity.*;

/**
 * A mutable Activity object. For testing only.
 */
//@@author A0139164A
public class TestActivity implements ReadOnlyActivity {

    private ActivityName name;
    private ActivityDate startDate;
    private ActivityTime startTime;
    private ActivityDate endDate;
    private ActivityTime endTime;
    private Note note;
    private String activityType;
    private Completed status;
    private Boolean emailSent;
    private Boolean activityTimePassed;
    private Boolean eventOngoing;
    
    // Every Activity Object will have an array list of it's details for ease of
    // accessibility
    private ArrayList<String> activityDetails;

    /**
     * For floatingTask Every field must be present and not null.
     */
    public TestActivity(String type, ActivityName name, Note note, Completed status) {
        
        this.activityType = type;
        this.name = name;
        this.note = note;
        this.status = status;
        setActivityDetails();
    }
    
    /**
     * For Task
     * Every field must be present and not null.
     */
    public TestActivity(String type, ActivityName name, Note note, ActivityDate startDate, ActivityTime startTime, Completed status, Boolean activityTimePassed, Boolean emailSent) {
        
        this.activityType = type;
        this.name = name;
        this.note = note;
        this.startDate = startDate;
        this.startTime = startTime;
        this.status = status;
        if (emailSent == null){
        	this.emailSent = false;
        }
        else {
        	this.emailSent = emailSent;
        }
        if (activityTimePassed == null){
        	this.activityTimePassed = false;
        }
        else {
        	this.activityTimePassed = activityTimePassed;
        }
        setActivityDetails();
    }
    
    /**
     * For Event
     * Every field must be present and not null.
     */
    public TestActivity(String type, ActivityName name, Note note, ActivityDate startDate, ActivityTime startTime, ActivityDate endDate, ActivityTime endTime, Completed status, Boolean activityTimePassed, Boolean eventOngoing) {
        
        this.activityType = type;
        this.name = name;
        this.note = note;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.status = status;
        if (activityTimePassed == null){
        	this.activityTimePassed = false;
        }
        else {
        	this.activityTimePassed = activityTimePassed;
        }
        if (eventOngoing == null){
        	this.eventOngoing = false;
        }
        else {
        	this.eventOngoing = eventOngoing;
        }
        setActivityDetails();
    }
    

    /**
     * Copy constructor.
     */
    public TestActivity (ReadOnlyActivity source) {
        
        if (source.getActivityType().equals(Activity.FLOATING_TASK_TYPE)) {
            activityType = source.getActivityType();
            name = source.getActivityName();
            note = source.getNote();
            status = source.getActivityStatus();
        } else if (source.getActivityType().equals(Activity.TASK_TYPE)) {
            activityType = source.getActivityType();;
            name = source.getActivityName();
            note = source.getNote();
            startDate = source.getActivityStartDate();
            startTime = source.getActivityStartTime();
            status = source.getActivityStatus();
            emailSent = source.isEmailSent();
            activityTimePassed = source.isTimePassed();
        } else if (source.getActivityType().equals(Activity.EVENT_TYPE)) {
            activityType = source.getActivityType();;
            name = source.getActivityName();
            note = source.getNote();
            startDate = source.getActivityStartDate();
            startTime = source.getActivityStartTime();
            endDate = source.getActivityEndDate();
            endTime = source.getActivityEndTime();
            status = source.getActivityStatus();
            activityTimePassed = source.isTimePassed();
            eventOngoing = source.isEventOngoing();
        }
        this.activityDetails = source.getActivityDetails();

    }
    
    // Setters
    @Override
    public void setCompleted() {
        this.status = new Completed(true);
    }

    @Override
    public void setUncompleted() {
        this.status = new Completed(false);
    }
    
    @Override
    public void setActivityDetails() {
        if (activityType == Activity.FLOATING_TASK_TYPE) {
            activityDetails = new ArrayList<String>(Activity.FLOATING_TASK_LENGTH);
            activityDetails.add(activityType);
            activityDetails.add(name.toString());
            activityDetails.add(note.toString());
            activityDetails.add(status.toString());
        } else if (activityType == Activity.TASK_TYPE) {
            activityDetails = new ArrayList<String>(Activity.TASK_LENGTH);
            activityDetails.add(activityType);
            activityDetails.add(name.toString());
            activityDetails.add(note.toString());
            activityDetails.add(startDate.toString());
            activityDetails.add(startTime.toString());
            activityDetails.add(status.toString());
        } else if (activityType == Activity.EVENT_TYPE) {
            activityDetails = new ArrayList<String>(Activity.EVENT_LENGTH);
            activityDetails.add(activityType);
            activityDetails.add(name.toString());
            activityDetails.add(note.toString());
            activityDetails.add(startDate.toString());
            activityDetails.add(startTime.toString());
            activityDetails.add(endDate.toString());
            activityDetails.add(endTime.toString());
            activityDetails.add(status.toString());
        }
    }
    
    // Getters
    @Override
    public ActivityName getActivityName() {
        return this.name;
    }

    @Override
    public Completed getActivityStatus() {
        return this.status;
    }

    @Override
    public Note getNote() {
        return this.note;
    }

    @Override
    public ActivityDate getActivityStartDate() {
        return this.startDate;
    }

    @Override
    public ActivityTime getActivityStartTime() {
        return this.startTime;
    }

    @Override
    public ActivityDate getActivityEndDate() {
        return this.endDate;
    }

    @Override
    public ActivityTime getActivityEndTime() {
        return this.endTime;
    }

    @Override
    public String getActivityType() {
        return this.activityType;
    }

    // returns the arrayList consisting of an activity's details.
    @Override
    public ArrayList<String> getActivityDetails() {
        return activityDetails;
    }
    
    /**
     * List of GetCommands for Testing
     * ------IMPORTANT FOR NEW DEVELOPERS-------
     * Update example where necessary, when changes are made to command
     */
    
    //Example: add helloworld by: tomorrow 9pm
    public String getAddCommand() {
        
        StringBuilder build = new StringBuilder();
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            build.append("add ");
            build.append(this.name.toString());
            build.append(" n:");
            build.append(this.getNote().toString());
        } else if (activityType.equals(Activity.TASK_TYPE)) {
            build.append("add ");
            build.append(this.name.toString());
            build.append(" by: ");
            build.append(changeDateFormat(this.getActivityStartDate().value));
            build.append(" ");
            build.append(this.getActivityStartTime().value);
            build.append(" n:");
            build.append(this.getNote().toString());
        } else if (activityType.equals(Activity.EVENT_TYPE)) {
            build.append("add ");
            build.append(this.name.toString());
            build.append(" from: ");
            build.append(changeDateFormat(this.getActivityStartDate().value));
            build.append(" ");
            build.append(this.getActivityStartTime().value);
            build.append(" to: ");
            build.append(changeDateFormat(this.getActivityEndDate().value));
            build.append(" ");
            build.append(this.getActivityEndTime().value);
            build.append(" n:");
            build.append(this.getNote().toString());
        }
        return build.toString();
    }

    //Example: complete task 1
    public String getCompleteCommand(int index) {
        
        StringBuilder build = new StringBuilder();
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            build.append(CompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.FLOATING_TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        } else if (activityType.equals(Activity.TASK_TYPE)) {
            build.append(CompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        } else if (activityType.equals(Activity.EVENT_TYPE)) {
            build.append(CompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.EVENT_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        }
        return build.toString();
    }

    //Example: uncomplete task 1
    public String getUncompleteCommand(int index) {
        
        StringBuilder build = new StringBuilder();
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            build.append(UnCompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.FLOATING_TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        } else if (activityType.equals(Activity.TASK_TYPE)) {
            build.append(UnCompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        } else if (activityType.equals(Activity.EVENT_TYPE)) {
            build.append(UnCompleteCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.EVENT_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
        }
        return build.toString();
    }
    
    //Example: edit task 1 name: helloworld
    public String getEditNameCommand(int index, String newName) {
        
        StringBuilder build = new StringBuilder();
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            build.append(EditCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.FLOATING_TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
            build.append(" ");
            build.append(EditParser.NAME_PARAM);
            build.append(" ");
            build.append(newName);
        } else if (activityType.equals(Activity.TASK_TYPE)) {
            build.append(EditCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
            build.append(" ");
            build.append(EditParser.NAME_PARAM);
            build.append(" ");
            build.append(newName);
        } else if (activityType.equals(Activity.EVENT_TYPE)) {
            build.append(EditCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.EVENT_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
            build.append(" ");
            build.append(EditParser.NAME_PARAM);
            build.append(" ");
            build.append(newName);
        }
        return build.toString();
    }

    //Example: edit task 1 note: helloworld
    public String getEditNoteCommand(int index, String newNote) {
        
        StringBuilder build = new StringBuilder();
        if (activityType.equals(Activity.FLOATING_TASK_TYPE)) {
            build.append(EditCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.FLOATING_TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
            build.append(" ");
            build.append(EditParser.NOTE_PARAM);
            build.append(" ");
            build.append(newNote);
        } else if (activityType.equals(Activity.TASK_TYPE)) {
            build.append(EditCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.TASK_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
            build.append(" ");
            build.append(EditParser.NOTE_PARAM);
            build.append(" ");
            build.append(newNote);
        } else if (activityType.equals(Activity.EVENT_TYPE)) {
            build.append(EditCommand.COMMAND_WORD);
            build.append(" ");
            build.append(Activity.EVENT_TYPE);
            build.append(" ");
            build.append(String.valueOf(index));
            build.append(" ");
            build.append(EditParser.NOTE_PARAM);
            build.append(" ");
            build.append(newNote);
        }
        return build.toString();
    }
    
    //Example: edit task 1 by: tomorrow 6pm
    public String getEditTaskDateTimeCommand(int index, String newDateTime) {
        
        StringBuilder build = new StringBuilder();
        build.append(EditCommand.COMMAND_WORD);
        build.append(" ");
        build.append(Activity.TASK_TYPE);
        build.append(" ");
        build.append(String.valueOf(index));
        build.append(" ");
        build.append(EditParser.TASK_DEADLINE_PARAM);
        build.append(" ");
        build.append(newDateTime);
        return build.toString();
    }
    
    //Example: edit event 1 from: tomorrow 2pm
    public String getEditEventFromDateTimeCommand(int index, String newDateTime) {
        
        StringBuilder build = new StringBuilder();
        build.append(EditCommand.COMMAND_WORD);
        build.append(" ");
        build.append(Activity.EVENT_TYPE);
        build.append(" ");
        build.append(String.valueOf(index));
        build.append(" ");
        build.append(EditParser.EVENT_FROM_PARAM);
        build.append(" ");
        build.append(newDateTime);
        return build.toString();
    }
    
    //Example: edit event 1 to: tomorrow 2pm
    public String getEditEventToDateTimeCommand(int index, String newDateTime) {
        
        StringBuilder build = new StringBuilder();
        build.append(EditCommand.COMMAND_WORD);
        build.append(" ");
        build.append(Activity.EVENT_TYPE);
        build.append(" ");
        build.append(String.valueOf(index));
        build.append(" ");
        build.append(EditParser.EVENT_TO_PARAM);
        build.append(" ");
        build.append(newDateTime);
        return build.toString();
    }
    
    //Example: edit floating 1 by: 12-12-2016 1700
    public String getFloatingTaskChangeCommand(int index, String newDateTime) {
        
        StringBuilder build = new StringBuilder();
        build.append(EditCommand.COMMAND_WORD);
        build.append(" ");
        build.append(Activity.FLOATING_TASK_TYPE);
        build.append(" ");
        build.append(String.valueOf(index));
        build.append(" ");
        build.append(EditParser.TASK_DEADLINE_PARAM);
        build.append(" ");
        build.append(newDateTime);
        return build.toString();
    }
    
    //Example: edit task 1 to: floating
    public String getTaskChangeCommand(int index) {
        
        StringBuilder build = new StringBuilder();
        build.append(EditCommand.COMMAND_WORD);
        build.append(" ");
        build.append(Activity.TASK_TYPE);
        build.append(" ");
        build.append(String.valueOf(index));
        build.append(" ");
        build.append(EditParser.EVENT_TO_PARAM);
        build.append(" ");
        build.append(Activity.FLOATING_TASK_TYPE);
        return build.toString();
    }
    
    // SETTERS
    /**
     * List of methods to set Activity's param : Name, Note, startDate, startTime
     */
    @Override
    public void setActivityType(String newType) throws IllegalValueException {
        
        if (!newType.equals(Activity.FLOATING_TASK_TYPE) && !newType.equals(Activity.TASK_TYPE) && !newType.equals(Activity.EVENT_TYPE)) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_TYPE);
        }
        else {
            this.activityType = newType;
        }
    }
    @Override
    public void setActivityName(String newName) {
        
        assert (newName != null);
        try {
            this.name = new ActivityName(newName);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setActivityNote(String newNote) {
        
        assert (newNote != null);
        try {
            this.note = new Note(newNote);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setActivityStartDateTime(String newDate, String newTime) throws IllegalValueException {
        
        boolean isTask = this.activityType.equals(Activity.TASK_TYPE);
        boolean isEvent = this.activityType.equals(Activity.EVENT_TYPE);
        assert (isTask || isEvent);
        
        ActivityDate newDateObject = new ActivityDate(newDate);
        ActivityTime newTimeObject = new ActivityTime(newTime);
        if (isEvent) {
            DateChecker check = new DateChecker();
            check.validEventDate(newDateObject, newTimeObject, this.endDate, this.endTime);
        }
        this.startDate = newDateObject;
        this.startTime = newTimeObject;

    }

    @Override
    public void setActivityEndDateTime(String newDate, String newTime) throws IllegalValueException {
        
        boolean isEvent = this.activityType.equals(Activity.EVENT_TYPE);
        DateChecker check = new DateChecker();
        assert (isEvent);
        ActivityDate newDateObject = new ActivityDate(newDate);
        ActivityTime newTimeObject = new ActivityTime(newTime);
        check.validEventDate(this.startDate, this.startTime, newDateObject, newTimeObject);
        this.endDate = newDateObject;
        this.endTime = newTimeObject;
    }
  
    //@@author A0139277U
    /**
     * This method changes the format of date from dd-mm-yyyy to mm-dd-yyyy
     * @param dateToChange
     * @return a date String in the format of mm-dd-yyyy
     */
    private static String changeDateFormat(String dateToChange){
    
    	String[] parts = dateToChange.split("-");
    	return parts[1] + "-" + parts[0] + "-" + parts[2]; 
    	
    }
  
    @Override
    public String toString() {
        switch(this.activityType){
        case Activity.FLOATING_TASK_TYPE:
            return getFloatingTaskAsText();
        case Activity.TASK_TYPE:
            return getTaskAsText();
        case Activity.EVENT_TYPE:
            return getEventAsText();
        }
        return null;
    }

    public Boolean isEmailSent(){
    	return this.emailSent;
    }
    
    public Boolean isTimePassed(){
    	return this.activityTimePassed;
    }
    
    public void setEmailSent(Boolean sentStatus){
    	this.emailSent = sentStatus;
    }
    
    public void setTimePassed(Boolean timePassed){
    	this.activityTimePassed = timePassed;
    }

	@Override
	public Boolean isEventOngoing() {
    	return this.eventOngoing;
	}

	@Override
	public void setEventOngoing(Boolean eventOngoing) {
    	this.eventOngoing = eventOngoing;		
	}
    
    @Override
    public Activity get() {
        return null;
    }
    
}