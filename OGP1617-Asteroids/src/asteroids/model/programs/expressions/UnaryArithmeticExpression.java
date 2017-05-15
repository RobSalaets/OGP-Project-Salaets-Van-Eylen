package asteroids.model.programs.expressions;

public class UnaryArithmeticExpression extends UnaryExpression<DoubleLiteral, DoubleLiteral> {


	public UnaryArithmeticExpression(Expression<? extends Type, DoubleLiteral> arg, UnaryArithmeticOperation operation) throws IllegalArgumentException {
		super(arg);
		if(operation == null)
			throw new IllegalArgumentException("The UnaryArithmeticOperation must be effective.");
		this.operationType = operation;
	}

	private final UnaryArithmeticOperation operationType;
	
	@Override
	public DoubleLiteral evaluate() {
		Double result = null;
		switch(operationType){
		case CHANGE_SIGN:
			result = -getArgument().evaluate().getValue();
			break;
		case SQUARE_ROOT:
			result = Math.sqrt(getArgument().evaluate().getValue()); //
			break;
		default:
			break;
		}
		return new DoubleLiteral(result);
	}
}
