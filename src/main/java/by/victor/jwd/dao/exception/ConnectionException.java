package by.victor.jwd.dao.exception;

import java.io.Serial;


/**
 * Exception is used to detect problems with ConnectionPool class.
 * Can be thrown at dao level.
 */
public class ConnectionException extends Exception{

    @Serial
    private static final long serialVersionUID = -6864214990473768059L;

    public ConnectionException() {
        super();
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Exception e) {
        super(e);
    }

    public ConnectionException(String message, Exception e) {
        super(message, e);
    }
}
