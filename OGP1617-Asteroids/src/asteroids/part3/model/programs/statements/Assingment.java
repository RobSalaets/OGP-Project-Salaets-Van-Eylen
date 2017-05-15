package asteroids.part3.model.programs.statements;

import asteroids.part3.model.programs.expressions.Expression;
import asteroids.part3.model.programs.expressions.Type;
import asteroids.part3.programs.SourceLocation;

public class Assingment extends SequentialStatement {
	
	public Assingment(SourceLocation location, String name, Expression<? extends Type, ? extends Type> expression){
		super(location);
		this.variableName = name;
		this.value = expression.evaluate();
	}

	private final String variableName;
	private Type value;
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
