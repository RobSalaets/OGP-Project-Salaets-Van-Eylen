package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class VariableExpression extends Expression<Type>{
	
	public VariableExpression(String varName) throws IllegalArgumentException{
		super();
		this.varName = varName;
	}
	
	private final String varName;

	@Override
	public Type evaluate(Scope scope) {
		return scope.getVariable(varName);
	}
}
