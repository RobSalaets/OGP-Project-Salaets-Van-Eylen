package asteroids.model.programs;

import java.util.List;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Type;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.IProgramFactory;
import asteroids.part3.programs.SourceLocation;

public class ProgramFactory implements IProgramFactory<Expression<? extends Type, ? extends Type>, Statement, Function, Program>{

	@Override
	public Program createProgram(List<Function> functions, Statement main) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function createFunctionDefinition(String functionName, Statement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createAssignmentStatement(String variableName, Expression<? extends Type, ? extends Type> value,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWhileStatement(Expression<? extends Type, ? extends Type> condition, Statement body,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createBreakStatement(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createReturnStatement(Expression<? extends Type, ? extends Type> value,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createIfStatement(Expression<? extends Type, ? extends Type> condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrintStatement(Expression<? extends Type, ? extends Type> value,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createReadVariableExpression(String variableName,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createReadParameterExpression(String parameterName,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createFunctionCallExpression(String functionName,
			List<Expression<? extends Type, ? extends Type>> actualArgs, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createChangeSignExpression(
			Expression<? extends Type, ? extends Type> expression, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createNotExpression(
			Expression<? extends Type, ? extends Type> expression, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createDoubleLiteralExpression(double value,
			SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createNullExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createSelfExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createShipExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createAsteroidExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createPlanetoidExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createBulletExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createPlanetExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createAnyExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createGetXExpression(Expression<? extends Type, ? extends Type> e,
			SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createGetYExpression(Expression<? extends Type, ? extends Type> e,
			SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createGetVXExpression(
			Expression<? extends Type, ? extends Type> e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createGetVYExpression(
			Expression<? extends Type, ? extends Type> e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createGetRadiusExpression(
			Expression<? extends Type, ? extends Type> e, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createLessThanExpression(
			Expression<? extends Type, ? extends Type> e1, Expression<? extends Type, ? extends Type> e2,
			SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createEqualityExpression(
			Expression<? extends Type, ? extends Type> e1, Expression<? extends Type, ? extends Type> e2,
			SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createAdditionExpression(
			Expression<? extends Type, ? extends Type> e1, Expression<? extends Type, ? extends Type> e2,
			SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createMultiplicationExpression(
			Expression<? extends Type, ? extends Type> e1, Expression<? extends Type, ? extends Type> e2,
			SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createSqrtExpression(Expression<? extends Type, ? extends Type> e,
			SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<? extends Type, ? extends Type> createGetDirectionExpression(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createThrustOnStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createThrustOffStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createFireStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createTurnStatement(Expression<? extends Type, ? extends Type> angle, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSkipStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
