package asteroids.model.programs.expressions;

import asteroids.model.Ship;

public class ShipEntity extends EntityLiteral {

	public ShipEntity(Ship ship) {
		this.setShip(ship);
	}

	@Override
	public Ship getValue() {
		return ship;
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
