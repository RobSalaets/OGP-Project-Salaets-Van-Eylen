package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.PlanetEntity;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class PlanetExpression<AT extends Type, ET extends Type> extends Expression<ET>{
	
	public PlanetExpression(Expression<? super PlanetEntity> arg, SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public ET evaluate(Scope scope, World world) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		world.getMinorPlanets().stream().filter(context.getExecutor().getPosition)
	}

}