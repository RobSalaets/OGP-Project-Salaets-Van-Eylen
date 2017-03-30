package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A Class to represent a Ship in the game
 * 
 * @invar  The orientation of each Ship must be a valid orientation for any Ship.
 *       	| isValidOrientation(getOrientation())
 * @invar  The thrustForce of each Ship must be a valid thrustForce for any Ship.
 *       	| isValidThrustForce(getThrustForce())
 */
public class Ship extends Entity{

	/**
	 * Initialize this new Ship with given x, y, xVelocity, yVelocity, orientation, radius and mass.
	 *
	 * @param x
	 *     		The x for this new Ship.
	 * @param y
	 *        	The y for this new Ship.
	 * @param xVelocity
	 * 			The x-velocity for this new Ship.
	 * @param yVelocity
	 *       	The y-velocity for this new Ship.
	 * @param orientation
	 *			The orientation for this new Ship.
	 * @param radius
	 *          The radius for this new Ship.
	 * @param mass
	 * 			The mass for this new Ship. 
	 * @pre    The given orientation must be a valid orientation for any Ship.
	 * 			| isValidOrientation(orientation)
	 * @effect The x of this new Ship is set to the given x. 
	 * 			| setXPosition(x)
	 * @effect The y of this new Ship is set to the given y. 
	 * 			| setYPosition(y)
	 * @effect The thrust force of this new Ship is set to the DEFAULT_THRUST_FORCE
	 * 			| setThrustForce(DEFAULT_THRUST_FORCE)
	 * @post If the given xVelocity and yVelocity form a total velocity smaller than the speed of light, the velocity components 
	 * 		 of this new Ship are equal to the given xVelocity and yVelocity. 
	 *       Otherwise, the velocity components of this new Ship are equal to 0. 
	 *       	| if (getVectorLength(xVelocity, yVelocity) <= 300000.0) 
	 *       	| then new.getXVelocity() == xVelocity && new.getYVelocity() == yVelocity
	 *       	| else new.getXVelocity() == 0 && new.getYVelocity() == 0
	 * @post The orientation of this new Ship is equal to the given
	 *       orientation.
	 *    		| new.getOrientation() == orientation
	 * @post The radius of this new Ship number is equal to
	 *		 the given radius.
	 *       	| new.getRadius() == radius
	 * @post If given mass is a valid mass value for this new Ship, the mass is equal to the given mass.
	 * 		 Otherwise the mass is set to the lowest possible mass.
	 * 			| let 
	 * 			| 	lowestMass = 4.0 / 3.0 * Math.PI * Math.pow(radius, 3) * getLowestMassDensity()
	 * 			| in
	 * 			|   if (mass > lowestMass) 
	 *       	|   then new.getMass() == mass
	 *       	|   else new.getMass() == lowestMass
	 * @throws IllegalArgumentException
	 *         The given radius is not a valid radius for any Ship.
	 *       	| ! isValidRadius(radius)
	 */
	public Ship(double x, double y, double xVelocity, double yVelocity, double orientation, double radius, double mass) throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, orientation, radius, mass, DEFAULT_THRUST_FORCE);
	}
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double orientation, double radius) throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, orientation, radius, 4.0 / 3.0 * Math.PI * Math.pow(radius, 3) * getLowestMassDensity(), DEFAULT_THRUST_FORCE);
	}
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double orientation, double radius, double mass, double thrustForce) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, mass);
		this.setOrientation(orientation);
		this.setThrustForce(thrustForce);
	}
	
	//TODO:
	/**
	 * zoek op spec voor inheritance constructor
	 * 			spec voor overloading constructor
	 */
	
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
	 * Move the ship to a new position given a time duration,
	 * with respect to this ships current velocity.
	 * @param  timeDelta
	 * 			The amount of time the ship moves with current velocity
	 * @throws IllegalArgumentException
	 * 			The timedelta cannot be less than zero
	 * 			| timeDelta < 0.0
	 */
	public void move(double timeDelta) throws IllegalArgumentException{
		if(timeDelta < 0.0)
			throw new IllegalArgumentException();
		setXPosition(getXPosition() + timeDelta * getXVelocity());
		setYPosition(getYPosition() + timeDelta * getYVelocity());
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
	 * 			| if (getThrusterStatus()==true);
	 *			| acceleration = this.getAcceleration();
	 *			| double newXVelocity = getXVelocity() + acceleration * Math.cos(getOrientation()) * timeDelta;
	 *			| double newYVelocity = getYVelocity() + acceleration * Math.sin(getOrientation()) * timeDelta; 
	 * @post  For an acceleration that results in a velocity exceeding the maximum velocity, 
	 * 		  the velocity direction remains unchanged and the total velocity
	 * 		  is set to the maximum velocity.
	 * 			| if(!canHaveAsVelocity(getXVelocity() + acceleration * Math.cos(getOrientation()) * timeDelta,
	 * 			| 		getYVelocity() + acceleration * Math.sin(getOrientation()) * timeDelta))
	 * 			| then getVectorLength(new.getXVelocity(), new.getYVelocity()) == getMaxVelocity()
	 */

	public void thrust(double timeDelta){
		if(getThrusterStatus() == true)
			;
		acceleration = this.getAcceleration();
		double newXVelocity = getXVelocity() + acceleration * Math.cos(getOrientation()) * timeDelta;
		double newYVelocity = getYVelocity() + acceleration * Math.sin(getOrientation()) * timeDelta;

		if(canHaveAsVelocity(newXVelocity, newYVelocity))
			setVelocity(newXVelocity, newYVelocity);
		else{
			double velocity = getVectorLength(getXVelocity(), getYVelocity());
			setXVelocity(getXVelocity() / velocity * getMaxVelocity());
			setYVelocity(getYVelocity() / velocity * getMaxVelocity());
		}

	}

	/**
	 * Returns the distance between this ship and another ship in kilometres.
	 * 
	 * @param  other 
	 * 			The other ship
	 * @return The euclidean distance between this ship and the other ship
	 * 			| result == getVectorLength(this.getXPosition()-other.getXPosition(),
	 * 			| this.getYPosition()-other.getYPosition())-this.getRadius()-other.getRadius()
	 *
	 * @throws NullPointerException
	 * 			| other == null
	 */

	public double getDistanceBetween(Ship other) throws NullPointerException{
		if(other == null)
			throw new NullPointerException();
		return getVectorLength(this.getXPosition() - other.getXPosition(), this.getYPosition() - other.getYPosition()) - this.getRadius() - other.getRadius();

	}

	/**
	 * Checks if this ships overlaps with another ship.
	 * 
	 * @param  other
	 * 			The other ship
	 * @return result == (this == other || getDistanceBetween(other) < 0.0)
	 * @throws NullPointerException
	 * 			| other == null
	 */
	public boolean overlaps(Ship other) throws NullPointerException{
		if(other == null)
			throw new NullPointerException();
		return this == other || getDistanceBetween(other) < 0.0;
	}

	/**
	 * Calculate the time before collision with the given other Ship, assuming the velocities
	 * of both ships do not change. If in the current state no collision will occur,
	 * the time to collision is considered infinite.
	 * 
	 * @param  other
	 * 			The other ship
	 * @post   The calculated result is cannot be negative
	 * 			| result >= 0.0
	 * @return Starting from the current position of each ship, when the current velocities 
	 * 		   are applied for the calculated duration of time on both ships, their boundaries touch.
	 * 		   In other words the distance between the center of this ship and the other ship will then 
	 * 		   equal the sum of their radii. Assuming the calculated result is a finite value.
	 * 			| let
	 * 			| 	thisCollisionX = this.getXPosition() + result * this.getXVelocity()
	 * 			| 	thisCollisionY = this.getYPosition() + result * this.getYVelocity()
	 * 			| 	otherCollisionX = other.getXPosition() + result * other.getXVelocity()
	 * 			|	otherCollisionY = other.getYPosition() + result * other.getYVelocity()
	 * 			| in
	 * 			|	if (result < Double.POSITIVE_INFINITY)
	 * 			| 	then getVectorLength(thisCollisionX - otherCollisionX, thisCollisionY - otherCollisionY)
	 * 			|		 	== this.getRadius() + other.getRadius()
	 * @throws NullPointerException
	 * 			| other == null
	 * @throws IllegalArgumentException 
	 * 			| this.overlaps(other)
	 */
	public double getTimeToCollision(Ship other) throws NullPointerException, IllegalArgumentException{
		if(other == null)
			throw new NullPointerException();
		if(this.overlaps(other))
			throw new IllegalArgumentException();

		double sigmaSq = Math.pow(this.getRadius() + other.getRadius(), 2);
		double rDotr = Math.pow(this.getXPosition() - other.getXPosition(), 2) + Math.pow(this.getYPosition() - other.getYPosition(), 2);
		double vDotv = Math.pow(this.getXVelocity() - other.getXVelocity(), 2) + Math.pow(this.getYVelocity() - other.getYVelocity(), 2);
		double vDotr = (this.getXVelocity() - other.getXVelocity()) * (this.getXPosition() - other.getXPosition()) + (this.getYVelocity() - other.getYVelocity()) * (this.getYPosition() - other.getYPosition());
		double d = vDotr * vDotr - vDotv * (rDotr - sigmaSq);
		if(vDotr >= 0 || d <= 0)
			return Double.POSITIVE_INFINITY;
		else return -(vDotr + Math.sqrt(d)) / vDotv;
	}

	/**
	 * Find the position of a possible collision with another ship,
	 * assuming the current velocities remain constant.
	 * @param other
	 * 			The other ship.
	 * @return The x- and y-coordinate of the calculated collision position, if in the current
	 * 		   state of the ships no collision will occur the result is null. The collision point
	 * 		   lies on the connecting line between the ships. It's position on the line is determined
	 * 		   by the radii of the ships.
	 * 		| let
	 * 		| 	thisCollisionX = this.getXPosition() + getTimeToCollision(other) * this.getXVelocity()
	 * 		| 	thisCollisionY = this.getYPosition() + getTimeToCollision(other) * this.getYVelocity()
	 * 		| 	otherCollisionX = other.getXPosition() + getTimeToCollision(other) * other.getXVelocity()
	 * 		|	otherCollisionY = other.getYPosition() + getTimeToCollision(other) * other.getYVelocity()
	 * 		| in
	 * 		|   if(getTimeToCollision(other) == Double.POSITIVE_INFINITY)
	 * 		|   then result == null
	 * 		|   else result == {(thisCollisionX * other.getRadius() + otherCollisionX * this.getRadius()) / (this.getRadius() + other.getRadius),
	 * 		|					(thisCollisionY * other.getRadius() + otherCollisionY * this.getRadius()) / (this.getRadius() + other.getRadius)}
	 * @throws NullPointerException
	 * 			| other == null
	 * @throws IllegalArgumentException
	 * 			| this.overlaps(other)
	 */
	public double[] getCollisionPosition(Ship other) throws NullPointerException, IllegalArgumentException{
		if(other == null)
			throw new NullPointerException();

		double timeTilCollision = getTimeToCollision(other);
		if(timeTilCollision == Double.POSITIVE_INFINITY)
			return null;
		double sumRadii = this.getRadius() + other.getRadius();
		double[] result = new double[2];
		result[0] = ((this.getXPosition() + timeTilCollision * this.getXVelocity()) * other.getRadius() + (other.getXPosition() + timeTilCollision * other.getXVelocity()) * this.getRadius()) / sumRadii;
		result[1] = ((this.getYPosition() + timeTilCollision * this.getYVelocity()) * other.getRadius() + (other.getYPosition() + timeTilCollision * other.getYVelocity()) * this.getRadius()) / sumRadii;
		return result;
	}
}