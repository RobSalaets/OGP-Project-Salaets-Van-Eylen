package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
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

	public abstract ET evaluate(Scope scope, World world) throws ExpressionEvaluationException, ProgramExecutionTimeException;
}
