package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A Class representing a Bullet in the game.
 * 
 * @invar  The maxBoundaryCollisions of each Bullet must be a valid maxBoundaryCollisions for any
 *         Bullet.
 *       | isValidMaxBoundaryCollisions(getMaxBoundaryCollisions())
 * @invar  The boundaryCollisionCount of each Bullet must be a valid boundaryCollisionCount for this
 *         Bullet.
 *       | canHaveAsBoundaryCollisionCount(getBoundaryCollisionCount())
 */
public class Bullet extends Entity{

	/**
	 * Initialize this new Bullet with given x, y, xVelocity, yVelocity, radius, mass and maxBoundaryCollision.
	 *
	 * @param x
	 *     		The x-position for this new Bullet.
	 * @param y
	 *        	The y-position for this new Bullet.
	 * @param xVelocity
	 * 			The x-velocity for this new Bullet.
	 * @param yVelocity
	 *       	The y-velocity for this new Bullet.
	 * @param radius
	 *          The radius for this new Bullet.
	 * @param mass
	 * 			The mass for this new Bullet.
	 * @param  maxBoundaryCollision
	 *         The maxBoundaryCollisions for this new Bullet.
	 * @effect This new Bullet is initialized as a new Entity with
	 * 		   given x, y, xVelocity, yVelocity, radius and mass.
	 * 			| super(x, y, xVelocity, yVelocity, radius, mass, container)
	 * @pre    The given maxBoundaryCollisions must be a valid maxBoundaryCollisions for any Bullet.
	 *      	| isValidMaxBoundaryCollisions(maxBoundaryCollisions)
	 * @post   The maxBoundaryCollisions of this new Bullet is equal to the given
	 *         maxBoundaryCollisions.
	 *       	| new.getMaxBoundaryCollisions() == maxBoundaryCollision
	 */
	@Raw
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass, Container<Entity> container, int maxBoundaryCollisions) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, mass, container);
		this.maxBoundaryCollision = maxBoundaryCollisions;
	}

	/**
	 * Initialize this new Bullet with given x, y, xVelocity, yVelocity, radius and mass.
	 * The maxBoundaryCollisions is set to the constant DEFAULT_MAX_BOUNDARY_COLLISIONS.
	 *
	 * @param x
	 *     		The x-position for this new Bullet.
	 * @param y
	 *        	The y-position for this new Bullet.
	 * @param xVelocity
	 * 			The x-velocity for this new Bullet.
	 * @param yVelocity
	 *       	The y-velocity for this new Bullet.
	 * @param radius
	 *          The radius for this new Bullet.
	 * @param mass
	 * 			The mass for this new Bullet.
	 * @effect This new Bullet is initialized as a new Entity with
	 * 		   given x, y, xVelocity, yVelocity, radius and mass.
	 * 			| this(x, y, xVelocity, yVelocity, radius, mass, container, DEFAULT_MAX_BOUNDARY_COLLISIONS)
	 */
	@Raw
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass, Container<Entity> container) throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, mass, container, DEFAULT_MAX_BOUNDARY_COLLISIONS);
	}

	/**
	 * The mininum radius for any Bullet in kilometres
	 */
	private static final double MIN_RADIUS = 1.0;

	@Basic
	@Override
	public double getMinRadius(){
		return MIN_RADIUS;
	}

	/**
	 * Return the boundaryCollisionCount of this Bullet.
	 */
	@Basic
	@Raw
	public int getBoundaryCollisionCount(){
		return this.boundaryCollisionCount;
	}

	/**
	 * Check whether the given boundaryCollisionCount is a valid boundaryCollisionCount for
	 * this Bullet.
	 *  
	 * @param  boundaryCollisionCount
	 *         The boundaryCollisionCount to check.
	 * @see implementation
	*/
	public boolean canHaveAsBoundaryCollisionCount(int boundaryCollisionCount){
		return boundaryCollisionCount >= 0 && boundaryCollisionCount < getMaxBoundaryCollisions();
	}

	/**
	 * Increment the boundaryCollisionCount of this Bullet
	 * 
	 * @post | if(!canHaveAsBoundaryCollisionCount(getBoundaryCollisionCount())
	 * 		 | then new.isTerminated()
	 */
	public void incrementBoundaryCollisionCount(){
		this.boundaryCollisionCount += 1;
		if(!canHaveAsBoundaryCollisionCount(getBoundaryCollisionCount()))
			this.terminate();
	}

	/**
	 * Variable registering the boundaryCollisionCount of this Bullet.
	 */
	private int boundaryCollisionCount;

	/**
	 * Return the maxBoundaryCollisions of this Bullet.
	 */
	@Basic
	@Raw
	public int getMaxBoundaryCollisions(){
		return this.maxBoundaryCollision;
	}

	/**
	 * Check whether the given maxBoundaryCollisions is a valid maxBoundaryCollisions for
	 * any Bullet.
	 *  
	 * @param  maxBoundaryCollisions
	 *         The maxBoundaryCollisions to check.
	 * @see implementation
	*/
	public static boolean isValidMaxBoundaryCollisions(int maxBoundaryCollision){
		return maxBoundaryCollision >= 0;
	}

	/**
	 * Variable registering the maximum number of boundary collisions of this Bullet.
	 */
	private final int maxBoundaryCollision;

	/**
	 * Constant registering the default number of maximum boundary collisions of any Bullet.
	 */
	private static final int DEFAULT_MAX_BOUNDARY_COLLISIONS = 3;
	
	/**
	 * Return the position of this Bullet
	 * If this bullet is loaded by a Ship the position
	 * of this bullet is equal to the position of the Ship
	 * @return 	| if(getContainer() instanceof Ship) 
	 * 			| then result.equals((Ship getContainer()).getPosition())
	 */
	@Override @Raw
	public Vector2d getPosition(){
		if(getContainer() instanceof Ship)
			return ((Ship) getContainer()).getPosition();
		return super.getPosition();
	}

	/**
	 * Check whether this Bullet can have the given container as
	 * its container.
	 * 
	 * @param  container
	 * 		   The container to check.
	 * @return If this Bullet is terminated, true if and only if the
	 *         given Container is not effective.
	 *       | if (this.isTerminated())
	 *       |   then result == (container == null)
	 * @return If this Bullet is not terminated, true if and only if the given
	 *         Container is not effective or an instance of Ship or World and not yet terminated.
	 *       | if (! this.isTerminated())
	 *       |   then result == (container == null) || ((container instanceof World) 
	 *       |							&& (!container.isTerminatedContainer()))
	 */
	@Raw
	public boolean canHaveAsContainer(Container<Entity> container){
		if(this.isTerminated())
			return container == null;
		return (container == null) || ((container instanceof World || container instanceof Ship) && (!container.isTerminatedContainer()));
	}

	/**
	 * Return the source of this Bullet if any exists.
	 *  
	 * @return Return the source of this Bullet if it has been fired by a Ship
	 *         or is attached to a Ship. Otherwise this method returns null.
	 * 		| if(getContainer() instanceof Ship) then result == (Ship getContainer())
	 * 		| else result == null
	 */
	public Ship getSource(){
		if(getContainer() instanceof Ship)
			return (Ship) getContainer();
		return null;
	}

	/**
	 * Terminate this Bullet.
	 *
	 * @post   This Bullet  is terminated.
	 *       | new.isTerminated()
	 * @post   This Bullet no longer references an effective container.
	 *       | new.getContainer() == null
	 * @post   If this Bullet was not yet terminated, this Bullet
	 *         is no longer one of the Entities for the Container to which
	 *         this Bullet belonged.
	 *       | if (! isTerminated())
	 *       |   then ! (new getContainer()).hasAsItem(this))
	 * @post   If this Bullet was not yet terminated, the size of
	 *         the container to which this Bullet belonged is decremented by 1.
	 *       | if (! isTerminated())
	 *       |   then (new getContainer()).getNbItems() ==
	 *       |            getContainer().getNbItems() - 1
	 */
	public void terminate(){
		if(!isTerminated()){
			Container<Entity> oldContainer = getContainer();
			if(oldContainer != null){
				setContainer(null);
				oldContainer.removeItem(this);
			}
			this.isTerminated = true;
		}
	}
}
