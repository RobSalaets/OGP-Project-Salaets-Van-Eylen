package asteroids.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A Class to represent a Ship in the game
 * 
 * @invar  The orientation of each Ship must be a valid orientation for any Ship.
 *       	| isValidOrientation(getOrientation())
 * @invar  The thrustForce of each Ship must be a valid thrustForce for any Ship.
 *       	| isValidThrustForce(getThrustForce())
 * @invar   Each Ship must have proper Bullets.
 *          | hasProperItems()
 */
public class Ship extends Entity implements Container<Entity>{

	/**
	 * Initialize this new Ship with given x, y, xVelocity, yVelocity, orientation, radius, mass, container and thrustForce
	 * with no Bullets yet.
	 *
	 * @param x
	 *     		The x-position for this new Ship.
	 * @param y
	 *        	The y-position for this new Ship.
	 * @param xVelocity
	 * 			The x-velocity for this new Ship.
	 * @param yVelocity
	 *       	The y-velocity for this new Ship.
	 * @param orientation
	 * 			The orientation for this new S
	 * @param radius
	 *          The radius for this new Ship.
	 * @param mass
	 * 			The mass for this new Ship.
	 * @param thrustForce
	 * 			The thrustForce for this new Ship.
	 * @param container
	 * 			The world for this new Ship.
	 * @effect This new Ship is initialized as a new Entity with
	 * 		   given x, y, xVelocity, yVelocity, radius and mass.
	 * 			| super(x, y, xVelocity, yVelocity, radius, mass, container)
	 * @post The orientation of this new Ship is equal to the given
	 *       orientation.
	 *    		| new.getOrientation() == orientation
	 * @effect The thrust force of this new Ship is set to the given thrustForce
	 * 			| setThrustForce(thrustForce)
	 * @post   This new Ship has no bullets yet.
	 *      	| new.getNbItems() == 0
	 */
	@Raw
	public Ship(double x, double y, double xVelocity, double yVelocity, double orientation, double radius, double mass, Container<Entity> container, double thrustForce) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, mass, container);
		this.setOrientation(orientation);
		this.setThrustForce(thrustForce);
	}

	/**
	 * Initialize this new Ship with given x, y, xVelocity, yVelocity, radius, orientation, mass and thrustForce.
	 *
	 * @param x
	 *     		The x-position for this new Ship.
	 * @param y
	 *        	The y-position for this new Ship.
	 * @param xVelocity
	 * 			The x-velocity for this new Ship.
	 * @param yVelocity
	 *       	The y-velocity for this new Ship.
	 * @param orientation
	 * 			The orientation for this new Ship
	 * @param radius
	 *          The radius for this new Ship.
	 * @param mass
	 * 			The mass for this new Ship.
	 * @param thrustForce
	 * 			The thrustForce for this new Ship.
	 * @effect This new Ship is initialized as a new Ship with given x, y, xVelocity,
	 * 		   yVelocity, radius, mass, no world and the default thrust force.
	 * 			| this(x, y, xVelocity, yVelocity, orientation, radius, mass, null, thrustForce)
	 * 
	 */
	@Raw
	public Ship(double x, double y, double xVelocity, double yVelocity, double orientation, double radius, double mass, double thrustForce) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, mass, null);
		this.setOrientation(orientation);
		this.setThrustForce(thrustForce);
	}

	/**
	 * Initialize this new Ship with given x, y, xVelocity, yVelocity, orientation, radius and mass.
	 *
	 * @param x
	 *     		The x-position for this new Ship.
	 * @param y
	 *        	The y-position for this new Ship.
	 * @param xVelocity
	 * 			The x-velocity for this new Ship.
	 * @param yVelocity
	 *       	The y-velocity for this new Ship.
	 * @param orientation
	 * 			The orientation for this new S
	 * @param radius
	 *          The radius for this new Ship.
	 * @param mass
	 * 			The mass for this new Ship.
	 * @effect This new Ship is initialized as a new Ship with given x, y, xVelocity,
	 * 		   yVelocity, radius, mass and the default thrust force.
	 * 			| this(x, y, xVelocity, yVelocity, orientation, radius, mass, DEFAULT_THRUST_FORCE)
	 * 
	 */
	@Raw
	public Ship(double x, double y, double xVelocity, double yVelocity, double orientation, double radius, double mass) throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, orientation, radius, mass, DEFAULT_THRUST_FORCE);
	}

	/**
	 * Return the orientation of this Ship.
	 */
	@Basic
	@Raw
	public double getOrientation(){
		return this.orientation;
	}

	/**
	 * Check whether the given orientation is a valid orientation for
	 * any Ship.
	 *  
	 * @param orientation
	 *         The orientation to check.
	 * @return | result == (0 <= orientation && orientation < 2*PI)
	*/
	public static boolean isValidOrientation(double orientation){
		return 0 <= orientation && orientation < 2 * Math.PI;
	}

	/**
	 * Set the orientation of this Ship to the given orientation.
	 * 
	 * @param  orientation
	 *         The new orientation for this Ship.
	 * @pre    The given orientation must be a valid orientation for any
	 *         Ship.
	 *       | isValidOrientation(orientation)
	 * @post   The orientation of this Ship is equal to the given
	 *         orientation.
	 *       | new.getOrientation() == orientation
	 */
	@Raw
	public void setOrientation(double orientation){
		assert isValidOrientation(orientation);
		this.orientation = orientation;
	}

	/**
	 * Variable registering the orientation of this Ship as an angle in radians.
	 */
	private double orientation;

	/**
	 * The mininum radius for any Ship in kilometres
	 */
	private static final double MIN_RADIUS = 10.0;

	@Basic
	@Override
	public double getMinRadius(){
		return MIN_RADIUS;
	}
	
	/**
	 * Compute the total mass of this Ship with respect
	 * to the bullets carried by this Ship
	 * @return The sum of the mass of the Ship itself and mass of
	 * 		   each bullet it carries
	 * 		| result == getMass() + sum(bullet.getMass() for each bullet in bullets)
	 */
	public double getTotalMass(){
		double mass = getMass();
		for(Bullet bullet : bullets)
			mass += bullet.getMass();
		return mass;
	}

	/**
	 * Check whether this Ship can have the given container as
	 * its container.
	 * 
	 * @param  container
	 * 		   The container to check.
	 * @return If this Ship is terminated, true if and only if the
	 *         given Container is not effective.
	 *       | if (this.isTerminated())
	 *       |   then result == (container == null)
	 * @return If this Ship is not terminated, true if and only if the given
	 *         Container is not effective or an instance of World and not yet terminated.
	 *       | if (! this.isTerminated())
	 *       |   then result == (container == null) && ((container instanceof World) && (!container.isTerminatedContainer()))
	 */
	@Raw
	public boolean canHaveAsContainer(Container<Entity> container){
		if(this.isTerminated())
			return container == null;
		return (container == null) ||  ((container instanceof World) && (!container.isTerminatedContainer()));
	}

	/**
	 * Return the thrustForce of this Ship.
	 */
	@Basic
	@Raw
	public double getThrustForce(){
		return this.thrustForce;
	}

	/**
	 * Check whether the given thrustForce is a valid thrustForce for
	 * any Ship.
	 *  
	 * @param  thrustForce
	 *         The thrustForce to check.
	 * @return 
	 *       | result == Double.isFinite(thrustForce) && thrustForce > 0
	*/
	public static boolean isValidThrustForce(double thrustForce){
		return Double.isFinite(thrustForce) && thrustForce > 0;
	}

	/**
	 * Set the thrustForce of this Ship to the given thrustForce.
	 * 
	 * @param  thrustForce
	 *         The new thrustForce for this Ship.
	 * @post   The thrustForce of this new Ship is equal to the given thrustForce. 
	 * 			| new.getThrustForce() == thrustForce
	 * @throws IllegalArgumentException
	 *         The given thrustForce is not a valid thrustForce for any Ship. 
	 *         	| !isValidThrustForce(thrustForce)
	 */
	@Raw
	public void setThrustForce(double thrustForce) throws IllegalArgumentException{
		if(!isValidThrustForce(thrustForce))
			throw new IllegalArgumentException();
		this.thrustForce = thrustForce;
	}

	/**
	 * Variable registering the thrust force of this Ship.
	 */
	private double thrustForce;

	/**
	 * Static constant variable registering the default thrust force of this Ship.
	 */
	public static final double DEFAULT_THRUST_FORCE = 1.1 * Math.pow(10, 21);

	/**
	 * Return the acceleration of this ship.
	 *		
	 * @post For an active thruster the Ships acceleration
	 *		 is equal to thrustForce / mass
	 *		| if (getThrusterStatus())
	 *		| then new.getAcceleration() == this.getThrustForce() / this.getMass()
	 * @post For an inactive thruster the Ships acceleration
	 *		 is equal to zero.
	 *		| new.getAcceleration() == 0.0
	 */
	@Basic
	public double getAcceleration(){
		if(getThrusterStatus())
			return this.getThrustForce() / this.getTotalMass();
		return 0.0;
	}

	/**
	 * Variable registering whether or not the thruster of the ship is active.
	 */
	private boolean thrusterStatus;

	/**
	 * Enables this Ship's thruster
	 * 
	 * @post The thrusterStatus of this Ship is true
	 * 		| new.getThrusterStatus() == true
	 */
	public void thrustOn(){
		thrusterStatus = true;
	}

	/**
	 * Disables this Ship's thruster
	 * 
	 * @post The thrusterStatus of this Ship is false
	 * 		| new.getThrusterStatus() == false
	 */
	public void thrustOff(){
		thrusterStatus = false;
	}

	/**
	 * Return the thruster status of this Ship.
	 */
	@Basic
	public boolean getThrusterStatus(){
		return thrusterStatus;
	}

	/**
	 * Turn the Ship with a given angle, by adding the angle
	 * to the current orientation.
	 * 
	 * @param  angle
	 *      	The angle in radians to turn the Ship.
	 * @effect The new orientation is the sum of given angle and the unchanged orientation
	 * 			| setOrientation(getOrientation() + angle);
	 */
	public void turn(double angle){
		setOrientation(getOrientation() + angle);
	}

	/**
	 * Adjust the velocity of this Ship to the current orientation, with its current acceleration
	 * and a given timeDelta.
	 * @post  For an active thruster and a positive acceleration, the Ship's velocity is updated
	 * 	  	  with respect to the current orientation and given timeDelta.
	 * 			| if (getThrusterStatus())
	 *			| then new.getVelocity().equals(new Vector2d(getVelocity().getX() + getAcceleration() * Math.cos(getOrientation()) * timeDelta,
	 *														 getVelocity().getY() + getAcceleration() * Math.sin(getOrientation()) * timeDelta))
	 * @post  For an acceleration that results in a velocity exceeding the maximum velocity, 
	 * 		  the velocity direction remains unchanged and the total velocity
	 * 		  is set to the maximum velocity.
	 * 			| if(!canHaveAsVelocity(getVelocity().getX() + getAcceleration() * Math.cos(getOrientation()) * timeDelta,
	 *			|						getVelocity().getY() + getAcceleration() * Math.sin(getOrientation()) * timeDelta)
	 * 			| then new.getVelocity().getLength() == getMaxVelocity()
	 */
	public void thrust(double timeDelta){
		assert getThrusterStatus();

		if(getThrusterStatus()){
			double acceleration = getAcceleration();
			Vector2d newVel = new Vector2d(getVelocity().getX() + acceleration * Math.cos(getOrientation()) * timeDelta, getVelocity().getY() + acceleration * Math.sin(getOrientation()) * timeDelta);

			if(!canHaveAsVelocity(newVel.getX(), newVel.getY()))
				newVel = getVelocity().normalize().mul(getMaxVelocity());

			setVelocity(newVel.getX(), newVel.getY());
		}
	}

	/**
	 * Return whether or not an Entity with given position and radius is within bounds of this Ship
	 * @see implementation
	 */
	@Override
	public boolean isInBounds(Vector2d position, double radius){
		return getRadius() - position.sub(getPosition()).getLength() > 0.99 * radius;
	}
  
	public boolean hasAsItem(@Raw Entity item){
		return bullets.contains(item);
	}

	/**
	 * Check whether this Ship can have the given Entity
	 * as one of its Bullets.
	 * 
	 * @param  item
	 *         The Entity to check.
	 * @return True if and only if the given Entity is effective
	 *         and an instance of a Bullet and that Bullet is a valid Bullet for a Ship and 
	 *         that the Bullet lies within the Ship.
	 *       | result == (item != null) && (item instanceof Bullet) && ((Bullet) item).canHaveAsContainer(this)
	 *       | && isInBounds(item.getPosition(), item.getRadius()) && ( ! item.getContainer instanceof World ||
	 *       | item.getContainer() == this.getContainer())
	 *
	 */
	@Override
	@Raw
	public boolean canHaveAsItem(Entity item){
		if(item.getContainer() instanceof World && this.getContainer() != item.getContainer())
			return false;
		return item != null && item instanceof Bullet && ((Bullet) item).canHaveAsContainer(this) && isInBounds(item.getPosition(), item.getRadius());
	}

	/**
	 * Check whether this Ship has proper Bullets attached to it.
	 * 
	 * @return True if and only if this Ship can have each of the
	 *         Bullets attached to it as one of its Bullets,
	 *         and if each of these Bullets references this Ship as
	 *         the Ship to which they are attached.
	 *       | for each bullet in bullets:
	 *       |   if (hasAsItem(bullet))
	 *       |     then canHaveAsItem(bullet) && (bullet.getContainer() == this) 
	 */
	@Override
	public boolean hasProperItems(){
		for(Bullet bullet : bullets){
			if(!canHaveAsItem(bullet))
				return false;
			if(bullet.getContainer() != this)
				return false;
		}
		return true;
	}

	/**
	 * Add the given item to the set of Bullets of this Ship.
	 * 
	 * @param  item
	 *         The Entity to be added.
	 * @post   This Ship has the given Entity as one of its Bullets.
	 * 			| new.hasAsItem(item)
	 * @throws IllegalArgumentException
	 * 		   The given Entity is cannot be a bullet of this Ships bullets
	 * 		   or does not reference this Ship as its container.
	 * 			| !canHaveAsItem(item) || (Bullet item).getContainer() != this
	 */
	@Override
	public void addItem(Entity item) throws IllegalArgumentException{
		if(!canHaveAsItem(item) || item.getContainer() != this)
			throw new IllegalArgumentException();
		bullets.add((Bullet) item);
	}

	/**
	 * Remove the given item from the set of bullets of this Ship.
	 * 
	 * @param  item
	 *         The Entity to be removed.
	 * @post   This Ship no longer has the given Entity as
	 *         one of its bullets.
	 *       | ! new.hasAsItem(item)
	 * @throws IllegalArgumentException
	 * 		   The Ship does not have the given Entity as one of its bullets
	 * 		   or is not an instance of Bullet or the given Entity still references
	 * 		   any Ship as its container.
	 * 			| !(item instanceof Bullet) || !this.hasAsItem(item) || item.getContainer() != null
	 */
	@Override
	@Raw
	public void removeItem(Entity item) throws IllegalArgumentException{
		if(!(item instanceof Bullet) || !this.hasAsItem(item) || item.getContainer() != null)
			throw new IllegalArgumentException();
		bullets.remove((Bullet)item);
	}

	@Override
	@Basic
	@Raw
	public int getNbItems(){
		return bullets.size();
	}

	/**
	 * Terminate this Ship.
	 *
	 * @post   This Ship  is terminated.
	 *       | new.isTerminated()
	 * @post   This Ship no longer references an effective container.
	 *       | new.getContainer() == null
	 * @post   If this Ship was not yet terminated, this Ship
	 *         is no longer one of the Entities for the Container to which
	 *         this Ship belonged.
	 *       | if (! isTerminated())
	 *       |   then ! (new getContainer()).hasAsItem(this))
	 * @post   If this Ship was not yet terminated, the size of
	 *         the container to which this Ship belonged is decremented by 1.
	 *       | if (! isTerminated())
	 *       |   then (new getContainer()).getNbItems() ==
	 *       |            getContainer().getNbItems() - 1
	 * @post   If this Ship was not terminated, each Bullet that belonged to this Ship
	 * 		   now has no container.
	 * 		 | if (! isTerminated())
	 *       |   then for each bullet in bullets: 
	 *       		(new bullet).getContainer() == null
	 * @post   If this Ship was not yet terminated, the set of bullets of this Ship
	 * 		   is now an empty set.
	 * 		 | if(! isTerminated())
	 * 		 |	 then new.getNbItems() == 0
	 */
	public void terminate(){
		if(!isTerminated()){
			Container<Entity> oldContainer = getContainer();
			if(oldContainer != null){
				setContainer(null);
				oldContainer.removeItem(this);
			}
			for(Bullet bullet : new ArrayList<Bullet>(bullets)){
				bullet.setContainer(null);
				removeItem(bullet);	
				bullet.terminate();
			}
			this.isTerminated = true;
		}
	}
	
	/**
	 * Return a set of all bullets loaded on this Ship.
	 * 
	 * @return The size of the resulting set is equal to the number of
	 *         bullets of this Ship.
	 *       | result.size() == getNbEntities()
	 */
	@Basic
	public Set<Bullet> getBullets(){
		return new HashSet<Bullet>(bullets);
	}

	/**
	 * Variable referencing a set collecting all the Bullets
	 * of this Ship.
	 * 
	 * @invar  The referenced set is effective.
	 *       | bullets != null
	 * @invar  Each Bullet registered in the referenced set is
	 *         effective and not yet terminated.
	 *       | for each bullet in bullets:
	 *       |   ( (bullet != null) &&
	 *       |     (! bullet.isTerminated()) )
	 */
	private final Set<Bullet> bullets = new HashSet<Bullet>();

	@Basic
	@Raw
	@Override
	public boolean isTerminatedContainer(){
		return this.isTerminated;
	}
	
	/**
	 * The initial bulletspeed for any bullet in kilometres per second.
	 */
	private static final double INITIAL_BULLETSPEED = 250.0;
	
	/**
	 * 
	 * @param bullets
	 * 		An array of the bullets that need to be loaded on this Ship.
	 * @post The number of bullets of this ship is incremented
	 *         by the given amount of bullets.
	 *       	| new.getNbItems() == getNbItems() + bullets.length
	 * @effect Each valid bullet is added to this Ship container.
	 * 			| for each bullet in bullets:
	 * 			| 	this.addItem(bullet)
	 * @throws NullPointerException
	 * 			| bullets.length == 0
	 * @throws IllegalArgumentExcetion
	 * 			If any of the given bullets can not be an item of this Ship
	 * 			| for some bullet in bullets:
	 * 			| 	!canHaveAsItem(bullet)
	 */
	public void loadBullet(Bullet... bullets) throws NullPointerException, IllegalArgumentException{
		if(bullets.length == 0)
			throw new NullPointerException();
		for (Bullet bullet : bullets){
			if(bullet.getContainer() != null){
				Container<Entity> old = bullet.getContainer();
				bullet.setContainer(null);
				old.removeItem(bullet);
			}
			bullet.setContainer(this);
			addItem(bullet);
		}
	}
	
	/**
	 * Fire a bullet from the bullets of this Ship into the World that contains this Ship.
	 * 
	 * @post If the container of this Ship is a World, the ship has available bullets,
	 *       the new position of the bullet is within the boundaries of the world
	 * 		 and the bullet doesn't overlap with any entities from this world at its new position,
	 * 		 then the bullet will be added and will be given the INITIAL_BULLETSPEED as its velocity
	 * 		 with respect to this ships current orientation.
	 * 		| if((getContainer() instanceof World) && (bullets.size() > 0) &&
	 * 		|		???
	 * 		| then bullet.getContainer() == this.getContainer() &&
	 *		| new.getContainer().hasAsItem(bullet) &&
	 *		| !this.hasAsItem(bullet) &&
	 *		| bullet.getPosition() == (this.getPosition().getX() + (this.getRadius()+bullet.getRadius()) * Math.cos(this.getOrientation()),
	 *		| 	this.getPosition().getY() + (this.getRadius()+bullet.getRadius()) * Math.sin(this.getOrientation()) ) &&
	 *		| bullet.getVelocity() == 
	 *		| 	(INITIAL_BULLETSPEED * Math.cos(this.getOrientation()), INITIAL_BULLETSPEED * Math.sin(this.getOrientation()))
	 * @post If the new position of the bullet will be outside the world boundaries
	 * 		 then the bullet shall be terminated.
	 *		| 	if ((this.getContainer() == null) || 
	 *		| 		!((World) getContainer()).isInBounds(bullet.getPosition(),bullet.getRadius()))
	 *		| 	then (new bullet).isTerminated()
	 */
	public void fireBullet(){
		if(!(getContainer() instanceof World) || bullets.size() == 0)
			return;
		World world = (World) getContainer();
		Bullet bullet = bullets.iterator().next();
		Vector2d newPosition = new Vector2d(this.getPosition().getX() + (this.getRadius()+bullet.getRadius()) * Math.cos(this.getOrientation()),
											this.getPosition().getY() + (this.getRadius()+bullet.getRadius()) * Math.sin(this.getOrientation()));
		
		bullet.setContainer(null);
		this.removeItem(bullet);
		if(!world.isInBounds(newPosition, bullet.getRadius())){
			bullet.terminate();
			return;
		}
		bullet.setPosition(newPosition.getX(), newPosition.getY());
		bullet.setVelocity(INITIAL_BULLETSPEED * Math.cos(getOrientation()), INITIAL_BULLETSPEED * Math.sin(getOrientation()));
		
		bullet.setContainer(world);
		List<Entity> overlapping = world.overlapsWithAnyEntity(bullet);
		bullet.setContainer(null);
		
		if (overlapping.size() > 0){
			for(Entity e : overlapping)
				world.resolve(new CollisionData(0.0, null, CollisionType.INTER_ENTITY, Arrays.asList(new Entity[]{bullet, e})));
			return;
		}else{
			bullet.setContainer(world);
			world.addItem(bullet);			
		}
	}
}