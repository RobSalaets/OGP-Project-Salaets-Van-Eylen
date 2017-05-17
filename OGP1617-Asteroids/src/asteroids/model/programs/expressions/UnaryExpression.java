package asteroids.model.programs.expressions;

import asteroids.model.programs.expressions.types.Type;
import be.kuleuven.cs.som.annotate.Basic;

public abstract class UnaryExpression<AT extends Type, ET extends Type> extends Expression<ET>{

	public UnaryExpression(Expression<? super AT> arg) throws IllegalArgumentException{
		super();
		if(!canHaveAsArgument(arg))
			throw new IllegalArgumentException("The argument of a UnaryExpression must be effective.");
		
		this.arg = arg;
	}
	
	public boolean canHaveAsArgument(Expression<? super AT> argument){
		return argument != null;
	}
	
	@Basic
	public Expression<? super AT> getArgument(){
		return arg;
	}
	
	private final Expression<? super AT> arg;
}
