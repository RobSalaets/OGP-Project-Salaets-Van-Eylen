package asteroids.model.programs.expressions;

import asteroids.model.Entity;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class UnaryEntityGetExpression extends UnaryExpression<EntityLiteral, DoubleLiteral>{

	public UnaryEntityGetExpression(Expression<? super EntityLiteral> arg, UnaryEntityGetOperation operation, SourceLocation location) throws IllegalArgumentException{
		super(arg, location);
		if(operation == null)
			throw new IllegalArgumentException("The UnaryEntityOperation must be effective.");
		this.operationType = operation;
	}

	private final UnaryEntityGetOperation operationType;
	
	@Override
	public DoubleLiteral evaluate(ExecutionContext context) throws ExpressionEvaluationException, ProgramExecutionTimeException{
		Type arg = getArgument().evaluate(context);
		if(!(arg instanceof EntityLiteral))
			throw new ExpressionEvaluationException("Given operand does not evaluate to EntityLiteral", getSourceLocation(), this);
		if(arg.getValue() == null)
			throw new ExpressionEvaluationException("Given operand evaluates to null", getSourceLocation(), this);
		
		Double result = null;
		switch(operationType){
		case GETX:
			result = ((Entity)((EntityLiteral)arg).getValue()).getPosition().getX();
			break;
		case GETY:
			result = ((Entity)((EntityLiteral)arg).getValue()).getPosition().getY();
			break;
		case GETVX:
			result = ((Entity)((EntityLiteral)arg).getValue()).getVelocity().getX();
			break;	
		case GETVY:
			result = ((Entity)((EntityLiteral)arg).getValue()).getVelocity().getY();
			break;	
		case GET_RADIUS:
			result = ((Entity)((EntityLiteral)arg).getValue()).getRadius();
			break;
		default:
			break;
		}
		return new DoubleLiteral(result);
	}
	
}
