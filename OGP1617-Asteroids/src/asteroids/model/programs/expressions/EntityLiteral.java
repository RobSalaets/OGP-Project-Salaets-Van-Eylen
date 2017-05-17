package asteroids.model.programs.expressions;
import asteroids.model.Entity;


public abstract class EntityLiteral extends Type{
	
		public EntityLiteral(Entity entity) {
			this.entity = entity;
		}

		public Entity getEntity() {
			return entity;
		}
		
		private final Entity entity;
		
		@Override
		public boolean equals(Object other) {
			if(other == null)
				return false;
			if(this.getClass() != other.getClass())
				return false;
			return this.equals(other);
		}
		
		@Override
		public String toString() {
			return String.valueOf(getValue());
		}
		
		
}


