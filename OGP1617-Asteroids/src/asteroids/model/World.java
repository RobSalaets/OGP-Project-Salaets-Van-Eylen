package asteroids.model;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;


public class World{
	/**
	 * @invar  Each world can have its width as width .
	 *       | canHaveAsWidth(this.getWidth())
	 * @invar  Each world can have its height as height .
	 *       | canHaveAsHeight(this.getHeight())       
	 */

	/**
	 * Initialize this new world with given width and height.
	 * 
	 * @param  Width
	 *         The width for this new world.
	 * @post   If the given width is a valid width for any world,
	 *         the width of this new world is equal to the given
	 *         width. Otherwise, the width of this new world is equal
	 *         to 0.
	 *       | if (isValidWidth(Width))
	 *       |   then new.getWidth() == Width
	 *       |   else new.getWidth() == 0
	 * @param  height
	 *         The height for this new world.
	 * @post   If the given height is a valid height for any world,
	 *         the height of this new world is equal to the given
	 *         height. Otherwise, the height of this new world is equal
	 *         to 0.
	 *       | if (isValidHeight(height))
	 *       |   then new.getHeight() == height
	 *       |   else new.getHeight() == 0
	 */
	public World(double width, double height) {
		if (! canHaveAsWidth(width))
			width = 0;
		this.width = width;
		if (! canHaveAsHeight(height))
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
	public boolean canHaveAsWidth(double width) {
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
	public boolean canHaveAsHeight(double height) {
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
	
	/**
	 * Variable referencing a list collecting all the entities of
	 * this world.
	 * 
	 * @invar  The referenced list is effective.
	 *       | Entities != null
	 * @invar  Each entity registered in the referenced list is
	 *         effective and not yet terminated.
	 *       | for each entity in entities:
	 *       |   ( (entity != null) && (!entity.isTerminated()) )
	 */
	private final List<Entity> entities = new ArrayList<Entity>();
	
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
	@Raw
	public boolean hasAsEntity(Entity entity) {
		return entities.contains(entity);
	// getEntity at index moet nog uitgewerkt worden
	}
	
	/**
	 * Return the number of entities of this world.
	 */
	@Basic
	@Raw
	public double getNbEntities() {
		return entities.size();
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
}
