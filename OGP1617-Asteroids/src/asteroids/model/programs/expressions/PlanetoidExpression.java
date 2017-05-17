package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.PlanetoidEntity;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class PlanetoidExpression<AT extends Type, ET extends Type> extends Expression<ET>{
	
	public PlanetoidExpression(Expression<? super PlanetoidEntity> arg, SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public ET evaluate(Scope scope, World world) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		world.getPlanetoids().stream().filter(context.getExecutor().getPosition)
	}

}