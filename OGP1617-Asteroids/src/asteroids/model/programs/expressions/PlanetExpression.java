package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.model.programs.expressions.types.PlanetEntity;
import asteroids.part3.programs.SourceLocation;

public class PlanetExpression extends Expression<EntityLiteral>{
	
	public PlanetExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public PlanetEntity evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
//		world.getMinorPlanets().stream().filter(context.getExecutor().getPosition)
		return null;
	}

}