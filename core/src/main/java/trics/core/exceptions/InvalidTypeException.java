package trics.core.exceptions;

import trics.core.lang.Lang;
import trics.core.lang.TextID;

public class InvalidTypeException extends Exception {

    public InvalidTypeException() {
        super(Lang.get(TextID.InvalidTypeExceptionMessage));
    }

}
