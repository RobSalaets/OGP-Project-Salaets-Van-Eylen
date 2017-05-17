package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.Type;

public class VariableExpression extends Expression<Type>{
	
	public VariableExpression(String varName) throws IllegalArgumentException{
		super();
		this.varName = varName;
	}
	
	private final String varName;

	@Override
	public Type evaluate(Scope scope, World world) throws ExpressionEvaluationException {
		Type result = scope.getVariable(varName);
		if(result == null)
			throw new ExpressionEvaluationException("No such variable: " + varName);
		return result;
	}
}
