package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A Class representing a Planetoid in the game.
 */
public class Planetoid extends MinorPlanet{

	/**
	 * Initialize this new Planetoid with given x, y, xVelocity, yVelocity, startRadius, mass and container.
	 *
	 * @param x
	 *     		The x-position for this new Planetoid.
	 * @param y
	 *        	The y-position for this new Planetoid.
	 * @param xVelocity
	 * 			The x-velocity for this new Planetoid.
	 * @param yVelocity
	 *       	The y-velocity for this new Planetoid.
	 * @param startRadius
	 *          The radius for this new Planetoid.
	 * @param container
	 * 			The container for this new Planetoid.
	 * @effect This new Planetoid is initialized as a new MinorPlanet with
	 * 		   given x, y, xVelocity, yVelocity, startRadius, container and a mass corresponding 
	 * 			to the startRadius of this Planetoid.
	 * 			| super(x, y, xVelocity, yVelocity, startRadius, 4.0/3.0*Math.PI*Math.pow(startRadius, 3)*PLANETOID_MASS_DENSITY, container)
	 * @effect The currentRadius of this new Planetoid is set to
	 *         the given startRadius.
	 *       | this.setRadius(startRadius)
	 */
	@Raw
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double startRadius, Container<Entity> container) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, startRadius, 4.0 / 3.0 * Math.PI * Math.pow(startRadius, 3) * PLANETOID_MASS_DENSITY, container);
		this.setRadius(startRadius);
	}

	/**
	 * Initialize this new Planetoid with given x, y, xVelocity, yVelocity, startRadius, mass.
	 * This Planetoid has no Container.
	 *
	 * @param x
	 *     		The x-position for this new Planetoid.
	 * @param y
	 *        	The y-position for this new Planetoid.
	 * @param xVelocity
	 * 			The x-velocity for this new Planetoid.
	 * @param yVelocity
	 *       	The y-velocity for this new Planetoid.
	 * @param startRadius
	 *          The radius for this new Planetoid.
	 * @effect This new Planetoid is initialized as a new MinorPlanet with
	 * 		   given x, y, xVelocity, yVelocity, startRadius, no container 
	 * 		   and a mass corresponding to the startRadius of this Planetoid.
	 * 			| this(x, y, xVelocity, yVelocity, startRadius, null)
	 */
	@Raw
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double startRadius) throws IllegalArgumentException{
		this(x, y, xVelocity, yVelocity, startRadius, null);
	}

	/**
	 * The mass density for any Planetoid in kilograms per cubic kilometre.
	 */
	public static final double PLANETOID_MASS_DENSITY = 0.917e12;
	
	/**
	 * Return the lowest possible massDensity for any Planetoid.
	 * 
	 * @return 
	 * 			| PLANETOID_MASS_DENSITY
	 */
	public double getLowestMassDensity(){
		return PLANETOID_MASS_DENSITY;
	}
	
	/**
	 * Check whether this Planetoid can have the given mass as its mass.
	 *  
	 * @param  mass
	 *         The mass to check.
	 * @return 
	 *       | result == mass > 4.0 / 3.0 * Math.PI * Math.pow(MIN_RADIUS, 3) * PLANETOID_MASS_DENSITY
	*/
	@Raw
	public boolean canHaveAsMass(double mass){
		return mass > 4.0 / 3.0 * Math.PI * Math.pow(MIN_RADIUS, 3) * PLANETOID_MASS_DENSITY;
	}

	/**
	 * Move the Planetoid to a new position given a time duration,
	 * with respect to this Planetoid's current velocity.
	 * 
	 * @param  timeDelta
	 * 			The amount of time the Planetoid moves with current velocity
	 * @effect  | super.move(timeDelta)
	 * @post	| if(canHaveAsRadius(getRadius() - getVelocity().mul(timeDelta).getLength() * 0.000001)
	 * 			| then new.getRadius() == getRadius() - getVelocity().mul(timeDelta).getLength() * 0.000001
	 * @post	| if(!canHaveAsRadius(getRadius() - getVelocity().mul(timeDelta).getLength() * 0.000001)
	 * 			| then new.isTerminated()
	 */
	@Override
	public void move(double timeDelta) throws IllegalArgumentException{
		super.move(timeDelta);
		double distance = getVelocity().mul(timeDelta).getLength();
		double newRadius = getRadius() - distance * 0.000001;//Komt op hetzelfde neer als de totale afgelegde weg bijhouden TODO remove
		if(canHaveAsRadius(newRadius))
			this.setRadius(newRadius);	
		else
			this.terminate();
	}

	/**
	 * Return the radius of this Planetoid.
	 */
	@Basic
	@Raw
	public double getRadius(){
		return this.currentRadius;
	}

	/**
	 * Set the currentRadius of this Planetoid to the given radius.
	 * 
	 * @param  radius
	 *         The new currentRadius for this Planetoid.
	 * @post   The currentRadius of this new Planetoid is equal to
	 *         the given radius.
	 *       | new.getRadius() == radius
	 * @effect The mass of this Planetoid is updated with respect to
	 * 			the new radius.
	 * 		 | setMass(4.0 / 3.0 * Math.PI * Math.pow(radius, 3) * PLANETOID_MASS_DENSITY)
	 * @throws IllegalArgumentException
	 *         The given radius is not a valid radius for this Planetoid.
	 *       | ! canHaveAsRadius(getRadius())
	 */
	@Raw
	public void setRadius(double radius) throws IllegalArgumentException{
		if(!canHaveAsRadius(radius))
			throw new IllegalArgumentException();
		this.setMass(4.0 / 3.0 * Math.PI * Math.pow(radius, 3) * PLANETOID_MASS_DENSITY);		
		this.currentRadius = radius;
	}

	/**
	 * Variable registering the current radius of this Planetoid.
	 */
	private double currentRadius;
}
