package asteroids.model.programs.statements;

import asteroids.model.programs.Breakable;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.BooleanLiteral;
import asteroids.model.programs.expressions.Expression;
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
	public void execute(Scope scope, ExecutionContext context) throws ProgramExecutionTimeException{
		while(condition.evaluate(scope).getValue() && !context.isBreaking()){
			body.execute(scope, context);
		}
		context.stopBreaking();
	}
	
	public MultiStatement getBody(){
		return body;
	}

	@Override
	public void handleEscape() {
		// TODO Auto-generated method stub
		
	}
}
