package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;


public class Bullet extends Entity{

	/**
	 * Initialize this new Bullet with given x, y, xVelocity, yVelocity, radius and mass.
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
	 * 			| super(x, y, xVelocity, yVelocity, radius, mass, container)
	 */
	protected Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass, Container<Entity> container) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, mass, container);
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
	 * Return the source of this Bullet if any exists.
	 *  
	 * @return Return the source of this Bullet if it has been fired by a Ship
	 *         or is attached to a Ship. Otherwise this method returns null.
	 * 		| if(getContainer() instanceof Ship) then result == (Ship getContainer())
	 * 		| else result == null
	 */
	public Ship getSource(){
		if(getContainer() instanceof Ship)
			return (Ship)getContainer();
		return null;
	}
	
	/**
	 * Terminate this Bullet.
	 *
	 * @post   This Bullet  is terminated.
	 *       | new.isTerminated()
	 * @post   TODO...
	 *       | ...
	 */
	 public void terminate() {
		 this.isTerminated = true;
	 } 
}
