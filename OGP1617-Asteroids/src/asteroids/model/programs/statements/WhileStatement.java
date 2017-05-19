package asteroids.model.programs.statements;

import asteroids.model.programs.Breakable;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.part3.programs.SourceLocation;

public class WhileStatement extends ConditionalStatement implements Breakable{
	
	public WhileStatement(SourceLocation location, Expression<BooleanLiteral> condition, Statement body) throws IllegalArgumentException{
		super(location, condition);
		if(body == null)
			throw new IllegalArgumentException();
		this.body = body;
	}

	private final Statement body;

	@Override
	public boolean execute(ExecutionContext context) throws ProgramExecutionTimeException, ExpressionEvaluationException{
		context.addToStack(this, getSourceLocation());
		while(condition.evaluate(context).getValue() &&
				!context.isBreaking() && context.canExecuteAction())
			body.execute(context);
		
		if(!context.isReturning() && context.isBreaking())
			context.stopBreaking();
		return false;
	}
}
