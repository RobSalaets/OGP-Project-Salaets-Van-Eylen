package asteroids.part3.model.programs.expressions;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class DoubleLiteral extends Type{
	
	public DoubleLiteral(double value) {
		this.value = value;
	}

	@Override
	public Double getValue() {
		return value;
	}
	
	private final double value;
	
	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		DoubleLiteral otherVector = (DoubleLiteral) other;
		return getValue() == otherVector.getValue();
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
}
