package asteroids.model.programs.statements;

import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class ConditionalStatement extends SequentialStatement {
	
	public ConditionalStatement(SourceLocation location){
		super(location);
	}

	private Expression condition;

	@Override
	public void execute(Scope scope) {
		// TODO Auto-generated method stub
		
	}
}
