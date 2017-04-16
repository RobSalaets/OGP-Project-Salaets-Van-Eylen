package asteroids.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import asteroids.part2.CollisionListener;
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
	 */
	public void evolve(double timeDelta) throws IllegalArgumentException{
		if(timeDelta < 0.0)
			throw new IllegalArgumentException();
		CollisionData next = getNextCollision();
		if(next.getTimeToCollision() > timeDelta){
			advanceEntities(timeDelta);
		}else{
			advanceEntities(next.getTimeToCollision());
			resolve(next);
			evolve(timeDelta - next.getTimeToCollision());
		}
	}
	
	/**
	 * Copy of evolve method with CollisionListener.
	 * 
	 * @see evolve(double timeDelta)
	 * @throws IllegalArgumentException
	 * 			| timeDelta < 0.0 || cl == null
	 */
	public void evolve(double timeDelta, CollisionListener cl) throws IllegalArgumentException{
		if(timeDelta < 0.0 || cl == null)
			throw new IllegalArgumentException();
		CollisionData next = getNextCollision();
		if(next.getTimeToCollision() > timeDelta){
			advanceEntities(timeDelta);
		}else{
			advanceEntities(next.getTimeToCollision());
			if(next.getCollisionType() == CollisionType.INTER_ENTITY){
				synchronized(cl){
					cl.notify();
					cl.objectCollision(next.getColliders().get(0), next.getColliders().get(1),
							next.getCollisionPoint().getX(), next.getCollisionPoint().getY());
				}
			}
			
			resolve(next);
			evolve(timeDelta - next.getTimeToCollision(), cl);
		}
	}
	
	/**
	 * Advance the entities in this world with given timeDelta
	 * @param timeDelta
	 * 			The given time delta
	 */
	private void advanceEntities(double timeDelta){
		List<Entity> values = new ArrayList<>(entities.values());
		entities.clear();
		for(Entity entity : values){
			entity.move(timeDelta);
			if(entity instanceof Ship && ((Ship) entity).getThrusterStatus())
				((Ship) entity).thrust(timeDelta);
			
			entities.put(entity.getPosition(), entity);
		}
	}
	
	/**
	 * Resolve a given collision appropriatly.
	 * 
	 * @param collisionData
	 * 			CollisionData about the given collision.
	 * @throws IllegalArgumentException
	 * 			There is no valid collision point
	 */
	void resolve(CollisionData collisionData) throws IllegalArgumentException{
		if(collisionData.getCollisionType() == CollisionType.BOUNDARY){
			assert collisionData.getColliders().size() == 1;
			Entity collider = collisionData.getColliders().get(0);
			if(isInRangeValue(0, collisionData.getCollisionPoint().getX(), 0.01) ||
				isInRangeValue(getWidth(), collisionData.getCollisionPoint().getX(), 0.01)){
				collider.setXVelocity(collider.getVelocity().getX() * -1.0);
			}else if(isInRangeValue(0, collisionData.getCollisionPoint().getY(), 0.01) ||
					isInRangeValue(getHeight(), collisionData.getCollisionPoint().getY(), 0.01)){
				collider.setYVelocity(collider.getVelocity().getY() * -1.0);	
			}else
				throw new IllegalArgumentException();
			
			if(collider instanceof Bullet)
				((Bullet)collider).incrementBoundaryCollisionCount();
		}else if(collisionData.getCollisionType() == CollisionType.INTER_ENTITY){
			assert collisionData.getColliders().size() == 2;
			boolean isOwnShip = false;
			for(Entity e : collisionData.getColliders())
				if(e instanceof Bullet && collisionData.getColliders().contains(((Bullet) e).getSource())){
					Bullet b = (Bullet)e;
					b.getSource().loadBullet(b);
					isOwnShip = true;
				}
			if(!isOwnShip)
				for(Entity e : collisionData.getColliders())
					e.terminate();
		}else if(collisionData.getCollisionType() == CollisionType.INTER_SHIP){
			assert collisionData.getColliders().size() == 2;
			Ship ei = (Ship) collisionData.getColliders().get(0);
			Ship ej = (Ship) collisionData.getColliders().get(1);
			double sigmaSq = Math.pow(ei.getRadius() + ej.getRadius(), 2);
			double dx = ej.getPosition().getX() - ei.getPosition().getX();
			double dy = ej.getPosition().getY() - ei.getPosition().getY();
			double j = 2.0 * ei.getTotalMass() * ej.getTotalMass() * (ej.getVelocity().sub(ei.getVelocity()).dot(ej.getPosition().sub(ei.getPosition())))
					/ (sigmaSq * (ei.getTotalMass() + ej.getTotalMass()) );
			ei.setVelocity(ei.getVelocity().getX() + j * dx / ei.getTotalMass(), ei.getVelocity().getY() + j * dy / ei.getTotalMass());
			ej.setVelocity(ej.getVelocity().getX() - j * dx / ej.getTotalMass(), ej.getVelocity().getY() - j * dy / ej.getTotalMass());
		}
	}
	
	/**
	 * Check whether or not a given value is equal to a given target value, allowing 
	 * a given error range, epsilon.
	 * @param target
	 * 		The target value.
	 * @param value
	 * 		The given value to check.
	 * @param epsilon
	 * 		The error range value.
	 * @see implementation
	 */
	private boolean isInRangeValue(double target, double value, double epsilon){
		return value <= target + epsilon && value >= target - epsilon;
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
												current instanceof Ship && entity instanceof Ship ? CollisionType.INTER_SHIP : CollisionType.INTER_ENTITY,
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
		return entities.containsKey(item.getPosition());
	}

	/**
	 * Check whether this World can have the given Entity
	 * as one of its entities.
	 * 
	 * @param  item
	 *         The Entity to check.
	 * @return True if and only if the given Entity is effective and this World is a valid container
	 * 			for the Entity and the Entity is in the bounds of this World.
	 *       | result == (item != null) && item.canHaveAsContainer(this) && isInBounds(item.getPosition(), item.getRadius())
	 */
	@Override
	@Raw
	public boolean canHaveAsItem(Entity item){
		return item != null && item.canHaveAsContainer(this) && isInBounds(item.getPosition(), item.getRadius());
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
	 * 		   The given Entity is cannot be an entity of this Worlds entities
	 * 		   or does not reference this World as its container or overlaps with any other Entity.
	 * 			| !canHaveAsItem(item) || (item.getContainer() != this) || overlapsWithAnyEntity(item).size() > 0
	 */
	@Override
	public void addItem(Entity item) throws IllegalArgumentException{
		if(!canHaveAsItem(item) || item.getContainer() != this || overlapsWithAnyEntity(item).size() > 0)
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
	 * 		   or the given Entity still references any World as its container.
	 * 			| !this.hasAsItem(item) || item.getContainer() != null
	 */
	@Override
	@Raw
	public void removeItem(Entity item) throws IllegalArgumentException{
		if(!this.hasAsItem(item) || item.getContainer() != null)
			throw new IllegalArgumentException();
		entities.remove(item.getPosition());
	}

	@Override
	public boolean isTerminatedContainer(){
		return this.isTerminated;
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
		Set<Ship> result = new HashSet<Ship>();
		for(Entity e : entities.values())
			if(e instanceof Ship)
				result.add((Ship) e);
		return result;
	}
	
	/**
	* Return a set of all the bullets of this world.
	* 
	* @return Each bullet in the resulting set is an item of this World.
	 * 		 | for each bullet in result:
	 * 		 | 	this.hasAsItem(bullet)
	*/
	public Set<Bullet> getBullets(){
		Set<Bullet> result = new HashSet<Bullet>();
		for(Entity e : entities.values())
			if(e instanceof Bullet)
				result.add((Bullet) e);
		return result;
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
	 * This function is used an entity not yet in the entities of this world,
	 * otherwise this function should always return an empty list.
	 * 
	 * @param entity
	 * 		The entity to check.
	 * @return The resulting list contains entities that overlap with the given entity
	 * 		| for each other in result:
	 * 		|	entity.overlaps(other)
	 * @throws NullPointerException
	 * 		| entity == null
	 */
	public List<Entity> overlapsWithAnyEntity(Entity entity) throws NullPointerException{
		if (entity == null)
			throw new NullPointerException();
		ArrayList<Entity> result = new ArrayList<>();
		for (Entity other : entities.values())
			if (entity.overlaps(other) && !entity.equals(other))
				result.add(other);
		
		return result;
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
