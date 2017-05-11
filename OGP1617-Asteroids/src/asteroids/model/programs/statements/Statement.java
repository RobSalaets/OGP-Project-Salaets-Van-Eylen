package asteroids.model.programs.statements;

import asteroids.part3.programs.SourceLocation;

public abstract class Statement{
	
	public Statement(SourceLocation location){
		this.location = location;
	}

	private final SourceLocation location;
	
	public abstract void execute();
		
	
}
