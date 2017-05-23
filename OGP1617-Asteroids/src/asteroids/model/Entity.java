package asteroids.model;

import java.util.Arrays;

import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A Class to represent an Entity in the game
 * 
 * @invar The position of this Entity must be a valid position for this Entity. 
 * 			| canHaveAsPosition(getPosition().getX(), getPosition().getY))
 * @invar The velocity components of this Entity must form a valid velocity for this Entity.
 * 			| canHaveAsVelocity(getXVelocity(), getYVelocity())
 * @invar  The radius of this Entity must be a valid radius for this Entity.
 *       	| canHaveAsRadius(getRadius())
 * @invar  The mass of this Entity must be a valid mass for this Entity.
 *       	| canHaveAsMass(getMass())
 * @invar  Each Entity can have its maxVelocity as maxVelocity.
 *          | canHaveAsMaxVelocity(this.getMaxVelocity())
 * @invar  Each Entity must have a proper container.
 *       	| hasProperContainer()

 */
public abstract class Entity{

	/**
	 * Initialize this new Entity with given x, y, xVelocity, yVelocity, radius, mass and container.
	 *
	 * @param x
	 *     		The x-position for this new Entity.
	 * @param y
	 *        	The y-position for this new Entity.
	 * @param xVelocity
	 * 			The x-velocity for this new Entity.
	 * @param yVelocity
	 *       	The y-velocity for this new Entity.
	 * @param radius
	 *          The radius for this new Entity.
	 * @param mass
	 * 			The mass for this new Entity.
	 * @param container
	 * 			The container for this new Entity.
	 * @effect The x-position and y-position of this new Entity is set to the given x and y. 
	 * 			| setPosition(x, y)
	 * @post If the given xVelocity and yVelocity form a total velocity smaller than the speed of light, 
	 * 		 the velocity components of this new Entity are equal to the given xVelocity and yVelocity. 
	 *       Otherwise, the velocity components of this new Entity are equal to 0. 
	 *       	| if (getVectorLength(xVelocity, yVelocity) <= SPEED_OF_LIGHT) 
	 *       	| then new.getXVelocity() == xVelocity && new.getYVelocity() == yVelocity
	 *       	| else new.getXVelocity() == 0 && new.getYVelocity() == 0
	 * @post The maximum velocity of this new Entity is set to the speed of light
	 * 			| new.getMaxVelocity() == SPEED_OF_LIGHT
	 * @post The radius of this new Entity number is equal to
	 *		 the given radius.
	 *       	| new.getRadius() == radius
	 * @post If given mass is greater than the lowest possible mass, the mass is equal to the given mass.
	 * 		 Otherwise the mass is set to the lowest possible mass.
	 * 			|   if (mass > 4.0 / 3.0 * Math.PI * Math.pow(radius, 3) * getLowestMassDensity()) 
	 *       	|   then new.getMass() == mass
	 *       	|   else new.getMass() == 4.0 / 3.0 * Math.PI * Math.pow(radius, 3) * getLowestMassDensity()
	 * @post   The container of this new Entity is the same as the
	 *         given container.
	 *       	| new.getContainer() == container
	 * @post   The given container has this new Entity as one of its
	 *         items.
	 *      	| (new container).hasAsItem(this)
	 * @throws IllegalArgumentException
	 * 			The new Entity cannot have the given container as its container
	 * 			| !canHaveAsContainer(container)
	 * @throws IllegalArgumentException
	 *         The given radius is not a valid radius for this Entity.
	 *       	| ! canHaveAsRadius(radius)
	 */
	@Model
	@Raw
	protected Entity(double x, double y, double xVelocity, double yVelocity, double radius, double mass, Container container) throws IllegalArgumentException{
		if(!canHaveAsRadius(radius))
			throw new IllegalArgumentException();
		this.setPosition(x, y);
		this.velocity = Vector2d.ZERO;
		this.maxVelocity = SPEED_OF_LIGHT;
		this.setVelocity(xVelocity, yVelocity);
		this.radius = radius;
		this.setMass(mass);
		setContainer(container);
		if(container != null)container.addItem(this);
	}

	/**
	 * Return the position of this Entity.
	 */
	@Basic
	@Raw
	public Vector2d getPosition(){
		return this.position;
	}

	/**
	 * Check whether this Entity can have this position.
	 * 
	 * @param x
	 * 			The x-position to check.
	 * @param y
	 * 			The y-position to check.
	 * @return If the container of this Entity is effective the position must be 
	 * 			in the bounds specified by the container. Otherwise the position 
	 * 			is unbounded. And in all cases the given x and y must be a number.
	 * 			| if(getContainer() != null)
	 * 			| then result == getContainer().isInbounds(new Vector2d(x,y), getRadius())
	 * 			| 	&& !Double.isNaN(x) && !Double.isNaN(y)
	 * 			| else result == !Double.isNaN(x) && !Double.isNaN(y)
	 */
	public boolean canHaveAsPosition(double x, double y){
		if (Double.isNaN(x) || Double.isNaN(y))
			return false;
		if(getContainer() != null)
			return getContainer().isInBounds(new Vector2d(x, y), getRadius());
		
		return true;
	}
	
	/**
	 * Set the position of this Entity to the given x and y.
	 * 
	 * @param  x
	 * 		      The new x-position for this Entity.
	 * @param  y
	 *            The new y-position for this Entity.
	 * @post   The x-position of this new Entity is equal to the given x. 
	 * 			| new.getPosition().getX() == x
	 * @post   The y-position of this new Entity is equal to the given y. 
	 * 			| new.getPosition().getY() == y
	 * @post 	If this entity is associated with a World, the entity is mapped to the new position.
	 * 			| if(getContainer() instanceof World)
	 * 			| then (World getContainer()).getEntityAt(new.getPosition()) == this
	 * @throws IllegalArgumentException
	 *             The given x and y do not form a valid position. 
	 *        	| !canHaveAsPosition(x, y)
	 */
	@Raw
	public void setPosition(double x, double y) throws IllegalArgumentException{
		if(!canHaveAsPosition(x, y))
			throw new IllegalArgumentException();
		Vector2d oldPos = getPosition();
		this.position = new Vector2d(x, y);
		if(getContainer() instanceof World)
			((World) getContainer()).updateEntityEntry(oldPos, this);
	}

	/**
	 * Variable referencing the position vector of this Entity in kilometers.
	 */
	private Vector2d position;

	/**
	 * Return the velocity of this Entity.
	 */
	@Basic
	@Raw
	public Vector2d getVelocity(){
		return this.velocity;
	}

	/**
	 * Check whether this Entity can have the given xVelocity, yVelocity as its velocity components.
	 *  
	 * @param xVelocity
	 *            The x-velocity to check.
	 * @param yVelocity
	 * 			  The y-velocity to check.
	 * @return | result == new Vector2d(xVelocity, yVelocity).getLength() <= getMaxVelocity()
	 */
	@Raw
	public boolean canHaveAsVelocity(double xVelocity, double yVelocity){
		try{
			return new Vector2d(xVelocity, yVelocity).getLength() <= getMaxVelocity();			
		}catch(IllegalArgumentException ex){
			return false;
		}
	}

	/**
	 * Set the x-velocity of this Entity to the given xVelocity.
	 * 
	 * @param xVelocity
	 *            The new x-velocity for this Entity.
	 * @post  If the given xVelocity in combination with the current y-veloctiy is a valid velocity,
	 * 	      the x-velocity is equal to the given xVelocity. 
	 *       	| if (canHaveAsVelocity(xVelocity, getVelocity().getY())) 
	 *       	| then new.getVelocity().getX() == xVelocity
	 */
	@Raw
	public void setXVelocity(double xVelocity){
		if(canHaveAsVelocity(xVelocity, getVelocity().getY()))
			this.velocity = new Vector2d(xVelocity, getVelocity().getY());
	}

	/**
	 * Set the y-velocity of this Entity to the given yVelocity.
	 * 
	 * @param yVelocity
	 *            The new y-velocity for this Entity.
	 * @post  If the given yVelocity in combination with the current x-veloctiy is a valid velocity,
	 * 	      the y-velocity is equal to the given yVelocity. 
	 *       	| if (canHaveAsVelocity(getVelocity().getX(), yVelocity)) 
	 *       	| then new.getVelocity().getY() == yVelocity
	 */
	@Raw
	public void setYVelocity(double yVelocity){
		if(canHaveAsVelocity(getVelocity().getX(), yVelocity))
			this.velocity = new Vector2d(getVelocity().getX(), yVelocity);
	}

	/**
	 * Set the velocity components of this Entity to the given xVelocity and yVelocity.
	 * 
	 * @param xVelocity
	 * 			  The new x-velocity for this Entity.
	 * @param yVelocity
	 *            The new y-velocity for this Entity.
	 * @post  If the given xVelocity and yVelocity form a valid velocity,
	 * 	      the new velocity components are equal to the given xVelocity and yVelocity. 
	 *       	| if (canHaveAsVelocity(xVelocity, yVelocity)) 
	 *       	| then new.getVelocity().getX() == xVelocity && new.getVelocity().getY() == yVelocity
	 */
	@Raw
	public void setVelocity(double xVelocity, double yVelocity){
		if(canHaveAsVelocity(xVelocity, yVelocity))
			this.velocity = new Vector2d(xVelocity, yVelocity);
	}
	
	/**
	 * Variable referencing the velocity vector of this Entity in kilometers/second.
	 */
	private Vector2d velocity;

	/**
	 * Return the maxVelocity of this Entity.
	 */
	@Basic
	@Raw
	@Immutable
	public double getMaxVelocity(){
		return this.maxVelocity;
	}

	/**
	 * Check whether this Entity can have the given maxVelocity as its maxVelocity.
	 * The maxVelocity must be positive and cannot exceed the speed of light.
	 *  
	 * @param  maxVelocity
	 *         The maxVelocity to check.
	 * @return 
	 *       | result == 0 < maxVelocity && maxVelocity <= SPEED_OF_LIGHT
	*/
	@Raw
	public boolean canHaveAsMaxVelocity(double maxVelocity){
		return 0 < maxVelocity && maxVelocity <= SPEED_OF_LIGHT;
	}

	/**
	 * Variable registering the maxVelocity of this Entity.
	 */
	private final double maxVelocity;

	/**
	 * Constant registering the speed of light in kilometres/second
	 */
	public final static double SPEED_OF_LIGHT = 300000.0;

	/**
	 * Return the radius of this Entity.
	 */
	@Basic
	@Raw
	public double getRadius(){
		return this.radius;
	}

	/**
	 * Check whether this Entity can have the given radius as radius.
	 *  
	 * @param radius
	 *            The radius to check.
	 * @return | result == getMinRadius() <= radius
	 */
	@Raw
	public boolean canHaveAsRadius(double radius){
		return getMinRadius() <= radius;
	}

	/**
	 * Returns this minimum radius for this Entity
	 */
	public abstract double getMinRadius();

	/**
	 * Variable registering the radius of this Entity in kilometres.
	 */
	protected final double radius;

	/**
	 * Return the mass of this Entity.
	 */
	@Basic
	@Raw
	public double getMass(){
		return this.mass;
	}

	/**
	 * Return the mass density of this Entity.
	 */
	@Basic
	public double getMassDensity(){
		return this.mass / (4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3));
	}
	
	/**
	 * Check whether this Entity can have the given mass as its mass.
	 *  
	 * @param  mass
	 *         The mass to check.
	 */
	public abstract boolean canHaveAsMass(double mass);
	
	
	/**
	 * Return the lowest possible massDensity for any Ship.
	 */
	public abstract double getLowestMassDensity();

	/**
	 * Set the mass of this Entity to the given mass.
	 * 
	 * @param  mass
	 *         The new mass for this Entity.
	 * @post   If the given mass is a valid mass for this Entity,
	 *         the mass of this Entity is equal to the given
	 *         mass.
	 *       | if (canHaveAsMass(mass))
	 *       |   then new.getMass() == mass
	 * @post   If the given amss is not a valid mass for this Entity
	 * 		   the mass of this Entity is equal to the lowest possible mass.
	 * 		 | if (!canHaveAsMass(mass))
	 *       |   then new.getMass() == 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * getLowestMassDensity()
	 */
	@Raw
	public void setMass(double mass){
		if(canHaveAsMass(mass))
			this.mass = mass;
		else this.mass = 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * getLowestMassDensity();
	}

	/**
	 * Variable registering the mass of this Entity.
	 */
	private double mass;

	/**
	 * Move the Entity to a new position given a time duration,
	 * with respect to this Entity's current velocity.
	 * @param  timeDelta
	 * 			The amount of time the Entity moves with current velocity
	 * @post	This entity moves with respect to the current velocity for a
	 * 			positive timeDelta.
	 * 			| if(timeDelta >= 0.0)
	 * 			| then new.getPosition().equals(new Vector2d(getPosition().getX() + timeDelta * getVelocity().getX(),
	 * 			|											 getPosition().getY() + timeDelta * getVelocity().getY())
	 * @throws IllegalArgumentException
	 * 			The timedelta cannot be less than zero
	 * 			| timeDelta < 0.0
	 */
	public void move(double timeDelta) throws IllegalArgumentException{
		if(timeDelta >= 0.0)
			setPosition(getPosition().getX() + timeDelta * getVelocity().getX(), getPosition().getY() + timeDelta * getVelocity().getY());			
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Returns the distance between this Entity and another Entity in kilometres.
	 * 
	 * @param  other 
	 * 			The other Entity
	 * @return The euclidean distance between this Entity and the other Entity
	 * 			| result == this.getPosition().sub(other.getPosition()).getLength() - this.getRadius() - other.getRadius()
	 * @throws NullPointerException
	 * 			| other == null
	 */

	public double getDistanceBetween(Entity other) throws NullPointerException{
		if(other == null)
			throw new NullPointerException();
		return this.getPosition().sub(other.getPosition()).getLength() - this.getRadius() - other.getRadius();
	}

	/**
	 * Checks if this Entity (significantly) overlaps with another Entity.
	 * The entities need not be in the same container.
	 * 
	 * @param  other
	 * 			The other Entity
	 * @return 	| result == (this == other || overlapsCircle(other.getPosition(), other.getRadius())
	 * @throws NullPointerException
	 * 			| other == null
	 */
	public boolean overlaps(Entity other) throws NullPointerException{
		if(other == null)
			throw new NullPointerException();
		return this == other || overlapsCircle(other.getPosition(), other.getRadius());
	}
	
	/**
	 * Check if this Entity (significantly) overlaps with a given circular object
	 * 
	 * @param position
	 * 		The position of the circle to check.
	 * @param radius
	 * 		The radius of the circle to check.
	 * @return | result ==  (this.getPosition().sub(position).getLength() <= 0.99 * (this.getRadius() + radius))
	 * @throws NullPointerException
	 * 			| position == null
	 */
	public boolean overlapsCircle(Vector2d position, double radius) throws NullPointerException{
		if(position == null)
			throw new NullPointerException();
		return (this.getPosition().sub(position).getLength() <= 0.99 * (this.getRadius() + radius));
	}

	/**
	 * Calculate the time before collision with the given other Entity, assuming the velocities
	 * of both Entities do not change. If in the current state no collision will occur,
	 * the time to collision is considered infinite.
	 * 
	 * @param  other
	 * 			The other Entity
	 * @return   The calculated result is cannot be negative
	 * 			| result >= 0.0
	 * @return Starting from the current position of each Entity, when the current velocities 
	 * 		   are applied for the calculated duration of time on both Entities, their boundaries touch.
	 * 		   In other words the distance between the center of this Entity and the other Entity will then 
	 * 		   equal the sum of their radii. Assuming the calculated result is a finite value.
	 * 			| let
	 * 			| 	thisCollision = this.getPosition().add(this.getVelocity().mul(result))
	 * 			| 	otherCollision = other.getPosition().add(other.getVelocity().mul(result))
	 * 			| in
	 * 			|	if (result < Double.POSITIVE_INFINITY)
	 * 			| 	then thisCollision.sub(otherCollision).getLength() == this.getRadius() + other.getRadius()
	 * @return No collision occurs prior to the resulting time value. Assuming the calculated result is a finite value.
	 * 			| for each value in [0,result):
	 * 			|    let
	 * 			| 	   thisCollision = this.getPosition().add(this.getVelocity().mul(value))
	 * 			| 	   otherCollision = other.getPosition().add(other.getVelocity().mul(value))
	 * 			|    in
	 * 			| 	   thisCollision.sub(otherCollision).getLength() > this.getRadius() + other.getRadius()
	 * @return If the container of this Entity and the given Entity does not match then there will be no
	 * 			collision so the time till the collision will be considered as infinity.
	 * 			| if this.getContainer() != other.getContainer()
	 * 			| then new.getTimeToCollision(other) == Double.POSITIVE_INFINITY
	 * @throws NullPointerException
	 * 			The other entity is ineffective
	 * 			| other == null
	 * @throws IllegalArgumentException 
	 * 			The entities overlap
	 * 			| this.overlaps(other)
	 */
	public double getTimeToCollision(Entity other) throws NullPointerException, IllegalArgumentException{
		if(other == null)
			throw new NullPointerException();
		if(this.overlaps(other))
			throw new IllegalArgumentException(); 
		if(this.getContainer() != other.getContainer())
			return Double.POSITIVE_INFINITY;

		double sigmaSq = Math.pow(this.getRadius() + other.getRadius(), 2);
		double rDotr = this.getPosition().sub(other.getPosition()).getLengthSquared();
		double vDotv = this.getVelocity().sub(other.getVelocity()).getLengthSquared();
		double vDotr = this.getVelocity().sub(other.getVelocity()).dot(this.getPosition().sub(other.getPosition()));
		double d = vDotr * vDotr - vDotv * (rDotr - sigmaSq);
		if(vDotr >= 0 || d <= 0)
			return Double.POSITIVE_INFINITY;
		else return -(vDotr + Math.sqrt(d)) / vDotv;
	}

	/**
	 * Find the position of a possible collision with another Entity,
	 * assuming the current velocities remain constant.
	 * @param other
	 * 			The other Entity.
	 * @return The x- and y-coordinate of the calculated collision position, if in the current
	 * 		   state of the Entities no collision will occur the result is null. The collision point
	 * 		   lies on the connecting line between the Entities. It's position on the line is determined
	 * 		   by the radii of the Entities.
	 * 		| let
	 * 		| 	thisCollision = this.getPosition().add(this.getVelocity().mul(result))
	 * 		| 	otherCollision = other.getPosition().add(other.getVelocity().mul(result))
	 * 		| in
	 * 		|   if(getTimeToCollision(other) == Double.POSITIVE_INFINITY)
	 * 		|   then result == null
	 * 		|	else result.equals(thisCollision.mul(other.getRadius()).add(otherCollision.mul(this.getRadius())).mul(1.0/(this.getRadius() + other.getRadius())))
	 * @throws NullPointerException
	 * 			| other == null
	 * @throws IllegalArgumentException
	 * 			| this.overlaps(other)
	 * @throws IllegalArgumentException 
	 * 			The container of this Entity and the given Entity does not match
	 * 			| this.getContainer() != other.getContainer()
	 */
	public Vector2d getCollisionPosition(Entity other) throws NullPointerException, IllegalArgumentException{
		double timeTilCollision = getTimeToCollision(other);
		if(timeTilCollision == Double.POSITIVE_INFINITY)
			return null;
		double sumRadii = this.getRadius() + other.getRadius();
		Vector2d thisCollisionPoint = this.getPosition().add(this.getVelocity().mul(timeTilCollision));
		Vector2d otherCollisionPoint = other.getPosition().add(other.getVelocity().mul(timeTilCollision));
		return thisCollisionPoint.mul(other.getRadius()).add(otherCollisionPoint.mul(this.getRadius())).mul(1.0 / sumRadii);
	}

	/**
	 * Compute the point of collision and the time to collision of this Entity
	 * with the bounds specified by a possible World container.
	 * 
	 * @return If the container of this Entity is a World, this entity
	 * 			collides with the bounds of its World after the computed amount of time,
	 * 			assuming the velocity remains unchanged.
	 * 			| if( getContainer() instance of World)
	 * 			| then (World getContainer()).isEntityCollidingBounds(getPosition().add(getVelocity().mul(result.getTimeToCollision())), getRadius)
	 * @return If the container of this Entity is a World, this entity
	 * 			collides with the bounds of its World on the computed position,
	 * 			assuming the velocity remains unchanged.
	 * 			| if( getContainer() instance of World)
	 * 			| then getPosition().add(getVelocity().mul(result.getTimeToCollision())).sub(result.getCollisionPoint()).length() == getRadius()
	 * @return If the container of this Entity is not a World or not effective
	 * 			the computed time to collision is equal to POSITIVE_INFINITy and
	 * 			the computed position is not effective.
	 * 			| if(! getContainer() instanceof World)
	 * 			| then result.equals(CollisionData.UNDEFINED_COLLISION)
	 */
	public CollisionData getBoundaryCollisionData(){
		if(getContainer() instanceof World){
			double width = ((World) getContainer()).getWidth();
			double height = ((World) getContainer()).getHeight();
			if(getVelocity().dot(Vector2d.X_AXIS) > 0.0){
				double time = Vector2d.intersect(new Vector2d(getPosition().getX() + getRadius(), getPosition().getY()), getVelocity(), new Vector2d(width, 0), Vector2d.Y_AXIS);
				if(time != Double.POSITIVE_INFINITY){	
					Vector2d intersect = new Vector2d(getPosition().getX() + getRadius(), getPosition().getY()).add(getVelocity().mul(time));
					if(intersect.getY() >= getRadius() && intersect.getY() <= height - getRadius())
						return new CollisionData(time, intersect, CollisionType.BOUNDARY, Arrays.asList(new Entity[]{this}));
				}
			}else{
				double time = Vector2d.intersect(new Vector2d(getPosition().getX() - getRadius(), getPosition().getY()), getVelocity(), Vector2d.ZERO, Vector2d.Y_AXIS);
				if(time != Double.POSITIVE_INFINITY){	
					Vector2d intersect = new Vector2d(getPosition().getX() - getRadius(), getPosition().getY()).add(getVelocity().mul(time));
					if(intersect.getY() >= getRadius() && intersect.getY() <= height - getRadius())
						return new CollisionData(time, intersect, CollisionType.BOUNDARY, Arrays.asList(new Entity[]{this}));					
				}
			}
			if(getVelocity().dot(Vector2d.Y_AXIS) > 0.0){
				double time = Vector2d.intersect(new Vector2d(getPosition().getX(), getPosition().getY() + getRadius()), getVelocity(), new Vector2d(0, height), Vector2d.X_AXIS);
				if(time != Double.POSITIVE_INFINITY){	
					Vector2d intersect = new Vector2d(getPosition().getX(), getPosition().getY() + getRadius()).add(getVelocity().mul(time));
					if(intersect.getX() >= getRadius() && intersect.getX() <= width - getRadius())
						return new CollisionData(time, intersect, CollisionType.BOUNDARY, Arrays.asList(new Entity[]{this}));
				}
			}else{
				double time = Vector2d.intersect(new Vector2d(getPosition().getX(), getPosition().getY() - getRadius()), getVelocity(), Vector2d.ZERO, Vector2d.X_AXIS);
				if(time != Double.POSITIVE_INFINITY){	
					Vector2d intersect = new Vector2d(getPosition().getX(), getPosition().getY() - getRadius()).add(getVelocity().mul(time));
					if(intersect.getX() >= getRadius() && intersect.getX() <= width - getRadius())
						return new CollisionData(time, intersect, CollisionType.BOUNDARY, Arrays.asList(new Entity[]{this}));
				}
			}
		}
		return CollisionData.UNDEFINED_COLLISION;
	}

	/**
	 * Return the time to the collision (if any) with the bounds of the possible World container of this Entity
	 * 
	 * @effect | getBoundaryCollisionData().getTimeToCollision()
	 */
	public double getTimeToBoundaryCollision(){
		return getBoundaryCollisionData().getTimeToCollision();
	}

	/**
	 * Return the collision point (if any) with the bounds of the possible World container of this Entity
	 * 
	 * @effect | getBoundaryCollisionData().getCollsionPoint()
	 */
	public Vector2d getBoundaryCollisionPosition(){
		return getBoundaryCollisionData().getCollisionPoint();
	}
	
	/**
	 * Evolve this Entity with a given timeDelta, based on its current properties.
	 * 
	 * @param timeDelta
	 * 		The given time duration.
	 * @throws IllegalArgumentException
	 * 			| timeDelta < 0
	 * @throws ProgramExecutionTimeException TODO
	 * 			When an error occurs during program execution
	 * @throws ExpressionEvaluationException
	 * 			When an error occurs during program execution,
	 * 			while evaluating an expression.
	 */
	public void evolve(double timeDelta) throws IllegalArgumentException, ProgramExecutionTimeException, ExpressionEvaluationException{
		move(timeDelta);
	}
	
	/**
	 * Resolve given collision case appropriatly
	 * 
	 * @param collisionData
	 * 			The given collision case
	 * @throws IllegalArgumentException
	 * 			| !(collisionData.getCollisionType() == CollisionType.BOUNDARY) ||
	 * 			| !(collisionData.getCollisionType() == CollisionType.INTER_ENTITY)
	 */
	public abstract void resolve(CollisionData collisionData) throws IllegalArgumentException;
	
	/**
	 * Resolve a given boundary collision case, by reflecting the velocity
	 * vector of this Entity on the bounds of its World.
	 * 
	 * @param collisionData
	 * 		The given collision case
	 * @post The x or y component of the velocity vector of this Entity 
	 * 		 has an opposing sign.
	 * 		| new.getVelocity().getX() == this.getVelocity().getX() * (-1.0) ^
	 * 		| new.getVelocity().getY() == this.getVelocity().getY() * (-1.0)
	 * @throws IllegalStateException
	 * 		This Entity has no World container
	 * 		| !(getContainer() instanceof World)
	 * @throws IllegalArgumentExeption
	 * 		The given collisionData has an invalid collisionPoint
	 * 		| !(collisionData.getCollisionPoint().isXInRangeOf(0, 0.01) ||
	 *		|	collisionData.getCollisionPoint().isXInRangeOf((World getContainer()).getWidth(), 0.01) ||
	 *		|	collisionData.getCollisionPoint().isYInRangeOf(0, 0.01) ||
	 *		|	collisionData.getCollisionPoint().isYInRangeOf((World getContainer()).getHeight(), 0.01)
	 */
	public void resolveBoundaryCollision(CollisionData collisionData) throws IllegalStateException, IllegalArgumentException{
		if(!(getContainer() instanceof World))
			throw new IllegalStateException();
		World world = (World) getContainer();
		if(collisionData.getCollisionPoint().isXInRangeOf(0, 0.01) ||
			collisionData.getCollisionPoint().isXInRangeOf(world.getWidth(), 0.01))
			setXVelocity(getVelocity().getX() * -1.0);
		else if(collisionData.getCollisionPoint().isYInRangeOf(0, 0.01) ||
				collisionData.getCollisionPoint().isYInRangeOf(world.getHeight(), 0.01))
			setYVelocity(getVelocity().getY() * -1.0);
		else
			throw new IllegalArgumentException();
		
		
	}
	
	public void resolveBounceCollision(Entity other, double thisMass, double otherMass){
		double sigmaSq = Math.pow(this.getRadius() + other.getRadius(), 2);
		double dx = other.getPosition().getX() - this.getPosition().getX();
		double dy = other.getPosition().getY() - this.getPosition().getY();
		double j = 2.0 * thisMass * otherMass * (other.getVelocity().sub(this.getVelocity()).dot(other.getPosition().sub(this.getPosition())))
				/ (sigmaSq * (thisMass + otherMass) );
		this.setVelocity(this.getVelocity().getX() + j * dx / thisMass, this.getVelocity().getY() + j * dy / thisMass);
		other.setVelocity(other.getVelocity().getX() - j * dx / otherMass, other.getVelocity().getY() - j * dy / otherMass);
	}
	
	/**
	 * Terminate this Entity.
	 *
	 * @post   This Entity  is terminated.
	 *       | new.isTerminated()
	 * @post   This Entity no longer references an effective container.
	 *       | new.getContainer() == null
	 * @post   If this Entity was not yet terminated, this Entity
	 *         is no longer one of the Entities for the Container to which
	 *         this Entity belonged.
	 *       | if (! isTerminated())
	 *       |   then ! new.getContainer().hasAsItem(this))
	 */
	public abstract void terminate();

	/**
	 * Return a boolean indicating whether or not this Entity
	 * is terminated.
	 */
	@Basic
	@Raw
	public boolean isTerminated(){
		return this.isTerminated;
	}

	/**
	 * Variable registering whether this entity is terminated.
	 */
	protected boolean isTerminated = false;

	/**
	 * Return the Container to which this Entity belongs.
	 */
	@Basic
	@Raw
	public Container getContainer(){
		return this.container;
	}

	/**
	 * Check whether this Entity can have the given container as
	 * its container.
	 * 
	 * @param  container
	 * 		   The container to check.
	 * @return If this Entity is terminated, true if and only if the
	 *         given Container is not effective.
	 *       | if (this.isTerminated())
	 *       |   then result == (container == null)
	 * @return If this Entity is not terminated, true if and only if the given
	 *         Container is not effective or an instance of Ship or World and not yet terminated.
	 *       | if (! this.isTerminated())
	 *       |   then result == (container == null) || ((container instanceof World) 
	 *       |							&& (!container.isTerminatedContainer()))
	 */
	@Raw
	public abstract boolean canHaveAsContainer(Container container);
	
	/**
	 * Check whether this Entity has a proper container.
	 * 
	 * @return True if and only if this Entity can have its container as its
	 *         container, and if the container of this Entity is either not effective
	 *         or if it has this Entity as one of its Entities.
	 *       | result == canHaveAsContainer(getContainer()) &&
	 *       |   ((getContainer() == null) || getContainer().hasAsItem(this))
	 */
	@Raw
	public boolean hasProperContainer(){
		return canHaveAsContainer(getContainer()) && ((getContainer() == null) || (getContainer().hasAsItem(this)));
	}

	/**
	 * Set the container of this Entity to the given container.
	 * 
	 * @param  container
	 *         The new container for this Entity.
	 * @post   The container of this Entity is the same as the
	 *         given container.
	 *       | new.getContainer() == container
	 * @throws IllegalArgumentException
	 *         This Entity cannot have the given container as its container.
	 *         Or the given container is effective and this Entity already has an effective Container.
	 *       | ! canHaveAsContainer(container) || (getContainer() != null && container != null)
	 */
	@Raw
	public void setContainer(Container container) throws IllegalArgumentException{
		if(!canHaveAsContainer(container) || (getContainer() != null && container != null))
			throw new IllegalArgumentException();
		this.container = container;
	}

	/**
	 * Variable referencing the Container to which this Entity belongs.
	 */
	private Container container;
  
}


