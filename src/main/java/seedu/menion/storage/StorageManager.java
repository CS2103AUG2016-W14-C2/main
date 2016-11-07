package seedu.menion.storage;

import com.google.common.eventbus.Subscribe;

import seedu.menion.commons.core.ComponentManager;
import seedu.menion.commons.core.LogsCenter;
import seedu.menion.commons.events.model.ActivityManagerChangedEvent;
import seedu.menion.commons.events.model.ActivityManagerChangedEventNoUI;
import seedu.menion.commons.events.storage.DataSavingExceptionEvent;
import seedu.menion.commons.events.storage.StoragePathChangedEvent;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of ActivityManager data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XmlActivityManagerStorage activityManagerStorage;
    private JsonUserPrefStorage userPrefStorage;


    public StorageManager(String activityManagerFilePath, String userPrefsFilePath) {
        super();
        this.activityManagerStorage = new XmlActivityManagerStorage(activityManagerFilePath);
        this.userPrefStorage = new JsonUserPrefStorage(userPrefsFilePath);
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefStorage.saveUserPrefs(userPrefs);
    }


    // ================ ActivityManager methods ==============================

    @Override
    public String getActivityManagerFilePath() {
        return activityManagerStorage.getActivityManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyActivityManager> readActivityManager() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + activityManagerStorage.getActivityManagerFilePath());

        return activityManagerStorage.readActivityManager(activityManagerStorage.getActivityManagerFilePath());
    }

    @Override
    public void saveActivityManager(ReadOnlyActivityManager activityManager) throws IOException {
        activityManagerStorage.saveActivityManager(activityManager, activityManagerStorage.getActivityManagerFilePath());
    }

    //@@author A0139515A
    @Subscribe
    public void handleStoragePathChangedEventEvent(StoragePathChangedEvent event) {
		this.activityManagerStorage = event.getUpdatedXmlActivityManagerStorage();
		try {
			this.saveActivityManager(event.getUpdatedReadOnlyActivityManager());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    //@@author
    
    @Override
    @Subscribe
    public void handleActivityManagerChangedEvent(ActivityManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveActivityManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    
    @Override
    @Subscribe
    public void handleActivityManagerChangedEventNoUI(ActivityManagerChangedEventNoUI abce) {
    	logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Local data changed, saving to file"));
    	try{
    		saveActivityManager(abce.data);
    	} catch (IOException e) {
    		raise(new DataSavingExceptionEvent(e));
    	}
    }




}
