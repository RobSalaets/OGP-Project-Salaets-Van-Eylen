package asteroids.model.programs.expressions;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class BinaryLessThanExpression extends BinaryExpression<DoubleLiteral, BooleanLiteral> {

	public BinaryLessThanExpression(Expression<? super DoubleLiteral> left, Expression<? super DoubleLiteral> right, SourceLocation location)
			throws IllegalArgumentException {
		super(left, right, location);
	}

	@Override
	public BooleanLiteral evaluate(Scope scope, World world, Ship executor) throws ExpressionEvaluationException{
		Type evalL = getLeftArgument().evaluate(scope, world, executor);
		Type evalR = getRightArgument().evaluate(scope, world, executor);
		if(!((evalL instanceof DoubleLiteral) && (evalR instanceof DoubleLiteral)))
			throw new ExpressionEvaluationException("Given operands do not evaluate to DoubleLiteral", getSourceLocation());
		
		return new BooleanLiteral(((DoubleLiteral)evalL).getValue() < ((DoubleLiteral)evalR).getValue());
	}
}
