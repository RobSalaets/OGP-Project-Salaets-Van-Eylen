package asteroids.model.programs.expressions;

public abstract class Expression<E extends Type>{
	
	protected Expression() throws IllegalArgumentException{
		
	}

	public abstract Object evaluate();
}
