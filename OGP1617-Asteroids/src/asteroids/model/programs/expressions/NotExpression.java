package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class NotExpression extends UnaryExpression<BooleanLiteral, BooleanLiteral> {

	public NotExpression(Expression<? extends Type, BooleanLiteral> arg) {
		super(arg);
	}

	@Override
	public BooleanLiteral evaluate(Scope scope) {
		return new BooleanLiteral(!getArgument().evaluate(scope).getValue());
	}
}
