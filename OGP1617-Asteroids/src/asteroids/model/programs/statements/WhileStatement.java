package asteroids.model.programs.statements;

import asteroids.part3.programs.SourceLocation;

public class WhileStatement extends ConditionalStatement {
	
	public WhileStatement(SourceLocation location){
		super(location);
	}

	private SequentialStatement body;
}
