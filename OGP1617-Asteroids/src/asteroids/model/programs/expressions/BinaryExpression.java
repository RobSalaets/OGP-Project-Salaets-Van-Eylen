package asteroids.model.programs.expressions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public abstract class BinaryExpression<E extends Type> extends Expression<E>{

	public BinaryExpression(Expression<E> left, Expression<E> right) throws IllegalArgumentException{
		super();
		if(!canHaveAsArgument(left) || !canHaveAsArgument(right))
			throw new IllegalArgumentException("The arguments of a BinaryExpression must be effective.");
		this.leftArg = left;
		this.rightArg = right;
	}
	
	public boolean canHaveAsArgument(Expression<E> argument){
		return argument != null;
	}
	
	@Basic
	@Immutable
	public Expression<E> getLeftArgument(){
		return leftArg;
	}
	
	@Basic
	@Immutable
	public Expression<E> getRightArgument(){
		return rightArg;
	}
	
	private final Expression<E> leftArg;
	private final Expression<E> rightArg;
}
