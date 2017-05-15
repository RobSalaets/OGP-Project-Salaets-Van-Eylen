package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

public class ConstantExpression<ET extends Type> extends Expression<ET> {
	
	public ConstantExpression(ET value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();
		this.value = value;
	}

	private final ET value;

	@Override
	public ET evaluate(Scope scope) {
		return value;
	}
}
