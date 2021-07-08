package trics.core.base;

import trics.core.exceptions.InvalidIDException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * The basis of all interactive components.
 * Contains fields that can displayed edited, used in scripts, etc.
 */
public class CoreObject extends Taggable {

    private final HashMap<String, Field<?>> fields = new HashMap<>();

    /**
     * A CoreObject with no fields and no tags.
     *
     * @param ID          The ID for this Unique, Displayable object.
     *                    It must be a valid ID based on <code>Unique.validID()</code>.
     * @param displayName The name of this component to be shown to a User.  This may be null.
     * @param description A description of this component to be shown to the User.  This may be null.
     * @throws InvalidIDException This will be thrown if the given ID is invalid.
     */
    public CoreObject(String ID, String displayName, String description) throws InvalidIDException {
        super(ID, displayName, description);
    }

    /**
     * A CoreObject with no fields but with tags.
     *
     * @param ID          The ID for this Unique, Displayable object.
     *                    It must be a valid ID based on <code>Unique.validID()</code>.
     * @param displayName The name of this component to be shown to a User.  This may be null.
     * @param description A description of this component to be shown to the User.  This may be null.
     * @param tags        A list of tags to initialize the component.
     *                    This is typically used for loading saved components.
     *                    This may be null.
     * @throws InvalidIDException This will be thrown if the given ID is invalid.
     */
    public CoreObject(String ID, String displayName, String description, List<String> tags) throws InvalidIDException {
        super(ID, displayName, description, tags);
    }

    /**
     * A CoreObject with fields and with tags.
     * This should mostly be used for loading in CoreObjects.
     *
     * @param ID          The ID for this Unique, Displayable object.
     *                    It must be a valid ID based on <code>Unique.validID()</code>.
     * @param displayName The name of this component to be shown to a User.  This may be null.
     * @param description A description of this component to be shown to the User.  This may be null.
     * @param tags        A list of tags to initialize the component.
     *                    This is typically used for loading saved components.
     *                    This may be null.
     * @param fields      A list of Fields in this CoreObject.
     * @throws InvalidIDException This will be thrown if the given ID is invalid.
     */
    public CoreObject(String ID, String displayName, String description, List<String> tags,
                      @NotNull List<Field<?>> fields) throws InvalidIDException {
        super(ID, displayName, description, tags);

        // Add all the given fields to this CoreObject
        fields.forEach(f -> this.fields.put(f.ID, f));
    }

    /**
     * Retrieves a list of all Fields in this CoreObject.
     *
     * @return An unmodifiable list of Fields.
     */
    public List<Field<?>> getAllFields() {
        return List.copyOf(fields.values());
    }

    /**
     * Retrieves a specific Field by its ID.
     *
     * @param id ID of the Field.
     * @return The Field with the given ID or null if this CoreObject
     * does not contain a Field with the given ID.
     */
    public Field<?> getField(String id) {
        return this.fields.get(id);
    }
}
