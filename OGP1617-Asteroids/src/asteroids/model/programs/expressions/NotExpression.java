package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.BooleanLiteral;

public class NotExpression extends UnaryExpression<BooleanLiteral, BooleanLiteral> {

	public NotExpression(Expression<? super BooleanLiteral> arg) {
		super(arg);
	}

	@Override
	public BooleanLiteral evaluate(Scope scope, World world) throws ExpressionEvaluationException{
		Object eval = getArgument().evaluate(scope, world);
		if(!(eval instanceof BooleanLiteral))
			throw new ExpressionEvaluationException("Given operand does not evaluate to BooleanLiteral");
		return new BooleanLiteral(!((BooleanLiteral)eval).getValue());
	}
}
