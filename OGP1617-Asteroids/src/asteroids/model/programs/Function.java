package asteroids.model.programs;

import java.util.List;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.types.Type;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public class Function implements Desertable{
	
	public Function(String functionName, Statement body, SourceLocation sourceLocation) throws IllegalArgumentException{
		if(body == null || sourceLocation == null)
			throw new IllegalArgumentException();
		this.name = functionName;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}
	
	private final Statement body;
	
	public void setExecutionContext(ExecutionContext context){
		this.context = context;
	}
	
	private ExecutionContext context;
	
	public Type execute(List<Expression<? extends Type>> arguments) throws ProgramExecutionTimeException{
		if(context == null)
			throw new ProgramExecutionTimeException("The execution context of this Function was not set", sourceLocation);
		localScope = new LocalScope(context.getGlobalScope());
		for(int i = 0; i < arguments.size(); i++)
			localScope.putVariable("$"+String.valueOf(i), arguments.get(i).evaluate(context.getCurrentScope(), context.getWorld(), context.getExecutor()), sourceLocation);
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
	
}
