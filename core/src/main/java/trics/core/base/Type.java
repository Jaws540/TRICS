package trics.core.base;

import trics.core.exceptions.InvalidTypeException;

/**
 * Valid data types the User may create, remove, or modify.
 */
public enum Type {

    INTEGER,
    DECIMAL,
    BOOLEAN,
    STRING;

    /**
     * Get the Type representation of object type.
     *
     * @param obj The object to determine its Type equivalent
     * @return A corresponding Type for obj.
     * @throws InvalidTypeException If no type match is made
     */
    public static Type getType(Object obj) throws InvalidTypeException {
        if (obj instanceof Integer)
            return INTEGER;
        else if (obj instanceof Double)
            return DECIMAL;
        else if (obj instanceof Boolean)
            return BOOLEAN;
        else if (obj instanceof String)
            return STRING;

        // Throw exception if no type match is made
        throw new InvalidTypeException();
    }

}
