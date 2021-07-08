package trics.core.lang;

/**
 * This class holds the definitions of translation IDs.
 * <p>
 * These IDs must fit the Unique.validID(), but this will
 * not be automatically checked!  They must match because
 * Lang.validTranslation's regex references Unique.validIDRegex
 * to match the translation IDs.
 */
public class TextID {

    // Exception Messages
    public static final String InvalidIDExceptionMessage = "InvalidIDExceptionMessage";
    public static final String InvalidTypeExceptionMessage = "InvalidTypeExceptionMessage";

}
