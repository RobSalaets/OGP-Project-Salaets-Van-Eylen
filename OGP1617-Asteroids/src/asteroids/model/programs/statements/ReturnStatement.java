package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Type;
import asteroids.part3.programs.SourceLocation;

public class ReturnStatement extends Statement {
	
	public ReturnStatement(SourceLocation location, Expression<? extends Type> value) throws IllegalArgumentException{
		super(location);
		this.value = value;
	}

	private Expression<? extends Type> value;
	
	@Override
	public void execute(Scope scope, ExecutionContext context) throws ProgramExecutionTimeException{
		//$0 = result
	}
}
