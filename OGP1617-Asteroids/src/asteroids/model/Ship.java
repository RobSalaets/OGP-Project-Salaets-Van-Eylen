package asteroids.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import asteroids.model.Program;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.ExpressionEvaluationException;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
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
	public static final double MIN_RADIUS = 10.0;

	@Basic
	@Override
	public double getMinRadius(){
		return MIN_RADIUS;
	}
	
	/**
	 * Return the lowest possible massDensity for any Ship.
	 * 
	 * @return 
	 * 			| 1.42 * Math.pow(10, 12)
	 */
	public double getLowestMassDensity(){
		return 1.42 * Math.pow(10, 12);
	}
	
	/**
	 * Check whether this Ship can have the given mass as its mass.
	 *  
	 * @param  mass
	 *         The mass to check.
	 * @return 
	 *       | result == mass > getLowestMass()
	*/
	@Raw
	public boolean canHaveAsMass(double mass){
		return mass > getLowestMass();
	}

	/**
	 * Return the lowest possible mass for this Ship.
	 * 
	 * @return 
	 * 			| 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * getLowestMassDensity()
	 */
	@Model
	protected double getLowestMass(){
		return 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * getLowestMassDensity();
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
		return (container == null) ||  ((container instanceof World) && (!container.isTerminated()));
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
	 * Static constant variable registering the default thrust force of this Ship in kg * km / s^2.
	 */
	public static final double DEFAULT_THRUST_FORCE = 1.1 * Math.pow(10, 18);

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
	 * @post  For an active thruster and a positive acceleration and a positive timeDelta, the Ship's velocity is updated
	 * 	  	  with respect to the current orientation and given timeDelta.
	 * 			| if (getThrusterStatus() && timeDelta > 0)
	 *			| then new.getVelocity().equals(new Vector2d(getVelocity().getX() + getAcceleration() * Math.cos(getOrientation()) * timeDelta,
	 *														 getVelocity().getY() + getAcceleration() * Math.sin(getOrientation()) * timeDelta))
	 * @post  For an acceleration that results in a velocity exceeding the maximum velocity and a positive timeDelta, 
	 * 		  the velocity direction remains unchanged and the total velocity
	 * 		  is set to the maximum velocity.
	 * 			| if(getThrusterState() && timeDelta > 0 &&
	 * 			|	!canHaveAsVelocity(getVelocity().getX() + getAcceleration() * Math.cos(getOrientation()) * timeDelta,
	 *			|						getVelocity().getY() + getAcceleration() * Math.sin(getOrientation()) * timeDelta)
	 * 			| then new.getVelocity().getLength() == getMaxVelocity()
	 */
	public void thrust(double timeDelta){
		assert getThrusterStatus();
		if(timeDelta <= 0.0)
			return;
		if(getThrusterStatus()){
			double acceleration = getAcceleration();
			Vector2d newVel = new Vector2d(getVelocity().getX() + acceleration * Math.cos(getOrientation()) * timeDelta, getVelocity().getY() + acceleration * Math.sin(getOrientation()) * timeDelta);

			if(!canHaveAsVelocity(newVel.getX(), newVel.getY()))
				newVel = getVelocity().normalize().mul(getMaxVelocity());

			setVelocity(newVel.getX(), newVel.getY());
		}
	}
	
	/**
	 * Resolve given collision case appropriatly
	 * 
	 * @param collisionData
	 * 			The given collision case
	 * @effect 	If the collisionType is BOUNDARY the boundary collision is resolved
	 * 			| if(collisionData.getCollisionType() == CollisionType.BOUNDARY) 
	 * 			| then resolveBoundaryCollision(collisionData)
	 * @effect 	If the collisionType is INTER_ENTITY and the other entity is a Ship,
	 * 			the collision will be handled so the entities bounce off each other.
	 * 			| if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY && 
	 * 			|	 collisionData.getOther(this) instanceof Ship) 
	 * 			| then resolveBounceCollision(collisionData.getOther(this), getTotalMass(),
	 * 			|								 collisionData.getOther(this).getTotalMass())
	 * @effect  If the collisionType is INTER_ENTITY and the other entity is an Asteroid,
	 * 			the collision will be handled so this Ship is terminated.
	 * 			| if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY && 
	 * 			|	 collisionData.getOther(this) instanceof Asteroid) 
	 * 			| then this.terminate()
	 * @effect  If the collisionType is INTER_ENTITY and the other entity is a Planetoid,
	 * 			the collision will be handled so this Ship is teleported.
	 * 			| if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY && 
	 * 			|	 collisionData.getOther(this) instanceof Planetoid) 
	 * 			| then teleport()
	 * @effect 	If the collisionType is INTER_ENTITY and the other entity is not a Ship, Asteroid
	 *  		or Planetoid the collision will be resolved by the other entity.
	 * 			| if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY && 
	 * 			|		!(collisionData.getOther(this) instanceof Ship) &&
	 * 			|		!(collisionData.getOther(this) instanceof Asteroid) &&
	 * 			|		!(collisionData.getOther(this) instanceof Planetoid)) 
	 * 			| then collisionData.getOther(this).resolve(collisionData)
	 * @throws IllegalArgumentException
	 * 			| !(collisionData.getCollisionType() == CollisionType.BOUNDARY ||
	 * 			| 	collisionData.getCollisionType() == CollisionType.INTER_ENTITY)
	 */
	@Override
	public void resolve(CollisionData collisionData) throws IllegalArgumentException{
		if(collisionData.getCollisionType() == CollisionType.BOUNDARY){
			resolveBoundaryCollision(collisionData);
		}else if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY){
			Entity other = collisionData.getOther(this);
			if(other instanceof Ship)
				resolveBounceCollision(other, getTotalMass(), ((Ship) other).getTotalMass());
			else if(other instanceof Asteroid)
				this.terminate();
			else if(other instanceof Planetoid)
				teleport();
			else
				other.resolve(collisionData);
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * A method for the teleport functionality associated with a Ship-Planetoid collision
	 * 
	 * @post If the container of this ship is a world, a random valid position is chosen
	 * 		 and set as the position of this Ship, however if the ship overlaps any other entity
	 * 		 at the chosen position, this ship is terminated.
	 * 			| if(getContainer() instanceof World) 
	 * 			| then let newPos = new Vector2d((World getContainer()).getWidth() * Math.random(), (World getContainer()).getHeight() * Math.random())
	 * 			| 	in 
	 * 			| 	if((World getContainer()).overlapsWithAnyEntity(newPos, getRadius()).size() > 0)
	 * 			| 	then new.isTerminated()
	 * 			| 	else (World getContainer()).isInBounds(new.getPosition(), new.getRadius()) 
	 */
	@Model
	private void teleport(){
		if(getContainer() instanceof World){
			World world = (World) getContainer();
			Vector2d newPos;
			do{
				newPos = new Vector2d(world.getWidth() * Math.random(), world.getHeight() * Math.random());
			}while(!world.isInBounds(newPos, getRadius()));
			if(world.overlapsWithAnyEntity(newPos, getRadius()).size() > 0)
				this.terminate();
			else
				setPosition(newPos.getX(), newPos.getY());
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
		if(!canHaveAsItem(item) || item.getContainer() != this || hasAsItem(item))
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
	 * @throws IllegalArgumentException
	 * 			If any of the given bullets can not be an item of this Ship
	 * 			| for some bullet in bullets:
	 * 			| 	!canHaveAsItem(bullet)
	 * @throws IllegalArgumentException
	 * 			If the given bullet(s) has a current container and the container
	 * 			is not this Ships world container.
	 * 			| bullet.getContainer() != null && ! ( bullet.getContainer() instanceof World && bullet.getContainer() == getContainer())
	 * @throws IllegalArgumentException
	 * 			If the given bullet in not in bounds of this Ship
	 * 			| !isInBounds(bullet.getPosition(), bullet.getRadius())
	 */
	public void loadBullet(Bullet... bullets) throws NullPointerException, IllegalArgumentException{
		if(bullets.length == 0)
			throw new NullPointerException();
		for (Bullet bullet : bullets){
			if(bullet.getContainer() != null &&
			(!(bullet.getContainer() instanceof World) || bullet.getContainer() != this.getContainer()))
				throw new IllegalArgumentException();
			if(!isInBounds(bullet.getPosition(), bullet.getRadius()))
				throw new IllegalArgumentException();
			bullet.setContainer(this);
			addItem(bullet);
			bullet.resetBoundaryCollisionCount();
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
	 * 		| let 
	 * 		|	newPosition = new Vector2d(this.getPosition().getX() + (this.getRadius()+bullet.getRadius()) * Math.cos(this.getOrientation()),
	 *		|								this.getPosition().getY() + (this.getRadius()+bullet.getRadius()) * Math.sin(this.getOrientation()))
	 * 		| for one bullet in bullets:
	 * 		| 	if((getContainer() instanceof World) && (bullets.size() > 0) &&
	 * 		|		getContainer().overlapsWithAnyEntity(bullet).size() == 0 && getContainer().isInBounds(newPosition, bullet.getRadius())
	 * 		| 	then 	bullet.getContainer() == this.getContainer() &&
	 *		|			this.getContainer().hasAsItem(bullet) && !new.hasAsItem(bullet) &&
	 *		|			bullet.getPosition().equals(newPosition) &&
	 *		| 			bullet.getVelocity() == new Vector2d(INITIAL_BULLETSPEED * Math.cos(this.getOrientation()),
	 *		|												 INITIAL_BULLETSPEED * Math.sin(this.getOrientation()))
	 * @post If the new position of the bullet is outside the world boundaries
	 * 		 then the bullet shall be terminated.
	 * 		| let 
	 * 		|	newPosition = new Vector2d(this.getPosition().getX() + (this.getRadius()+bullet.getRadius()) * Math.cos(this.getOrientation()),
	 *		|								this.getPosition().getY() + (this.getRadius()+bullet.getRadius()) * Math.sin(this.getOrientation()))
	 * 		| for one bullet in bullets:
	 *		| 	if (getContainer() instanceof World && getContainer().isInBounds(newPosition, bullet.getRadius()))
	 *		| 	then (new bullet).isTerminated()
	 * @post If the new position of the bullet results in an overlap with other entities,
	 * 		 both the bullet and the other entity(ies) shall be terminated.
	 * 		| for one bullet in bullets:
	 * 		| 	for each entity in getContainer().overlapsWithAnyEntity(bullet):
	 * 		|		(new entity).isTerminated() && (new bullet).isTerminated()
	 */
	public void fireBullet(){
		if(!(getContainer() instanceof World) || bullets.size() == 0)
			return;
		World world = (World) getContainer();
		Bullet bullet = bullets.iterator().next();
		Vector2d newPosition = new Vector2d(this.getPosition().getX() + (this.getRadius()+bullet.getRadius()) * Math.cos(this.getOrientation()),
											this.getPosition().getY() + (this.getRadius()+bullet.getRadius()) * Math.sin(this.getOrientation()));
		
		bullet.setContainer(null);
		removeItem(bullet);
		if(!world.isInBounds(newPosition, bullet.getRadius())){
			bullet.terminate();
			return;
		}
		bullet.setPosition(newPosition.getX(), newPosition.getY());
		bullet.setVelocity(INITIAL_BULLETSPEED * Math.cos(getOrientation()), INITIAL_BULLETSPEED * Math.sin(getOrientation()));
		
		List<Entity> overlapping = world.overlapsWithAnyEntity(bullet);
		
		if (overlapping.size() > 0){
			for(Entity e : overlapping)
				new CollisionData(0.0, null, CollisionType.INTER_ENTITY, Arrays.asList(new Entity[]{bullet, e})).resolve();
			return;
		}
		bullet.setContainer(world);			
		world.addItem(bullet);
	}
	
	/**
	 * Set the program of this Ship to the given program.
	 * 
	 * @param  program
	 *         The new program for this Ship.
	 * @post   The program of this new Ship is equal to the given program. 
	 * 			| new.getProgram() == program
	 */
	public void setProgram(Program program) throws NullPointerException{
		this.program = program;
		program.addExecutor(this);
	}
	
	/**
	 * Return the program of this Ship.
	 */
	@Basic
	public Program getProgram(){
		return program;
	}
	
	/**
	 * Execute the current program on this Ship (if any) for a given amount of time.
	 * This method returns the values printed by the program during execution.
	 * 
	 * @param dt
	 * 		The given time amount.
	 * @return The objects in the resulting list are printed on the standard output stream.
	 * 			If no program is loaded on the ship, the result is not effective.
	 * 		| if(getProgram() == null)
	 * 		| then result == null
	 */
	public List<Object> executeProgram(double dt) throws ProgramExecutionTimeException, ExpressionEvaluationException{
		if(program != null){
			return program.execute(dt);
		}
		return null;
	}
	
	/**
	 * A variable referencing the program of this Ship
	 */
	private Program program;
}