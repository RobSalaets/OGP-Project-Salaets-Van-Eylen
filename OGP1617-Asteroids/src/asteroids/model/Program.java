package asteroids.model;

import java.util.List;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.Function;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.ExpressionEvaluationException;
import asteroids.model.programs.statements.Statement;

public class Program{

	public Program(List<Function> functions, Statement body) throws IllegalArgumentException{
		if(functions.contains(null) || body == null)
			throw new IllegalArgumentException();
		this.functions = functions;
		this.body = body;
	}
	
	private final List<Function> functions;
	private final Statement body;
	
	public void addExecutor(Ship ship) throws IllegalArgumentException{
		if(ship == null)
			throw new IllegalArgumentException();
		context = new ExecutionContext(ship, (World) ship.getContainer());
		for(Function f : functions){
			f.setExecutionContext(context);
			context.getGlobalScope().putFunction(f.getName(), f, f.getSourceLocation());
		}
	}
	
	private ExecutionContext context;

	public List<Object> execute(double dt) throws ProgramExecutionTimeException, ExpressionEvaluationException{
		context.addExecTime(dt);
		body.execute(context);
		context.clearStack();
		return context.getPrintLog();
	}
}
