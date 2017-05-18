package asteroids.model.programs.expressions;

import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;

public class AnyExpression extends Expression<EntityLiteral>{
	
	public AnyExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public EntityLiteral evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		
//		if(!world.getAllEntities().iterator().hasNext())
//			return new NullType();
//		
//		Entity random = world.getAllEntities().iterator().next();
//		while(random == executor){
//		Entity random = world.getAllEntities().iterator().next();}
//		return new EntityLiteral(random);
		
		Entity result = world.getAllEntities().iterator().hasNext() ? world.getAllEntities().iterator().next() : null;
		result = result == executor && world.getAllEntities().iterator().hasNext() ? world.getAllEntities().iterator().next() : null;
		return new EntityLiteral(result);
	}

}
