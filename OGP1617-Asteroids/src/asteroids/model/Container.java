package asteroids.model;

/**
 * An interface of an entity container in the game
 * 
 */
public interface Container{
	
	public boolean isInBounds(Vector2d position, double radius);
	
	/**
     * Check whether this container has the given item as one
     * of its items.
     * @param item
     * 		The item to check.
     */
	public boolean hasAsItem(Entity item);
	
	public boolean canHaveAsItem(Entity item);
	
	public boolean hasProperItems();
	
	/**
	 * Return the number of items associated with this Container.
	 *
	 * @return  The total number of items collected in this Container.
	 *        | result == card({item:T | hasAsItem({item)})
	 */
	public int getNbItems();
	
	public void addItem(Entity item);
	
	public void removeItem(Entity item);

	/**
	 * Return whether or not this Container
	 * is terminated.
	 */
	public boolean isTerminated();
	
}
