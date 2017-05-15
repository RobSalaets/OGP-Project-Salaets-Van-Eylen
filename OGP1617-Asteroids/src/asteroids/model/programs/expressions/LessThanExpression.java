package asteroids.model.programs.expressions;

public class LessThanExpression extends BinaryExpression<DoubleLiteral, BooleanLiteral> {

	public LessThanExpression(Expression<? extends Type, DoubleLiteral> left, Expression<? extends Type, DoubleLiteral> right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public BooleanLiteral evaluate() {
		return new BooleanLiteral(getLeftArgument().evaluate().getValue() < getRightArgument().evaluate().getValue());
	}
}
