package asteroids.model.programs;

import asteroids.model.programs.statements.Statement;

/**
 * An interface for Statements associated with an inner interrupt statement.
 */
public interface Interruptable{
	
	public void onBreak(ExecutionContext executionContext);
	
	public boolean isValidInterruptStatement(Statement s);

}
