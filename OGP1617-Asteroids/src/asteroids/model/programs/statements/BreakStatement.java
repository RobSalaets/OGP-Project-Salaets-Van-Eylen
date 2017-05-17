package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.part3.programs.SourceLocation;

public class BreakStatement extends Statement{
	
	public BreakStatement(SourceLocation location){
		super(location);
	}

	@Override
	public void execute(Scope scope, ExecutionContext context) throws ProgramExecutionTimeException{
		context.breakFromCurrent(getSourceLocation());
	}
}
