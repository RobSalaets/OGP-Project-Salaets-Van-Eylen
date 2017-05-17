package asteroids.model.programs.expressions;

import java.util.List;

import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class FunctionCallExpression extends Expression<Type> {


	public FunctionCallExpression(String functionName, List<Expression<? extends Type>> actualArgs, SourceLocation sourceLocation) throws IllegalArgumentException {
		super(sourceLocation);
		if(functionName == null || actualArgs == null)
			throw new IllegalArgumentException();
		this.functionName = functionName;
		this.arguments = actualArgs;
	}
	
	private final String functionName;
	private final List<Expression<? extends Type>> arguments;

	@Override
	public Type evaluate(Scope scope, World world) throws ProgramExecutionTimeException {
		return scope.getFunction(functionName, getSourceLocation()).execute(arguments);
	}

}
