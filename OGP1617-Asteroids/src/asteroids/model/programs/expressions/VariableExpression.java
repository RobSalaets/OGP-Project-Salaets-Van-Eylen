package asteroids.model.programs.expressions;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class VariableExpression extends Expression<Type>{
	
	public VariableExpression(String varName, SourceLocation location) throws IllegalArgumentException{
		super(location);
		if(varName == null)
			throw new IllegalArgumentException();
		this.varName = varName;
	}
	
	private final String varName;

	@Override
	public Type evaluate(ExecutionContext context) throws ExpressionEvaluationException {
		Type result = context.getCurrentScope().getVariable(varName, getSourceLocation());
		return result;
	}
}
