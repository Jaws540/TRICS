package trics.core.base;

import trics.core.exceptions.InvalidIDException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tags allow for components to be labeled, filtered, and sorted based on tags.
 * All Taggable objects extend Displayable.
 * <br>
 * <strong>Tags are case insensitive and will not contain duplicates.</strong>
 */
public abstract class Taggable extends Displayable {

    /**
     * The list of Tags this component is labeled with.
     */
    private final List<InsensitiveString> tags;

    /**
     * Allows components to be labeled with tags the User can interact with for
     * filtering or searching.
     *
     * @param ID          The ID for this Unique, Displayable object.
     *                    It must be a valid ID based on <code>Unique.validID()</code>.
     * @param displayName The name of this component to be shown to a User.  This may be null.
     * @param description A description of this component to be shown to the User.  This may be null.
     * @throws InvalidIDException This will be thrown if the given ID is invalid.
     */
    public Taggable(String ID, String displayName, String description) throws InvalidIDException {
        super(ID, displayName, description);

        // Initialize an empty list of tags
        this.tags = new ArrayList<InsensitiveString>();
    }

    /**
     * Allows components to be labeled with tags the User can interact with for
     * filtering or searching.
     *
     * @param ID          The ID for this Unique, Displayable object.
     *                    It must be a valid ID based on <code>Unique.validID()</code>.
     * @param displayName The name of this component to be shown to a User.  This may be null.
     * @param description A description of this component to be shown to the User.  This may be null.
     * @param tags        A list of tags to initialize the component.
     *                    This is typically used for loading saved components.
     *                    A null value will initialize an empty list.
     * @throws InvalidIDException This will be thrown if the given ID is invalid.
     */
    public Taggable(String ID, String displayName, String description, List<String> tags) throws InvalidIDException {
        super(ID, displayName, description);

        // Shallow copy of the given list of tags
        if (tags != null)
            this.tags = InsensitiveString.convertStringList(tags);
        else
            this.tags = new ArrayList<>();
    }

    /**
     * Retrieves the components list of tags as Strings.
     *
     * @return An unmodifiable list of Strings
     */
    public List<String> tags() {
        return tags.stream().map(tag -> tag.data).collect(Collectors.toUnmodifiableList());
    }

    /**
     * Checks if the component is labeled with the given tag.
     *
     * @param tag The tag to check.
     * @return <code>true</code> if the component is labeled with the tag,
     * <code>false</code> otherwise.
     */
    public boolean isTagged(@NotNull String tag) {
        return tags.contains(new InsensitiveString(tag));
    }

    /**
     * Add a tag to this component.
     * Note, tags are unique and case insensitive.
     *
     * @param tag The label to add to the components tags
     * @return <code>true</code> if the tag was successfully added,
     * <code>false</code> if the tag was a duplicate.
     */
    public boolean tag(@NotNull String tag) {
        // Test if the tag is already a label of the component
        if (!isTagged(tag)) {
            // Wrap tag as an InsensitiveString and add it to the list
            tags.add(new InsensitiveString(tag));

            // Successful add
            return true;
        }

        // tag is already a label of the component
        return false;
    }

    /**
     * Remove a tag from the component.
     *
     * @param tag The tag to be removed.
     * @return <code>true</code> if the tag was a label and was removed,
     * <code>false</code> otherwise.
     */
    public boolean untag(@NotNull String tag) {
        // Convert to InsensitiveString to avoid type warning on equals()
        InsensitiveString toRemove = new InsensitiveString(tag);

        // Find the tag, if it exists as a label of the component and remove it
        return tags.removeIf(x -> x.equals(toRemove));
    }

}
