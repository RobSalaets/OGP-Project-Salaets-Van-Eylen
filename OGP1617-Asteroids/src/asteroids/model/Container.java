package asteroids.model;

/**
 * An interface of a container in the game
 * 
 * @param T
 * 		The type of Entity being an item of a container
 */
public interface Container<T extends Entity>{
	
	public boolean isInBounds(Vector2d position, double radius);
	
	/**
     * Check whether this container has the given item as one
     * of its items.
     * @param item
     * 		The item to check.
     */
	public boolean hasAsItem(T item);
	
	public boolean canHaveAsItem(T item);
	
	public boolean hasProperItems();
	
	/**
	 * Return the number of items associated with this Container.
	 *
	 * @return  The total number of items collected in this Container.
	 *        | result == card({item:T | hasAsItem({item)})
	 */
	public int getNbItems();
	
	public void addItem(T item);
	
	public void removeItem(T item);

	/**
	 * Return whether or not this Container
	 * is terminated.
	 */
	public boolean isTerminatedContainer();
	
}
