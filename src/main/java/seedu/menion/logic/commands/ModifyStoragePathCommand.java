
package seedu.menion.logic.commands;

import java.io.File;

import seedu.menion.commons.core.Config;
import seedu.menion.commons.core.EventsCenter;
import seedu.menion.commons.events.storage.StoragePathChangedEvent;
import seedu.menion.commons.events.ui.ModifyStorageEvent;
import seedu.menion.commons.util.ConfigUtil;
import seedu.menion.commons.util.FileUtil;
import seedu.menion.commons.util.XmlUtil;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.storage.XmlActivityManagerStorage;

//@@author A0139515A
/**
 * Modify the activity manager storage location.
 */
public class ModifyStoragePathCommand extends Command {

    public static final String currentFilePath = new File(ModifyStoragePathCommand.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
    public static final String DEFAULT_STORAGE_PATH =  currentFilePath + File.separator + "data/menion.xml";
    public static final String ORIGINAL_TEST_STORAGE_PATH = currentFilePath + File.separator + FileUtil.getPath("src/test/data/sandbox/sampleData.xml");
    public static final String TEST_STORAGE_PATH = currentFilePath + File.separator + FileUtil.getPath("src/test/data/ModifyStoragePathTest/test.xml");
    
    public static final String COMMAND_WORD = "modify";
    public static final String MESSAGE_SUCCESS = "You have successfully changed Menion's storage location to %1$s \n";
    public static final String MESSAGE_FAILURE = "Please provide a valid storage path!\n" + 
    										"Examples to modify storage path: \n" + 
    										"Modify to default storage path: modify default\n" +
    										"Modify to a specified storage path: modify [FILEPATH]\n" +
    										"Note that file path specified will be in user home directory.";
   
    private final String pathToChange;
    private Config initializedConfig;
    
    public ModifyStoragePathCommand(String pathToChange) {
    	
    	if (!pathToChange.isEmpty()) {
    		this.pathToChange = updateStoragePathToChange(pathToChange);  		
    	}
    	else {
    		this.pathToChange = null;
    	}
    }

    @Override
    public CommandResult execute() {
    	assert model != null;
    	
    	model.updateRecentChangedActivity(null);
    	
    	ReadOnlyActivityManager before = new ActivityManager(model.getActivityManager());		
    	
		String newPath;
    	
        if (pathToChange != null) {
        
    		// Initialising Config file
        	initializedConfig = ConfigUtil.initializeConfig();
        	
        	//for test
        	if (pathToChange.equals(TEST_STORAGE_PATH)) {
        		model.addStoragePathToUndoStack(initializedConfig.getActivityManagerFilePath());
    			model.addStoragePathToUndoStack(ORIGINAL_TEST_STORAGE_PATH);
        	}
        	else {
            	// Add old file path to undo stack
            	model.addStoragePathToUndoStack(initializedConfig.getActivityManagerFilePath());            	
        	}
    		
        	// Setting up file path of new storage path
    		if (!pathToChange.equals(DEFAULT_STORAGE_PATH) && !pathToChange.equals(TEST_STORAGE_PATH)) {
	            String root = System.getProperty("user.home");
	            newPath = root + File.separator + pathToChange;
    		}
    		else {
    			newPath = pathToChange;
    		}
    		
    		// Deleting old files
    		XmlUtil.deleteOldStorageFile(initializedConfig.getActivityManagerFilePath());
    		
            // Saving configuration
    		if (!ConfigUtil.savingNewConfiguration(initializedConfig, newPath)) {
    			return new CommandResult("Unable to save configuration!");
    		}
        	
        	// Setting up new storage location
    		XmlActivityManagerStorage newStorage = new XmlActivityManagerStorage(newPath);
    		EventsCenter.getInstance().post(new StoragePathChangedEvent(newStorage, before));		

			// Update status bar
			EventsCenter.getInstance().post(new ModifyStorageEvent(newPath));
			
        	return new CommandResult(String.format(MESSAGE_SUCCESS, pathToChange));
        }
        return new CommandResult(MESSAGE_FAILURE);
    }
    
	/**
	 * Sets the correct storage path to change to based on user input
	 * @param pathToChange
	 * @return correct storage path
	 */
	private String updateStoragePathToChange(String pathToChange) {
		if (pathToChange.toLowerCase().trim().equals("default")) {
			return DEFAULT_STORAGE_PATH;
		}
		else if (pathToChange.toLowerCase().trim().equals("test")) {
			return TEST_STORAGE_PATH;
		}
		else {
			return pathToChange.trim();
		}
	}
}