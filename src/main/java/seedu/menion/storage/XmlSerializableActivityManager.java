package seedu.menion.storage;

import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.tag.Tag;
import seedu.menion.model.tag.UniqueTagList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable ActivityManager that is serializable to XML format
 */
@XmlRootElement(name = "activitymanager")
public class XmlSerializableActivityManager implements ReadOnlyActivityManager {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<Tag> tags;

    {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableActivityManager() {}

    /**
     * Conversion
     */
    public XmlSerializableActivityManager(ReadOnlyActivityManager src) {
        tasks.addAll(src.getActivityList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (UniqueTagList.DuplicateTagException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UniqueActivityList getUniqueActivityList() {
        UniqueActivityList lists = new UniqueActivityList();
        for (XmlAdaptedTask t : tasks) {
            try {
                lists.add(t.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    @Override
    public List<ReadOnlyActivity> getActivityList() {
        return tasks.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
