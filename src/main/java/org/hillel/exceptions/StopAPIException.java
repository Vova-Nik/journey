package org.hillel.exceptions;

import jdk.jshell.spi.ExecutionControl;

public class StopAPIException extends RuntimeException {
    public StopAPIException() {
        super("Stop API Exception");
    }
    public StopAPIException(String message, Throwable cause) {
        super(message, cause);
    }
    public StopAPIException(String message) {
        super(message);
    }
    public StopAPIException(Throwable cause) {
        super(cause);
    }
}
