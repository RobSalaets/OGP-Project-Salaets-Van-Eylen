package asteroids.model.programs.expressions;

public class VariableExpression<E extends Type> extends Expression<E>{
	
	public VariableExpression(E value) throws IllegalArgumentException{
		super();
		if(value == null)
			throw new IllegalArgumentException("The value of a VariableExpression must be effective.");
		this.value = value;
	}
	
	public E getValue(){
		return value;
	}
	
	private final E value;

	@Override
	public Object evaluate() {
		return value.getValue();
	}
}
