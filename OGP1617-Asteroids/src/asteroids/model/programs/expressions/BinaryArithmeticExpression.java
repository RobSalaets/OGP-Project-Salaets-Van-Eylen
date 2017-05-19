package asteroids.model.programs.expressions;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
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
	public DoubleLiteral evaluate(ExecutionContext context) throws ExpressionEvaluationException, ProgramExecutionTimeException{
		Type evalL = getLeftArgument().evaluate(context);
		Type evalR = getRightArgument().evaluate(context);
		if(!((evalL instanceof DoubleLiteral) && (evalR instanceof DoubleLiteral)))
			throw new ExpressionEvaluationException("Given operands do not evaluate to DoubleLiteral", getSourceLocation(), this);
		
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
