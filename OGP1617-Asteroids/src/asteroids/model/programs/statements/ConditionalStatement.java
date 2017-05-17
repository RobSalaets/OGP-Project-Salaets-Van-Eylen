package asteroids.model.programs.statements;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.part3.programs.SourceLocation;

public abstract class ConditionalStatement extends Statement {
	
	public ConditionalStatement(SourceLocation location, Expression<BooleanLiteral> condition)  throws IllegalArgumentException{
		super(location);
		if(condition == null)
			throw new IllegalArgumentException();
		this.condition = condition;
	}

	protected final Expression<BooleanLiteral> condition;
}
