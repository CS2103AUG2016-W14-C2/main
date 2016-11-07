package seedu.menion.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.commons.util.FileUtil;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.ActivityManager;
import seedu.menion.storage.XmlActivityManagerStorage;
import seedu.menion.testutil.TypicalTestActivities;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlActivityManagerStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readActivityManager_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readActivityManager(null);
    }

    private java.util.Optional<ReadOnlyActivityManager> readActivityManager(String filePath) throws Exception {
        return new XmlActivityManagerStorage(filePath).readActivityManager(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readActivityManager("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readActivityManager("NotXmlFormatActivityManager.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveActivityManager_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempActivityManager.xml";
        
        TypicalTestActivities td = new TypicalTestActivities();
        ActivityManager original = td.getTypicalActivityManager();
        XmlActivityManagerStorage xmlActivityManagerStorage = new XmlActivityManagerStorage(filePath);

        //Save in new file and read back
        xmlActivityManagerStorage.saveActivityManager(original, filePath);
        ReadOnlyActivityManager readBack = xmlActivityManagerStorage.readActivityManager(filePath).get();
        assertEquals(original, new ActivityManager(readBack));

        //Modify data, overwrite exiting file, and read back
       
        original.addTask(new Activity(TypicalTestActivities.task6));
        original.removeTask(new Activity(TypicalTestActivities.task));

        xmlActivityManagerStorage.saveActivityManager(original, filePath);
        readBack = xmlActivityManagerStorage.readActivityManager(filePath).get();
        assertEquals(original, new ActivityManager(readBack));

    }

    @Test
    public void saveActivityManager_nullActivityManager_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveActivityManagerk(null, "SomeFile.xml");
    }

    private void saveActivityManagerk(ReadOnlyActivityManager addressBook, String filePath) throws IOException {
        new XmlActivityManagerStorage(filePath).saveActivityManager(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveActivityManager_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveActivityManagerk(new ActivityManager(), null);
    }


}
