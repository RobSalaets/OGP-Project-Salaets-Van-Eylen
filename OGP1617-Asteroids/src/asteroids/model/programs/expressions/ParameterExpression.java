package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.Scope;
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
	public Type evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException {
		return scope.getVariable(parameter, getSourceLocation());
	}

}
