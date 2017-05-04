package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * An abstract class respresenting a MinorPlanet
 */
public abstract class MinorPlanet extends Entity{

	/**
	 * Initialize this new MinorPlanet with given x, y, xVelocity, yVelocity, radius, mass and container.
	 *
	 * @param x
	 *     		The x-position for this new MinorPlanet.
	 * @param y
	 *        	The y-position for this new MinorPlanet.
	 * @param xVelocity
	 * 			The x-velocity for this new MinorPlanet.
	 * @param yVelocity
	 *       	The y-velocity for this new MinorPlanet.
	 * @param radius
	 *          The radius for this new MinorPlanet.
	 * @param mass
	 * 			The mass for this new MinorPlanet.
	 * @param container
	 * 			The container for this new MinorPlanet.
	 * @effect This new MinorPlanet is initialized as a new Entity with
	 * 		   given x, y, xVelocity, yVelocity, radius, mass and container.
	 * 			| super(x, y, xVelocity, yVelocity, radius, mass, container)
	 */
	@Model
	@Raw
	protected MinorPlanet(double x, double y, double xVelocity, double yVelocity, double radius, double mass, Container<Entity> container) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, mass, container);
	}

	/**
	 * The mininum radius for any minor planet in kilometres
	 */
	public static final double MIN_RADIUS = 5.0;

	@Basic
	@Override
	public double getMinRadius(){
		return MIN_RADIUS;
	}

	/**
	 * Resolve given collision case appropriatly
	 * 
	 * @param collisionData
	 * 			The given collision case
	 * @effect 	If the collisionType is BOUNDARY the boundary collision is resolved
	 * 			| if(collisionData.getCollisionType() == CollisionType.BOUNDARY) 
	 * 			| then resolveBoundaryCollision(collisionData)
	 * @effect 	If the collisionType is INTER_ENTITY and the other entity is a MinorPlanet,
	 * 			the collision will be handled so the entities bounce off each other.
	 * 			| if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY && 
	 * 			|	 collisionData.getOther(this) instanceof MinorPlanet) 
	 * 			| then resolveBounceCollision(collisionData.getOther(this), getMass(),
	 * 			|								 collisionData.getOther(this).getMass())
	 * @effect 	If the collisionType is INTER_ENTITY and the other entity is a Bullet,
	 * 			the collision will be resolved by the bullet.
	 * 			| if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY && 
	 * 			|		collisionData.getOther(this) instanceof Bullet) 
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
			if(other instanceof MinorPlanet)
				resolveBounceCollision(other, getMass(), ((MinorPlanet) other).getMass());
			else if(other instanceof Bullet)
				other.resolve(collisionData);
		}else{
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Terminate this MinorPlanet.
	 *
	 * @post   This MinorPlanet  is terminated.
	 *       | new.isTerminated()
	 * @post   This MinorPlanet no longer references an effective container.
	 * 		 | if (! isTerminated())
	 *       | then new.getContainer() == null
	 * @post   If this MinorPlanet was not yet terminated, this MinorPlanet
	 *         is no longer one of the Entities for the Container to which
	 *         this MinorPlanet belonged.
	 *       | if (! isTerminated())
	 *       |   then ! new.getContainer().hasAsItem(this))
	 * @post   If this MinorPlanet was not yet terminated, the size of
	 *         the container to which this MinorPlanet belonged is decremented by 1.
	 *       | if (! isTerminated())
	 *       |   then new.getContainer().getNbItems() ==
	 *       |            getContainer().getNbItems() - 1
	 * @post  If this MinorPlanet was not yet terminated, the source of this MinorPlanet
	 * 		  is not effective.
	 * 		 | if (! isTerminated())
	 * 		 | new.getSource() == null
	 */
	@Override
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

	/**
	 * Check whether this MinorPlanet can have the given container as
	 * its container.
	 * 
	 * @param  container
	 * 		   The container to check.
	 * @return If this MinorPlanet is terminated, true if and only if the
	 *         given Container is not effective.
	 *       | if (this.isTerminated())
	 *       |   then result == (container == null)
	 * @return If this MinorPlanet is not terminated, true if and only if the given
	 *         Container is not effective or an instance of World and not yet terminated.
	 *       | if (! this.isTerminated())
	 *       |   then result == (container == null) && ((container instanceof World) && (!container.isTerminatedContainer()))
	 */
	@Raw @Override
	public boolean canHaveAsContainer(Container<Entity> container){
		if(this.isTerminated())
			return container == null;
		return (container == null) ||  ((container instanceof World) && (!container.isTerminated()));
	}
}