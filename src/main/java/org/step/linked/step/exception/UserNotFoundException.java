package org.step.linked.step.exception;

public class UserNotFoundException extends RuntimeException {

    private Long id;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
