package asteroids.model;

import java.util.HashSet;
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
 *        | hasProperItems()
 * @post   This new Ship has no Bullets yet.
 *        | new.getNbItems() == 0
 */
public class Ship extends Entity implements Container<Entity>{

	/**
	 * Initialize this new Ship with given x, y, xVelocity, yVelocity, radius, mass, container and thrustForce
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
	 * @param radius
	 *          The radius for this new Ship.
	 * @param mass
	 * 			The mass for this new Ship.
	 * @param thrustForce
	 * 			The thrustForce for this new Ship.
	 * @param container
	 * 			The container for this new Ship.
	 * @effect This new Ship is initialized as a new Entity with
	 * 		   given x, y, xVelocity, yVelocity, radius and mass.
	 * 			| super(x, y, xVelocity, yVelocity, radius, mass, container)
	 * @post The orientation of this new Ship is equal to the given
	 *       orientation.
	 *    		| new.getOrientation() == orientation
	 * @effect The thrust force of this new Ship is set to the given thrustForce
	 * 			| setThrustForce(thrustForce)
	 * 
	 */
	public Ship(double x, double y, double xVelocity, double yVelocity, double orientation, double radius, double mass, Container<Entity> container, double thrustForce) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, mass, container);
		this.setOrientation(orientation);
		this.setThrustForce(thrustForce);
	}
	
	/**
	 * Initialize this new Ship with given x, y, xVelocity, yVelocity, radius and mass.
	 *
	 * @param x
	 *     		The x-position for this new Ship.
	 * @param y
	 *        	The y-position for this new Ship.
	 * @param xVelocity
	 * 			The x-velocity for this new Ship.
	 * @param yVelocity
	 *       	The y-velocity for this new Ship.
	 * @param radius
	 *          The radius for this new Ship.
	 * @param mass
	 * 			The mass for this new Ship.
	 * @effect This new Ship is initialized as a new Ship with given x, y, xVelocity,
	 * 		   yVelocity, radius, mass, no world and the default thrust force.
	 * 			| this(x, y, xVelocity, yVelocity, orientation, radius, mass, null, DEFAULT_THRUST_FORCE)
	 * 
	 */
	public Ship(double x, double y, double xVelocity, double yVelocity, double orientation, double radius, double mass, double thrustForce) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, mass, null);
		this.setOrientation(orientation);
		this.setThrustForce(thrustForce);
	}
	
	/**
	 * Initialize this new Ship with given x, y, xVelocity, yVelocity, radius and mass.
	 *
	 * @param x
	 *     		The x-position for this new Ship.
	 * @param y
	 *        	The y-position for this new Ship.
	 * @param xVelocity
	 * 			The x-velocity for this new Ship.
	 * @param yVelocity
	 *       	The y-velocity for this new Ship.
	 * @param radius
	 *          The radius for this new Ship.
	 * @param mass
	 * 			The mass for this new Ship.
	 * @effect This new Ship is initialized as a new Ship with given x, y, xVelocity,
	 * 		   yVelocity, radius, mass and the default thrust force.
	 * 			| this(x, y, xVelocity, yVelocity, orientation, radius, mass, DEFAULT_THRUST_FORCE)
	 * 
	 */
	public Ship(double x, double y, double xVelocity, double yVelocity, double orientation, double radius, double mass) throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, orientation, radius, mass, null, DEFAULT_THRUST_FORCE);
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
			return this.getThrustForce() / this.getMass();
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
	 * Variable registering the acceleration of this Ship.
	 */
	private double acceleration;

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
		assert getThrusterStatus(); // TODO

		if(getThrusterStatus()){
			acceleration = this.getAcceleration();
			Vector2d newVel = new Vector2d(getVelocity().getX() + acceleration * Math.cos(getOrientation()) * timeDelta,
										getVelocity().getY() + acceleration * Math.sin(getOrientation()) * timeDelta);

			if(!canHaveAsVelocity(newVel.getX(), newVel.getY()))
				newVel = getVelocity().normalize().mul(getMaxVelocity());

			setVelocity(newVel.getX(), newVel.getY());
		}
	}

	@Override
	public boolean isInBounds(Entity item){
		// TODO Auto-generated method stub
		return false;
	}

	@Override @Basic @Raw
	public boolean hasAsItem(@Raw Entity item){
		if(item instanceof Bullet)
			bullets.contains(item);
		return false;
	}
	
	/**
	 * Check whether this Ship can have the given Entity
	 * as one of its Bullets.
	 * 
	 * @param  item
	 *         The Entity to check.
	 * @return True if and only if the given Entity is effective
	 *         and an instance of a Bullet and that Bullet is a valid Bullet for a Ship.
	 *       | result == (item != null) && (item instanceof Bullet) && ((Bullet) item).canHaveAsContainer(this);
	 */
	@Override @Raw
	public boolean canHaveAsItem(Entity item){
		return item != null && item instanceof Bullet && ((Bullet) item).canHaveAsContainer(this);
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
	 *       |     then canHaveAsBullet(bullet) && (bullet.getShip() == this) 
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
	 * @post   This Ship has the given Bullet as one of its Bullets.
	 * 			| new.hasAsBullet(bullet)
	 * @throws IllegalArgumentException
	 * 		   The given Entity is ineffective or is not an instance of Bullet
	 * 		   or does not already reference this Ship as its container
	 * 			| (item == null) || !(item instanceof Bullet) || (Bullet item).getContainer() == this
	 */
	@Override
	public void addItem(Entity item) throws IllegalArgumentException{
		if(item == null || !(item instanceof Bullet) || item.getContainer() == this)
			throw new IllegalArgumentException();
		bullets.add((Bullet) item);
	}

	/**
	 * Remove the given item from the set of bullets of this Ship.
	 * 
	 * @param  item
	 *         The Entity to be removed.
	 * @pre    This Ship has the given Entity as one of
	 *         its bullets, and the given Bullet does not
	 *         reference any Ship.
	 *       | this.hasAsBullet(bullet) &&
	 *       | (bullet.getShip() == null)
	 * @post   This Ship no longer has the given Bullet as
	 *         one of its Bullets.
	 *       | ! new.hasAsBullet(bullet)
	 * @throws IllegalArgumentException
	 * 		   The Ship does not have the given Entity as one of its bullets
	 * 		   or is not an instance of Bullet or the given Entity still references
	 * 		   any Ship as its container.
	 * 			| !(item instanceof Bullet) || !this.hasAsItem(item) || item.getContainer() != null
	 */
	@Override @Raw
	public void removeItem(Entity item) throws IllegalArgumentException{
		if( !(item instanceof Bullet) || !this.hasAsItem(item) || item.getContainer() != null)
			throw new IllegalArgumentException();
		assert bullets.remove((Bullet)item);
	}
	
	
	@Override
	public int getNbItems(){
		return bullets.size();
	}
	
	/**
	 * Terminate this Ship.
	 *
	 * @post   This Ship  is terminated.
	 *       | new.isTerminated()
	 * @post   TODO...
	 *       | ...
	 */
	 public void terminate() {
		 this.isTerminated = true;
	 }


	/**
	 * Variable referencing a set collecting all the Bullets
	 * of this Ship.
	 * 
	 * @invar  The referenced set is effective.
	 *       | bullets != null
	 * @invar  Each Bullet registered in the referenced list is
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
}