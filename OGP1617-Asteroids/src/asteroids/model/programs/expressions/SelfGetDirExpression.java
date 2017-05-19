package asteroids.model.programs.expressions;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.part3.programs.SourceLocation;

public class SelfGetDirExpression extends Expression<DoubleLiteral>{
	
	public SelfGetDirExpression(SourceLocation location){
		super(location);
	}

	@Override
	public DoubleLiteral evaluate(ExecutionContext context) throws ExpressionEvaluationException{
		return new DoubleLiteral(context.getExecutor().getOrientation());
	}
	
}
