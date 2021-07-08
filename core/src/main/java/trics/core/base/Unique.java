package trics.core.base;

import org.jetbrains.annotations.NotNull;
import trics.core.exceptions.InvalidIDException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A Unique object contains a unique ID.
 * This is also to identify specific interactive components.
 * All user interactive components MUST extend this class in its parent tree.
 * Not all Unique objects must be displayed to the User.
 */
public abstract class Unique {

    /**
     * All Unique objects IDs are registered here to maintain uniqueness among all IDs.
     */
    private static final List<String> ID_REGISTRY = new ArrayList<>();

    /**
     * A regular expression meant to match the equivalent of "^[a-zA-Z0-9]*$" in
     * any language.
     * <p>
     * This expression may be too explicit in what categories of characters to omit.
     */
    public static final String validIDRegex = "^[\\p{L}\\p{N}&&[^\\p{Z}\\p{P}\\p{C}\\p{S}]]*$";
    private static final Pattern validIDPattern = Pattern.compile(validIDRegex, Pattern.CANON_EQ);

    /**
     * A Unique objects ID.
     */
    public final String ID;

    /**
     * This will check that the given ID is valid based on <code>Unique.validID()</code>.
     *
     * @param ID The ID to be used as a unique identifier.
     * @throws InvalidIDException This will be thrown if the given ID is invalid.
     */
    public Unique(String ID) throws InvalidIDException {
        // Ensure a ID is of a valid form
        if (Unique.validID(ID)) {
            this.ID = ID;

            // Register the ID
            if (!ID_REGISTRY.contains(ID))
                ID_REGISTRY.add(ID);
        }

        // Invalid IDs will throw core.exceptions to be caught and logged.
        // If necessary, the exception will be presented to the User
        // so it may be fixed.
        else {
            throw new InvalidIDException();
        }
    }

    /**
     * Determines if any Unique ID is valid.
     * This will compare an ID with a regular expression defining a valid ID and
     * check if the ID is already registered.
     *
     * @param ID The ID to check for validity.
     * @return <code>true</code> if the ID matches the valid ID regular expression, is not in use,
     * and is not null. <code>false</code> otherwise.
     */
    public static boolean validID(@NotNull String ID) {
        // Match IDs with the regular expression
        if (validIDPattern.matcher(ID).matches())
            // Ensure each ID is unique
            return !ID_REGISTRY.contains(ID);

        return false;
    }

    /**
     * Equality is determined by lexicographical equality of two IDs.
     * A <code>String</code> may be substituted for a <code>Unique</code> object.
     * All other objects will return <code>false</code>.
     *
     * @param obj The object to compare for equality.
     * @return <code>true</code> if the object is a <code>Unique</code> object,
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        String otherID = null;
        if (obj instanceof Unique) {
            otherID = ((Unique) obj).ID;
        } else if (obj instanceof String) {
            otherID = (String) obj;
        }

        return ID.equalsIgnoreCase(otherID);
    }

    /**
     * A hash code to represent a Unique object.
     *
     * @return The hash code of the ID.
     */
    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    /**
     * A simple representation of a Unique object.
     *
     * @return The ID of this Unique object.
     */
    @Override
    public String toString() {
        return ID;
    }

}
