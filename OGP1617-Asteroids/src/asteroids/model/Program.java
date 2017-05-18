package asteroids.model;

import java.util.Arrays;
import java.util.List;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.Function;
import asteroids.model.programs.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.ExpressionEvaluationException;
import asteroids.model.programs.statements.MultiStatement;
import asteroids.model.programs.statements.Statement;

public class Program{


	public Program(List<Function> functions, Statement main) throws IllegalArgumentException{
		if(functions.contains(null) || main == null)
			throw new IllegalArgumentException();
		this.functions = functions;
		this.body = new MultiStatement(main.getSourceLocation(), Arrays.asList(new Statement[]{main}));
	}
	
	private final List<Function> functions;
	private final MultiStatement body;
	
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
		List<Object> log = context.getPrintLog();
		return body.hasFullyExecuted() ? log : null;
	}
}
