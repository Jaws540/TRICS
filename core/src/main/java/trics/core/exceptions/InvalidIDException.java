package trics.core.exceptions;

import trics.core.lang.Lang;
import trics.core.lang.TextID;

public class InvalidIDException extends Exception {

    public InvalidIDException() {
        super(Lang.get(TextID.InvalidIDExceptionMessage));
    }

}
