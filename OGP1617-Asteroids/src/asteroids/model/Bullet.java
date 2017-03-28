package asteroids.model;

public class Bullet extends Entity{

	protected Bullet(double x, double y, double xVelocity, double yVelocity, double radius, double massDensity) throws IllegalArgumentException{
		super(x, y, xVelocity, yVelocity, radius, massDensity);
	}
	
	private static final double MIN_RADIUS = 1.0;

	@Override
	public double getMinRadius(){
		return MIN_RADIUS;
	}

}
