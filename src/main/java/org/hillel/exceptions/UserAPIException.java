package org.hillel.exceptions;

public class UserAPIException extends RuntimeException{
    public UserAPIException() {
        super();
    }
    public UserAPIException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserAPIException(String message) {
        super(message);
    }
    public UserAPIException(Throwable cause) {
        super(cause);
    }
}
