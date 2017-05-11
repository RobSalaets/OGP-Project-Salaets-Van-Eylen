package asteroids.model.programs.expressions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public abstract class UnaryExpression<E extends Type> extends Expression<E>{

	public UnaryExpression(Expression<E> arg) throws IllegalArgumentException{
		super();
		if(!canHaveAsArgument(arg))
			throw new IllegalArgumentException("The argument of a UnaryExpression must be effective.");
		this.arg = arg;
	}
	
	public boolean canHaveAsArgument(Expression<E> argument){
		return argument != null;
	}
	
	@Basic
	@Immutable
	public Expression<E> getArgument(){
		return arg;
	}
	
	private final Expression<E> arg;
}
