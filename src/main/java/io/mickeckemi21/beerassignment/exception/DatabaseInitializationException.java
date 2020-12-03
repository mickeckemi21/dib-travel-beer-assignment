package io.mickeckemi21.beerassignment.exception;

public class DatabaseInitializationException extends RuntimeException {

    public DatabaseInitializationException(String message) {
        super(message);
    }

    public DatabaseInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseInitializationException(Throwable cause) {
        super(cause);
    }

}
