package asteroids.model.programs;

import asteroids.model.programs.statements.Statement;

public interface Interruptable{
	
	public void onBreak(ExecutionContext executionContext);
	
	public boolean isValidInterruptStatement(Statement s);

}
