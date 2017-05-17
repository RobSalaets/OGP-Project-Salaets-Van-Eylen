package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class AnyExpression<AT extends Type, ET extends Type> extends Expression<ET>{
	
	public AnyExpression(Expression<? super EntityLiteral> arg, SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public ET evaluate(Scope scope, World world) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		world.getAllEntities().stream().filter(context.getExecutor().getPosition)
	}

}
