package asteroids.model.programs.expressions;

import java.util.Optional;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.model.programs.expressions.types.ShipEntity;
import asteroids.part3.programs.SourceLocation;

public class ShipExpression extends Expression<EntityLiteral>{
	
	public ShipExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public EntityLiteral evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		 Ship closest = world.getShips().stream().reduce((a,b) -> a.getDistanceBetween(executor) < b.getDistanceBetween(executor) || b==executor ? a:b).orElse(null);
		 return closest ==null ? new NullType(): new ShipEntity(closest);
	}

}