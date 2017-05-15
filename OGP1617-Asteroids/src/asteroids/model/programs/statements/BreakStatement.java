package asteroids.model.programs.statements;

import asteroids.model.programs.Scope;
import asteroids.part3.programs.SourceLocation;

public class BreakStatement extends Statement{
	
	public BreakStatement(SourceLocation location){
		super(location);
	}

	private WhileStatement whileLoop;

	@Override
	public void execute(Scope scope) {
		// TODO Auto-generated method stub
		
	}
}
