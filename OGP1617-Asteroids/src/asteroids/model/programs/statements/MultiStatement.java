package asteroids.model.programs.statements;

import java.util.List;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.part3.programs.SourceLocation;

public class MultiStatement extends Statement{
	

	public MultiStatement(SourceLocation location, List<Statement> statements) throws IllegalArgumentException{
		super(location);
		if(statements.contains(null))
			throw new IllegalArgumentException("At: " + getSourceLocation().toString());
		this.statements = statements;
	}
	
	private final List<Statement> statements;

	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException{
		for(Statement s : statements){
			s.execute(context);
			if(context.isBreaking())
				return;
		}
		
	}
	
}
