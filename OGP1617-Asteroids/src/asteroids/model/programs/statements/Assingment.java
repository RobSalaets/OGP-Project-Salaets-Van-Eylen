package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class Assingment extends Statement {
	
	public Assingment(SourceLocation location, String name, Expression<? extends Type> expression) throws IllegalArgumentException{
		super(location);
		if(expression == null || name == null)
			throw new IllegalArgumentException();
		this.variableName = name;
		this.expression = expression;
	}

	private final String variableName;
	private final Expression<? extends Type> expression;
	
	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException{
		context.getCurrentScope().putVariable(variableName, expression.evaluate(context.getCurrentScope(), context.getWorld()), getSourceLocation());
	}
}
