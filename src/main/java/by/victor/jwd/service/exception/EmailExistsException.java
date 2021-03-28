package by.victor.jwd.service.exception;

import java.io.Serial;

public class EmailExistsException extends ServiceException{
    @Serial
    private static final long serialVersionUID = -4879249364945765741L;

    public EmailExistsException() {
        super();
    }

    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException(Exception e) {
        super(e);
    }

    public EmailExistsException(String message, Exception e) {
        super(message, e);
    }

}
