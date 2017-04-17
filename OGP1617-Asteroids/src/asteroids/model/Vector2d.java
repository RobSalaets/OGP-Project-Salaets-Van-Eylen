package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;

/**
 * A Class to represent a two component vector
 * @invar The x component of each Vector2d must be a valid component.
 *       | canHaveAsComponent(this.getX())
 * @invar The y component of each Vector2d must be a valid component.
 *       | canHaveAsComponent(this.getY())
 */
@Value
public class Vector2d{

	/**
	 * Initialize this new Vector2d with given x and y component.
	 * 
	 * @param  x
	 *         The x for this new Vector2d.
	 * @param  y 
	 * 		   The y for this new Vector2d.
	 * @post   The x of this new Vector2d is equal to the given x.
	 *       | new.getX() == x
	 * @post   The y of this new Vector2d is equal to the given y.
	 *       | new.getY() == y
	 * @throws IllegalArgumentException
	 *         This new Vector2d cannot have the given x as its x.
	 *       | ! canHaveAsComponent(this.getX())
	 * @throws IllegalArgumentException
	 *         This new Vector2d cannot have the given y as its y.
	 *       | ! canHaveAsComponent(this.getY())
	 */
	public Vector2d(double x, double y) throws IllegalArgumentException{
		if(!canHaveAsComponent(x) || !canHaveAsComponent(y))
			throw new IllegalArgumentException();
		this.x = x;
		this.y = y;
	}

	/**
	 * Return the x of this Vector2d.
	 */
	@Basic
	@Raw
	@Immutable
	public double getX(){
		return this.x;
	}

	/**
	 * Return the y of this Vector2d.
	 */
	@Basic
	@Raw
	@Immutable
	public double getY(){
		return this.y;
	}

	/**
	 * Check whether this Vector2d can have the given component as its x or y.
	 *  
	 * @param  comp
	 *         The component to check.
	 * @return 
	 *       | result == Double.isFinite(comp)
	*/
	@Raw
	public boolean canHaveAsComponent(double comp){
		return Double.isFinite(comp);
	}

	/**
	 * Variable registering the x of this Vector2d.
	 */
	private final double x;

	/**
	 * Variable registering the y of this Vector2d.
	 */
	private final double y;

	/**
	 * Variable referencing a vector with its components both zero.
	 * 
	 * @return | ZERO.equals(new Vector2d(0.0,0.0))
	 */
	public static final Vector2d ZERO = new Vector2d(0.0, 0.0);

	/**
	 * Variable referencing a unit vector aligned to the x-axis.
	 * 
	 * @return | X_AXIS.equals(new Vector2d(1.0,0.0))
	 */
	public static final Vector2d X_AXIS = new Vector2d(1.0, 0.0);

	/**
	 * Variable referencing a unit vector aligned to the y-axis.
	 * 
	 * @return | Y_AXIS.equals(new Vector2d(0.0,1.0))
	 */
	public static final Vector2d Y_AXIS = new Vector2d(0.0, 1.0);

	/**
	 * Compute the componentwise sum of this vector and a given other vector
	 * 
	 * @param other
	 * 			The other Vector2d to add.
	 * @return A vector with its components equal to the sum of this and the other vectors components
	 * 			| result == new Vector2d(this.getX() + other.getX(), this.getY() + other.getY());
	 * @throws IllegalArgumentException
	 * 			| other == null
	 */
	public Vector2d add(Vector2d other) throws IllegalArgumentException{
		if(other == null)
			throw new IllegalArgumentException();
		return new Vector2d(this.getX() + other.getX(), this.getY() + other.getY());
	}

	/**
	 * Compute the componentwise subtraction of this vector and a given other vector
	 * 
	 * @param other
	 * 			The other Vector2d to subtract.
	 * @return A vector with its components equal to the subtraction of this and the other vectors components
	 * 			| result == new Vector2d(this.getX() - other.getX(), this.getY() - other.getY());
	 * @throws IllegalArgumentException
	 * 			| other == null
	 */
	public Vector2d sub(Vector2d other) throws IllegalArgumentException{
		if(other == null)
			throw new IllegalArgumentException();
		return new Vector2d(this.getX() - other.getX(), this.getY() - other.getY());
	}

	/**
	 * Compute the componentwise multiplication of this vector and a given other vector
	 * 
	 * @param other
	 * 			The other Vector2d to multiply.
	 * @return A vector with its components equal to the multiplication of this and the other vectors components
	 * 			| result == new Vector2d(this.getX() * other.getX(), this.getY() * other.getY());
	 * @throws IllegalArgumentException
	 * 			| other == null
	 */
	public Vector2d mul(Vector2d other) throws IllegalArgumentException{
		if(other == null)
			throw new IllegalArgumentException();
		return new Vector2d(this.getX() * other.getX(), this.getY() * other.getY());
	}

	/**
	 * Compute the componentwise multiplication of this vector and a given value
	 * 
	 * @param value
	 * 			The value to mulitply
	 * @return A vector with its components equal to the multiplication of the components with the given value
	 * 			| result == new Vector2d(this.getX() * other.getX(), this.getY() * other.getY());
	 * @throws IllegalArgumentException
	 * 			| other == null
	 */
	public Vector2d mul(double value) throws IllegalArgumentException{
		return new Vector2d(this.getX() * value, this.getY() * value);
	}

	/**
	 * Return this vectors euclidean length
	 * 
	 * @return | result == Math.sqrt(getX() * getX() + getY() * getY())
	 */
	public double getLength(){
		return Math.sqrt(getX() * getX() + getY() * getY());
	}

	/**
	 * Return this vectors euclidean length squared
	 * 
	 * @return | result == getX() * getX() + getY() * getY()
	 */
	public double getLengthSquared(){
		return getX() * getX() + getY() * getY();
	}

	/**
	 * Compute the dot product of this vector and a given other vector
	 * 
	 * @param other
	 * 			The other Vector2d.
	 * @return  | result == this.getX() * other.getX() + this.getY() * other.getY();
	 * @throws IllegalArgumentException
	 * 			| other == null
	 */
	public double dot(Vector2d other){
		if(other == null)
			throw new IllegalArgumentException();
		return this.getX() * other.getX() + this.getY() * other.getY();
	}

	/**
	 * Compute a vector with normalized components from this Vector2d
	 * @return A vector form by this vectors components divided by this vectors length
	 * 			| result == new Vector2d(getX() / getLength(), getY() / getLength())
	 * @return The resulting vector has a length equal to 1.0.
	 * 			| result.getLength() == 1.0
	 */
	public Vector2d normalize(){
		double length = getLength();
		return new Vector2d(getX() / length, getY() / length);
	}

	/**
	 * Compute the alpha value of the intersection of two lines, given a point and direction
	 * of each line. Where alpha is the intersection value corresponding to the given first direction,
	 * so originA added with directionA mulitplied by alpha results in the intersection point.
	 * @param originA
	 * 			A point on the first line
	 * @param directionA
	 * 			The direction of the first line
	 * @param originB
 * 				A point on the second line
	 * @param directionB
	 * 			The direction of the second line
	 * @return	The intersection value
	 * 			@see implementation
	 */
	public static double intersect(Vector2d originA, Vector2d directionA, Vector2d originB, Vector2d directionB){
		double n = directionA.getX() * directionB.getY() - directionA.getY() * directionB.getX();
		if(n == 0)
			return Double.POSITIVE_INFINITY;
		double alpha = (directionB.getX() * originA.getY() - directionB.getY() * originA.getX()
						- directionB.getX() * originB.getY() + directionB.getY() * originB.getX()) / n;
		return alpha;
	}

	/**
	 * Return the velocity components as an array
	 * @return | result[0] == x && result[1] == y
	 */
	public double[] asArray(){
		return new double[] { x, y };
	}

	/**
	 * Check whether this vector is equal to the given object
	 * @return | result == (other != null) && (this.getClass() == other.getClass()) 
	 * 		   | && this.getX() == (Vector2d other).getX() && this.getY() == (Vector2d other).getY()
	 */
	@Override
	public boolean equals(Object other){
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		Vector2d otherVector = (Vector2d) other;
		return getX() == otherVector.getX() && getY() == otherVector.getY();
	}

	/**
	 * Return the hash code for this vector
	 */
	@Override
	public int hashCode(){
		return (int) (getX() * 31 + getY());
	}
	
	/**
	 * Return the string representation of this vector
	 */
	@Override
	public String toString(){
		return "(" + getX() + ", " + getY() + ")";
	}
}

