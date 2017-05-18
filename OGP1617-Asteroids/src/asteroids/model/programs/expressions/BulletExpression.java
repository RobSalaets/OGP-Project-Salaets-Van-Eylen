package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.BulletEntity;
import asteroids.model.programs.expressions.types.EntityLiteral;
import asteroids.part3.programs.SourceLocation;
	
public class BulletExpression extends Expression<EntityLiteral>{
		
		public BulletExpression(SourceLocation location) throws IllegalArgumentException{
			super(location);
		}

		@Override
		public BulletEntity evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException, ProgramExecutionTimeException {
//			world.getBullets().stream().filter(context.getExecutor().getPosition)
			return null;
		}

	}
	
