package asteroids.model.programs;

import java.util.List;

import asteroids.model.Program;
import asteroids.model.programs.expressions.BinaryArithmeticExpression;
import asteroids.model.programs.expressions.BinaryArithmeticOperation;
import asteroids.model.programs.expressions.DoubleLiteralExpression;
import asteroids.model.programs.expressions.EqualsExpression;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.FunctionCallExpression;
import asteroids.model.programs.expressions.LessThanExpression;
import asteroids.model.programs.expressions.NotExpression;
import asteroids.model.programs.expressions.ParameterExpression;
import asteroids.model.programs.expressions.UnaryArithmeticExpression;
import asteroids.model.programs.expressions.UnaryArithmeticOperation;
import asteroids.model.programs.expressions.VariableExpression;
import asteroids.model.programs.expressions.types.BooleanLiteral;
import asteroids.model.programs.expressions.types.DoubleLiteral;
import asteroids.model.programs.expressions.types.Type;
import asteroids.model.programs.statements.ActionType;
import asteroids.model.programs.statements.AssingmentStatement;
import asteroids.model.programs.statements.BasicAction;
import asteroids.model.programs.statements.BreakStatement;
import asteroids.model.programs.statements.IfStatement;
import asteroids.model.programs.statements.MultiStatement;
import asteroids.model.programs.statements.PrintStatement;
import asteroids.model.programs.statements.ReturnStatement;
import asteroids.model.programs.statements.Statement;
import asteroids.model.programs.statements.TurnAction;
import asteroids.model.programs.statements.WhileStatement;
import asteroids.part3.programs.IProgramFactory;
import asteroids.part3.programs.SourceLocation;

@SuppressWarnings("all")
public class ProgramFactory implements IProgramFactory<Expression<? extends Type>, Statement, Function, Program>{

	@Override
	public Program createProgram(List<Function> functions, Statement main) {
		return new Program(functions, main);
	}

	@Override
	public Function createFunctionDefinition(String functionName, Statement body, SourceLocation sourceLocation) {
		return new Function(functionName, body, sourceLocation);
	}

	@Override
	public Statement createAssignmentStatement(String variableName, Expression<? extends Type> value,
			SourceLocation sourceLocation) {
		return new AssingmentStatement(sourceLocation, variableName, value);
	}

	@Override
	public Statement createWhileStatement(Expression<? extends Type> condition, Statement body,
			SourceLocation sourceLocation) {
		return new WhileStatement(sourceLocation, (Expression<BooleanLiteral>) condition, body);
	}

	@Override
	public Statement createBreakStatement(SourceLocation sourceLocation) {
		return new BreakStatement(sourceLocation);
	}

	@Override
	public Statement createReturnStatement(Expression<? extends Type> value, SourceLocation sourceLocation) {
		return new ReturnStatement(sourceLocation, value);
	}

	@Override
	public Statement createIfStatement(Expression<? extends Type> condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		return new IfStatement(sourceLocation, (Expression<BooleanLiteral>) condition, ifBody, elseBody);
	}

	@Override
	public Statement createPrintStatement(Expression<? extends Type> value, SourceLocation sourceLocation) {
		return new PrintStatement(sourceLocation, value);
	}

	@Override
	public Statement createSequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		return new MultiStatement(sourceLocation, statements);
	}

	@Override
	public Expression<? extends Type> createReadVariableExpression(String variableName, SourceLocation sourceLocation) {
		return new VariableExpression(variableName, sourceLocation);
	}

	@Override
	public Expression<? extends Type> createReadParameterExpression(String parameterName,
			SourceLocation sourceLocation) {
		return new ParameterExpression(parameterName, sourceLocation);
	}

	@Override
	public Expression<? extends Type> createFunctionCallExpression(String functionName,
			List<Expression<? extends Type>> actualArgs, SourceLocation sourceLocation) {
		return new FunctionCallExpression(functionName, actualArgs, sourceLocation);
	}

	@Override
	public Expression<? extends Type> createChangeSignExpression(Expression<? extends Type> expression,
			SourceLocation sourceLocation) {
		return new UnaryArithmeticExpression((Expression<? super DoubleLiteral>) expression, UnaryArithmeticOperation.CHANGE_SIGN, sourceLocation);
	}

	@Override
	public Expression<? extends Type> createNotExpression(Expression<? extends Type> expression,
			SourceLocation sourceLocation) {
		return new NotExpression((Expression<? super BooleanLiteral>) expression, sourceLocation);
	}

	@Override
	public Expression<? extends Type> createDoubleLiteralExpression(double value, SourceLocation location) {
		return new DoubleLiteralExpression(new DoubleLiteral(value), location);
	}

	@Override
	public Expression<? extends Type> createNullExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createSelfExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createShipExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createAsteroidExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createPlanetoidExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createBulletExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createPlanetExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createAnyExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createGetXExpression(Expression<? extends Type> e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createGetYExpression(Expression<? extends Type> e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createGetVXExpression(Expression<? extends Type> e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createGetVYExpression(Expression<? extends Type> e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createGetRadiusExpression(Expression<? extends Type> e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type> createLessThanExpression(Expression<? extends Type> e1,
			Expression<? extends Type> e2, SourceLocation location) {
		return new LessThanExpression((Expression<? super DoubleLiteral>)e1, (Expression<? super DoubleLiteral>)e2, location);
	}

	@Override
	public Expression<? extends Type> createEqualityExpression(Expression<? extends Type> e1,
			Expression<? extends Type> e2, SourceLocation location) {
		return new EqualsExpression(e1, e2, location);
	}

	@Override
	public Expression<? extends Type> createAdditionExpression(Expression<? extends Type> e1,
			Expression<? extends Type> e2, SourceLocation location) {
		return new BinaryArithmeticExpression((Expression<? super DoubleLiteral>)e1, (Expression<? super DoubleLiteral>)e2, BinaryArithmeticOperation.ADD, location);
	}

	@Override
	public Expression<? extends Type> createMultiplicationExpression(Expression<? extends Type> e1,
			Expression<? extends Type> e2, SourceLocation location) {
		return new BinaryArithmeticExpression((Expression<? super DoubleLiteral>)e1, (Expression<? super DoubleLiteral>)e2, BinaryArithmeticOperation.MULTIPLY, location);
	}

	@Override
	public Expression<? extends Type> createSqrtExpression(Expression<? extends Type> e, SourceLocation location) {
		return new UnaryArithmeticExpression((Expression<? super DoubleLiteral>) e, UnaryArithmeticOperation.SQUARE_ROOT, location);
	}

	@Override
	public Expression<? extends Type> createGetDirectionExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createThrustOnStatement(SourceLocation location) {
		return new BasicAction(location, ActionType.THRUST_ON);
	}

	@Override
	public Statement createThrustOffStatement(SourceLocation location) {
		return new BasicAction(location, ActionType.THRUST_OFF);
	}

	@Override
	public Statement createFireStatement(SourceLocation location) {
		return new BasicAction(location, ActionType.FIRE_BULLET);
	}

	@Override
	public Statement createTurnStatement(Expression<? extends Type> angle, SourceLocation location) {
		return new TurnAction(location, (Expression<? super DoubleLiteral>) angle);
	}

	@Override
	public Statement createSkipStatement(SourceLocation location) {
		return new BasicAction(location, ActionType.SKIP);
	}
	
}
