package asteroids.model.programs.expressions;

import asteroids.model.programs.Scope;

/**
 * @param <ET>
 * 		The evaluation type of this Expression
 */
public abstract class Expression<ET extends Type>{
	
	protected Expression() throws IllegalArgumentException{
		
	}

	public abstract ET evaluate(Scope scope);
}
