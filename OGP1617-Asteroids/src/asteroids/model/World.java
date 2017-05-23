package asteroids.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * @invar  Each world has a valid width .
 *       | isValidWidth(this.getWidth())
 * @invar  Each world has a valid height .
 *       | isValidHeight(this.getHeight())
 * @invar  Each world must have proper entities.
 *       | hasProperItems()       
 */
public class World implements Container{

	/**
	 * Initialize this new World as a non-terminated world with given width and height
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
	 *       | new.getNbEntities() == 0
	 */
	@Raw
	public World(double width, double height){
		if(isValidWidth(width))
			this.width = width;
		else this.width = 0.0;
		if(isValidHeight(height))
			this.height = height;
		else this.height = 0.0;
	}

	/**
	 * Return the width of this world.
	 */
	@Basic
	@Raw
	@Immutable
	public double getWidth(){
		return this.width;
	}

	/**
	 * Check whether this world can have the given width as its width.
	 *  
	 * @param  Width
	 *         The width to check.
	 * @return 
	 *       | result == (0 <= width && width <= MAX_BOUNDARY)
	*/
	@Raw
	public boolean isValidWidth(double width){
		return (0 <= width && width <= MAX_BOUNDARY);
	}

	/**
	 * Variable registering the width of this world.
	 */
	private final double width;

	/**
	 * Return the height of this world.
	 */
	@Basic
	@Raw
	@Immutable
	public double getHeight(){
		return this.height;
	}

	/**
	 * Check whether this world can have the given height as its height.
	 *  
	 * @param  height
	 *         The height to check.
	 * @return 
	 *       | result == (0 <= height && height <= MAX_BOUNDARY)
	*/
	@Raw
	public boolean isValidHeight(double height){
		return (0 <= height && height <= MAX_BOUNDARY);
	}

	/**
	 * Variable registering the height of this world.
	 */
	private final double height;

	/**
	 * The maximum upperbound for the width and height for any World.
	 */
	private static final double MAX_BOUNDARY = Double.MAX_VALUE;
	
	/**
	 * Method to evolve the state of this World with a given time delta
	 * 
	 * @param timeDelta
	 * 			The amount of time to apply to the current state of this World.
	 * @throws IllegalArgumentException
	 * 			| timeDelta < 0.0 
	 * @throws ProgramExecutionTimeException
	 * 			When an error occurs during program execution
	 * @throws ExpressionEvaluationException
	 * 			When an error occurs during program exection,
	 * 			while evaluating an expression.
	 */
	public void evolve(double timeDelta) throws IllegalArgumentException, ProgramExecutionTimeException, ExpressionEvaluationException{
		if(timeDelta >= 0.0){
			CollisionData next = getNextCollision();
			if(next.getTimeToCollision() > timeDelta){
				evolveEntities(timeDelta);
			}else{
				evolveEntities(next.getTimeToCollision());
				next.resolve();
				evolve(timeDelta - next.getTimeToCollision());
			}	
		}else{
			throw new IllegalArgumentException();			
		}
	}
	
	/**
	 * Copy of evolve method with CollisionListener.
	 * 
	 * @see evolve(double timeDelta)
	 * @throws IllegalArgumentException
	 * 			| timeDelta < 0.0 || cl == null
	 * @throws ProgramExecutionTimeException
	 * 			When an error occurs during program execution
	 * @throws ExpressionEvaluationException
	 * 			When an error occurs during program exection,
	 * 			while evaluating an expression.
	 */
	public void evolve(double timeDelta, CollisionListener cl) throws IllegalArgumentException, ProgramExecutionTimeException, ExpressionEvaluationException{
		if(timeDelta >= 0.0 && cl != null){
			CollisionData next = getNextCollision();
			if(next.getTimeToCollision() > timeDelta){
				evolveEntities(timeDelta);
			}else{
				evolveEntities(next.getTimeToCollision());
				if(next.getCollisionType() == CollisionType.INTER_ENTITY){
					synchronized(cl){
						cl.notify();
						cl.objectCollision(next.getColliders().get(0), next.getColliders().get(1),
								next.getCollisionPoint().getX(), next.getCollisionPoint().getY());
					}
				}
				next.resolve();
				evolve(timeDelta - next.getTimeToCollision(), cl);
			}
		}else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Evolve the entities in this world with given timeDelta
	 * 
	 * @param timeDelta
	 * 			The given time delta
	 * @throws IllegalArgumentException
	 * 			| timeDelta < 0
	 * @throws ProgramExecutionTimeException
	 * 			When an error occurs during program execution
	 * @throws ExpressionEvaluationException
	 * 			When an error occurs during program exection,
	 * 			while evaluating an expression.
	 */
	private void evolveEntities(double timeDelta) throws IllegalArgumentException, ProgramExecutionTimeException, ExpressionEvaluationException{
		if(timeDelta < 0)
			throw new IllegalArgumentException();
		List<Entity> values = new ArrayList<>(entities.values());
		for(Entity entity : values)
			entity.evolve(timeDelta);
		
	}
	
	/**
	 * Update a given Entity in this Worlds entity collection
	 * 
	 * @param oldPos
	 * 		The position this Entity is stil mapped to.
	 * @param entity
	 * 		The given Entity with a new position
	 * @post	| new.getAllEntities().get(entity.getPosition()) == entity
	 * @throws IllegalArgumentException
	 * 			| !hasAsItem(entity) ||
	 * 			| !(getAllEntities().containsKey(oldPos) && getAllEntities().containsValue(entity))
	 */
	public void updateEntityEntry(Vector2d oldPos, Entity entity) throws IllegalArgumentException{
		if(hasAsItem(entity) && entities.remove(oldPos, entity))
			entities.put(entity.getPosition(), entity);
		else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Check whether or not an entity would collide with given position and radius with the bounds of this World.
	 * @param position
	 * 			The given position
	 * @param radius
	 * 			The given radius
	 * @see implementation
	 */
	public boolean isEntityCollidingBounds(Vector2d position, double radius){
		return (position.getX() > radius * 0.99 && position.getX() < radius * 1.01) ||
			   (position.getY() > radius * 0.99 && position.getY() < radius * 1.01) ||
			   (getWidth() - position.getX() > radius * 0.99 && getWidth() - position.getX() < radius * 1.01) ||
			   (getHeight() - position.getY() > radius * 0.99 && getHeight() - position.getY() < radius * 1.01);
	}
	
	/**
	 * Return the first occurring collision for this World.
	 * 
	 * @see implementation
	 */
	public CollisionData getNextCollision(){
		CollisionData boundaryCase = getNextBoundaryCollision();
		CollisionData entityCase = getNextEntityCollision();
		return boundaryCase.getTimeToCollision() < entityCase.getTimeToCollision() ? boundaryCase : entityCase;
	}
	
	/**
	 * Return the first occurring boundary collision for this World.
	 * Assuming the course of an entity does not get obstructed until 
	 * the colliding boundary.
	 * 
	 * @return  | if(entities.size() == 0)
	 * 			| then result.equals(CollisionData.UNDEFINED_COLLISION)
	 * @return  | for each entity in entities.values():
	 * 			| 	result.getTimeToCollision() <= entity.getBoundaryCollision().getTimeToCollision()
	 * @return  | if( ! result.getTimeToCollision().equals(CollisionData.UNDEFINED_COLLISION))
	 * 			|  	result.getCollisionType() == CollisionType.BOUNDARY
	 * @return  If no collision occurs the result equals CollisionData.UNDEFINED_COLLISION.
	 */
	public CollisionData getNextBoundaryCollision(){
		CollisionData firstBoundaryCollision = CollisionData.UNDEFINED_COLLISION;
		for(Entity e : entities.values()){
			CollisionData boundaryCase = e.getBoundaryCollisionData();
			if(boundaryCase.getTimeToCollision() < firstBoundaryCollision.getTimeToCollision())
				firstBoundaryCollision = boundaryCase;
		}
		return firstBoundaryCollision;
	}
	
	/**
	 * Return the first ocurring collision between entities for this World.
	 * 
	 * @return  | if(entities.size() == 0)
	 * 			| then result.equals(CollisionData.UNDEFINED_COLLISION)
	 * @return  | for each entity1, entity2 in entities.values():
	 * 			| 	result.getTimeToCollision() <= entity1.getTimeToCollision(entity2)
	 * @return  | if( ! result.getTimeToCollision().equals(CollisionData.UNDEFINED_COLLISION))
	 * 			|  	result.getCollisionType() == CollisionType.INTER_ENTITY
	 * @return  If no collision occurs the result equals CollisionData.UNDEFINED_COLLISION.
	 */
	public CollisionData getNextEntityCollision(){
		Set<Entity> unchecked = new HashSet<Entity>(entities.values());
		return checkEntityCollisions(unchecked);
	}
	
	/**
	 * Method containing the recursive implementation of getNextEntityCollision()
	 * 
	 * @param remainingEntities
	 * 			A set containing the unchecked entities
	 * @see specification getNextEntityCollision()
	 */
	private CollisionData checkEntityCollisions(Set<Entity> remainingEntities){
		Entity current = null;
		CollisionData fCollisionInvCurrent = CollisionData.UNDEFINED_COLLISION;
		if(remainingEntities.size() <= 1)
			return fCollisionInvCurrent;
		for(Entity entity : remainingEntities){
			if(current == null){
				current = entity;
				continue;
			}
			double collisionTime = current.getTimeToCollision(entity);
			if(collisionTime < fCollisionInvCurrent.getTimeToCollision())
				fCollisionInvCurrent = new CollisionData(collisionTime, current.getCollisionPosition(entity),
														CollisionType.INTER_ENTITY,
														Arrays.asList(new Entity[]{current, entity}));
		}
		remainingEntities.remove(current);
		CollisionData recursiveResult = checkEntityCollisions(remainingEntities);
		return fCollisionInvCurrent.getTimeToCollision() < recursiveResult.getTimeToCollision() ? 
				fCollisionInvCurrent : recursiveResult;
	}

	/**
	 * Terminate this World.
	 *
	 * @post   This World  is terminated.
	 *       | new.isTerminated()
	 * @post   If this World was not yet terminated, each Entity that belonged to this World
	 * 		   is now unbounded and has no container.
	 * 		 | if (! isTerminated())
	 *       |   then for each enitty in entities: 
	 *       		(new entity).getContainer() == null
	 * @post   If this World was not yet terminated, the set of bullets of this World
	 * 		   is now an empty set.
	 * 		 | if(! isTerminated())
	 * 		 |	 then new.getNbItems() == 0
	 */
	public void terminate(){
		if(!isTerminated()){
			for(Entity entity : new ArrayList<Entity>(entities.values())){
				entity.setContainer(null);
				removeItem(entity);
			}
			this.isTerminated = true;
		}
	}

	/**
	 * Return a boolean indicating whether or not this World
	 * is terminated.
	 */
	@Basic
	@Raw
	public boolean isTerminated(){
		return this.isTerminated;
	}

	/**
	 * Variable registering whether this person is terminated.
	 */
	private boolean isTerminated = false;

	/**
	 * Return whether or not an Entity with given position and radius is within bounds of this World
	 * @see implementation
	 */
	@Override
	public boolean isInBounds(Vector2d position, double radius){
		return (position.getX() >= radius * 0.99 && getWidth() - position.getX() >=  radius * 0.99) &&
				(position.getY() >= radius * 0.99 && getHeight() - position.getY() >=  radius * 0.99);
	}

	@Override
	@Basic
	@Raw
	public boolean hasAsItem(@Raw Entity item){
		return entities.containsValue(item);
	}

	/**
	 * Check whether this World can have the given Entity
	 * as one of its entities.
	 * 
	 * @param  item
	 *         The Entity to check.
	 * @return True if and only if the given Entity is effective and this World is a valid container
	 * 			for the Entity and the Entity is in the bounds of this World and
	 * 			references this World as its container and does not overlap with any other Entity of this World,
	 * 			and if the current container is not an instance of World.
	 *       | result == (item != null) && item.canHaveAsContainer(this) && isInBounds(item.getPosition(), item.getRadius())
	 *       | 				&& overlapsWithAnyEntity(item).size() == 0
	 */
	@Override
	@Raw
	public boolean canHaveAsItem(Entity item){
		return item != null && item.canHaveAsContainer(this) && isInBounds(item.getPosition(), item.getRadius())
				&& overlapsWithAnyEntity(item).size() == 0;
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
	 *       |     then canHaveAsItem(entity) && (entity.getContainer() == this) && overlapsWithAnyEntity(entity).size() == 0
	 */
	@Override
	public boolean hasProperItems(){
		for(Entity entity : entities.values()){
			if(!canHaveAsItem(entity))
				return false;
			if(entity.getContainer() != this)
				return false;
			if(overlapsWithAnyEntity(entity).size() > 0)
				return false;
		}
		return true;
	}

	@Override
	@Basic
	@Raw
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
	 * 		   The given Entity is cannot be an entity of this Worlds entities.
	 * 			| !canHaveAsItem(item) || hasAsItem(item) || item.getContainer() != this 
	 */
	@Override
	public void addItem(Entity item) throws IllegalArgumentException{
		if(!canHaveAsItem(item) || item.getContainer() != this || hasAsItem(item))
			throw new IllegalArgumentException();
		entities.put(item.getPosition(), item);
	}

	/**
	 * Remove the given item from the entities of this World.
	 * 
	 * @param  item
	 *         The Entity to be removed.
	 * @post   This World no longer has the given Entity as
	 *         one of its entities.
	 *       | ! new.hasAsItem(item)
	 * @throws IllegalArgumentException
	 * 		   The World does not have the given Entity as one of its entities
	 * 		   or the given Entity still references any World as its container or the given Entity is null.
	 * 			| !this.hasAsItem(item) || item.getContainer() != null
	 */
	@Override
	@Raw
	public void removeItem(Entity item) throws IllegalArgumentException{
		if(!this.hasAsItem(item) || item.getContainer() != null)
			throw new IllegalArgumentException();
		entities.remove(item.getPosition());
	}
  
    /**
	 * Return a set of all the entities of this world.
	 * 
	 * @return The size of the resulting set is equal to the number of
	 *         entities of this world.
	 *       | result.size() == getNbEntities()
	 * @return Each entity in the resulting set is an item of this World.
	 * 		 | for each entity in result:
	 * 		 | 	this.hasAsItem(entity)
	 */
	public Set<Entity> getAllEntities() {
		return new HashSet<Entity>(entities.values());
	}
	
	/**
	* Return a set of all the Ships of this world.
	* 
	* @return Each ship in the resulting set is an item of this World.
	 * 		 | for each ship in result:
	 * 		 | 	this.hasAsItem(ship)
	*/
	public Set<Ship> getShips(){
		return entities.values().stream().filter(e -> e instanceof Ship).map(e->(Ship)e).collect(Collectors.toSet());
	}
	
	/**
	* Return a set of all the bullets of this world.
	* 
	* @return Each bullet in the resulting set is an item of this World.
	 * 		 | for each bullet in result:
	 * 		 | 	this.hasAsItem(bullet)
	*/
	public Set<Bullet> getBullets(){
		return entities.values().stream().filter(e -> e instanceof Bullet).map(e->(Bullet)e).collect(Collectors.toSet());
	}
	
	/**
	* Return a set of all the minor planets of this world.
	* 
	* @return Each minor planet in the resulting set is an item of this World.
	 * 		 | for each mPlanet in result:
	 * 		 | 	this.hasAsItem(mPlanet)
	*/
	public Set<MinorPlanet> getMinorPlanets(){
		return entities.values().stream().filter(e -> e instanceof MinorPlanet).map(e->(MinorPlanet)e).collect(Collectors.toSet());
	}
	
	/**
	* Return a set of all the asteroids of this world.
	* 
	* @return Each asteroid in the resulting set is an item of this World.
	 * 		 | for each asteroid in result:
	 * 		 | 	this.hasAsItem(asteroid)
	*/
	public Set<Asteroid> getAsteroids(){
		return entities.values().stream().filter(e -> e instanceof Asteroid).map(e->(Asteroid)e).collect(Collectors.toSet());
	}
	
	/**
	* Return a set of all the planetoids of this world.
	* 
	* @return Each planetoid in the resulting set is an item of this World.
	 * 		 | for each planetoid in result:
	 * 		 | 	this.hasAsItem(planetoid)
	*/
	public Set<Planetoid> getPlanetoids(){
		return entities.values().stream().filter(e -> e instanceof Planetoid).map(e->(Planetoid)e).collect(Collectors.toSet());
	}
	
	/**
	 * Return a set of entities that satisfy the given filter.
	 * 
	 * @return Each entity in the resulting set is an item of this World.
	 * 		 | for each entity in result:
	 * 		 | 	this.hasAsItem(entity)
	 */
	public Set<Entity> getEntitiesByFilter(Predicate<? super Entity> filter){
		return entities.values().stream().filter(filter).collect(Collectors.toSet());
	}
	
	/**
	 * Return an Entity at the given position,
	 * otherwise the result is null.
	 * @param position
	 * 			The given position
	 * @return  | if(entities.hasKey(position))
	 * 			| then result.getPosition() == position
	 * 			| else result == null
	 * 
	 */
	public Entity getEntityAt(Vector2d position){
		return entities.get(position);
	}
	
	/**
	 * Return the entities which the given entity overlaps with in this world.
	 * This function is used for an entity not yet in the entities of this world,
	 * otherwise this function returns an empty list.
	 * 
	 * @param entity
	 * 		The entity to check.
	 * @return The resulting list contains entities that overlap with the given entity
	 * 		| for each other in result:
	 * 		|	entity.overlaps(other) && entity != other
	 * @throws NullPointerException
	 * 		| entity == null
	 */
	public List<Entity> overlapsWithAnyEntity(Entity entity) throws NullPointerException{
		if (entity == null)
			throw new NullPointerException();
		return entities.values().stream().filter(e -> e.overlaps(entity) && entity != e).collect(Collectors.toList());
	}
	
	/**
	 * Return the entities which an object with given radius and position, would overlap with in this world.
	 * 
	 * @param position 
	 * 		The given position.
	 * @return The resulting list contains entities that overlap with an entity with given position 
	 * 			and radius.
	 * 		| for each other in result:
	 * 		|	entity.overlapsCircle(position, radius)
	 * @throws NullPointerException
	 * 		| position == null
	 */
	public List<Entity> overlapsWithAnyEntity(Vector2d position, double radius) throws NullPointerException{
		if (position == null)
			throw new NullPointerException();
		return entities.values().stream().filter(e -> e.overlapsCircle(position, radius)).collect(Collectors.toList());
	}

	/**
	 * Variable referencing a map collecting all the entities
	 * of this world mapped by their position.
	 * 
	 * @invar  The referenced map is effective.
	 *       | entities != null
	 * @invar  Each entity registered in the referenced set is
	 *         effective and not yet terminated.
	 *       | for each {position, entity} in entities:
	 *       |   ( (entity != null) &&
	 *       |     (! entity.isTerminated()) )
	 * @invar The Entities are mapped to their current position
	 * 		 | for each {position, entity} in entities:
	 *       |   entity.getPosition().equals(position)
	 */
	private final Map<Vector2d, Entity> entities = new HashMap<Vector2d, Entity>();

}
