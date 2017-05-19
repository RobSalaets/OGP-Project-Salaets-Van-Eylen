package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.ExpressionEvaluationException;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.part3.programs.SourceLocation;

public class IfStatement extends ConditionalStatement {

	public IfStatement(SourceLocation location, Expression<BooleanLiteral> condition, Statement body, Statement elseBody) {
		super(location, condition);
		this.body = body;
		this.elseBody = elseBody;
	}

	private final Statement body;
	private final Statement elseBody;

	@Override
	public boolean execute(ExecutionContext context) throws ProgramExecutionTimeException, ExpressionEvaluationException {
		if(condition.evaluate(context).getValue()){
			return body.execute(context);
		}else if(elseBody != null){
			return elseBody.execute(context);
		}
		return false;
	}
}
