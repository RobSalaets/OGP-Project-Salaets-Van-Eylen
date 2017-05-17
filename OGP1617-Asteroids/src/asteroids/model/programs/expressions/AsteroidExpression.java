package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.AsteroidEntity;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class AsteroidExpression<AT extends Type, ET extends Type> extends Expression<ET>{
	
	public AsteroidExpression(Expression<? super AsteroidEntity> arg, SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public ET evaluate(Scope scope, World world) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		world.getAsteroids().stream().filter(context.getExecutor().getPosition)
	}

}