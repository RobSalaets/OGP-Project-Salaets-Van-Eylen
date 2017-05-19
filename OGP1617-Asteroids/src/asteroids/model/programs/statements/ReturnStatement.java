package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class ReturnStatement extends Statement {
	
	public ReturnStatement(SourceLocation location, Expression<? extends Type> value) throws IllegalArgumentException{
		super(location);
		this.value = value;
	}

	private Expression<? extends Type> value;
	
	@Override
	public boolean execute(ExecutionContext context) throws ProgramExecutionTimeException, ExpressionEvaluationException{
		Type result = value.evaluate(context);
		context.getCurrentScope().putVariable("$0", result, getSourceLocation());
		context.returnFromCurrent(getSourceLocation());
		return false;
	}
}
