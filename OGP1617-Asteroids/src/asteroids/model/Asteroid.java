package asteroids.model;

import be.kuleuven.cs.som.annotate.Raw;

/**
 * A Class representing an Asteroid in the game.
 */
public class Asteroid extends MinorPlanet{
	
	/**
	 * Initialize this new Asteroid with given x, y, xVelocity, yVelocity, radius, mass and container.
	 *
	 * @param x
	 *     		The x-position for this new Asteroid.
	 * @param y
	 *        	The y-position for this new Asteroid.
	 * @param xVelocity
	 * 			The x-velocity for this new Asteroid.
	 * @param yVelocity
	 *       	The y-velocity for this new Asteroid.
	 * @param radius
	 *          The radius for this new Asteroid.
	 * @param container
	 * 			The container for this new Asteroid.
	 * @effect This new Asteroid is initialized as a new MinorPlanet with
	 * 		   given x, y, xVelocity, yVelocity, radius, container and a mass corresponding 
	 * 			to the radius of this Asteroid.
	 * 			| super(x, y, xVelocity, yVelocity, radius, 4.0/3.0*Math.PI*Math.pow(radius, 3)*ASTEROID_MASS_DENSITY, container)
	 */
	@Raw
	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius, Container container) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, 4.0/3.0*Math.PI*Math.pow(radius, 3)*ASTEROID_MASS_DENSITY, container);
	}
	
	/**
	 * Initialize this new Asteroid with given x, y, xVelocity, yVelocity, radius, mass.
	 * This Asteroid has no Container.
	 *
	 * @param x
	 *     		The x-position for this new Asteroid.
	 * @param y
	 *        	The y-position for this new Asteroid.
	 * @param xVelocity
	 * 			The x-velocity for this new Asteroid.
	 * @param yVelocity
	 *       	The y-velocity for this new Asteroid.
	 * @param radius
	 *          The radius for this new Asteroid.
	 * @effect This new Asteroid is initialized as a new MinorPlanet with
	 * 		   given x, y, xVelocity, yVelocity, radius, no container 
	 * 		   and a mass corresponding to the radius of this Asteroid.
	 * 			| super(x, y, xVelocity, yVelocity, radius, 4.0/3.0*Math.PI*Math.pow(radius, 3)*ASTEROID_MASS_DENSITY, null)
	 */
	@Raw
	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, 4.0/3.0*Math.PI*Math.pow(radius, 3)*ASTEROID_MASS_DENSITY, null);
	}

	/**
	 * The mass density for any Asteroid in kilograms per cubic kilometer.
	 */
	public static final double ASTEROID_MASS_DENSITY = 2.65e12;
	
	/**
	 * Return the lowest possible massDensity for any Asteroid.
	 * 
	 * @return | ASTEROID_MASS_DENSITY
	 */
	public double getLowestMassDensity(){
		return ASTEROID_MASS_DENSITY;
	}
	
	/**
	 * Check whether this Asteroid can have the given mass as its mass.
	 *  
	 * @param  mass
	 *         The mass to check.
	 * @return 
	 *       | result == mass > 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * ASTEROID_MASS_DENSITY
	*/
	@Raw
	public boolean canHaveAsMass(double mass){
		return mass == 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * ASTEROID_MASS_DENSITY;
	}
}
