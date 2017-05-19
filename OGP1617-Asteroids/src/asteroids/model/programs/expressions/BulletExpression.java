package asteroids.model.programs.expressions;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import asteroids.model.Bullet;
import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;
	
public class BulletExpression extends Expression<EntityLiteral>{
		
		public BulletExpression(SourceLocation location) throws IllegalArgumentException{
			super(location);
		}

		@Override
		public EntityLiteral evaluate(ExecutionContext context) {
			Set<Bullet> firedbullets = (Set<Bullet>) context.getWorld().getBullets().stream().filter(b -> b.getSource() == context.getExecutor()).collect(Collectors.toSet());
			Iterator<Bullet> iterator = firedbullets.iterator();
			return new EntityLiteral(iterator.hasNext() ? iterator.next() : null);
		}

	}
	
