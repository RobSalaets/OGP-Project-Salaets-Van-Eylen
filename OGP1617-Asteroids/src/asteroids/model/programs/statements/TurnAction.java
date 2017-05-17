package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.ExpressionEvaluationException;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.part3.programs.SourceLocation;

public class TurnAction extends Action {

	public TurnAction(Expression<? super DoubleLiteral> angle, SourceLocation location) {
		super(location);
		this.angle = angle;
	}
	
	private final Expression<? super DoubleLiteral> angle;

	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException {
		Object eval = angle.evaluate(context.getCurrentScope(), context.getWorld());
		if(!(eval instanceof DoubleLiteral))
			throw new ExpressionEvaluationException("Given operand does not evaluate to DoubleLiteral", getSourceLocation());
		
		context.getExecutor().turn(((DoubleLiteral) eval).getValue());
	}

}
