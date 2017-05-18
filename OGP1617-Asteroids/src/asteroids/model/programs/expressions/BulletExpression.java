package asteroids.model.programs.expressions;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;
	
public class BulletExpression extends Expression<EntityLiteral>{
		
		public BulletExpression(SourceLocation location) throws IllegalArgumentException{
			super(location);
		}

		@Override
		public EntityLiteral evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
			Set<Bullet> firedbullets = (Set<Bullet>) world.getBullets().stream().filter(b -> b.getSource() == executor).collect(Collectors.toSet());
			Iterator<Bullet> iterator = firedbullets.iterator();
			return new EntityLiteral(iterator.hasNext() ? iterator.next() : null);
		}

	}
	
