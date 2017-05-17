package asteroids.model.programs.expressions;

import asteroids.model.Entity;

public class Any extends EntityLiteral{

	public Any(Entity entity) {
		this.setEntity(entity);
	}

	@Override
	public Entity getValue() {
		return this;
	}
	
	public Entity entity;
	
	public void setEntity(Entity entity){
		if(entity == null)
			throw new IllegalArgumentException();
		this.entity = entity;
	}
	
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
}
