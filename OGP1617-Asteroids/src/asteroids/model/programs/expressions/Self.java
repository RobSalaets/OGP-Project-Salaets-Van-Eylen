package asteroids.model.programs.expressions;

public abstract class Self extends Ship{
	public Self(Ship self) {
		this.setShip(self);
	}

	@Override
	public Ship getValue() {
		return this;
	}
	
	private Ship self;
	
	public void setShip(Ship self){
		if(self == null)
			throw new IllegalArgumentException();
		this.self = self;
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
	
}
