package asteroids.part3.model.programs.expressions;

import be.kuleuven.cs.som.annotate.Basic;

public abstract class BinaryExpression<AT extends Type, ET extends Type> extends Expression<AT, ET>{

	public BinaryExpression(Expression<? extends Type, AT> left, Expression<? extends Type, AT> right) throws IllegalArgumentException{
		super();
		if(!canHaveAsArgument(left) || !canHaveAsArgument(right))
			throw new IllegalArgumentException("The arguments of a BinaryExpression must be effective.");
		this.leftArg = left;
		this.rightArg = right;
	}
	
	public boolean canHaveAsArgument(Expression<? extends Type, AT> argument){
		return argument != null;
	}
	
	@Basic
	public Expression<? extends Type, AT> getLeftArgument(){
		return leftArg;
	}
	
	@Basic
	public Expression<? extends Type, AT> getRightArgument(){
		return rightArg;
	}
	
	private final Expression<? extends Type, AT> leftArg;
	private final Expression<? extends Type, AT> rightArg;
}
