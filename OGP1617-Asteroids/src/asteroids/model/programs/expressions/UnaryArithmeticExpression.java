package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class UnaryArithmeticExpression extends UnaryExpression<DoubleLiteral, DoubleLiteral> {

	public UnaryArithmeticExpression(Expression<? super DoubleLiteral> arg, UnaryArithmeticOperation operation) throws IllegalArgumentException {
		super(arg);
		if(operation == null)
			throw new IllegalArgumentException("The UnaryArithmeticOperation must be effective.");
		this.operationType = operation;
	}

	private final UnaryArithmeticOperation operationType;
	
	@Override
	public DoubleLiteral evaluate(Scope scope) {
		Object eval = getArgument().evaluate(scope);
		if(!(eval instanceof DoubleLiteral))
			throw new IllegalArgumentException();
		Double result = null;
		switch(operationType){
		case CHANGE_SIGN:
			result = -((DoubleLiteral)eval).getValue();
			break;
		case SQUARE_ROOT:
			result = Math.sqrt(((DoubleLiteral)eval).getValue()); 
			break;
		default:
			break;
		}
		return new DoubleLiteral(result);
	}
}
