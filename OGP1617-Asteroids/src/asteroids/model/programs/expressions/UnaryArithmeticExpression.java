package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.part3.programs.SourceLocation;

public class UnaryArithmeticExpression extends UnaryExpression<DoubleLiteral, DoubleLiteral> {

	public UnaryArithmeticExpression(Expression<? super DoubleLiteral> arg, UnaryArithmeticOperation operation, SourceLocation location) throws IllegalArgumentException {
		super(arg, location);
		if(operation == null)
			throw new IllegalArgumentException("The UnaryArithmeticOperation must be effective.");
		this.operationType = operation;
	}

	private final UnaryArithmeticOperation operationType;

	@Override
	public DoubleLiteral evaluate(Scope scope, World world) throws ExpressionEvaluationException {
		Object eval = getArgument().evaluate(scope, world);
		if(!(eval instanceof DoubleLiteral))
			throw new ExpressionEvaluationException("Given operand does not evaluate to DoubleLiteral", getSourceLocation());
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
