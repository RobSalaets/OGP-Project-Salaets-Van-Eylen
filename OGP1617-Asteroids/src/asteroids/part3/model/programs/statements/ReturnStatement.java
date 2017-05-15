package asteroids.part3.model.programs.statements;

import asteroids.part3.model.programs.Function;
import asteroids.part3.model.programs.expressions.Expression;
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
