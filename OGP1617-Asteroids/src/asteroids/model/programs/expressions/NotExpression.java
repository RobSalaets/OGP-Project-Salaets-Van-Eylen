package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class NotExpression extends UnaryExpression<BooleanLiteral, BooleanLiteral> {

	public NotExpression(Expression<? super BooleanLiteral> arg) {
		super(arg);
	}

	@Override
	public BooleanLiteral evaluate(Scope scope) {
		Object eval = getArgument().evaluate(scope);
		if(!(eval instanceof BooleanLiteral))
			throw new IllegalArgumentException();
		return new BooleanLiteral(!((BooleanLiteral)eval).getValue());
	}
}
