package by.victor.jwd.dao.exception;

import java.io.Serial;

public class DAOException extends Exception {

	@Serial
	private static final long serialVersionUID = -7583928808489935508L;

	public DAOException() {
		super();
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Exception e) {
		super(e);
	}

	public DAOException(String message, Exception e) {
		super(message, e);
	}

}
