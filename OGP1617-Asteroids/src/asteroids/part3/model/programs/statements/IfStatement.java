package asteroids.part3.model.programs.statements;

import asteroids.part3.programs.SourceLocation;

public class IfStatement extends ConditionalStatement {
	
	public IfStatement(SourceLocation location){
		super(location);
	}

	private SequentialStatement body;
	private SequentialStatement elseBody;
}
