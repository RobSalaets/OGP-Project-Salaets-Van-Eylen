package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

/**
 * @param <AT>
 * 		The argument type of this Expression
 * @param <ET>
 * 		The evaluation type of this Expression
 */
public abstract class Expression<AT extends Type, ET extends Type>{
	
	protected Expression() throws IllegalArgumentException{
		
	}

	public abstract ET evaluate(Scope scope);
}
