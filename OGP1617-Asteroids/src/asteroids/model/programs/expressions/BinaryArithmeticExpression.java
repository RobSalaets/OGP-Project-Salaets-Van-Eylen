package asteroids.model.programs.expressions;

public class BinaryArithmeticExpression extends BinaryExpression<DoubleLiteral>{

	public BinaryArithmeticExpression(Expression<DoubleLiteral> left, Expression<DoubleLiteral> right, BinaryArithmeticOperation operation) throws IllegalArgumentException{
		super(left, right);
		if(operation == null)
			throw new IllegalArgumentException("The BinaryArithmeticOperation must be effective.");
		this.operationType = operation;
	}

	private final BinaryArithmeticOperation operationType;
	
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
		default:
			break;
		}
		return result;
	}
}
