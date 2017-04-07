package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

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
	@Raw
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass, Container<Entity> container) throws IllegalArgumentException{
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
	@Raw
	public boolean canHaveAsContainer(Container<Entity> container){
		if(this.isTerminated())
			return container == null;
		return (container == null) || ((container instanceof World || container instanceof Ship) && (!container.isTerminatedContainer()));
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
			return (Ship) getContainer();
		return null;
	}

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
	 *       |   then ! (new getContainer()).hasAsItem(this))
	 * @post   If this Bullet was not yet terminated, the size of
	 *         the container to which this Bullet belonged is decremented by 1.
	 *       | if (! isTerminated())
	 *       |   then (new getContainer()).getNbItems() ==
	 *       |            getContainer().getNbItems() - 1
	 */
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
