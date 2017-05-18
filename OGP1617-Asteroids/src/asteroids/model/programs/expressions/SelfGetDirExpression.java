package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.part3.programs.SourceLocation;

public class SelfGetDirExpression extends Expression<DoubleLiteral>{
	
	public SelfGetDirExpression(SourceLocation location){
		super(location);
	}

	@Override
	public DoubleLiteral evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException{
		return new DoubleLiteral(executor.getOrientation());
	}
	
}
