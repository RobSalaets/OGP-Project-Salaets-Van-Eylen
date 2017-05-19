package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;

public class ShipExpression extends Expression<EntityLiteral> {

	public ShipExpression(SourceLocation location) throws IllegalArgumentException {
		super(location);
	}

	@Override
	public EntityLiteral evaluate(ExecutionContext context) {
		Ship closest = context.getWorld().getShips().stream()
				.filter(e -> e != context.getExecutor())
				.reduce((a, b) -> a.getDistanceBetween(context.getExecutor()) < b.getDistanceBetween(context.getExecutor()) ? a : b).orElse(null);
		return new EntityLiteral(closest);
	}

}