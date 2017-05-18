package asteroids.model.programs.expressions;

import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class UnaryEntityExpression extends UnaryExpression<EntityLiteral, DoubleLiteral>{

	public UnaryEntityExpression(Expression<? super EntityLiteral> arg, UnaryEntityOperation operation, SourceLocation location) throws IllegalArgumentException{
		super(arg, location);
		if(operation == null)
			throw new IllegalArgumentException("The UnaryEntityOperation must be effective.");
		this.operationType = operation;
	}

	private final UnaryEntityOperation operationType;
	
	@Override
	public DoubleLiteral evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException{
		Type arg = getArgument().evaluate(scope, world, executor);
		if(!((arg instanceof EntityLiteral)))
			throw new ExpressionEvaluationException("Given operand does not evaluate to EntityLiteral", getSourceLocation());
		
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
			result = ((Entity)((EntityLiteral)arg).getValue()).getPosition().getY();
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
