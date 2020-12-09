package smo.exception;

import java.io.IOException;

public class SMOException extends IOException {

    public SMOException(final String error){
        super(error);
    }

}
