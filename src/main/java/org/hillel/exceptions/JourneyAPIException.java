package org.hillel.exceptions;

public class JourneyAPIException extends RuntimeException{
    public JourneyAPIException() {
        super();
    }
    public JourneyAPIException(String message, Throwable cause) {
        super(message, cause);
    }
    public JourneyAPIException(String message) {
        super(message);
    }
    public JourneyAPIException(Throwable cause) {
        super(cause);
    }
}
