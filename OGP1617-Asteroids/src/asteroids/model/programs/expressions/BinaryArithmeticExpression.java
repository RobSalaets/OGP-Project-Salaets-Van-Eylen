package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class BinaryArithmeticExpression extends BinaryExpression<DoubleLiteral, DoubleLiteral>{

	public BinaryArithmeticExpression(Expression<? super DoubleLiteral> left, Expression<? super DoubleLiteral> right, BinaryArithmeticOperation operation) throws IllegalArgumentException{
		super(left, right);
		if(operation == null)
			throw new IllegalArgumentException("The BinaryArithmeticOperation must be effective.");
		this.operationType = operation;
	}

	private final BinaryArithmeticOperation operationType;
	
	@Override
	public DoubleLiteral evaluate(Scope scope) {
		Type evalL = getLeftArgument().evaluate(scope);
		Type evalR = getRightArgument().evaluate(scope);
		if(!((evalL instanceof DoubleLiteral) && (evalR instanceof DoubleLiteral)))
			throw new IllegalArgumentException();
		
		Double result = null;
		switch(operationType){
		case ADD:
			result = ((DoubleLiteral)evalL).getValue() + ((DoubleLiteral)evalR).getValue();
			break;
		case MULTIPLY:
			result = ((DoubleLiteral)evalL).getValue() * ((DoubleLiteral)evalR).getValue();
			break;
		default:
			break;
		}
		return new DoubleLiteral(result);
	}
}
