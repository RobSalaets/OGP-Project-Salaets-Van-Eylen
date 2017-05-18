package asteroids.model.programs.expressions;

import asteroids.model.MinorPlanet;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;

public class PlanetExpression extends Expression<EntityLiteral> {

	public PlanetExpression(SourceLocation location) throws IllegalArgumentException {
		super(location);
	}

	@Override
	public EntityLiteral evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		MinorPlanet closest = world.getMinorPlanets().stream().reduce(
				(a, b) -> a.getDistanceBetween(executor) < b.getDistanceBetween(executor) ? a : b
																		).orElse(null);
		return new EntityLiteral(closest);
	}

}