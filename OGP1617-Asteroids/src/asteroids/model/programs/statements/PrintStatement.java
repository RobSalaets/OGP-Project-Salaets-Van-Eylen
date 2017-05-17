package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class PrintStatement extends Statement {


	public PrintStatement(SourceLocation location, Expression<? extends Type> content) throws IllegalArgumentException{
		super(location);
		if(content == null)
			throw new IllegalArgumentException();
		this.content = content;
	}

	private final Expression<? extends Type> content;
	
	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException{
		Type eval = content.evaluate(context.getCurrentScope(), context.getWorld());
		context.addToPrintLog(eval, getSourceLocation());
		System.out.println(eval.toString());
	}
}
