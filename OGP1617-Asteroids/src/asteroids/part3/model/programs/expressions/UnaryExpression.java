package asteroids.part3.model.programs.expressions;

import be.kuleuven.cs.som.annotate.Basic;

public abstract class UnaryExpression<AT extends Type, ET extends Type> extends Expression<AT, ET>{

	public UnaryExpression(Expression<? extends Type, AT> arg) throws IllegalArgumentException{
		super();
		if(!canHaveAsArgument(arg))
			throw new IllegalArgumentException("The argument of a UnaryExpression must be effective.");
		this.arg = arg;
	}
	
	public boolean canHaveAsArgument(Expression<? extends Type, AT> argument){
		return argument != null;
	}
	
	@Basic
	public Expression<? extends Type, AT> getArgument(){
		return arg;
	}
	
	private final Expression<? extends Type, AT> arg;
}
