package asteroids.model.programs.expressions;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

/**
 * @param <ET>
 * 		The evaluation type of this Expression
 */
public abstract class Expression<ET extends Type>{
	
	private final SourceLocation location;

	protected Expression(SourceLocation location) throws IllegalArgumentException{
		this.location = location;
	}
	
	public SourceLocation getSourceLocation(){
		return location;
	}

	public abstract ET evaluate(ExecutionContext context) throws ExpressionEvaluationException, ProgramExecutionTimeException;
}
