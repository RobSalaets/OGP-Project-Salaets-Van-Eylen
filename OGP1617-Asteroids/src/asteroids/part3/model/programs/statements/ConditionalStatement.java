package asteroids.part3.model.programs.statements;

import asteroids.part3.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class ConditionalStatement extends SequentialStatement {
	
	public ConditionalStatement(SourceLocation location){
		super(location);
	}

	private Expression condition;

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
