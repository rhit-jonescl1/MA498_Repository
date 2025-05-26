package dlx_solver;

import java.io.Serial;

public class DLXException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public DLXException() {
		// Do nothing
	}

	public DLXException(String message) {
		super(message);
	}

}
