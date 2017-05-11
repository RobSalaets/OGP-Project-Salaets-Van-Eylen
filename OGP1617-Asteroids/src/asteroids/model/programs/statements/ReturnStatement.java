package asteroids.model.programs.statements;

import asteroids.model.programs.Function;
import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class ReturnStatement extends Statement {
	
	public ReturnStatement(SourceLocation location){
		super(location);
	}

	private Expression result;
	private String function;
	private Function f;
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
