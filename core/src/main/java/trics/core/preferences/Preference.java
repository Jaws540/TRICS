package trics.core.preferences;

import org.jetbrains.annotations.NotNull;
import trics.core.base.Unique;
import trics.core.exceptions.InvalidIDException;

/**
 * Stores a User preference that can be queried from the PreferencesManager.
 * <p>
 * Modules are responsible for parsing their own preferences.
 */
public class Preference extends Unique {

    public final String value;

    /**
     * This will check that the given ID is valid based on <code>Unique.validID()</code>.
     *
     * @param ID    The ID to be used as a unique preference identifier.
     * @param value The String representation of the value of a preference.
     * @throws InvalidIDException This will be thrown if the given ID is invalid.
     */
    public Preference(String ID, @NotNull String value) throws InvalidIDException {
        super(ID);

        this.value = value;
    }
}
