package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.model.programs.expressions.types.ShipEntity;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class UnaryGetDirExpression extends UnaryExpression<ShipEntity, DoubleLiteral>{
	
	public UnaryGetDirExpression(Expression<? super ShipEntity> arg, SourceLocation location){
		super(arg, location);
	}

	@Override
	public DoubleLiteral evaluate(Scope scope, World world) throws ExpressionEvaluationException{
		Type arg = getArgument().evaluate(scope, world);
		if(!((arg instanceof ShipEntity)))
			throw new ExpressionEvaluationException("Given operands do not evaluate to ShipEntity", getSourceLocation());
		return new DoubleLiteral(((Ship)((ShipEntity)arg).getValue()).getOrientation());
	}
	
}
