package asteroids.model.programs;

import java.util.List;

import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.types.Type;
import asteroids.model.programs.statements.BlockStatement;
import asteroids.model.programs.statements.ReturnStatement;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public class Function implements Interruptable{
	
	public Function(String functionName, Statement body, SourceLocation sourceLocation) throws IllegalArgumentException{
		if(body == null || sourceLocation == null)
			throw new IllegalArgumentException();
		this.name = functionName;
		this.body = new BlockStatement(body.getSourceLocation(), body);
		this.sourceLocation = sourceLocation;
	}
	
	public Type execute(ExecutionContext context, List<Type> arguments) throws ProgramExecutionTimeException{
		if(context == null)
			throw new ProgramExecutionTimeException("The execution context of this Function was not set", getSourceLocation());
		localScope = new LocalScope(context.getGlobalScope());
		for(int i = 0; i < arguments.size(); i++)
			getLocalScope().putVariable("$"+String.valueOf(i+1), arguments.get(i), getSourceLocation());
		context.getGlobalScope().setReadOnly(true);
		context.addToStack(this, getSourceLocation());
		body.execute(context);
		if(context.isReturning()){
			context.setBreakBlockStatement(false);
			context.setReturn(false);
			context.getGlobalScope().setReadOnly(false);
			return localScope.getVariable("$0", getSourceLocation());
		}
		else if(context.isBreaking()){
			context.getGlobalScope().setReadOnly(false);
			return null;
		}
		throw new ProgramExecutionTimeException("No return statement in function: " + name, getSourceLocation());
	}

	public LocalScope getLocalScope() {
		return localScope;
	}
	
	private LocalScope localScope;
	
	public SourceLocation getSourceLocation(){
		return sourceLocation;
	}
	
	private final SourceLocation sourceLocation;
	
	public String getName(){
		return name;
	}
	
	private final String name;
	
	private final BlockStatement body;
	
	@Override
	public void onInterrupt(ExecutionContext executionContext) {
		executionContext.setBreakBlockStatement(true);
		executionContext.setReturn(true);
	}
	
	@Override
	public boolean isValidInterruptStatement(Statement s) {
		return s instanceof ReturnStatement;
	}

	public Function getNew(){
		return new Function(getName(), body, getSourceLocation());
	}
	
}
