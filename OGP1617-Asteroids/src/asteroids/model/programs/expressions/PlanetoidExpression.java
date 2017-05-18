package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.model.programs.expressions.types.PlanetoidEntity;
import asteroids.part3.programs.SourceLocation;

public class PlanetoidExpression extends Expression<EntityLiteral>{
	
	public PlanetoidExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public PlanetoidEntity evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
//		world.getPlanetoids().stream().filter(context.getExecutor().getPosition)
		return null;
	}

}