package asteroids.model.programs.expressions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import asteroids.model.Asteroid;
import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.AsteroidEntity;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.model.programs.expressions.types.NullType;
import asteroids.part3.programs.SourceLocation;

public class AsteroidExpression extends Expression<EntityLiteral>{
	
	public AsteroidExpression(SourceLocation location) throws IllegalArgumentException{
		super(location);
	}

	@Override
	public EntityLiteral evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
		Asteroid closest = world.getAsteroids().stream().reduce(
				(a, b) -> executor.getPosition().sub(a.getPosition()).getLength() < executor.getPosition().sub(b.getPosition()).getLength() ?
						a : b).orElse(null);
		List<Entity> l = new ArrayList<>();
		Iterator<Entity> iterator = l.iterator();
		
		Entity result = iterator.hasNext() ? iterator.next() : null;
		result = result == executor && iterator.hasNext() ? iterator.next() : null;
		
		return closest == null ? new NullType() : new AsteroidEntity(closest);
	}

}