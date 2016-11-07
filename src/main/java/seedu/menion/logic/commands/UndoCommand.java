package seedu.menion.logic.commands;


import seedu.menion.commons.core.Config;
import seedu.menion.commons.core.EventsCenter;
import seedu.menion.commons.events.storage.StoragePathChangedEvent;
import seedu.menion.commons.events.ui.ModifyStorageEvent;
import seedu.menion.commons.util.ConfigUtil;
import seedu.menion.commons.util.XmlUtil;
import seedu.menion.model.ActivityManager;
import seedu.menion.storage.XmlActivityManagerStorage;

//@@author A0139515A
/**
 * Revert to previous activity manager state.
 *
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Menion successfully undo your previous changes";
    public static final String MESSAGE_FAILURE = "There are no changes for Menion to undo\n" + 
											"Examples of undo: \n" +
											"For normal undo: undo\n" +
											"For undo of modify storage path: undo modify\n";
    public final String argument;
    private Config initializedConfig;
    
    public UndoCommand(String argument) {
    	this.argument = argument.trim();
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        
        model.updateRecentChangedActivity(null);
        
        boolean ableToUndo;
        
        if (argument.equals("modify")){
        	ableToUndo = undoStoragePath();
        }
        else {
        	ableToUndo = undoActivityManger();
        }
        
        if (ableToUndo) {
        	return new CommandResult(MESSAGE_SUCCESS);
        }
        
        return new CommandResult(MESSAGE_FAILURE);
    }

    /**
     * Return true if able revert to previous activity manager, otherwise return false.
     */
	public boolean undoActivityManger() {
		assert model != null;
		
		if (model.checkStatesInUndoStack()) {
			return false;
		}
		
		model.addStateToRedoStack(new ActivityManager(model.getActivityManager()));
		model.resetData(model.retrievePreviousStateFromUndoStack());
		
		return true;
	}
	
    /**
     * Return true if able revert to previous storage path, otherwise return false.
     */
	public boolean undoStoragePath() {
		assert model != null;
		
		if (model.checkStoragePathInUndoStack()) {
			return false;
		}
		
		// Initialising Config file
        initializedConfig = ConfigUtil.initializeConfig();
    	
		model.addStoragePathToRedoStack(initializedConfig.getActivityManagerFilePath());
		
		String storagePathToUndo = model.retrievePreviouStoragePathFromUndoStack();
    	
		// Deleting old files
		XmlUtil.deleteOldStorageFile(initializedConfig.getActivityManagerFilePath());
		
		// Saving configuration
		if (!ConfigUtil.savingNewConfiguration(initializedConfig, storagePathToUndo)) {
			return false;
		}

    	// Setting up new storage location
		XmlActivityManagerStorage newStorage = new XmlActivityManagerStorage(storagePathToUndo);
		EventsCenter.getInstance().post(new StoragePathChangedEvent(newStorage, model.getActivityManager()));		
		
		// Update status bar
		EventsCenter.getInstance().post(new ModifyStorageEvent(storagePathToUndo));
		
		return true;
	}
}