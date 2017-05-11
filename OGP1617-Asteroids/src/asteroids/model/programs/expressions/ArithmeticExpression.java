package asteroids.model.programs.expressions;

public class ArithmeticExpression extends BinaryExpression<DoubleLiteral>{

	public ArithmeticExpression(Expression<DoubleLiteral> left, Expression<DoubleLiteral> right, ArithmeticOperationType operation) {
		super(left, right);
		this.operationType = operation;
	}

	private final ArithmeticOperationType operationType;
	
	@Override
	public Object evaluate() {
		Object result = null;
		switch(operationType){
		case ADD:
			result = (Double)(getLeftArgument().evaluate()) + (Double)(getRightArgument().evaluate());
			break;
		case MULTIPLY:
			result = (Double)(getLeftArgument().evaluate()) * (Double)(getRightArgument().evaluate());
			break;
		}
		return result;
	}
}
