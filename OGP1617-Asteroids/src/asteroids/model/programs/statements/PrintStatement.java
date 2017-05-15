package asteroids.model.programs.statements;

import asteroids.model.programs.Scope;
import asteroids.part3.programs.SourceLocation;

public class PrintStatement extends SequentialStatement {

	public PrintStatement(SourceLocation location){
		super(location);
	}
	
	private String message;

	@Override
	public void execute(Scope scope) {
		// TODO Auto-generated method stub
		
	}
}
