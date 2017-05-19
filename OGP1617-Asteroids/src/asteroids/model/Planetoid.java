package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A Class representing a Planetoid in the game.
 */
public class Planetoid extends MinorPlanet{

	/**
	 * Initialize this new Planetoid with given x, y, xVelocity, yVelocity, startRadius, mass, container
	 * 	and totalTraveledDistance.
	 *
	 * @invar  Each Planetoid can have its totalTraveledDistance as totalTraveledDistance.
	 *       	| canHaveAsTotalTraveledDistance(this.getTotalTraveledDistance())
	 * @param x
	 *     		The x-position for this new Planetoid.
	 * @param y
	 *        	The y-position for this new Planetoid.
	 * @param xVelocity
	 * 			The x-velocity for this new Planetoid.
	 * @param yVelocity
	 *       	The y-velocity for this new Planetoid.
	 * @param startRadius
	 *          The radius for this new Planetoid.
	 * @param container
	 * 			The container for this new Planetoid.
	 * @param totalTraveledDistance
	 *         	The totalTraveledDistance for this new Planetoid.
	 * @effect This new Planetoid is initialized as a new MinorPlanet with
	 * 		   given x, y, xVelocity, yVelocity, startRadius, container and a mass corresponding 
	 * 			to the startRadius of this Planetoid.
	 * 			| super(x, y, xVelocity, yVelocity, startRadius, 4.0/3.0*Math.PI*Math.pow(startRadius, 3)*PLANETOID_MASS_DENSITY, container)
	 * @effect The currentRadius of this new Planetoid is set to
	 *         the given startRadius.
	 *       	| this.setRadius(startRadius)
	 * @post   The totalTraveledDistance of this new Planetoid is equal to the given
	 *         totalTraveledDistance.
	 *       	| new.getTotalTraveledDistance() == totalTraveledDistance
	 * @effect This Planetoid is shrunk with respect to the given total traveled distance.
	 * 			| shrink()
	 */
	@Raw
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double startRadius, Container container, double totalTraveledDistance){
		super(x, y, xVelocity, yVelocity, startRadius, 4.0 / 3.0 * Math.PI * Math.pow(startRadius, 3) * PLANETOID_MASS_DENSITY, container);
		this.setRadius(startRadius);
		this.setTotalTraveledDistance(totalTraveledDistance);
		shrink();
	}

	/**
	 * Initialize this new Planetoid with given x, y, xVelocity, yVelocity, startRadius, mass.
	 * This Planetoid has no Container.
	 *
	 * @param x
	 *     		The x-position for this new Planetoid.
	 * @param y
	 *        	The y-position for this new Planetoid.
	 * @param xVelocity
	 * 			The x-velocity for this new Planetoid.
	 * @param yVelocity
	 *       	The y-velocity for this new Planetoid.
	 * @param startRadius
	 *          The radius for this new Planetoid.
	 * @effect This new Planetoid is initialized as a new MinorPlanet with
	 * 		   given x, y, xVelocity, yVelocity, startRadius, no container 
	 * 		   and a mass corresponding to the startRadius of this Planetoid.
	 * 			| this(x, y, xVelocity, yVelocity, startRadius, null)
	 */
	@Raw
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double startRadius, double totalTraveledDistance){
		this(x, y, xVelocity, yVelocity, startRadius, null, totalTraveledDistance);
	}

	/**
	 * The mass density for any Planetoid in kilograms per cubic kilometre.
	 */
	public static final double PLANETOID_MASS_DENSITY = 0.917e12;
	
	/**
	 * The minimum radius for a planetoid to spawn asteroids on termination, in kilometres.
	 */
	public static final double PLANETOID_MIN_SPAWN_RADIUS = 30.0;
	
	/**
	 * Return the lowest possible massDensity for any Planetoid.
	 * 
	 * @return 
	 * 			| PLANETOID_MASS_DENSITY
	 */
	public double getLowestMassDensity(){
		return PLANETOID_MASS_DENSITY;
	}
	
	/**
	 * Check whether this Planetoid can have the given mass as its mass.
	 *  
	 * @param  mass
	 *         The mass to check.
	 * @return 
	 *       | result == mass > 4.0 / 3.0 * Math.PI * Math.pow(MIN_RADIUS, 3) * PLANETOID_MASS_DENSITY
	*/
	@Raw
	public boolean canHaveAsMass(double mass){
		return mass > 4.0 / 3.0 * Math.PI * Math.pow(MIN_RADIUS, 3) * PLANETOID_MASS_DENSITY;
	}
	
	/**
	 * Return the totalTraveledDistance of this Planetoid.
	 */
	@Basic @Raw
	public double getTotalTraveledDistance() {
		return this.totalTraveledDistance;
	}
	
	/**
	 * Check whether the given totalTraveledDistance is a valid totalTraveledDistance for
	 * this Planetoid.
	 *  
	 * @param  totalTraveledDistance
	 *         The totalTraveledDistance to check.
	 * @return True if the totalTraveledDistance is finite and not negative.
	 *       | result == totalTraveledDistance >= 0
	 */
	public boolean canHaveAsTotalTraveledDistance(double totalTraveledDistance) {
		return totalTraveledDistance >= 0;
	}

	/**
	 * Set the totalTraveledDistance of this Planetoid to the given totalTraveledDistance.
	 * 
	 * @param  totalTraveledDistance
	 *         The new totalTraveledDistance for this Planetoid.
	 * @post   The totalTraveledDistance of this new Planetoid is equal to
	 *         the given totalTraveledDistance.
	 *       | new.getTotalTraveledDistance() == totalTraveledDistance
	 * @throws IllegalArgumentException
	 *         The given totalTraveledDistance is not a valid totalTraveledDistance for any
	 *         Planetoid.
	 *       | ! isValidTotalTraveledDistance(getTotalTraveledDistance())
	 */
	@Raw
	public void setTotalTraveledDistance(double totalTraveledDistance) 
			throws IllegalArgumentException {
		if (! canHaveAsTotalTraveledDistance(totalTraveledDistance))
			throw new IllegalArgumentException();
		this.totalTraveledDistance = totalTraveledDistance;
	}

	/**
	 * Variable registering the totalTraveledDistance of this Planetoid.
	 */
	private double totalTraveledDistance;
	
	/**
	 * Move the Planetoid to a new position given a time duration,
	 * with respect to this Planetoid's current velocity.
	 * 
	 * @param  timeDelta
	 * 			The amount of time the Planetoid moves with current velocity
	 * @effect  | super.move(timeDelta)
	 * @post	| if(canHaveAsRadius(getRadius() - getVelocity().mul(timeDelta).getLength() * 0.000001)
	 * 			| then new.getRadius() == getRadius() - getVelocity().mul(timeDelta).getLength() * 0.000001
	 * @post	| if(!canHaveAsRadius(getRadius() - getVelocity().mul(timeDelta).getLength() * 0.000001)
	 * 			| then new.isTerminated()
	 */
	@Override
	public void move(double timeDelta) throws IllegalArgumentException{
		super.move(timeDelta);
		double distance = getVelocity().mul(timeDelta).getLength();
		setTotalTraveledDistance(getTotalTraveledDistance() + distance);
		shrink();
	}

	/**
	 * Return the radius of this Planetoid.
	 */
	@Basic
	@Raw
	public double getRadius(){
		return this.currentRadius;
	}
	
	/**
	 * Return the start radius of this Planetoid.
	 */
	@Basic
	@Raw
	@Immutable
	public double getStartRadius(){
		return this.radius;
	}
	
	/**
	 *  Shrinks the current radius of this Planetoid to the new radius if the new radius is valid, 
	 *  else this Planetoid will be terminated.
	 *  
	 * @post	| if(canHaveAsRadius(this.getStartRadius() - 0.000001*getTotalTraveledDistance())
	 * 			| then new.getRadius() == (this.getStartRadius() - 0.000001*getTotalTraveledDistance())
	 * @post	| if(!canHaveAsRadius(this.getStartRadius() - 0.000001*getTotalTraveledDistance())
	 * 			| then new.isTerminated()
	 */
	@Model
	private void shrink(){
		double newRadius = this.getStartRadius() - 0.000001*getTotalTraveledDistance();
		if(canHaveAsRadius(newRadius))
			setRadius(newRadius);
		else
			this.terminate();
	}
	
	/**
	 * Set the currentRadius of this Planetoid to the given radius.
	 * 
	 * @param  radius
	 *         The new currentRadius for this Planetoid.
	 * @post   The currentRadius of this new Planetoid is equal to
	 *         the given radius.
	 *       | new.getRadius() == radius
	 * @effect The mass of this Planetoid is updated with respect to
	 * 			the new radius.
	 * 		 | setMass(4.0 / 3.0 * Math.PI * Math.pow(radius, 3) * PLANETOID_MASS_DENSITY)
	 * @throws IllegalArgumentException
	 *         The given radius is not a valid radius for this Planetoid.
	 *       | ! canHaveAsRadius(getRadius())
	 */
	@Raw
	public void setRadius(double radius) throws IllegalArgumentException{
		if(!canHaveAsRadius(radius))
			throw new IllegalArgumentException();
		this.setMass(4.0 / 3.0 * Math.PI * Math.pow(radius, 3) * PLANETOID_MASS_DENSITY);		
		this.currentRadius = radius;
	}

	/**
	 * Variable registering the current radius of this Planetoid.
	 */
	private double currentRadius;
	
	/**
	 * Terminate this Planetoid.
	 * If the radius of this Planetoid is bigger than the PLANETOID_MIN_SPAWN_RADIUS,
	 * two asteroids are added to the former world container of this Planetoid.
	 * 
	 * @effect | super.terminate()
	 * @post   | if(!isTerminated() && getContainer() instanceof World && getRadius() >= PLANETOID_MIN_SPAWN_RADIUS)
	 * 		   | then getContainer().getNbItems() + 1 == (new getContainer()).getNbItems()
	 */
	@Override
	public void terminate(){
		if(!isTerminated()){
			Container oldContainer = getContainer();
			super.terminate();
			if(oldContainer instanceof World && getRadius() >= PLANETOID_MIN_SPAWN_RADIUS){
				Vector2d randomDirection = Vector2d.randomUnit();
				Vector2d pos1 = getPosition().add(randomDirection.mul(getRadius()/2.0));
				Vector2d pos2 = getPosition().sub(randomDirection.mul(getRadius()/2.0));
				Vector2d vel1 = randomDirection.mul(1.5 * getVelocity().getLength());
				Vector2d vel2 = randomDirection.mul(-1.5 * getVelocity().getLength());
				new Asteroid(pos1.getX(), pos1.getY(), vel1.getX(), vel1.getY(), getRadius()/2.0, oldContainer);
				new Asteroid(pos2.getX(), pos2.getY(), vel2.getX(), vel2.getY(), getRadius()/2.0, oldContainer);
			}
		}
	}
}

