package asteroids.model.programs.expressions;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.part3.programs.SourceLocation;

public class NotExpression extends UnaryExpression<BooleanLiteral, BooleanLiteral> {

	public NotExpression(Expression<? super BooleanLiteral> arg, SourceLocation location) {
		super(arg, location);
	}

	@Override
	public BooleanLiteral evaluate(ExecutionContext context) throws ExpressionEvaluationException, ProgramExecutionTimeException{
		Object eval = getArgument().evaluate(context);
		if(!(eval instanceof BooleanLiteral))
			throw new ExpressionEvaluationException("Given operand does not evaluate to BooleanLiteral", getSourceLocation(), this);
		return new BooleanLiteral(!((BooleanLiteral)eval).getValue());
	}
}
