package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A Class to represent a ship in the game
 * 
 * @invar The x of each Ship must be a valid x for any Ship. 
 * 			| isValidXPosition(getXPosition())
 * @invar The y of each Ship must be a valid y for any Ship. 
 * 			| isValidYPosition(getYPosition())
 * @invar The velocity components of each Ship must form a valid velocity for any Ship.
 * 			| canHaveAsVelocity(getXVelocity(), getYVelocity())
 * @invar  The orientation of each Ship must be a valid orientation for any Ship.
 *       	| isValidOrientation(getOrientation())
 * @invar  The radius of each Ship must be a valid radius for any Ship.
 *       	| isValidRadius(getRadius())
 */
public class Ship{

	/**
	 * Initialize this new Ship with given x, y, xV, yV, orientation and radius.
	 *
	 * @param x
	 *     		The x for this new Ship.
	 * @param y
	 *        	The y for this new Ship.
	 * @param xV
	 * 			The x-velocity for this new Ship.
	 * @param yV
	 *       	The y-velocity for this new Ship.
	 * @param orientation
	 *			The orientation for this new Ship.
	 * @param radius
	 *          The radius for this new Ship.
	 * @param massDensity
	 * 			The massDensity for this new Ship. 
	 * @param thrustForce
	 * 			The thrustForce for this new Ship.
	 * @pre    The given orientation must be a valid orientation for any Ship.
	 * 			| isValidOrientation(orientation)
	 * @effect The x of this new Ship is set to the given x. 
	 * 			| this.setXPosition(x)
	 * @effect The y of this new Ship is set to the given y. 
	 * 			| this.setYPosition(y)
	 * @post If the given xV and yV form a total velocity smaller than the speed of light, the velocity components 
	 * 		 of this new Ship are equal to the given xV and yV. 
	 *       Otherwise, the velocity components of this new Ship are equal to 0. 
	 *       	| if (getVectorLength(xV, yV) <= 300000.0) 
	 *       	| then new.getXVelocity() == xV && new.getYVelocity() == yV
	 *       	| else new.getXVelocity() == 0 && new.getYVelocity() == 0
	 * @post The orientation of this new Ship is equal to the given
	 *       orientation.
	 *    		| new.getOrientation() == orientation
	 * @post The radius of this new Ship number is equal to
	 *		 the given radius.
	 *       	| new.getRadius() == radius
	 * @post The thrustForce of this new Ship is equal to
	 * 		 the given thrustForce.
	 * 			| new.getThrustForce() == thrustForce
	 * @throws IllegalArgumentException
	 *         The given radius is not a valid radius for any Ship.
	 *       	| ! isValidRadius(radius)
	 */
	public Ship(double x, double y, double xV, double yV, double orientation, double radius, double massDensity, double thrustForce) throws IllegalArgumentException{
		if(!isValidRadius(radius))
			throw new IllegalArgumentException();
		this.setXPosition(x);
		this.setYPosition(y);
		this.velocity = new double[2];
		this.setVelocity(xV, yV);
		this.setOrientation(orientation);
		this.radius = radius;
		this.massDensity = massDensity;
	}

	/**
	 * Return the x of this Ship.
	 */
	@Basic
	@Raw
	public double getXPosition(){
		return this.x;
	}

	/**
	 * Return the y of this Ship.
	 */
	@Basic
	@Raw
	public double getYPosition(){
		return this.y;
	}

	/**
	 * Check whether the given x is a valid x for any Ship.
	 * 
	 * @param x
	 *            The x to check.
	 * @return | result == Double.isFinite(x)
	 */
	public static boolean isValidXPosition(double x){
		return Double.isFinite(x);
	}

	/**
	 * Check whether the given y is a valid y for any Ship.
	 * 
	 * @param y
	 *            The y to check.
	 * @return | result == Double.isFinite(y)
	 */
	public static boolean isValidYPosition(double y){
		return Double.isFinite(y);
	}

	/**
	 * Set the x of this Ship to the given x.
	 * 
	 * @param  x
	 *            The new x for this Ship.
	 * @post   The x of this new Ship is equal to the given x. 
	 * 			| new.getXPosition() == x
	 * @throws IllegalArgumentException
	 *             The given x is not a valid x for any Ship. 
	 *        	| !isValidXPosition(getXPosition())
	 */
	@Raw
	public void setXPosition(double x) throws IllegalArgumentException{
		if(!isValidXPosition(x)) throw new IllegalArgumentException();
		this.x = x;
	}

	/**
	 * Set the y of this Ship to the given y.
	 * 
	 * @param  y
	 *            The new y for this Ship.
	 * @post   The y of this new Ship is equal to the given y. 
	 * 			| new.getYPosition() == y
	 * @throws IllegalArgumentException
	 *             The given y is not a valid y for any Ship. 
	 *         	| !isValidYPosition(getYPosition())
	 */
	@Raw
	public void setYPosition(double y) throws IllegalArgumentException{
		if(!isValidYPosition(y)) throw new IllegalArgumentException();
		this.y = y;
	}

	/**
	 * Variable registering the x-position of this Ship in kilometers.
	 */
	private double x;

	/**
	 * Variable registering the y-position of this Ship in kilometers.
	 */
	private double y;

	/**
	 * Return the x-velocity of this Ship.
	 */
	@Basic
	@Raw
	public double getXVelocity(){
		return this.velocity[0];
	}

	/**
	 * Return the y-velocity of this Ship.
	 */
	@Basic
	@Raw
	public double getYVelocity(){
		return this.velocity[1];
	}
	
	/**
	 * Return the velocity of this Ship.
	 */
	@Basic
	@Raw
	public double[] getVelocity(){
		return this.velocity;
	}
	
	/**
	 * Check whether this Ship can have the given xV, yV as its velocity components.
	 *  
	 * @param xV
	 *            The x-velocity to check.
	 * @param yV
	 * 			  The y-velocity to check.
	 * @return | result == getVectorLength(xV, yV) <= getMaxVelocity()
	 */
	@Raw
	public boolean canHaveAsVelocity(double xV, double yV) {
		return getVectorLength(xV, yV) <= getMaxVelocity();
	}

	/**
	 * Set the x-velocity of this Ship to the given xV.
	 * 
	 * @param xV
	 *            The new x-velocity for this Ship.
	 * @post  If the given xV in combination with the current y-veloctiy is a valid velocity,
	 * 	      the x-velocity is equal to the given xV. 
	 *       	| if (canHaveAsVelocity(xV, getYVelocity())) 
	 *       	| then new.getXVelocity() == xV
	 */
	@Raw
	public void setXVelocity(double xV){
		if(canHaveAsVelocity(xV, this.getYVelocity())) this.velocity[0] = xV;
	}

	/**
	 * Set the y-velocity of this Ship to the given yV.
	 * 
	 * @param yV
	 *            The new y-velocity for this Ship.
	 * @post  If the given yV in combination with the current x-veloctiy is a valid velocity,
	 * 	      the y-velocity is equal to the given yV. 
	 *       	| if (canHaveAsVelocity(getXVelocity(), yV)) 
	 *       	| then new.getYVelocity() == yV
	 */
	@Raw
	public void setYVelocity(double yV){
		if(canHaveAsVelocity(this.getXVelocity(), yV)) this.velocity[1] = yV;
	}

	/**
	 * Set the velocity components of this Ship to the given xV and yV.
	 * 
	 * @param xV
	 * 			  The new x-velocity for this Ship.
	 * @param yV
	 *            The new y-velocity for this Ship.
	 * @post  If the given xV and yV form a valid velocity,
	 * 	      the new velocity components are equal to the given xV and yV. 
	 *       	| if (canHaveAsVelocity(xV, yV)) 
	 *       	| then new.getXVelocity() == xV && new.getYVelocity() == yV
	 */
	@Raw
	public void setVelocity(double xV, double yV){
		if(canHaveAsVelocity(xV, yV)){
			this.velocity[0] = xV;
			this.velocity[1] = yV;
		}
	}

	/**
	 * Return the length of a vector, given the (x,y) components
	 * 
	 * @return | sqrt(x * x + y * y)
	 */
	private static double getVectorLength(double x, double y){
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Variable containing the (vx,vy) components of the velocity vector of this Ship in kilometers/second.
	 */
	private double[] velocity;
	
	
	/**
	 * Return the highest possible velocity for this ship
	 * 
	 * @return 
	 */
	@Basic
	@Immutable
	public double getMaxVelocity() {
		return 300000.0;
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
	 * Return the radius of this Ship.
	 */
	@Basic
	@Raw
	@Immutable
	public double getRadius(){
		return this.radius;
	}

	/**
	 * Check whether the given radius is a valid radius for
	 * any Ship.
	 *  
	 * @param  radius
	 *         The radius to check.
	 * @return 
	 *       | result == (minRadius < radius)
	*/
	public static boolean isValidRadius(double radius){
		return minRadius < radius;
	}
	
	/**
	 * The mininum radius for any Ship in kilometres
	 */
	public static final double minRadius = 10.0;

	/**
	 * Variable registering the radius of this Ship in kilometres.
	 */
	private final double radius;
	
	/**
	 * Return the massDensity of this Ship.
	 */
	@Basic @Raw
	public double getMassDensity() {
		return this.massDensity;
	}
	
	/**
	 * Return the lowest possible massDensity for this ship.
	 * 
	 * @return 
	 */
	@Basic
	@Immutable
	public double getLowestMassDensity() {
		return 1.42 * Math.pow(10, 12);
	}
	
	/**
	 * Check whether the given massDensity is a valid massDensity for
	 * any Ship.
	 *  
	 * @param  massDensity
	 *         The massDensity to check.
	 * @return 
	 *       | result == (massDensity >= 1.42 * Math.pow(10, 12))
	*/
	public static boolean isValidMassDensity(double massDensity) {
		return (massDensity >= 1.42 * Math.pow(10, 12));
	}
	
	/**
	 * Set the massDensity of this Ship to the given massDensity.
	 * 
	 * @param  massDensity
	 *         The new massDensity for this Ship.
	 * @post   If the given massDensity is a valid massDensity for any Ship,
	 *         the massDensity of this new Ship is equal to the given
	 *         massDensity. 
	 *         Else it will be set to the lowest ThrustForce there is.
	 *       | if (isValidMassDensity(massDensity))
	 *       |   then new.getMassDensity() == massDensity
	 *       | this.massDensity == getLowestMassDensity()
	 */
	@Raw
	public void setMassDensity(double massDensity) {
		if (isValidMassDensity(massDensity))
			this.massDensity = massDensity;
		this.massDensity = getLowestMassDensity();
	}
	
	/**
	 * Variable registering the massDensity of this Ship.
	 */
	private double massDensity;

	/**
	 * Return the mass of this Ship.
	 */
	@Basic @Raw
	public double getMass() {
		return getMassDensity() * 4/3 * Math.PI * Math.pow(getRadius(), 3);
	}
	
	
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
	 * 
	 * @param force
	 * 			The force provided by the thruster in newton.
	 * @param mass
	 * 			The mass of the ship in kilogram.
	 * @return
	 */
	public double getAcceleration(){
		return force/this.getMass();
	}
	
	/**
	 * Adjust the velocity of this Ship to the current orientation,
	 * with a given amount.
	 * @param amount
	 * 			A value representing the magnitude of this effect
	 * @post  For a positive amount, the Ship's velocity is updated
	 * 	  	  with respect to the current orientation and given amount.
	 * 			| if(amount > 0)
	 * 			| then (new.getXVelocity() == getXVelocity() + amount * cos(getOrientation()))
	 * 			| && (new.getYVelocity() == getYVelocity() + amount * sin(getOrientation()))
	 * @post  For an amount that results in a velocity exceeding the maximum velocity, 
	 * 		  the velocity direciton remains unchanged and the total velocity
	 * 		  is set to the maximum velocity.
	 * 			| if(canHaveAsVelocity(getXVelocity() + amount * cos(getOrientation()),
	 * 			| 		getYVelocity() + amount * sin(getOrientation()))
	 * 			| then getVectorLength(new.getXVelocity(), new.getYVelocity()) == getMaxVelocity()
	 */
	
	public void thrust(double amount){
		if(amount <= 0.0)
			return;
		double newXVelocity = getXVelocity() + amount * Math.cos(getOrientation());
		double newYVelocity = getYVelocity() + amount * Math.sin(getOrientation());
		
		if(canHaveAsVelocity(newXVelocity, newYVelocity))
			setVelocity(newXVelocity, newYVelocity);
		else{
			double velocity = getVectorLength(getXVelocity(), getYVelocity());
			setXVelocity(getXVelocity() / velocity * getMaxVelocity());
			setYVelocity(getYVelocity() / velocity * getMaxVelocity());
		}
		
	}
	
	/**
	 * 
	 * @param timeDelta
	 */
	public void thrustOn(double timeDelta){
		if (this.getAcceleration(1, 1) < 0);
			double acceleration =0;
		acceleration = this.getAcceleration(1, 1);
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
	
	public void thrustOff(){
	}
	
	/**
	 * Returns the distance between this ship and another ship in kilometres.
	 * 
	 * @param  other 
	 * 			The other ship
	 * @return The euclidean distance between this ship and the other ship
	 * 			| getVectorLength(this.getXPosition()-other.getXPosition(),
	 * 			| this.getYPosition()-other.getYPosition())-this.getRadius()-other.getRadius()
	 *
	 * @throws NullPointerException
	 * 			| other == null
	 */
	
	public double getDistanceBetween(Ship other) throws NullPointerException{
		if(other == null)
			throw new NullPointerException();
		return getVectorLength(this.getXPosition()-other.getXPosition(),this.getYPosition()-other.getYPosition())-this.getRadius()-other.getRadius();
		
	}
	
	/**
	 * Checks if this ships overlaps with another ship.
	 * 
	 * @param  other
	 * 			The other ship
	 * @return this == other || getDistanceBetween(other) < 0.0
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
	 * @return The time until collision in seconds
	 * @post   The calculated result is cannot be negative
	 * 			| result >= 0.0
	 * @post   Starting from the current position of each ship, when the current velocities 
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
		double vDotr = (this.getXVelocity() - other.getXVelocity())*(this.getXPosition() - other.getXPosition()) +
						(this.getYVelocity() - other.getYVelocity()) * (this.getYPosition() - other.getYPosition());
		double d = vDotr * vDotr - vDotv * (rDotr - sigmaSq);
		if(vDotr >= 0 || d <= 0)
			return Double.POSITIVE_INFINITY;
		else
			return -(vDotr + Math.sqrt(d)) / vDotv;	
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
		result[0] = ((this.getXPosition() + timeTilCollision * this.getXVelocity()) * other.getRadius() + 
					(other.getXPosition() + timeTilCollision * other.getXVelocity()) * this.getRadius()) / sumRadii;
		result[1] = ((this.getYPosition() + timeTilCollision * this.getYVelocity()) * other.getRadius() + 
					(other.getYPosition() + timeTilCollision * other.getYVelocity()) * this.getRadius()) / sumRadii;
		return result;
	}
}