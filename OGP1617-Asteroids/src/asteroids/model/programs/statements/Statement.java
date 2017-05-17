package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.Basic;

public abstract class Statement{
	
	public Statement(SourceLocation location) throws IllegalArgumentException{
		if(location == null)
			throw new IllegalArgumentException();
		this.location = location;
	}

	private final SourceLocation location;
	
	@Basic 
	public SourceLocation getSourceLocation(){
		return location;
	}
	
	public abstract void execute(ExecutionContext context) throws ProgramExecutionTimeException;
		
	
}
