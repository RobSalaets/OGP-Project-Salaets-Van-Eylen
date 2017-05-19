package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.part3.programs.SourceLocation;

public class TurnAction extends Action {

	public TurnAction(SourceLocation location, Expression<? super DoubleLiteral> angle) {
		super(location);
		this.angle = angle;
	}
	
	private final Expression<? super DoubleLiteral> angle;

	@Override
	public boolean execute(ExecutionContext context) throws ProgramExecutionTimeException, ExpressionEvaluationException {
		Object eval = angle.evaluate(context);
		if(!(eval instanceof DoubleLiteral))
			throw new ExpressionEvaluationException("Given operand does not evaluate to DoubleLiteral", getSourceLocation(), angle);
		if(context.canExecuteAction()){
			context.decrementExecutionTime(getSourceLocation());
			try{
				context.getExecutor().turn(((DoubleLiteral) eval).getValue());
			}catch(AssertionError e){
				throw new ProgramExecutionTimeException("Turning with given angle: " + ((DoubleLiteral) eval).getValue().toString() +
														" results in invalid ship orientation.", getSourceLocation());
			}
			return true;
		}
		throw new ProgramExecutionTimeException("Executing action with not enough time left", getSourceLocation());
	}

}
