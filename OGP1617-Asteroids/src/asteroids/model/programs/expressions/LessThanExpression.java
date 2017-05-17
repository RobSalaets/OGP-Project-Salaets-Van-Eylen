package asteroids.model.programs.expressions;

import asteroids.model.World;
import asteroids.model.programs.Scope;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.model.programs.expressions.types.Type;

public class LessThanExpression extends BinaryExpression<DoubleLiteral, BooleanLiteral> {

	public LessThanExpression(Expression<? super DoubleLiteral> left, Expression<? super DoubleLiteral> right)
			throws IllegalArgumentException {
		super(left, right);
	}

	@Override
	public BooleanLiteral evaluate(Scope scope, World world) throws ExpressionEvaluationException{
		Type evalL = getLeftArgument().evaluate(scope, world);
		Type evalR = getRightArgument().evaluate(scope, world);
		if(!((evalL instanceof DoubleLiteral) && (evalR instanceof DoubleLiteral)))
			throw new ExpressionEvaluationException("Given operands do not evaluate to DoubleLiteral");
		
		return new BooleanLiteral(((DoubleLiteral)evalL).getValue() < ((DoubleLiteral)evalR).getValue());
	}
}
