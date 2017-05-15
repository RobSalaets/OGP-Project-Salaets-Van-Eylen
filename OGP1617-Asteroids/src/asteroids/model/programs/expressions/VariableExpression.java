package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class VariableExpression<ET extends Type> extends Expression<Type, ET>{
	
	public VariableExpression(String varName) throws IllegalArgumentException{
		super();
		this.varName = varName;
	}
	
	private final String varName;

	@Override
	public ET evaluate(Scope scope) {
		return scope.getVariable(varName); //Probleem: variableExpression generic type niet af te leiden uit String
	}
}
