package asteroids.model.programs.statements;

import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Type;
import asteroids.part3.programs.SourceLocation;

public class Assingment extends SequentialStatement {
	
	public Assingment(SourceLocation location, String name, Expression<? extends Type, ? extends Type> expression){
		super(location);
		this.variableName = name;
		this.expression = expression;
	}

	private final String variableName;
	private final Expression<? extends Type, ? extends Type> expression;
	
	@Override
	public void execute(Scope scope) {
		scope.putVariable(variableName, expression.evaluate(scope));
	}
}
