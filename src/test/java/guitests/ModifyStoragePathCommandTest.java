package guitests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import seedu.menion.commons.core.Config;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.commons.util.ConfigUtil;
import seedu.menion.commons.util.FileUtil;
import seedu.menion.commons.util.XmlUtil;
import seedu.menion.logic.commands.ModifyStoragePathCommand;
import seedu.menion.storage.XmlFileStorage;
import seedu.menion.storage.XmlSerializableActivityManager;

//@@author A0139515A

public class ModifyStoragePathCommandTest extends ActivityManagerGuiTest {

	Config originalConfig;
	File originalFile;
	String originalStoragePath;
	XmlSerializableActivityManager originalData;

	@Test
	public void modifyStoragePath() throws DataConversionException, JAXBException, IOException {

		Config config;
		String filePath;

		/* Comment this line when running test on travis */
		// saveOriginalConfig();

		// testing for correct filepath
		filePath = ModifyStoragePathCommand.TEST_STORAGE_PATH;
		commandBox.runCommand("modify " + filePath);
		config = readFromCurrentConfig();
		assertEquals(filePath, config.getActivityManagerFilePath());

		filePath = ModifyStoragePathCommand.DEFAULT_STORAGE_PATH;
		commandBox.runCommand("modify default");
		config = readFromCurrentConfig();
		assertEquals(filePath, config.getActivityManagerFilePath());

		// for invalid command
		commandBox.runCommand("modify");
		assertResultMessage(ModifyStoragePathCommand.MESSAGE_FAILURE);

		// revert to original file path
		commandBox.runCommand("undo modify");
		commandBox.runCommand("undo modify");
		commandBox.runCommand("undo modify");

		/* Comment this line when running test on travis */
		// restoreOriginalConfig();
	}

	private Config readFromCurrentConfig() {
		Config testConfig;
		try {
			Optional<Config> configOptional = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
			testConfig = configOptional.orElse(Config.getInstance());
		} catch (DataConversionException e) {
			testConfig = Config.getInstance();
		}
		return testConfig;
	}

	/**
	 * Methods below are only used when user do testing to save and restore
	 * current storage
	 */

	private void restoreOriginalConfig() throws IOException, FileNotFoundException, JAXBException {
		FileUtil.createIfMissing(originalFile);
		XmlUtil.saveDataToFile(originalFile, originalData);
	}

	private void saveOriginalConfig() throws IOException, DataConversionException, FileNotFoundException {
		try {
			Optional<Config> configOptional = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
			originalConfig = configOptional.orElse(Config.getInstance());
		} catch (DataConversionException e) {
			originalConfig = Config.getInstance();
		}
		originalFile = new File(originalConfig.getActivityManagerFilePath());
		FileUtil.createIfMissing(originalFile);
		originalData = XmlFileStorage.loadDataFromSaveFile(originalFile);
		originalStoragePath = originalConfig.getActivityManagerFilePath();
	}
}