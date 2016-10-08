package seedu.menion.model;


import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.tag.Tag;
import seedu.menion.model.tag.UniqueTagList;

import java.util.List;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyActivityManager {

    UniqueTagList getUniqueTagList();

    UniqueActivityList getUniqueActivityList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyActivity> getActivityList();

    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
