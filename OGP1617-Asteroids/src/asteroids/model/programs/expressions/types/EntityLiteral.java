package asteroids.model.programs.expressions.types;

public abstract class EntityLiteral extends Type{
	
		@Override
		public boolean equals(Object other) {
			return this.equals(other);
		}
		
		@Override
		public String toString() {
			return String.valueOf(getValue());
		}
		
		
}


