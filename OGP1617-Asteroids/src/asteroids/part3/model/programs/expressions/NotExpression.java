package asteroids.part3.model.programs.expressions;

public class NotExpression extends UnaryExpression<BooleanLiteral, BooleanLiteral> {

	public NotExpression(Expression<? extends Type, BooleanLiteral> arg) {
		super(arg);
	}

	@Override
	public BooleanLiteral evaluate() {
		return new BooleanLiteral(!getArgument().evaluate().getValue());
	}
}
