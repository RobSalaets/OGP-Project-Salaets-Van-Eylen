package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
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
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException, ExpressionEvaluationException{
		Type eval = content.evaluate(context);
		context.addToPrintLog(eval, getSourceLocation());
		System.out.println(eval.toString());
	}
}
