package asteroids.model;

import be.kuleuven.cs.som.annotate.Value;

/**
 * An enumaration indicating the collision type.
 */
@Value
public enum CollisionType {
	BOUNDARY, INTER_ENTITY, UNDEFINED
}
