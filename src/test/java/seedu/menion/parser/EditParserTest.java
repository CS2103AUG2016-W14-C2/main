package seedu.menion.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.parser.EditParser;

//@@author A0139164A
public class EditParserTest {
    
    /**
     * Test for EditParser class
     * When a user passes in a String, EditParser will return an array of String
     * index 0 = (To be ignored) -> Because it is the edit command
     * index 1 = Activity Type: floating, task, event
     * index 2 = INDEX_OF_PARAM: refer to list below.
     * index 3 = The index in the list: 1, 2, 3... 
     * index 4 = The changes: (Index to hold Date from Natty), helloWorld
     * index 5 = (Extra index to hold Time from Natty)
     * 
     * ---USEFUL INFO FOR INDEX_OF_PARAM------
     * 0 = name (For all) 
     * 1 = note (For all) 
     * 2 = by (For Tasks only, and floating Task) 
     * 3 = from (For Event's Start Date & Time)
     * 4 = to (For Event's End Date & Time)
     */
    public String testCommand = null;
    public String[] received = null;
    
    @Test
    public void correctActivityNameEditShouldReturnTrue() {
        
        testCommand = "edit floating 3 name: Hello World";
        try {
            received = EditParser.parseEditCommand(testCommand);
        } catch (IllegalValueException e) {
            // Should not get here!
            e.printStackTrace();
        }
        assertEquals("floating", received[1]);
        assertEquals("2", received[2]); //EditParser will minus index off by 1. For reference in list
        assertEquals("0", received[3]); // This is indexOfParam, Refer to above list.
        assertEquals("Hello World ", received[4]);
    }
    
    @Test
    public void correctActivityNoteEditShouldReturnTrue() {
        
        testCommand = "edit event 2 n: hey there";
        try {
            received = EditParser.parseEditCommand(testCommand);
        } catch (IllegalValueException e) {
            // Should not get here!
            e.printStackTrace();
        }
        assertEquals("event", received[1]);
        assertEquals("1", received[2]);
        assertEquals("1", received[3]);
        assertEquals("hey there ", received[4]);
    }
    
    @Test
    public void correctTaskDateEditShouldReturnTrue() {
        
        testCommand = "edit task 1 by: 10 nov 2016 6pm";
        try {
            received = EditParser.parseEditCommand(testCommand);
        } catch (IllegalValueException e) {
            // Should not get here!
            e.printStackTrace();
        }
        assertEquals("task", received[1]);
        assertEquals("0", received[2]);
        assertEquals("2", received[3]);
        assertEquals("10-11-2016", received[4]); // Follows dd-mm-yyyy format
        assertEquals("1800", received[5]); // 24-hr format
    }
    
    @Test
    public void correctEventToDateShouldReturnTrue() {
        
        testCommand = "edit event 1 to: 10 dec 2016 9pm";
        try {
            received = EditParser.parseEditCommand(testCommand);
        } catch (IllegalValueException e) {
            // Should not get here!
            e.printStackTrace();
        }
        assertEquals("event", received[1]);
        assertEquals("0", received[2]);
        assertEquals("4", received[3]);
        assertEquals("10-12-2016", received[4]);
        assertEquals("2100", received[5]);
    }
    
    @Test
    public void correctEventFromDateShouldReturnTrue() {
        
        testCommand = "edit event 1 from: 10 dec 2016 9pm";
        try {
            received = EditParser.parseEditCommand(testCommand);
        } catch (IllegalValueException e) {
            // Should not get here!
            e.printStackTrace();
        }
        assertEquals("event", received[1]);
        assertEquals("0", received[2]);
        assertEquals("3", received[3]);
        assertEquals("10-12-2016", received[4]);
        assertEquals("2100", received[5]);
    }
    
    // Test for parsing changing an activity type from Floating -> Task
    @Test
    public void correctFloatingTaskChangeShouldReturnTrue() {
        
        testCommand = "edit floating 1 by: 06 nov 2016 10pm";
        try {
            received = EditParser.parseEditCommand(testCommand);
        } catch (IllegalValueException e) {
            // Should not get here!
            e.printStackTrace();
        }
        assertEquals("floating", received[1]);
        assertEquals("0", received[2]);
        assertEquals("2", received[3]);
        assertEquals("06-11-2016", received[4]);
        assertEquals("2200", received[5]);
    }
    
    // Test for parsing changing an activity type from Task -> Floating
    @Test
    public void correctTaskChangeShouldReturnTrue() {
        
        testCommand = "edit task 2 to: floating";
        try {
            received = EditParser.parseEditCommand(testCommand);
        } catch (IllegalValueException e) {
            // Should not get here!
            e.printStackTrace();
        }
        assertEquals("task", received[1]);
        assertEquals("1", received[2]);
        assertEquals("4", received[3]);
        assertEquals("floating ", received[4]);
    }
    
}
