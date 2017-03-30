package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A Class to represent an Entity in the game
 * 
 * @invar The x of each Entity must be a valid x for any Entity. 
 * 			| isValidXPosition(getXPosition())
 * @invar The y of each Entity must be a valid y for any Entity. 
 * 			| isValidYPosition(getYPosition())
 * @invar The velocity components of this Entity must form a valid velocity for this Entity.
 * 			| canHaveAsVelocity(getXVelocity(), getYVelocity())
 * @invar  The radius of this Entity must be a valid radius for this Entity.
 *       	| canHaveAsRadius(getRadius())
 * @invar  The mass of this Entity must be a valid mass for this Entity.
 *       	| canHaveAsMass(getMass())
 * @invar  Each Entity can have its maxVelocity as maxVelocity.
 *          | canHaveAsMaxVelocity(this.getMaxVelocity())
 */
public abstract class Entity{

	/**
	 * Initialize this new Entity with given x, y, xVelocity, yVelocity, radius and mass.
	 *
	 * @param x
	 *     		The x for this new Entity.
	 * @param y
	 *        	The y for this new Entity.
	 * @param xVelocity
	 * 			The x-velocity for this new Entity.
	 * @param yVelocity
	 *       	The y-velocity for this new Entity.
	 * @param radius
	 *          The radius for this new Entity.
	 * @param mass
	 * 			The mass for this new Entity.
	 * @effect The x of this new Entity is set to the given x. 
	 * 			| setXPosition(x)
	 * @effect The y of this new Entity is set to the given y. 
	 * 			| setYPosition(y)
	 * @post If the given xVelocity and yVelocity form a total velocity smaller than the speed of light, 
	 * 		 the velocity components of this new Entity are equal to the given xVelocity and yVelocity. 
	 *       Otherwise, the velocity components of this new Entity are equal to 0. 
	 *       	| if (getVectorLength(xVelocity, yVelocity) <= SPEED_OF_LIGHT) 
	 *       	| then new.getXVelocity() == xVelocity && new.getYVelocity() == yVelocity
	 *       	| else new.getXVelocity() == 0 && new.getYVelocity() == 0
	 * @post The maximum velocity of this new Entity is set to the speed of light
	 * 			| new.getMaxVelocity() == SPEED_OF_LIGHT
	 * @post The radius of this new Entity number is equal to
	 *		 the given radius.
	 *       	| new.getRadius() == radius
	 * @post If given mass is greater than the lowest possible mass, the mass is equal to the given mass.
	 * 		 Otherwise the mass is set to the lowest possible mass.
	 * 			| let 
	 * 			| 	lowestMass = 4.0 / 3.0 * Math.PI * Math.pow(radius, 3) * getLowestMassDensity()
	 * 			| in
	 * 			|   if (mass > lowestMass) 
	 *       	|   then new.getMass() == mass
	 *       	|   else new.getMass() == lowestMass
	 * @throws IllegalArgumentException
	 *         The given radius is not a valid radius for this Entity.
	 *       	| ! canHaveAsRadius(radius)
	 */
	@Model
	protected Entity(double x, double y, double xVelocity, double yVelocity, double radius, double mass) throws IllegalArgumentException{
		if(!canHaveAsRadius(radius))
			throw new IllegalArgumentException();
		this.position = new double[2];
		this.setXPosition(x);
		this.setYPosition(y);
		this.velocity = new double[2];
		this.setVelocity(xVelocity, yVelocity);
		this.maxVelocity = SPEED_OF_LIGHT; // miss overloaden
		this.radius = radius;
		this.setMass(mass);
	}

	/**
	 * Return the x of this Entity.
	 */
	@Basic
	@Raw
	public double getXPosition(){
		return this.position[0];
	}

	/**
	 * Return the y of this Entity.
	 */
	@Basic
	@Raw
	public double getYPosition(){
		return this.position[1];
	}

	/**
	 * Check whether the given x is a valid x for any Entity.
	 * 
	 * @param x
	 *            The x to check.
	 * @return | result == Double.isFinite(x)
	 */
	public static boolean isValidXPosition(double x){
		return Double.isFinite(x);
	}

	/**
	 * Check whether the given y is a valid y for any Entity.
	 * 
	 * @param y
	 *            The y to check.
	 * @return | result == Double.isFinite(y)
	 */
	public static boolean isValidYPosition(double y){
		return Double.isFinite(y);
	}

	/**
	 * Set the x of this Entity to the given x.
	 * 
	 * @param  x
	 *            The new x for this Entity.
	 * @post   The x of this new Entity is equal to the given x. 
	 * 			| new.getXPosition() == x
	 * @throws IllegalArgumentException
	 *             The given x is not a valid x for any Entity. 
	 *        	| !isValidXPosition(getXPosition())
	 */
	@Raw
	public void setXPosition(double x) throws IllegalArgumentException{
		if(!isValidXPosition(x))
			throw new IllegalArgumentException();
		this.position[0] = x;
	}

	/**
	 * Set the y of this Entity to the given y.
	 * 
	 * @param  y
	 *            The new y for this Entity.
	 * @post   The y of this new Entity is equal to the given y. 
	 * 			| new.getYPosition() == y
	 * @throws IllegalArgumentException
	 *             The given y is not a valid y for any Entity. 
	 *         	| !isValidYPosition(getYPosition())
	 */
	@Raw
	public void setYPosition(double y) throws IllegalArgumentException{
		if(!isValidYPosition(y))
			throw new IllegalArgumentException();
		this.position[1] = y;
	}

	/**
	 * Variable containing the (x,y) components of the position vector of this Entity in kilometers.
	 */
	private double[] position;

	/**
	 * Return the x-velocity of this Entity.
	 */
	@Basic
	@Raw
	public double getXVelocity(){
		return this.velocity[0];
	}

	/**
	 * Return the y-velocity of this Entity.
	 */
	@Basic
	@Raw
	public double getYVelocity(){
		return this.velocity[1];
	}

	/**
	 * Return the velocity of this Entity.
	 */
	@Basic
	@Raw
	public double[] getVelocity(){
		return this.velocity;
	}

	/**
	 * Check whether this Entity can have the given xV, yV as its velocity components.
	 *  
	 * @param xV
	 *            The x-velocity to check.
	 * @param yV
	 * 			  The y-velocity to check.
	 * @return | result == getVectorLength(xV, yV) <= getMaxVelocity()
	 */
	@Raw
	public boolean canHaveAsVelocity(double xV, double yV){
		return getVectorLength(xV, yV) <= getMaxVelocity();
	}

	/**
	 * Set the x-velocity of this Entity to the given xV.
	 * 
	 * @param xV
	 *            The new x-velocity for this Entity.
	 * @post  If the given xV in combination with the current y-veloctiy is a valid velocity,
	 * 	      the x-velocity is equal to the given xV. 
	 *       	| if (canHaveAsVelocity(xV, getYVelocity())) 
	 *       	| then new.getXVelocity() == xV
	 */
	@Raw
	public void setXVelocity(double xV){
		if(canHaveAsVelocity(xV, this.getYVelocity()))
			this.velocity[0] = xV;
	}

	/**
	 * Set the y-velocity of this Entity to the given yV.
	 * 
	 * @param yV
	 *            The new y-velocity for this Entity.
	 * @post  If the given yV in combination with the current x-veloctiy is a valid velocity,
	 * 	      the y-velocity is equal to the given yV. 
	 *       	| if (canHaveAsVelocity(getXVelocity(), yV)) 
	 *       	| then new.getYVelocity() == yV
	 */
	@Raw
	public void setYVelocity(double yV){
		if(canHaveAsVelocity(this.getXVelocity(), yV))
			this.velocity[1] = yV;
	}

	/**
	 * Set the velocity components of this Entity to the given xV and yV.
	 * 
	 * @param xV
	 * 			  The new x-velocity for this Entity.
	 * @param yV
	 *            The new y-velocity for this Entity.
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
	protected static double getVectorLength(double x, double y){
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Variable containing the (vx,vy) components of the velocity vector of this Entity in kilometers/second.
	 */
	private double[] velocity;

	/**
	 * Return the maxVelocity of this Entity.
	 */
	@Basic
	@Raw
	@Immutable
	public double getMaxVelocity(){
		return this.maxVelocity;
	}

	/**
	 * Check whether this Entity can have the given maxVelocity as its maxVelocity.
	 * The maxVelocity must be positive and cannot exceed the speed of light.
	 *  
	 * @param  maxVelocity
	 *         The maxVelocity to check.
	 * @return 
	 *       | result == 0 < maxVelocity && maxVelocity <= SPEED_OF_LIGHT
	*/
	@Raw
	public boolean canHaveAsMaxVelocity(double maxVelocity){
		return 0 < maxVelocity && maxVelocity <= SPEED_OF_LIGHT;
	}

	/**
	 * Variable registering the maxVelocity of this Entity.
	 */
	private final double maxVelocity;
	
	/**
	 * Constant registering the speed of light in kilometres/second
	 */
	public final static double SPEED_OF_LIGHT = 300000.0;

	/**
	 * Return the radius of this Entity.
	 */
	@Basic
	@Raw
	@Immutable
	public double getRadius(){
		return this.radius;
	}

	/**
	 * Check whether this Entity can have the given radius as radius.
	 *  
	 * @param radius
	 *            The radius to check.
	 * @return | result == getMinRadius() < radius
	 */
	@Raw
	public boolean canHaveAsRadius(double radius){
		return getMinRadius() < radius;
	}

	/**
	 * Returns this minimum radius for this Entity
	 */
	public abstract double getMinRadius();

	/**
	 * Variable registering the radius of this Entity in kilometres.
	 */
	private final double radius;

	/**
	 * Return the mass of this Entity.
	 */
	@Basic
	@Raw
	public double getMass(){
		return this.mass;
	}

	/**
	 * Return the mass density of this Entity.
	 */
	@Basic
	public double getMassDensity(){
		return this.mass / (4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3));
	}

	/**
	 * Return the lowest possible massDensity for this Entity.
	 * 
	 * @return 
	 * 			| 1.42 * Math.pow(10, 12)
	 */
	@Model
	protected static double getLowestMassDensity(){
		return 1.42 * Math.pow(10, 12);
	}

	/**
	 * Return the lowest possible mass for this Entity.
	 * 
	 * @return 
	 * 			| 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * getLowestMassDensity()
	 */
	@Model
	protected double getLowestMass(){
		return 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * getLowestMassDensity();
	}

	/**
	 * Check whether this Entity can have the given mass as its mass.
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
	 * Set the mass of this Entity to the given mass.
	 * 
	 * @param  mass
	 *         The new mass for this Entity.
	 * @post   If the given mass is a valid mass for this Entity,
	 *         the mass of this Entity is equal to the given
	 *         mass.
	 *       | if (canHaveAsMass(mass))
	 *       |   then new.getMass() == mass
	 * @post   If the given amss is not a valid mass for this Entity
	 * 		   the mass of this Entity is equal to the lowest possible mass.
	 * 		 | if (!canHaveAsMass(mass))
	 *       |   then new.getMass() == getLowestMass()
	 */
	@Raw
	public void setMass(double mass){
		if(canHaveAsMass(mass))
			this.mass = mass;
		else this.mass = getLowestMass();
	}

	/**
	 * Variable registering the mass of this Entity.
	 */
	private double mass;

	/**
	 * Terminate this Entity.
	 *
	 * @post   This Entity  is terminated.
	 *       | new.isTerminated()
	 * @post   ???????
	 *       | ???????
	 */
	public void terminate(){
		// TODO: dis
		this.isTerminated = true;
	}

	/**
	 * Return a boolean indicating whether or not this Entity
	 * is terminated.
	 */
	@Basic
	@Raw
	public boolean isTerminated(){
		return this.isTerminated;
	}

	/**
	 * Variable registering whether this person is terminated.
	 */
	private boolean isTerminated = false;

}
