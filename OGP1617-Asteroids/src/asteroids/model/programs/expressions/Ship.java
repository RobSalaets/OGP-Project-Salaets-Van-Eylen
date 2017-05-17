package asteroids.model.programs.expressions;

public class Ship extends EntityLiteral {

	public Ship(Ship ship) {
		this.setShip(ship);
	}

	@Override
	public Ship getValue() {
		return this;
	}
	
	private Ship ship;
	
	public void setShip(Ship ship){
		if(ship == null)
			throw new IllegalArgumentException();
		this.ship = ship;
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
	
}
