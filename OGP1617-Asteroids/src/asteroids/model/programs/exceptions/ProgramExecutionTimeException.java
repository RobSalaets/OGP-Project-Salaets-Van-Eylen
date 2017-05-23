package asteroids.model.programs.exceptions;

import asteroids.part3.programs.SourceLocation;

/**
 * A class for signaling faulty execution constructs of a Program.
 */
public class ProgramExecutionTimeException extends RuntimeException {

	private static final long serialVersionUID = 4078655052856062012L;

	public ProgramExecutionTimeException(String errorMessage, SourceLocation line) {
		System.err.println("Exception: " + errorMessage + " " + line.toString());
	}
}
