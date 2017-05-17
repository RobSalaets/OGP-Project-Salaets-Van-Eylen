package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class BinaryArithmeticExpression extends BinaryExpression<DoubleLiteral, DoubleLiteral>{

	public BinaryArithmeticExpression(Expression<? super DoubleLiteral> left, Expression<? super DoubleLiteral> right, BinaryArithmeticOperation operation, SourceLocation location) throws IllegalArgumentException{
		super(left, right, location);
		if(operation == null)
			throw new IllegalArgumentException("The BinaryArithmeticOperation must be effective.");
		this.operationType = operation;
	}

	private final BinaryArithmeticOperation operationType;
	
	@Override
	public DoubleLiteral evaluate(Scope scope, World world) throws ExpressionEvaluationException{
		Type evalL = getLeftArgument().evaluate(scope, world);
		Type evalR = getRightArgument().evaluate(scope, world);
		if(!((evalL instanceof DoubleLiteral) && (evalR instanceof DoubleLiteral)))
			throw new ExpressionEvaluationException("Given operands do not evaluate to DoubleLiteral", getSourceLocation());
		
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
