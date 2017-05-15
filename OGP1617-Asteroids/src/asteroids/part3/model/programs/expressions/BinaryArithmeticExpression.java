package asteroids.part3.model.programs.expressions;

public class BinaryArithmeticExpression extends BinaryExpression<DoubleLiteral, DoubleLiteral>{

	public BinaryArithmeticExpression(Expression<? extends Type, DoubleLiteral> left, Expression<? extends Type, DoubleLiteral> right, BinaryArithmeticOperation operation) throws IllegalArgumentException{
		super(left, right);
		if(operation == null)
			throw new IllegalArgumentException("The BinaryArithmeticOperation must be effective.");
		this.operationType = operation;
	}

	private final BinaryArithmeticOperation operationType;
	
	@Override
	public DoubleLiteral evaluate() {
		Double result = null;
		switch(operationType){
		case ADD:
			result = getLeftArgument().evaluate().getValue() + getRightArgument().evaluate().getValue();
			break;
		case MULTIPLY:
			result = getLeftArgument().evaluate().getValue() * getRightArgument().evaluate().getValue();
			break;
		default:
			break;
		}
		return new DoubleLiteral(result);
	}
}
