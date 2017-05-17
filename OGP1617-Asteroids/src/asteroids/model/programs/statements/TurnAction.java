package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.ExpressionEvaluationException;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.part3.programs.SourceLocation;

public class TurnAction extends Action {

	public TurnAction(SourceLocation location, Expression<? super DoubleLiteral> angle) {
		super(location);
		this.angle = angle;
	}
	
	private final Expression<? super DoubleLiteral> angle;

	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException {
		Object eval = angle.evaluate(context.getCurrentScope(), context.getWorld());
		if(!(eval instanceof DoubleLiteral))
			throw new ExpressionEvaluationException("Given operand does not evaluate to DoubleLiteral", getSourceLocation());
		if(context.canExecuteAction()){
			context.decrementExecutionTime(getSourceLocation());
			double angle = (context.getExecutor().getOrientation() + ((DoubleLiteral) eval).getValue()) % (2 * Math.PI);
			context.getExecutor().turn(angle - context.getExecutor().getOrientation());
		}
	}

}
