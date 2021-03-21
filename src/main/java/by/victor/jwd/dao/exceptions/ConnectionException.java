package by.victor.jwd.dao.exceptions;

import java.io.Serial;

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
