package by.victor.jwd.controller.exception;

import java.io.Serial;


/**
 * Exception thrown at the controller level
 */
public class ControllerException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -619469427543491547L;

    public ControllerException() {
        super();
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Exception e) {
        super(e);
    }

    public ControllerException(String message, Exception e) {
        super(message, e);
    }
}
