package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.model.programs.expressions.types.ShipEntity;
import asteroids.part3.programs.SourceLocation;

public class SelfExpression extends Expression<EntityLiteral>{
	
	public SelfExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public ShipEntity evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
//		world.getShips().stream().filter(context.getExecutor().getPosition)
		return null;
	}

}