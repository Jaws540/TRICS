package trics.core.lang;

import trics.core.base.Unique;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads selected language files for translations to other languages.
 * <p>
 * This is also used to retrieve Strings that will be displayed to the User
 * to ensure everything is translated and no displayable Strings are hard coded.
 */
public final class Lang {

    /**
     * TRICS should use the UTF-16 encoding for all strings both in input and output
     */
    public static final Charset charset = StandardCharsets.UTF_16;

    /**
     * A valid line in a language file contains the standard name for the String
     * as referenced in TextID followed by a colon then the rest of the line should
     * contain the intended String in the corresponding language for the file.
     * There should be no spaces or other whitespace characters before or after the
     * standard name or colon.
     */
    private static final Pattern validTranslation = Pattern.compile("^(" + Unique.validIDRegex + "):(.*)$");

    /**
     * Dictionary of translations mapped to standard names for the Strings.
     * <p>
     * Example: RandomTestString could map to "This is a testing String!"
     */
    private static HashMap<String, String> translations = null;

    /**
     * If no translation text is found, this message will be displayed.
     */
    private static final String noTranslation = "Error: No text is defined";

    /**
     * Loads and sets the language for output strings.
     *
     * @param langFileName The file name of the language file.
     *                     File name should be of the form: <standard language identifier>.core.lang
     *                     Example: en-us.core.lang
     * @return <code>true</code> if the language file was successfully loaded, <code>false</code> otherwise.
     * @throws IOException Thrown if there was a problem opening the language file.
     */
    public static boolean setLanguage(String langFileName) throws IOException {
        // Get all the lines from the language file
        List<String> langFileLines = Files.readAllLines(
                Paths.get(".", "lang", langFileName),
                Lang.charset
        );

        // This will replace translations after all lines have been loaded
        HashMap<String, String> tempTranslations = new HashMap<>();

        // This will verify a line matches the regex
        Matcher verifier = null;

        // Loop through all lines to match and add
        for (String line : langFileLines) {
            verifier = validTranslation.matcher(line);

            if (verifier.matches())
                // Pull the String identifier and the translation, and
                // put them in the temp translations
                tempTranslations.put(verifier.group(1), verifier.group(2));
        }

        if (tempTranslations.size() != 0) {
            // Verify there are valid translations in the map
            translations = tempTranslations;

            return true;
        }

        // Return false if the translation/tempTranslation swap was not able to complete
        return false;
    }

    /**
     * Get a translation from the loaded language file.
     * This should be used for all displayed strings.
     *
     * @param text The standard name for a specific string of text as defined in TRICS.core.TextID.
     * @return A string in the correct language translation as defined in the language file.
     */
    public static String get(String text) {
        if (Lang.translations != null)
            return translations.get(text);

        return noTranslation;
    }

}
