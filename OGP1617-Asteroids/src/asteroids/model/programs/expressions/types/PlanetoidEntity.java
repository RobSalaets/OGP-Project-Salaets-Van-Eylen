package asteroids.model.programs.expressions.types;

import asteroids.model.Planetoid;

public class PlanetoidEntity extends PlanetEntity {
	
	public PlanetoidEntity(Planetoid planetoid) {
		this.setPlanetoid(planetoid);
	}

	@Override
	public Planetoid getValue() {
		return planetoid;
	}
	
	public Planetoid planetoid;
	
	public void setPlanetoid(Planetoid planetoid){
		if(planetoid == null)
			throw new IllegalArgumentException();
		this.planetoid = planetoid;
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
	
}
