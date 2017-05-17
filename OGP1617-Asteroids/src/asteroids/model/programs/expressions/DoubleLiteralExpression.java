package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.DoubleLiteral;

public class DoubleLiteralExpression extends Expression<DoubleLiteral> {
	
	public DoubleLiteralExpression(DoubleLiteral value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();
		this.value = value;
	}

	private final DoubleLiteral value;

	@Override
	public DoubleLiteral evaluate(Scope scope, World world){
		return value;
	}
}
