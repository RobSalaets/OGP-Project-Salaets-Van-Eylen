package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class NullExpression extends Expression<Type>{
	
	public NullExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public Type evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		return new NullType();
	}

}