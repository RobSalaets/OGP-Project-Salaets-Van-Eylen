package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class EqualsExpression<AT extends Type> extends BinaryExpression<AT, BooleanLiteral> {

	public EqualsExpression(Expression<? extends Type, AT> left, Expression<? extends Type, AT> right) throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public BooleanLiteral evaluate(Scope scope) {
		return new BooleanLiteral(getLeftArgument().evaluate(scope).equals(getRightArgument().evaluate(scope)));
	}

}
