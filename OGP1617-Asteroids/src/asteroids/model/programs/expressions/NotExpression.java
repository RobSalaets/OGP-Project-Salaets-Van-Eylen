package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.part3.programs.SourceLocation;

public class NotExpression extends UnaryExpression<BooleanLiteral, BooleanLiteral> {

	public NotExpression(Expression<? super BooleanLiteral> arg, SourceLocation location) {
		super(arg, location);
	}

	@Override
	public BooleanLiteral evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException{
		Object eval = getArgument().evaluate(scope, world, executor);
		if(!(eval instanceof BooleanLiteral))
			throw new ExpressionEvaluationException("Given operand does not evaluate to BooleanLiteral", getSourceLocation());
		return new BooleanLiteral(!((BooleanLiteral)eval).getValue());
	}
}
