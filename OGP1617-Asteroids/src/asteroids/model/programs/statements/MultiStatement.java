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
	}
	
	private final List<Statement> statements;

	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException, ExpressionEvaluationException{
		for(int i = statementPointer; i < statements.size(); i++){
			statementPointer = i;
			if(context.canExecuteAction()){
				statements.get(i).execute(context); //Probleem return uit execute eerste keer moet pointer op i+1
			}										// daarna i+1
			if(!context.canExecuteAction()){
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
		executed = true;
	}
	
	private int statementPointer = 0;
	
	public boolean hasFullyExecuted(){
		return executed;
	}
	
	private boolean executed = false;
}
