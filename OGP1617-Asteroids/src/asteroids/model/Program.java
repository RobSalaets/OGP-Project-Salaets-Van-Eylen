package asteroids.model;

import java.util.List;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.Function;
import asteroids.model.programs.exceptions.ExpressionEvaluationException;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.statements.BlockStatement;
import asteroids.model.programs.statements.Statement;

public class Program{


	public Program(List<Function> functions, Statement main) throws IllegalArgumentException{
		if(functions.contains(null) || main == null)
			throw new IllegalArgumentException();
		this.functions = functions;
		this.body = new BlockStatement(main.getSourceLocation(), main);
	}
	
	private final List<Function> functions;
	private final BlockStatement body;
	
	public void addExecutor(Ship ship) throws IllegalArgumentException{
		if(ship == null)
			throw new IllegalArgumentException();
		context = new ExecutionContext(ship, (World) ship.getContainer());
		for(Function f : functions){
			context.getGlobalScope().putFunction(f.getName(), f, f.getSourceLocation());
		}
	}
	
	private ExecutionContext context;

	public List<Object> execute(double dt) throws ProgramExecutionTimeException, ExpressionEvaluationException{
		context.addExecTime(dt);
		body.execute(context);
		context.clearStack();
		if(body.hasFullyExecuted()){
			List<Object> log = context.getPrintLog();
			context.clearPrintLog();
			return log;
		}
		return null;
	}
}
