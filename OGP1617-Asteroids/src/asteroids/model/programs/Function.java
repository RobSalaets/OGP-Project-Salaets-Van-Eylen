package asteroids.model.programs;

import java.util.List;

import asteroids.model.programs.expressions.types.Type;
import asteroids.model.programs.statements.BlockStatement;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public class Function implements Desertable{
	
	public Function(String functionName, Statement body, SourceLocation sourceLocation) throws IllegalArgumentException{
		if(body == null || sourceLocation == null)
			throw new IllegalArgumentException();
		this.name = functionName;
		this.body = new BlockStatement(body.getSourceLocation(), body);
		this.sourceLocation = sourceLocation;
	}
	
	public Type execute(ExecutionContext context, List<Type> arguments) throws ProgramExecutionTimeException{
		if(context == null)
			throw new ProgramExecutionTimeException("The execution context of this Function was not set", sourceLocation);
		localScope = new LocalScope(context.getGlobalScope());
		for(int i = 0; i < arguments.size(); i++)
			localScope.putVariable("$"+String.valueOf(i+1), arguments.get(i), sourceLocation);
		context.getGlobalScope().setReadOnly(true);
		context.addToStack(this, sourceLocation);
		body.execute(context);
		if(!(context.isReturning() || context.isBreaking()))
			throw new ProgramExecutionTimeException("No return statement in function: " + name, sourceLocation);
		if(context.isReturning()){
			context.stopBreaking();
			context.stopReturning();
			return localScope.getVariable("$0", sourceLocation);
		}
		context.stopReturning();
		return null;
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
	
}
