package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.Scope;
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
	public Type evaluate(Scope scope, World world) throws ExpressionEvaluationException {
		Type result = scope.getVariable(varName, getSourceLocation());
		if(result == null)
			throw new ExpressionEvaluationException("No such variable: " + varName, getSourceLocation());
		return result;
	}
}
