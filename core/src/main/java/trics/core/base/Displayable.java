package trics.core.base;

import trics.core.exceptions.InvalidIDException;

/**
 * Any component that will be displayed to the User MUST be a Displayable.
 * This class adds additional information to the User to distinguish it from other components.
 */
public abstract class Displayable extends Unique {

    /**
     * The Title or Name of a component that a User will see.
     */
    public final String displayName;

    /**
     * A description of a component for the User.
     */
    public final String description;

    /**
     * Adds additional information such as a name and description to components a User may see.
     *
     * @param ID          The ID for this Unique, Displayable object.
     *                    It must be a valid ID based on <code>Unique.validID()</code>.
     * @param displayName The name of this component to be shown to a User.  This may be null.
     * @param description A description of this component to be shown to the User.  This may be null.
     * @throws InvalidIDException This will be thrown if the given ID is invalid.
     */
    public Displayable(String ID, String displayName, String description) throws InvalidIDException {
        super(ID);

        // If the displayName or description are null, an empty String will be substituted.
        this.displayName = (displayName != null) ? displayName : "";
        this.description = (description != null) ? description : "";
    }

}
