package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class BinaryArithmeticExpression extends BinaryExpression<DoubleLiteral, DoubleLiteral>{

	public BinaryArithmeticExpression(Expression<? extends Type, DoubleLiteral> left, Expression<? extends Type, DoubleLiteral> right, BinaryArithmeticOperation operation) throws IllegalArgumentException{
		super(left, right);
		if(operation == null)
			throw new IllegalArgumentException("The BinaryArithmeticOperation must be effective.");
		this.operationType = operation;
	}

	private final BinaryArithmeticOperation operationType;
	
	@Override
	public DoubleLiteral evaluate(Scope scope) {
		Double result = null;
		switch(operationType){
		case ADD:
			result = getLeftArgument().evaluate(scope).getValue() + getRightArgument().evaluate(scope).getValue();
			break;
		case MULTIPLY:
			result = getLeftArgument().evaluate(scope).getValue() * getRightArgument().evaluate(scope).getValue();
			break;
		default:
			break;
		}
		return new DoubleLiteral(result);
	}
}
