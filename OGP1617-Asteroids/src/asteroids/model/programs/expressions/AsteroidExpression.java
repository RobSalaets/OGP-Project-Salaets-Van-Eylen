package asteroids.model.programs.expressions;

import asteroids.model.Asteroid;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;

public class AsteroidExpression extends Expression<EntityLiteral>{
	
	public AsteroidExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public EntityLiteral evaluate(ExecutionContext context) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		Asteroid closest = context.getWorld().getAsteroids().stream().reduce(
				(a, b) -> a.getDistanceBetween(context.getExecutor()) < b.getDistanceBetween(context.getExecutor()) ? a : b
																).orElse(null);
		return new EntityLiteral(closest);
	}

}