package asteroids.model.programs.expressions.types;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class BooleanLiteral extends Type{
	
	public BooleanLiteral(boolean state) {
		this.state = state;
	}

	@Override
	public Boolean getValue() {
		return state;
	}
	
	private final boolean state;
	
	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		BooleanLiteral otherVector = (BooleanLiteral) other;
		return getValue().equals(otherVector.getValue());
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
}
