package asteroids.model.programs.expressions;

public class NotExpression extends UnaryExpression<BooleanLiteral> {

	public NotExpression(Expression<BooleanLiteral> arg) {
		super(arg);
	}

	@Override
	public Object evaluate() {
		return !(Boolean)(getArgument().evaluate());
	}
}
