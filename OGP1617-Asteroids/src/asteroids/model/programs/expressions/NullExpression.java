package asteroids.model.programs.expressions;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;

public class NullExpression extends Expression<EntityLiteral>{
	
	public NullExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public EntityLiteral evaluate(ExecutionContext context) {
		return new EntityLiteral(null);
	}

}