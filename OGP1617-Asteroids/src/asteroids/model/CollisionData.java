package asteroids.model;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A Class encapsulating information about a collision in the game.
 */
@Value
public class CollisionData{

	/**
	 * Initialise this CollisionData with given timeToCollision, collisionPoint, collisionType and collidingAgents
	 * 
	 * @param timeToCollision
	 * 			The given timeToCollision
	 * @param collisionPoint
	 * 			The given collisionPoint
	 * @param collisionType
	 * 			The given collisionType
	 * @param colliders
	 * 			The set of colliding entities
	 * @post | new.getTimeToCollision() == timeToCollision
	 * @post | new.getCollisionPoint() == collisionPoint
	 * @post | new.getCollisionType() == collisionType
	 * @post | new.getColliders() == colliders
	 * @throws IllegalArgumentException
	 * 		 | collisionType == CollisionType.BOUNDARY && colliders.size() != 1
	 * @throws IllegalArgumentException
	 * 		 | collisionType == CollisionType.INTER_ENTITY && colliders.size() != 2
	 * @throws IllegalArgumentException
	 * 		 | collisionType == CollisionType.INTER_SHIP && colliders.size() != 2
	 */
	public CollisionData(double timeToCollision, Vector2d collisionPoint, CollisionType collisionType, List<Entity> colliders) throws IllegalArgumentException{
		if(collisionType == CollisionType.BOUNDARY && colliders.size() != 1)
			throw new IllegalArgumentException();
		if(collisionType == CollisionType.INTER_ENTITY && colliders.size() != 2)
			throw new IllegalArgumentException();
		this.timeToCollision = timeToCollision;
		this.collisionPoint = collisionPoint;
		this.collisionType = collisionType;
		this.colliders = colliders;
	}
	
	public static final CollisionData UNDEFINED_COLLISION = new CollisionData(Double.POSITIVE_INFINITY, null , CollisionType.UNDEFINED, new ArrayList<Entity>());

	/**
	 * Return the timeToCollision of this CollisionData.
	 */
	@Basic
	@Raw
	@Immutable
	public double getTimeToCollision(){
		return this.timeToCollision;
	}

	/**
	 * Variable registering the timeToCollision of this CollisionData.
	 */
	private final double timeToCollision;
	
	/**
	 * Return the collisionPoint of this CollisionData.
	 */
	@Basic
	@Raw
	@Immutable
	public Vector2d getCollisionPoint(){
		return this.collisionPoint;
	}

	/**
	 * Variable registering the collisionPoint of this CollisionData.
	 */
	private final Vector2d collisionPoint;
	
	/**
	 * Return the collisionType of this CollisionData.
	 */
	@Basic
	@Raw
	@Immutable
	public CollisionType getCollisionType(){
		return this.collisionType;
	}

	/**
	 * Variable registering the collisionType of this CollisionData.
	 */
	private final CollisionType collisionType;
	
	/**
	 * Return the colliders of this CollisionData.
	 */
	@Basic
	@Raw
	@Immutable
	public List<Entity> getColliders(){
		return this.colliders;
	}

	/**
	 * Variable registering the colliders of this CollisionData
     * the list consists of a single element in case of a BOUNDARY collision.
	 */
	private final List<Entity> colliders;
	
	/**
	 * For a given colliding Entity return the other colliding Entity.
	 * 
	 * @param entity
	 * 		The given Entity
	 * @return The other entity
	 * 			| getColliders().get((getColliders().indexOf(entity) + 1) % 2);
	 * @throws IllegalArgumentException
	 * 			| !getColliders().contains(entity) || getColliders().size() == 1
	 */
	public Entity getOther(Entity entity) throws IllegalArgumentException{
		if(!getColliders().contains(entity) || getColliders().size() == 1)
			throw new IllegalArgumentException();
		return getColliders().get((getColliders().indexOf(entity) + 1) % 2);
	}
	
	/**
	 * Resolve this collision appropriatly.
	 * 
	 * @see implementation
	 */
	public void resolve(){
		getColliders().get(0).resolve(this);
	}
	
	/**
	 * Check whether this CollisionData is equal to the given object
	 * 
	 * @return | result == (other != null) && (this.getClass() == other.getClass()) 
	 * 		   | && this.getTimeToCollision() == (CollisionData other).getTimeToCollision() && (getCollisionPoint() == null && (CollisionData other).getCollisionPoint() == null || 
	 * 		   | getCollisionPoint().equals((CollisionData other).getCollisionPoint())) && getCollisionType().equals((CollisionData other).getCollisionType())
	 */
	@Override
	public boolean equals(Object other){
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		CollisionData otherData = (CollisionData) other;
		return getTimeToCollision() == otherData.getTimeToCollision() &&
				(getCollisionPoint() == null && otherData.getCollisionPoint() == null || 
				 getCollisionPoint().equals(otherData.getCollisionPoint())) &&
				getCollisionType().equals(otherData.getCollisionType());
	}

	/**
	 * Return the hash code for this CollisionData
	 */
	@Override
	public int hashCode(){
		return (int) (getTimeToCollision() * 31 + getCollisionPoint().hashCode() + getCollisionType().hashCode());
	}
}
