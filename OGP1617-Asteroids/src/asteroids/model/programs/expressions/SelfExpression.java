package asteroids.model.programs.expressions;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;

public class SelfExpression extends Expression<EntityLiteral>{
	
	public SelfExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public EntityLiteral evaluate(ExecutionContext context) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		return new EntityLiteral(context.getExecutor());
	}

}