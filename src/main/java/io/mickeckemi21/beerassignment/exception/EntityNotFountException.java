package io.mickeckemi21.beerassignment.exception;

public class EntityNotFountException extends RuntimeException {

    public EntityNotFountException(String message) {
        super(message);
    }

    public EntityNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFountException(Throwable cause) {
        super(cause);
    }

}
