package asteroids.model.programs.expressions;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class ParameterExpression extends Expression<Type> {
	
	private final String parameter;

	public ParameterExpression(String param, SourceLocation location) throws IllegalArgumentException{
		super(location);
		if(param == null)
			throw new IllegalArgumentException();
		this.parameter = param;
	}

	@Override
	public Type evaluate(ExecutionContext context) throws ProgramExecutionTimeException {
		return context.getCurrentScope().getVariable(parameter, getSourceLocation());
	}

}
