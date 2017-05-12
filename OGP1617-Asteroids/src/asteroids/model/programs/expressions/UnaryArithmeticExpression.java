package asteroids.model.programs.expressions;

public class UnaryArithmeticExpression extends UnaryExpression<DoubleLiteral> {


	public UnaryArithmeticExpression(Expression<DoubleLiteral> arg, UnaryArithmeticOperation operation) throws IllegalArgumentException {
		super(arg);
		if(operation == null)
			throw new IllegalArgumentException("The UnaryArithmeticOperation must be effective.");
		this.operationType = operation;
	}

	private final UnaryArithmeticOperation operationType;
	
	@Override
	public Object evaluate() {
		Object result = null;
		switch(operationType){
		case CHANGE_SIGN:
			result = -(Double)(getArgument().evaluate());
			break;
		case SQUARE_ROOT:
			result = Math.sqrt((Double)(getArgument().evaluate())); //TODO exception
			break;
		default:
			break;
		}
		return result;
	}
}
