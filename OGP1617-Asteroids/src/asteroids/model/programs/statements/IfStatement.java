package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.BooleanLiteral;
import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class IfStatement extends ConditionalStatement {
	
	public IfStatement(SourceLocation location, Expression<BooleanLiteral> condition){
		super(location, condition);
	}

	private MultiStatement body;
	private MultiStatement elseBody;
	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException {
		
	}
}
