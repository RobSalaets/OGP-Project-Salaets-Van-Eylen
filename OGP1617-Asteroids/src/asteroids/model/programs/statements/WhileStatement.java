package asteroids.model.programs.statements;

import asteroids.model.programs.Interruptable;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.part3.programs.SourceLocation;

public class WhileStatement extends ConditionalStatement implements Interruptable{
	
	public WhileStatement(SourceLocation location, Expression<BooleanLiteral> condition, Statement body) throws IllegalArgumentException{
		super(location, condition);
		if(body == null)
			throw new IllegalArgumentException();
		this.body = new BlockStatement(getSourceLocation(), body);
	}

	private final BlockStatement body;

	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException, ExpressionEvaluationException{
		context.addToStack(this, getSourceLocation());
		while(condition.evaluate(context).getValue() && !context.isBreaking() && context.canExecuteAction())
			body.execute(context);
		
		if(!context.isReturning() && context.isBreaking())
			context.setBreakBlockStatement(false);
	}

	@Override
	public void onInterrupt(ExecutionContext executionContext) {
		executionContext.setBreakBlockStatement(true);
	}

	@Override
	public boolean isValidInterruptStatement(Statement s) {
		return s instanceof BreakStatement;
	}
}
