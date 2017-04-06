package asteroids.model;

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
	 * Initialise this CollisionData with given timeToCollision, collisionPoint and collisionType
	 * 
	 * @param timeToCollision
	 * 			The given timeToCollision
	 * @param collisionPoint
	 * 			The given collisionPoint
	 * @post | new.getTimeToCollision() == timeToCollision
	 * @post | new.getCollisionPoint() == collisionPoint
	 */
	public CollisionData(double timeToCollision, Vector2d collisionPoint, CollisionType collisionType){
		this.timeToCollision = timeToCollision;
		this.collisionPoint = collisionPoint;
		this.collisionType = collisionType;
	}
	
	public static final CollisionData UNDEFINED_COLLISION = new CollisionData(Double.POSITIVE_INFINITY, null, CollisionType.UNDEFINED);

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
	 * Check whether this CollisionData is equal to the given object
	 * @return | result == (other != null) && (this.getClass() == other.getClass()) 
	 * 		   | && this.getTimeToCollision() == (CollisionData other).getTimeToCollision() && this.getCollisionPoint() == (CollisionData other).getCollisionPoint()
	 */
	@Override
	public boolean equals(Object other){
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		CollisionData otherData = (CollisionData) other;
		return getTimeToCollision() == otherData.getTimeToCollision() &&
				getCollisionPoint() == otherData.getCollisionPoint() && getCollisionType() == otherData.getCollisionType();
	}

	/**
	 * Return the hash code for this CollisionData
	 */
	@Override
	public int hashCode(){
		return (int) (getTimeToCollision() * 31 + getCollisionPoint().hashCode() + getCollisionType().hashCode());
	}
}
