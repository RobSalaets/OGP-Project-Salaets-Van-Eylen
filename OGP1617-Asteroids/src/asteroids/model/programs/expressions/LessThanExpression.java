package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class LessThanExpression extends BinaryExpression<DoubleLiteral, BooleanLiteral> {

	public LessThanExpression(Expression<? super DoubleLiteral> left, Expression<? super DoubleLiteral> right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public BooleanLiteral evaluate(Scope scope) {
		Object evalL = getLeftArgument().evaluate(scope);
		Object evalR = getRightArgument().evaluate(scope);
		if(!((evalL instanceof DoubleLiteral) && (evalR instanceof DoubleLiteral)))
			throw new IllegalArgumentException();
		
		return new BooleanLiteral(((DoubleLiteral)evalL).getValue() < ((DoubleLiteral)evalR).getValue());
	}
}
