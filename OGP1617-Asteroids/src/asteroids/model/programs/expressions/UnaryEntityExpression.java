package asteroids.model.programs.expressions;

import asteroids.model.Entity;
import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.model.programs.expressions.types.Type;

public class UnaryEntityExpression extends UnaryExpression<EntityLiteral, DoubleLiteral>{

	public UnaryEntityExpression(Expression<? super EntityLiteral> arg, UnaryEntityOperation operation) throws IllegalArgumentException{
		super(arg);
		if(operation == null)
			throw new IllegalArgumentException("The UnaryEntityOperation must be effective.");
		this.operationType = operation;
	}

	private final UnaryEntityOperation operationType;
	
	@Override
	public DoubleLiteral evaluate(Scope scope, World world) throws ExpressionEvaluationException{
		Type arg = getArgument().evaluate(scope, world);
		if(!((arg instanceof EntityLiteral)))
			throw new ExpressionEvaluationException("Given operands do not evaluate to EntityLiteral");
		
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
