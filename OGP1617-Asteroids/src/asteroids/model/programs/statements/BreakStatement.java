package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.part3.programs.SourceLocation;

public class BreakStatement extends Statement{
	
	public BreakStatement(SourceLocation location){
		super(location);
	}

	@Override
	public boolean execute(ExecutionContext context) throws ProgramExecutionTimeException{
		context.interruptFromCurrent(this, getSourceLocation());
		return false;
	}
}
