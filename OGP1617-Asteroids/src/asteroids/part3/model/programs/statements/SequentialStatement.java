package asteroids.part3.model.programs.statements;

import asteroids.part3.programs.SourceLocation;

public abstract class SequentialStatement extends Statement{
	
	public SequentialStatement(SourceLocation location){
		super(location);
	}
	
	private Statement nextStatement;
}
