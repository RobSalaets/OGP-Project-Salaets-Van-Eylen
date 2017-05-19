package asteroids.model.programs.exceptions;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling wrong expression evaluations.
 */
public class ExpressionEvaluationException extends RuntimeException {

	/**
	 * Initialize this new ExpressionEvaluationException involving the given
	 * expression.
	 *
	 * @param   expression
	 *          The faulty expression.
	 * @post    The expression involved in this new ExpressionEvaluationException
	 * 			is the same as the given expression.
	 *          | new.getExpression() == expression
	 */
	public ExpressionEvaluationException(String string, SourceLocation sourceLocation, Expression<? extends Type> expression) {
		this.expression = expression;
		System.err.println("Error: " + string + " " + sourceLocation.toString());
	}
	
	/**
	 * Return the expression of this ExpressionEvaluationException.
	 */
	@Basic
	@Immutable
	public Expression<? extends Type> getExpression() {
		return this.expression;
	}

	/**
	 * Variable registering the expression of this ExpressionEvaluationException.
	 */
	private final Expression<? extends Type> expression;

	private static final long serialVersionUID = -7611353027712222222L;

}
