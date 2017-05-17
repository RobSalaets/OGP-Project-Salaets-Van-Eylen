package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.model.programs.expressions.types.Type;


public class EqualsExpression extends Expression<BooleanLiteral> {

	public EqualsExpression(Expression<? extends Type> left, Expression<? extends Type> right) throws IllegalArgumentException {
		if(left == null || right == null)
			throw new IllegalArgumentException();
		this.leftArg = left;
		this.rightArg = right;
	}
	
	private final Expression<? extends Type> leftArg;
	private final Expression<? extends Type> rightArg;

	@Override
	public BooleanLiteral evaluate(Scope scope, World world){
		return new BooleanLiteral(leftArg.evaluate(scope, world).equals(rightArg.evaluate(scope, world)));
	}

}
