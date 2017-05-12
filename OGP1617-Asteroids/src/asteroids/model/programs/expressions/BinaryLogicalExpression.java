package asteroids.model.programs.expressions;

public class BinaryLogicalExpression extends ??? {//splitsen gaat niet samen

	public BinaryLogicalExpression(Expression<BooleanLiteral> left, Expression<BooleanLiteral> right) throws IllegalArgumentException{
		super(left, right);
		if(operation == null)
			throw new IllegalArgumentException("The BinaryLogicalOperation must be effective.");
	}
}
