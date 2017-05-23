package asteroids.model.programs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.model.programs.expressions.types.Type;
import asteroids.model.programs.statements.Action;
import asteroids.model.programs.statements.BlockStatement;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public class ExecutionContext {


	public ExecutionContext(Ship executor, World world) throws IllegalArgumentException{
		if(executor == null)
			throw new IllegalArgumentException();
		this.executor = executor;
		this.globalScope = new GlobalScope();
		this.world = world;
	}

	public void addToPrintLog(Type value, SourceLocation line) throws ProgramExecutionTimeException {
		if (value == null)
			throw new ProgramExecutionTimeException("Trying to add null to the print log", line);
		if(inFunction())
			throw new ProgramExecutionTimeException("Trying to print in a function environment", line);
		printLog.add(value.getValue());
	}
	
	public List<Object> getPrintLog() {
		return new ArrayList<Object>(printLog);
	}
	
	public void clearPrintLog() {
		printLog.clear();
	}
	
	private List<Object> printLog = new ArrayList<Object>();
	
	public World getWorld(){
		return world;
	}

	private final World world;
	
	public Ship getExecutor(){
		return executor;
	}
	
	private final Ship executor;
	
	public Scope getCurrentScope(){
		Function f = getCurrentFunction();
		if(f != null)
			return f.getLocalScope();
		return getGlobalScope();
	}
	
	public GlobalScope getGlobalScope(){
		return globalScope;
	}
	
	private final GlobalScope globalScope;
	
	private Function getCurrentFunction(){
		Function f = null;
		for(Interruptable d : stack)
			if(d instanceof Function)
				f = (Function) d;
		return f;
	}
	
	private boolean inFunction(){
		return getCurrentFunction() != null;
	}

	public void addToStack(Interruptable d, SourceLocation line) throws ProgramExecutionTimeException {
		if (d == null)
			throw new ProgramExecutionTimeException("Trying to add null to the execution stack", line);
		stack.push(d);
	}
	
	public void interruptFromCurrent(Statement interruptStatement, SourceLocation line) throws ProgramExecutionTimeException {
		Interruptable top = null;
		do {
			if (stack.isEmpty())
				throw new ProgramExecutionTimeException("No Interruptable to interrupt from.", line);
			top = stack.pop();
		} while (!(top.isValidInterruptStatement(interruptStatement)));
		top.onBreak(this);
	}
	
	private Stack<Interruptable> stack = new Stack<Interruptable>();
	
	public boolean isBreaking(){
		return breaking;
	}
	
	public boolean isReturning(){
		return returning;
	}
	
	public void stopBreaking(){
		breaking = false;
	}
	
	public void stopReturning(){
		if(!inFunction())
			getGlobalScope().setReadOnly(false);
		returning = false;
	}
	
	public void setBreak(){
		breaking = true;
	}
	
	public void setReturn(){
		returning = true;
	}
	
	private boolean breaking = false;
	private boolean returning = false;
	
	public void clearStack(){
		stack.clear();
	}
	
	public void addExecTime(double timeDelta){
		executionTimeLeft += timeDelta;
	}
	
	public void decrementExecutionTime(SourceLocation line) throws ProgramExecutionTimeException{
		if(inFunction())
			throw new ProgramExecutionTimeException("Trying to execute action statement in function environment", line);
		executionTimeLeft -= Action.ACTION_TIME;
	}
	
	public boolean canExecuteAction(){
		return executionTimeLeft >= Action.ACTION_TIME;
	}
	
	private double executionTimeLeft;
	
	public int getBlockPointerFor(BlockStatement b){
		if(!inFunction() && blockPointers.containsKey(b))
				return blockPointers.get(b);
		blockPointers.put(b, 0);
		return 0;
	}
	
	public void setBlockPointer(BlockStatement b, int pointer, SourceLocation line){
		if(blockPointers.containsKey(b))
			blockPointers.put(b, pointer);
		else
			throw new ProgramExecutionTimeException("No pointer for " + b.toString(), line);
	}
	
	private final HashMap<BlockStatement, Integer> blockPointers = new HashMap<BlockStatement, Integer>();
}
