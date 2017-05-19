package asteroids.model.programs.expressions;

import asteroids.model.MinorPlanet;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;

public class PlanetExpression extends Expression<EntityLiteral> {

	public PlanetExpression(SourceLocation location) throws IllegalArgumentException {
		super(location);
	}

	@Override
	public EntityLiteral evaluate(ExecutionContext context) {
		MinorPlanet closest = context.getWorld().getMinorPlanets().stream().reduce(
				(a, b) -> a.getDistanceBetween(context.getExecutor()) < b.getDistanceBetween(context.getExecutor()) ? a : b
																		).orElse(null);
		return new EntityLiteral(closest);
	}

}