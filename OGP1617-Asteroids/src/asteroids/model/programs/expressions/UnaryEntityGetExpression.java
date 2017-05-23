package asteroids.model.programs.expressions;

import asteroids.model.Entity;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class UnaryEntityGetExpression extends UnaryExpression<EntityLiteral, DoubleLiteral>{

	public UnaryEntityGetExpression(Expression<? super EntityLiteral> arg, EntityDataExtractor extractor, SourceLocation location) throws IllegalArgumentException{
		super(arg, location);
		if(extractor == null)
			throw new IllegalArgumentException("The EntityDataExtractor must be effective.");
		this.extractor = extractor;
	}

	private final EntityDataExtractor extractor;
	
	@Override
	public DoubleLiteral evaluate(ExecutionContext context) throws ExpressionEvaluationException, ProgramExecutionTimeException{
		Type arg = getArgument().evaluate(context);
		if(!(arg instanceof EntityLiteral))
			throw new ExpressionEvaluationException("Given operand does not evaluate to EntityLiteral", getSourceLocation(), this);
		if(arg.getValue() == null)
			throw new ExpressionEvaluationException("Given operand evaluates to null", getSourceLocation(), this);
		Double result = null;
		try{
			result = extractor.extractValue(((Entity)((EntityLiteral)arg).getValue()));
		}catch(Exception e){
			throw new ExpressionEvaluationException("Exception during EntityDataExtractor evaluation.", getSourceLocation(), this);
		}
		
		return new DoubleLiteral(result);
	}
	
}
