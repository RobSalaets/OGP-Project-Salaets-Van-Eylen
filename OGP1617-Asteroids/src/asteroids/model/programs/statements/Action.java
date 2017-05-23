package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.part3.programs.SourceLocation;

public abstract class Action extends Statement {
	
	public Action(SourceLocation location){
		super(location);
	}
	
	@Override
	public void execute(ExecutionContext context)
			throws ProgramExecutionTimeException, ExpressionEvaluationException {
		if(!context.canExecuteAction())
			throw new ProgramExecutionTimeException("Executing action with not enough time left", getSourceLocation());
		context.decrementExecutionTime(getSourceLocation());
	}
	
	public static final double ACTION_TIME = 0.2;
}
