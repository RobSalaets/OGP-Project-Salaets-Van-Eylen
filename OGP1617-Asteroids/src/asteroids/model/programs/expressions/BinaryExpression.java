package asteroids.model.programs.expressions;

import asteroids.model.programs.expressions.types.Type;
import be.kuleuven.cs.som.annotate.Basic;

/**
 * @param <AT>
 * 		The argument type of this BinaryExpression
 * @param <ET>
 * 		The evaluation type of this BinaryExpression
 */
public abstract class BinaryExpression<AT extends Type, ET extends Type> extends Expression<ET>{

	public BinaryExpression(Expression<? super AT> left, Expression<? super AT> right) throws IllegalArgumentException{
		super();
		if(!canHaveAsArgument(left) || !canHaveAsArgument(right))
			throw new IllegalArgumentException("The arguments of a BinaryExpression must be effective.");
		this.leftArg = left;
		this.rightArg = right;
	}
	
	public boolean canHaveAsArgument(Expression<? super AT> argument){
		return argument != null;
	}
	
	@Basic
	public Expression<? super AT> getLeftArgument(){
		return leftArg;
	}
	
	@Basic
	public Expression<? super AT> getRightArgument(){
		return rightArg;
	}
	
	private final Expression<? super AT> leftArg;
	private final Expression<? super AT> rightArg;
}
