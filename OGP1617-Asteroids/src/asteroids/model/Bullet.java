package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;

public class Bullet extends Entity{

	protected Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double mass) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, mass);
	}
	
	/**
	 * The mininum radius for any Bullet in kilometres
	 */
	private static final double MIN_RADIUS = 1.0;
	
	@Basic
	@Override
	public double getMinRadius(){
		return MIN_RADIUS;
	}

}
