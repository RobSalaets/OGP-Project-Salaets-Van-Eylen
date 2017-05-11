package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class Assingment extends SequentialStatement {
	
	public Assingment(SourceLocation location){
		super(location);
	}

	private String variable;
	private Expression value;
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
