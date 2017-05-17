package asteroids.model.programs.statements;

import asteroids.model.programs.Breakable;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.part3.programs.SourceLocation;

public class WhileStatement extends ConditionalStatement implements Breakable{
	
	public WhileStatement(SourceLocation location, Expression<BooleanLiteral> condition, MultiStatement body) throws IllegalArgumentException{
		super(location, condition);
		if(body == null)
			throw new IllegalArgumentException();
		this.body = body;
	}

	private final MultiStatement body;

	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException{
		context.addToStack(this, getSourceLocation());
		while(condition.evaluate(context.getScope(), context.getWorld()).getValue() && !context.isBreaking())
			body.execute(context);
		
		if(!context.isReturning() && context.isBreaking())
			context.stopBreaking();
	}
	
	public MultiStatement getBody(){
		return body;
	}
}
