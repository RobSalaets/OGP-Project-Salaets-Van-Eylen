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
	 * Initialize this new Bullet with given x, y, xVelocity, yVelocity, radius, container and maxBoundaryCollisions.
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
	 * @param container
	 * 			The container for this new Bullet.
	 * @param  maxBoundaryCollision
	 *         The maxBoundaryCollisions for this new Bullet.
	 * @effect This new Bullet is initialized as a new Entity with
	 * 		   given x, y, xVelocity, yVelocity, radius, container and mass
	 * 		   corresponding to the radius of this Bullet.
	 * 			| super(x, y, xVelocity, yVelocity, radius, 4.0/3.0*Math.PI*Math.pow(radius, 3)*BULLET_MASS_DENSITY, container)
	 * @pre    The given maxBoundaryCollisions must be a valid maxBoundaryCollisions for any Bullet.
	 *      	| isValidMaxBoundaryCollisions(maxBoundaryCollisions)
	 * @post   The maxBoundaryCollisions of this new Bullet is equal to the given
	 *         maxBoundaryCollisions.
	 *       	| new.getMaxBoundaryCollisions() == maxBoundaryCollision
	 * @post   If the given container is a Ship, the source of this bullet
	 * 			is set to the given container.
	 * 		 	| if(container instanceof Ship) then new.getSource() == container
	 * @throws AssertionError
	 * 			| !isValidMaxBoundaryCollisions(maxBoundaryCollisions)
	 */
	@Raw
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, Container<Entity> container, int maxBoundaryCollisions) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, 4.0/3.0*Math.PI*Math.pow(radius, 3)*BULLET_MASS_DENSITY, container);
		assert isValidMaxBoundaryCollisions(maxBoundaryCollisions);
		this.maxBoundaryCollision = maxBoundaryCollisions;
	}

	/**
	 * Initialize this new Bullet with given x, y, xVelocity, yVelocity, radius, container and mass.
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
	 * @param container
	 * 			The container for this new Bullet.
	 * @effect This new Bullet is initialized as a new Entity with
	 * 		   given x, y, xVelocity, yVelocity, radius and container,
	 * 		   with the default number of maxBoundaryCollisions
	 * 			| this(x, y, xVelocity, yVelocity, radius, container, DEFAULT_MAX_BOUNDARY_COLLISIONS)
	 */
	@Raw
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, Container<Entity> container) throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, container, DEFAULT_MAX_BOUNDARY_COLLISIONS);
	}
	
	/**
	 * Initialize this new Bullet with given x, y, xVelocity, yVelocity, radius and mass.
	 * The maxBoundaryCollisions is set to the constant DEFAULT_MAX_BOUNDARY_COLLISIONS.
	 * This bullet has no Container.
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
	 * @effect This new Bullet is initialized as a new Entity with
	 * 		   given x, y, xVelocity, yVelocity and radius,
	 * 		   no container and the default number of maxBoundaryCollisions.
	 * 			| this(x, y, xVelocity, yVelocity, radius, null, DEFAULT_MAX_BOUNDARY_COLLISIONS)
	 */
	@Raw
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, radius, null, DEFAULT_MAX_BOUNDARY_COLLISIONS);
	}

	/**
	 * The mininum radius for any Bullet in kilometres
	 */
	public static final double MIN_RADIUS = 1.0;
	
	/**
	 * The mass density for any Bullet in kilograms per cubic kilometre.
	 */
	public static final double BULLET_MASS_DENSITY = 7.8e12;
	
	/**
	 * Return the lowest possible massDensity for any Bullet.
	 * 
	 * @return 
	 * 			| BULLET_MASS_DENSITY
	 */
	public double getLowestMassDensity(){
		return BULLET_MASS_DENSITY;
	}
	
	/**
	 * Check whether this Bullet can have the given mass as its mass.
	 *  
	 * @param  mass
	 *         The mass to check.
	 * @return 
	 *       | result == mass == 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * BULLET_MASS_DENSITY
	*/
	@Raw
	public boolean canHaveAsMass(double mass){
		return mass == 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * BULLET_MASS_DENSITY;
	}

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
	 * Increment the boundaryCollisionCount of this Bullet.
	 * 
	 * @post | if(!canHaveAsBoundaryCollisionCount(getBoundaryCollisionCount())
	 * 		 | then new.isTerminated()
	 * 		 | else new.getBoundaryCollisionCount() == this.getBoundaryCollisionCount() + 1
	 */
	public void incrementBoundaryCollisionCount(){
		this.boundaryCollisionCount++;
		if(!canHaveAsBoundaryCollisionCount(getBoundaryCollisionCount()))
			this.terminate();
	}

	/**
	 * Reset the boundaryCollisionCount of this Bullet to 0.
	 * 
	 * @post | if(!isTerminated()) then getBoundaryCollisionCount() == 0
	 */
	public void resetBoundaryCollisionCount(){
		if(!isTerminated())
			this.boundaryCollisionCount = 0;
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
	public static final int DEFAULT_MAX_BOUNDARY_COLLISIONS = 3;
	
	/**
	 * Resolve given collision case appropriatly
	 * 
	 * @param collisionData
	 * 			The given collision case
	 * @effect 	| if(collisionData.getCollisionType() == CollisionType.BOUNDARY) 
	 * 			| then resolveBoundaryCollision(collisionData)
	 * @effect 	| if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY && 
	 * 			|		collisionData.getColliders().contains(getSource())) 
	 * 			| then getSource().loadBullet(this)
	 * @post 	| if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY && 
	 * 			|		!collisionData.getColliders().contains(getSource())) 
	 * 			| then for each entity in collisionData.getColliders():
	 * 			|			entity.isTerminated()
	 * @throws IllegalArgumentException
	 * 			| !(collisionData.getCollisionType() == CollisionType.BOUNDARY ||
	 * 			| 	collisionData.getCollisionType() == CollisionType.INTER_ENTITY)
	 */
	@Override
	public void resolve(CollisionData collisionData) throws IllegalArgumentException {
		if(collisionData.getCollisionType() == CollisionType.BOUNDARY)
			resolveBoundaryCollision(collisionData);
		else if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY){
			Entity other = collisionData.getOther(this);
			if(other instanceof Ship && other == getSource()){
				Container<Entity> old = getContainer();
				setContainer(null);
				old.removeItem(this);
				setPosition(other.getPosition().getX(), other.getPosition().getY());
				getSource().loadBullet(this);
			}else{
				this.terminate();
				other.terminate();
			}
		}else
			throw new IllegalArgumentException();
		
	}
	
	/**
	 * Resolve a given boundary collision case, by reflecting the velocity
	 * vector of this Entity on the bounds of its World.
	 * 
	 * @param collisionData
	 * 		The given collision case
	 * @effect 	| super.resolveBoundaryCollision(collisionData)
	 * @effect  | incrementBoundaryCollisionCount()
	 */
	@Override
	public void resolveBoundaryCollision(CollisionData collisionData) throws IllegalStateException, IllegalArgumentException{
		super.resolveBoundaryCollision(collisionData);
		incrementBoundaryCollisionCount();
	}
	
	/**
	 * Return the position of this Bullet
	 * If this bullet is loaded by a Ship the position
	 * of this bullet is equal to the position of the Ship
	 * @return 	| if(getContainer() instanceof Ship) 
	 * 			| then result.equals((Ship getContainer()).getPosition())
	 * 			| else result.equals(super.getPosition())
	 */
	@Override @Raw
	public Vector2d getPosition(){
		if(getContainer() instanceof Ship)
			return ((Ship) getContainer()).getPosition();
		return super.getPosition();
	}
	
	/**
	 * Return the velocity of this Bullet
	 * If this bullet is loaded by a Ship the velocity
	 * of this bullet is equal to the velocity of the Ship
	 * @return 	| if(getContainer() instanceof Ship) 
	 * 			| then result.equals((Ship getContainer()).getVelocity())
	 * 			| else result.equals(super.getVelocity())
	 */
	@Override @Raw
	public Vector2d getVelocity(){
		if(getContainer() instanceof Ship)
			return ((Ship) getContainer()).getVelocity();
		return super.getVelocity();
	}
	
	/**
	 * Set the container of this Entity to the given container.
	 * 
	 * @param  container
	 *         The new container for this Entity.
	 * @post   The container of this Entity is the same as the
	 *         given container.
	 *       | new.getContainer() == container
	 * @post   If the given container is a Ship, the source of this bullet
	 * 			is set to the given container.
	 * 		 | if(container instanceof Ship) then new.getSource() == container
	 * @throws IllegalArgumentException
	 *         This Entity cannot have the given container as its container.
	 *       | ! canHaveAsContainer(container)
	 */
	@Override @Raw
	public void setContainer(Container<Entity> container){
		super.setContainer(container);
		if(container instanceof Ship)
			this.source = (Ship) container;
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
	@Raw @Override
	public boolean canHaveAsContainer(Container<Entity> container){
		if(this.isTerminated())
			return container == null;
		return (container == null) || ((container instanceof World || container instanceof Ship) && (!container.isTerminated()));
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
		return source;
	}
	
	/**
	 * Variable referencing the source of this Bullet
	 */
	private Ship source;

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
	 *       |   then ! new.getContainer().hasAsItem(this))
	 * @post   If this Bullet was not yet terminated, the size of
	 *         the container to which this Bullet belonged is decremented by 1.
	 *       | if (! isTerminated())
	 *       |   then new.getContainer().getNbItems() ==
	 *       |            getContainer().getNbItems() - 1
	 */
	@Override
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
