package asteroids.model.programs.expressions.types;

import asteroids.model.Asteroid;

public class AsteroidEntity extends PlanetEntity {
	
	public AsteroidEntity(Asteroid asteroid) throws IllegalArgumentException{
		this.setAsteroid(asteroid);
	}

	@Override
	public Asteroid getValue() {
		return asteroid;
	}
	
	public Asteroid asteroid;
	
	public void setAsteroid(Asteroid asteroid) throws IllegalArgumentException{
		if(asteroid == null)
			throw new IllegalArgumentException();
		this.asteroid = asteroid;
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
}
