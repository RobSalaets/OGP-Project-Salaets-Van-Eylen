package asteroids.model.programs.expressions;

import java.util.Iterator;

import asteroids.model.Entity;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;

public class AnyExpression extends Expression<EntityLiteral>{
	
	public AnyExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public EntityLiteral evaluate(ExecutionContext context) {
		Iterator<Entity> iterator = context.getWorld().getAllEntities().iterator();
		Entity result = iterator.hasNext() ? iterator.next() : null;
		result = (result == context.getExecutor() && iterator.hasNext()) ? iterator.next() : result;
		return new EntityLiteral(result);
	}

}
