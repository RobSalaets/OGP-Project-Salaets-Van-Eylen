package asteroids.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;


public class World{
	/**
	 * @invar  Each world can have its width as width .
	 *       | canHaveAsWidth(this.getWidth())
	 * @invar  Each world can have its height as height .
	 *       | canHaveAsHeight(this.getHeight())
	 * @invar  Each world must have proper entities.
	 *       | hasProperEntities()       
	 */

	/**
	 * Initialize this new world as a non-terminated world with given width and height
	 * with no entities yet
	 * 
	 * @param  width
	 *         The width for this new world.
	 * @param  height
	 *         The height for this new world.
	 * @post   If the given width is a valid width for any world,
	 *         the width of this new world is equal to the given
	 *         width. Otherwise, the width of this new world is equal
	 *         to 0.
	 *       | if (isValidWidth(Width))
	 *       |   then new.getWidth() == width
	 *       |   else new.getWidth() == 0
	 * @post   If the given height is a valid height for any world,
	 *         the height of this new world is equal to the given
	 *         height. Otherwise, the height of this new world is equal
	 *         to 0.
	 *       | if (isValidHeight(height))
	 *       |   then new.getHeight() == height
	 *       |   else new.getHeight() == 0
	 * @post   This new world has no entities yet.
	 *       | new.getNbEntitys() == 0
	 */
	@Raw
	public World(double width, double height) {
		if (! isValidWidth(width))
			width = 0;
		this.width = width;
		if (! isValidHeight(height))
			height = 0;
		this.height = height;
	}
	
	/**
	 * Return the width of this world.
	 */
	@Basic @Raw @Immutable
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Check whether this world can have the given width as its width.
	 *  
	 * @param  Width
	 *         The width to check.
	 * @return 
	 *       | result == (0 < width && width < maxWidth)
	*/
	@Raw
	public boolean isValidWidth(double width) {
		return (0 < width && width < MAX_WIDTH);
	}
	
	/**
	 * Variable registering the width of this world.
	 */
	private final double width;
	
	/**
	 * The maximum upperbound for the width for any World.
	 */
	private static final double MAX_WIDTH = Double.MAX_VALUE ;

	/**
	 * Return the height of this world.
	 */
	@Basic @Raw @Immutable
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * Check whether this world can have the given height as its height.
	 *  
	 * @param  height
	 *         The height to check.
	 * @return 
	 *       | result == (0 < height && height < maxHeight)
	*/
	@Raw
	public boolean isValidHeight(double height) {
		return (0 < height && height < MAX_HEIGHT);
	}
	
	/**
	 * Variable registering the height of this world.
	 */
	private final double height;
	
	/**
	 * The maximum upperbound for the height for any World.
	 */
	private static final double MAX_HEIGHT = Double.MAX_VALUE ;
	
	
	
	/**
	 * Check whether this world has the given entity as one of
	 * its entities.
	 *
	 * @param  entity
	 *         The entity to check.
	 * @return True if and only if this world has the given entity
	 *         as one of its entities at some index.
	 *       | result ==
	 *       |   for some index in 1..getNbEntities():
	 *       |     getEntityAt(index).equals(entity)
	 */
	@Basic
	@Raw
	public boolean hasAsEntity(Entity entity) {
		return entities.contains(entity);
	// getEntity at index moet nog uitgewerkt worden
	}

	/**
	 * Check whether this world can have the given entity
	 * as one of its entities.
	 * 
	 * @param  entity
	 *         The entity to check.
	 * @return True if and only if the given entity is effective, and
	 *         if that entity can have this world as its world.
	 *       | result ==
	 *       |   (entity != null) &&
	 *       |   entity.canHaveAsWorld(this)
	 */
	@Raw
	public boolean canHaveAsEntity(Entity entity) {
		return (entity != null) && entity.canHaveAsWorld(this);
	}
	
	/**
	 * Check whether this world has proper entities attached to it.
	 * 
	 * @return True if and only if this world can have each of the
	 *         entities attached to it as one of its entities,
	 *         and if each of these entities references this world as
	 *         the world to which they are attached.
	 *       | for each entity in Entity:
	 *       |   if (hasAsEntity(entity))
	 *       |     then canHaveAsEntity(entity) &&
	 *       |          (entity.getWorld() == this)
	 */
	public boolean hasProperEntitys() {
		for (Entity entity : entities) {
			if (!canHaveAsEntity(entity))
				return false;
			if (entity.getWorld() != this)
				return false;
		}
		return true;
	}

	/**
	 * Return the number of entities associated with this world.
	 *
	 * @return  The total number of entities collected in this world.
	 *        | result ==
	 *        |   card({entity:Entity | hasAsEntity({entity)})
	 */
	@Basic
	@Raw
	public int getNbEntities() {
		return entities.size();
	}

	/**
	 * Add the given entity to the set of entities of this world.
	 * 
	 * @param  entity
	 *         The entity to be added.
	 * @pre    The given entity is effective and already references
	 *         this world.
	 *       | (entity != null) && (entity.getWorld() == this)
	 * @post   This world has the given entity as one of its entities.
	 *       | new.hasAsEntity(entity)
	 */
	public void addEntity(@Raw Entity entity) {
		assert (entity != null) && (entity.getWorld() == this);
		entities.add(entity);
	}

	/**
	 * Remove the given entity from the set of entities of this world.
	 * 
	 * @param  entity
	 *         The entity to be removed.
	 * @pre    This world has the given entity as one of
	 *         its entities, and the given entity does not
	 *         reference any world.
	 *       | this.hasAsEntity(entity) &&
	 *       | (entity.getWorld() == null)
	 * @post   This world no longer has the given entity as
	 *         one of its entities.
	 *       | ! new.hasAsEntity(entity)
	 */
	@Raw
	public void removeEntity(Entity entity) {
		assert this.hasAsEntity(entity) && (entity.getWorld() == null);
		entities.remove(entity);
	}

	/**
	 * Variable referencing a set collecting all the entities
	 * of this world.
	 * 
	 * @invar  The referenced set is effective.
	 *       | entities != null
	 * @invar  Each entity registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each entity in entities:
	 *       |   ( (entity != null) &&
	 *       |     (! entity.isTerminated()) )
	 */
	private final Set<Entity> entities = new HashSet<Entity>();
	
	/**
	 * Return the entity of this world at the given index.
	 * 
	 * @param  index
	 *         The index of the entity to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of entities of this world.
	 *       | (index < 1) || (index > getNbEntities())
	 */
	@Basic
	@Raw
	public Entity getEntityAt(int index) throws IndexOutOfBoundsException {
		return entities.get(index - 1);
	}
	
	/**
	 * Return the index at which the given entity is registered
	 * in the set of entities for this world.
	 *  
	 * @param  entity
	 *         The entity to search for.
	 * @return If this world has the given entity as one of its
	 *         entities, that entity is registered at the resulting
	 *         index. Otherwise, the resulting value is -1.
	 *       | if (hasAsEntity(entity))
	 *       |    then getEntityAt(result) == entity
	 *       |    else result == -1
	 */
	@Raw
	public int getIndexOfOwning(Entity entity) {
		return entities.indexOf(entity);
	}
	
	/**
	 * Check whether this world can have the given entity
	 * as one of its entities at the given index.
	 * 
	 * @param  entity
	 *         The entity to check.
	 * @param  index
	 *         The index to check.
	 * @return False if the given index is not positive or exceeds
	 *         the number of entities of this world + 1.
	 *       | if ( (index < 1) || (index > getNbentities()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this world cannot have the
	 *         given entity as one of its entities.
	 *       | else if (! canHaveAsEntity(entity))
	 *       |   then result == false
	 *         Otherwise, true if and only if the given entity is
	 *         not already registered at another index.
	 *       | else result ==
	 *       |   for each i in 1..getNbEntities():
	 *       |     ( (i == index) || (getEntityAt(i) != entity) )
	 */
	@Raw
	public boolean canHaveAsEntityAt(Entity entity, int index) {
		if ((index < 1) || (index > getNbEntities() + 1))
			return false;
		if (!canHaveAsEntity(entity))
			return false;
		for (int pos = 1; pos <= getNbEntities(); pos++)
			if ((pos != index) && (getEntityAt(pos) == entity))
				return false;
		return true;
	}
	
	/**
	 * Check whether this world is terminated.
	 */
	@Basic
	@Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Variable reflecting whether or not this world is terminated.
	 */
	private boolean isTerminated;
	
	
}
