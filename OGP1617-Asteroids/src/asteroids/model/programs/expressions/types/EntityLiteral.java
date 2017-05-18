package asteroids.model.programs.expressions.types;

import asteroids.model.Entity;

public class EntityLiteral extends Type {

	public EntityLiteral(Entity entity) {
		setEntity(entity);
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}

	@Override
	public Object getValue() {
		return entity;
	}

	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		EntityLiteral o = (EntityLiteral) other;
		return getValue() == o.getValue();
	}
	
	public void setEntity(Entity entity){
		this.entity = entity;
	}
	
	public Entity entity;


}
