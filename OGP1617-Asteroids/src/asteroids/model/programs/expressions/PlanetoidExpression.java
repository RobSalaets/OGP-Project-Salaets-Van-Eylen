package asteroids.model.programs.expressions;

import asteroids.model.Planetoid;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;

public class PlanetoidExpression extends Expression<EntityLiteral>{
	
	public PlanetoidExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public EntityLiteral evaluate(ExecutionContext context) {
		Planetoid closest = context.getWorld().getPlanetoids().stream().reduce(
				(a, b) -> a.getDistanceBetween(context.getExecutor()) < b.getDistanceBetween(context.getExecutor()) ? a : b
																		).orElse(null);
		return new EntityLiteral(closest);
	}

}