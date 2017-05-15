package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class UnaryArithmeticExpression extends UnaryExpression<DoubleLiteral, DoubleLiteral> {


	public UnaryArithmeticExpression(Expression<? extends Type, DoubleLiteral> arg, UnaryArithmeticOperation operation) throws IllegalArgumentException {
		super(arg);
		if(operation == null)
			throw new IllegalArgumentException("The UnaryArithmeticOperation must be effective.");
		this.operationType = operation;
	}

	private final UnaryArithmeticOperation operationType;
	
	@Override
	public DoubleLiteral evaluate(Scope scope) {
		Double result = null;
		switch(operationType){
		case CHANGE_SIGN:
			result = -getArgument().evaluate(scope).getValue();
			break;
		case SQUARE_ROOT:
			result = Math.sqrt(getArgument().evaluate(scope).getValue()); //
			break;
		default:
			break;
		}
		return new DoubleLiteral(result);
	}
}
