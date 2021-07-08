package trics.core.preferences;

import org.jetbrains.annotations.NotNull;
import trics.core.base.Unique;
import trics.core.exceptions.InvalidIDException;
import trics.core.lang.Lang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Preferences Manager is the root of all User settings.
 * This class handles the loading and unloading of User settings
 * and is queried by the other modules to get their settings.
 * <p>
 * The settings file will be broken down by module to keep related
 * settings together.
 */
public final class PreferencesManager {

    /**
     * Holds the mapping of groupIDs to module preferences and
     * the mapping of preferenceIDs to their preferences.
     */
    private static final Map<String, Map<String, Preference>> allPreferences = new HashMap<>();

    /**
     * A valid line in the preferences file is constructed as follows:
     * The groupID is first on the line followed by a dot separator then
     * the preferenceID.  A colon is used to delineate the IDs over the
     * preference value which will follow the colon.
     */
    private static final Pattern validPreferenceLine = Pattern.compile(
            "^(" + Unique.validIDRegex + ")\\.(" + Unique.validIDRegex + "):(.*)$");
    //          ^ groupID (group 1)             ^ preferenceID (group 2)     ^ preference value (group 3)

    /**
     * Loads the preferences defined in the preference file.
     * <p>
     * This should always be run once at startup.
     * This could be re-run as a result of a reload request
     * in the application.
     */
    public static void loadPreferences() throws IOException {
        // Get all the lines from the preference file
        List<String> preferenceFileLines = Files.readAllLines(
                Paths.get(".", "user.preferences"),
                Lang.charset
        );

        Matcher verifier = null;

        // Process all lines in the preference file
        for (String line : preferenceFileLines) {
            verifier = validPreferenceLine.matcher(line);

            // Ensure we are working with a line defining a preference
            if (verifier.matches()) {
                // Extract the preference definition
                String groupID = verifier.group(1);
                String preferenceID = verifier.group(2);
                String preferenceValue = verifier.group(3);

                // Get the mapping for the specified group.
                // Note: This will return an empty Map if the group mapping
                // is not found.
                Map<String, Preference> groupPreferences = getGroupPreferences(groupID);

                // Ensure this groupIDs mapping is present in allPreferences
                allPreferences.putIfAbsent(groupID, groupPreferences);

                // Ignore any preferences with invalid IDs.
                // Modules are responsible for ensuing they have all necessary preferences loaded.
                try {
                    Preference newPreference = new Preference(preferenceID, preferenceValue);

                    groupPreferences.put(preferenceID, newPreference);
                } catch (InvalidIDException e) {
                    // Do not load an invalid preference
                }
            }
        }
    }

    /**
     * Gets the mapping of preferenceIDs to their matching Preference.
     *
     * @param groupID The ID used by a module to group its settings.
     * @return The mapping of preferences if present, or an empty map
     * if the given groupID was not found.
     */
    private static Map<String, Preference> getGroupPreferences(@NotNull String groupID) {
        Map<String, Preference> group = allPreferences.get(groupID);

        // Ensure a null value is not passed
        return (group != null) ? group : new HashMap<>();
    }

    /**
     * This should be used by modules to retrieve specific settings.
     *
     * @param groupID      The ID used by a module to group its settings.
     * @param preferenceID The specific ID of a setting within the groupID.
     * @return An Optional string where if the setting is present, the value
     * will be returned in the Optional.
     */
    public static Optional<Preference> getPreference(@NotNull String groupID, @NotNull String preferenceID) {
        // Try to find the preference
        Preference value = getGroupPreferences(groupID).get(preferenceID);

        // A preference may have been deleted from the preferences file.
        // An optional is used to avoid a null value and force the module
        // to determine an alternative.
        return Optional.ofNullable(value);
    }

}
