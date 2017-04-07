package asteroids.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * @invar  Each world can have its width as width .
 *       | canHaveAsWidth(this.getWidth())
 * @invar  Each world can have its height as height .
 *       | canHaveAsHeight(this.getHeight())
 * @invar  Each world must have proper entities.
 *       | hasProperItems()       
 */
public class World implements Container<Entity>{

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
		if (isValidWidth(width))
			this.width = width;
		else
			this.width = 0.0;
		if (isValidHeight(height))
			this.height = height;
		else
			this.height = 0.0;
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
	 * Terminate this World.
	 *
	 * @post   This World  is terminated.
	 *       | new.isTerminated()
	 * @post   TODO...
	 *       | ...
	 */
	 public void terminate() {
		 this.isTerminated = true;
	 }
	 
	 /**
	  * Return a boolean indicating whether or not this World
	  * is terminated.
	  */
	 @Basic @Raw
	 public boolean isTerminated() {
		 return this.isTerminated;
	 }
	 
	 /**
	  * Variable registering whether this person is terminated.
	  */
	 private boolean isTerminated = false;

	@Override
	public boolean isInBounds(Entity item){
		// TODO Auto-generated method stub
		return false;
	}

	@Override @Basic @Raw
	public boolean hasAsItem(@Raw Entity item){
		return entities.contains(item);
	}

	/**
	 * Check whether this World can have the given Entity
	 * as one of its entities.
	 * 
	 * @param  item
	 *         The Entity to check.
	 * @return True if and only if the given Entity is effective
	 *         and is a valid Entity for a World.
	 *       | result == (item != null) && item.canHaveAsContainer(this);
	 */
	@Override @Raw
	public boolean canHaveAsItem(Entity item){
		return item != null && item.canHaveAsContainer(this);
	}

	/**
	 * Check whether this World has proper Entities attached to it.
	 * 
	 * @return True if and only if this World can have each of the
	 *         entities attached to it as one of its entities,
	 *         and if each of these entities references this World as
	 *         the World to which they are attached.
	 *       | for each entity in entities:
	 *       |   if (hasAsItem(entity))
	 *       |     then canHaveAsItem(entity) && (entity.getContainer() == this) 
	 */
	@Override
	public boolean hasProperItems(){
		for(Entity entity : entities){
			if(!canHaveAsItem(entity))
				return false;
			if(entity.getContainer() != this)
				return false;
		}
		return true;
	}

	@Override @Basic @Raw
	public int getNbItems(){
		return entities.size();
	}

	/**
	 * Add the given item to the set of entities of this World.
	 * 
	 * @param  item
	 *         The Entity to be added.
	 * @post   This World has the given Entity as one of its entities.
	 * 			| new.hasAsItem(item)
	 * @throws IllegalArgumentException
	 * 		   The given Entity is cannot be an entity of this Worlds entities
	 * 		   or does not reference this World as its container.
	 * 			| !canHaveAsItem(item) || (item.getContainer() != this)
	 */
	@Override
	public void addItem(Entity item) throws IllegalArgumentException{
		if(!canHaveAsItem(item) || item.getContainer() != this)
			throw new IllegalArgumentException();
		entities.add(item);
	}

	/**
	 * Remove the given item from the set of entities of this World.
	 * 
	 * @param  item
	 *         The Entity to be removed.
	 * @post   This World no longer has the given Entity as
	 *         one of its entities.
	 *       | ! new.hasAsItem(item)
	 * @throws IllegalArgumentException
	 * 		   The World does not have the given Entity as one of its entities
	 * 		   or the given Entity still references any World as its container.
	 * 			| !this.hasAsItem(item) || item.getContainer() != null
	 */
	@Override @Raw
	public void removeItem(Entity item) throws IllegalArgumentException{
		if(!this.hasAsItem(item) || item.getContainer() != null)
			throw new IllegalArgumentException();
		assert entities.remove(item);
	}

	@Override
	public boolean isTerminatedContainer(){
		return this.isTerminated;
	}
	
	/**
	 * Variable referencing a set collecting all the entities
	 * of this world.
	 * 
	 * @invar  The referenced set is effective.
	 *       | entities != null
	 * @invar  Each entity registered in the referenced set is
	 *         effective and not yet terminated.
	 *       | for each entity in entities:
	 *       |   ( (entity != null) &&
	 *       |     (! entity.isTerminated()) )
	 */
	private final Set<Entity> entities = new HashSet<Entity>();
	
	/**
	 * Return a set of all the entities of this world.
	 * 
	 * @return The size of the resulting set is equal to the number of
	 *         entities of this world.
	 *       | result.size() == getNbEntities()
	 */
	public Set<Entity> getAllEntities() {
		return new HashSet<Entity>(entities);
	}
	
	/**
	 * Check whether the given entity overlaps with any entities in this world.
	 * 
	 * @param entity
	 * 		The entity to check.
	 * @return False if and only if none of the entities from this world overlap with the given entity.
	 * 		| 	for each other in entities:
	 * 		|		if (entity.overlaps(other) == true)
	 * 		|			result == true
	 * 		|	result == false
	 * @throws NullPointerException
	 * 		| entity == null
	 */
	public boolean overlapWithAnyEntity(Entity entity) throws NullPointerException{
		if (entity == null)
			throw new NullPointerException();
		for (Entity other : entities) {
			if (entity.overlaps(other))
				return true;
		}
		return false;
	}
}