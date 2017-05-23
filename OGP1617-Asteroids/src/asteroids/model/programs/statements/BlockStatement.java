package asteroids.model.programs.statements;

import java.util.Arrays;
import java.util.List;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.part3.programs.SourceLocation;

public class BlockStatement extends Statement{
	

	public BlockStatement(SourceLocation location, List<Statement> statements) throws IllegalArgumentException{
		super(location);
		if(statements.contains(null))
			throw new IllegalArgumentException();
		this.statements = statements;
	}
	
	public BlockStatement(SourceLocation location, Statement statement) throws IllegalArgumentException{
		this(location, Arrays.asList(new Statement[]{statement}));
	}
	
	private final List<Statement> statements;

	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException, ExpressionEvaluationException{
		for(int i = context.getBlockPointerFor(this); i < statements.size() && !context.isBreaking(); i++){
			if(context.canExecuteAction())
				statements.get(i).execute(context);
			
			if(!context.canExecuteAction()){
				context.setBlockPointer(this, statements.get(i) instanceof Action ? i + 1 : i, getSourceLocation());
				return;
			}
			
		}
		
		context.setBlockPointer(this, 0, getSourceLocation());
		setExecuted();
	}
	
	private void setExecuted(){
		executed = true;
	}
	
	
	public boolean hasFullyExecuted(){
		return executed;
	}
	
	private boolean executed = false;

}
