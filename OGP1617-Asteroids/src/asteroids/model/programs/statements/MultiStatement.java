package asteroids.model.programs.statements;

import java.util.List;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.ExpressionEvaluationException;
import asteroids.part3.programs.SourceLocation;

public class MultiStatement extends Statement{
	

	public MultiStatement(SourceLocation location, List<Statement> statements) throws IllegalArgumentException{
		super(location);
		if(statements.contains(null))
			throw new IllegalArgumentException();
		this.statements = statements;
		resetPointer();
	}
	
	private final List<Statement> statements;

	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException, ExpressionEvaluationException{
		for(int i = statementPointer; i < statements.size(); i++){
			statements.get(i).execute(context); 
			if(!context.canExecuteAction()){ // Action didn't execute
				statementPointer = i;
				return;
			}
			
			if(context.isBreaking())
				break;
		}
		resetPointer();
	}
	
	private void resetPointer(){
		statementPointer = 0;
	}
	
	private int statementPointer;
	
}
