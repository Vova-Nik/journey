package org.hillel.exceptions;

public class SynonimAPIException extends RuntimeException{
    public SynonimAPIException() {
        super("Synonim API Exception");
    }
    public SynonimAPIException(String message, Throwable cause) {
        super(message, cause);
    }
    public SynonimAPIException(String message) {
        super(message);
    }
    public SynonimAPIException(Throwable cause) {
        super(cause);
    }
}
