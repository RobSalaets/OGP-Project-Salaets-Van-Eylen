package asteroids.model.programs.expressions;

public class Asteroid extends PlanetEntity {
	
	public Asteroid(Asteroid asteroid) {
		this.setAsteroid(asteroid);
	}

	@Override
	public Asteroid getValue() {
		return this;
	}
	
	public Asteroid asteroid;
	
	public void setAsteroid(Asteroid asteroid){
		if(asteroid == null)
			throw new IllegalArgumentException();
		this.asteroid = asteroid;
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
}
