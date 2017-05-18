package asteroids.model.programs.expressions.types;

import asteroids.model.MinorPlanet;

public class PlanetEntity extends EntityLiteral{
	
	
	@Override
	public boolean equals(Object other) {
		return this.equals(other);
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
	
	@Override
	public Object getValue() {
		return minorplanet;
	}
	public MinorPlanet minorplanet;
}