package asteroids.model.programs.expressions.types;

import asteroids.model.Entity;


public class EntityLiteral extends Type{
	
		public EntityLiteral(Entity entity) {
		 this.entity=entity;
	}

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
			return entity;
		}
		public Entity entity;
		
}


