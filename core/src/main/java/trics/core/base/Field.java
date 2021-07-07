package trics.core.base;

import trics.core.exceptions.InvalidIDException;
import trics.core.exceptions.InvalidTypeException;
import org.jetbrains.annotations.NotNull;

/**
 * A container for interactive data.
 * This is a user interactive variable.
 */
public class Field<T> extends Displayable {

    /**
     * The data type this Field will hold.
     */
    public final Type dataType;

    /**
     * The value of the data in this field
     */
    private T value;

    /**
     * True if the data can be modified within the application
     */
    public final boolean mutable;

    /**
     * Initializes a Field with a data type, value, and mutability rules.
     *
     * @param ID          The ID for this Unique, Displayable object.
     *                    It must be a valid ID based on <code>Unique.validID()</code>.
     * @param displayName The name of this component to be shown to a User.
     *                    A null value will initialize to an empty String.
     * @param description A description of this component to be shown to the User.
     *                    A null value will initialize to an empty String
     * @param value       The value of the Field converted to an Object.
     *                    Valid object to pass in are: Integer, Double, Boolean, and String.
     *                    This cannot be null.
     * @param mutable     The mutability rule for the Field within the application.
     * @throws InvalidIDException This will be thrown if the given ID is invalid.
     */
    public Field(String ID, String displayName, String description, @NotNull T value, boolean mutable)
            throws InvalidIDException, InvalidTypeException {

        super(ID, displayName, description);

        this.dataType = Type.getType(value);
        this.value = value;
        this.mutable = mutable;
    }

    /**
     * Gets the data type of this Field.
     *
     * @return The Type of this Field.
     */
    public Type getDataType() {
        return dataType;
    }

    /**
     * Gets the Field value.
     *
     * @return The Field value.
     */
    public T getData() {
        return value;
    }

    /**
     * Sets the Field value.
     *
     * @param newValue The new value to be used in this Field.
     *                 This cannot be null.
     * @return The old value of this Field.
     * This will return <code>null</code> if the Field is immutable.
     */
    public T setData(@NotNull Field<T> newValue) {
        if (mutable && dataType == newValue.dataType) {
            T oldValue = value;
            value = newValue.value;

            return oldValue;
        }

        return null;
    }

    /**
     * Compares the values of this Field with another Field.
     *
     * @param obj The object to compare for equality.
     * @return <code>true</code> if the two Fields have the same data type and
     * their values are equal, <code>false</code> if the values are not equal,
     * the Field does not have the same data type, or a Field is not passed as
     * the argument.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Field) {
            Field<?> other = (Field<?>) obj;

            if (other.dataType == dataType) {
                return value.equals(other.value);
            }
        }

        return false;
    }

}
