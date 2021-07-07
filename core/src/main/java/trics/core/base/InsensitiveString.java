package trics.core.base;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A wrapper of the String class to provide case insensitive equality checks
 */
public class InsensitiveString {

    public final String data;

    /**
     * Creates a wrapped String to provide case insensitive equality checks.
     *
     * @param data The String value to use.
     */
    public InsensitiveString(String data) {
        this.data = data;
    }

    /**
     * Converts a List of Strings to a List of InsensitiveString
     *
     * @param lst List of Strings to convert.
     * @return A List of InsensitiveStrings containing all the elements of lst converted
     * to InsensitiveStrings in order returned by lst's iterator.
     */
    public static List<InsensitiveString> convertStringList(@NotNull List<String> lst) {
        // Output list containing the converted Strings
        return lst.stream().map(InsensitiveString::new).collect(Collectors.toList());
    }

    /**
     * Converts a List of InsensitiveString to a List of String
     *
     * @param lst List of InsensitiveString to convert.
     * @return A List of String containing all the elements of lst converted
     * to String in order returned by lst's iterator.
     */
    public static List<String> revertToStringList(@NotNull List<InsensitiveString> lst) {
        // Output list containing the converted InsensitiveStrings
        return lst.stream().map(is -> is.data).collect(Collectors.toList());
    }

    /**
     * Provides case insensitive equality with other InsensitiveStrings or regular Strings
     *
     * @param obj An object for comparison.
     * @return <code>true</code> if the object is an InsensitiveString or String AND is case
     * insensitively equal to this InsensitiveString, <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        String otherData = null;
        if (obj instanceof InsensitiveString)
            otherData = ((InsensitiveString) obj).data;
        else if (obj instanceof String)
            otherData = ((String) obj).toString();

        return data.equalsIgnoreCase(otherData);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return "InsensitiveString: " + data;
    }
}
