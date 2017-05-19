package asteroids.model.programs.expressions;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;


public class EqualsExpression extends Expression<BooleanLiteral> {

	public EqualsExpression(Expression<? extends Type> left, Expression<? extends Type> right, SourceLocation location) throws IllegalArgumentException {
		super(location);
		if(left == null || right == null)
			throw new IllegalArgumentException();
		this.leftArg = left;
		this.rightArg = right;
	}
	
	private final Expression<? extends Type> leftArg;
	private final Expression<? extends Type> rightArg;

	@Override
	public BooleanLiteral evaluate(ExecutionContext context) throws ExpressionEvaluationException, ProgramExecutionTimeException{
		return new BooleanLiteral(leftArg.evaluate(context).equals(rightArg.evaluate(context)));
	}

}
