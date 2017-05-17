package asteroids.model.programs.expressions;

public class Planetoid extends Planet {
	
	public Planetoid(Planetoid planetoid) {
		this.setPlanetoid(planetoid);
	}

	@Override
	public Planetoid getValue() {
		return this;
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
