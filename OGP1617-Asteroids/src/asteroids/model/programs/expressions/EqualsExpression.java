package asteroids.model.programs.expressions;

public class EqualsExpression<AT extends Type> extends BinaryExpression<AT, BooleanLiteral> {

	public EqualsExpression(Expression<? extends Type, AT> left, Expression<? extends Type, AT> right) throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public BooleanLiteral evaluate() {
		return new BooleanLiteral(getLeftArgument().evaluate().equals(getRightArgument().evaluate()));
	}

}
