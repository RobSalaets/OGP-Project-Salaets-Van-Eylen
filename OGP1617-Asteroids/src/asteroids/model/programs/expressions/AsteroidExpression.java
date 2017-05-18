package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.AsteroidEntity;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;

public class AsteroidExpression extends Expression<EntityLiteral>{
	
	public AsteroidExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public AsteroidEntity evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		return null;
//		world.getAsteroids().stream().filter(context.getExecutor().getPosition)
	}

}