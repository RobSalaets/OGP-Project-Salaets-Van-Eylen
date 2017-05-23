package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.part3.programs.SourceLocation;

public class IfStatement extends ConditionalStatement {

	public IfStatement(SourceLocation location, Expression<BooleanLiteral> condition, Statement body, Statement elseBody) {
		super(location, condition);
		this.body = new BlockStatement(getSourceLocation(), body);
		this.elseBody = elseBody == null ? null : new BlockStatement(getSourceLocation(), elseBody);
	}

	private final BlockStatement body;
	private final BlockStatement elseBody;

	@Override
	public void execute(ExecutionContext context) throws ProgramExecutionTimeException, ExpressionEvaluationException {
		if(condition.evaluate(context).getValue()){
			body.execute(context);
		}else if(elseBody != null){
			elseBody.execute(context);
		}
	}
}
