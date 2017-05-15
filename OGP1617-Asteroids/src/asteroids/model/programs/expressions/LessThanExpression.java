package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class LessThanExpression extends BinaryExpression<DoubleLiteral, BooleanLiteral> {

	public LessThanExpression(Expression<? extends Type, DoubleLiteral> left, Expression<? extends Type, DoubleLiteral> right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public BooleanLiteral evaluate(Scope scope) {
		return new BooleanLiteral(getLeftArgument().evaluate(scope).getValue() < getRightArgument().evaluate(scope).getValue());
	}
}
