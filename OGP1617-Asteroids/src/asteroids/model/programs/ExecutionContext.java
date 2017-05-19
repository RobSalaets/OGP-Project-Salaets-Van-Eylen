package asteroids.model.programs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.expressions.types.Type;
import asteroids.model.programs.statements.Action;
import asteroids.model.programs.statements.BlockStatement;
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
		if(getCurrentFunction() != null)
			throw new ProgramExecutionTimeException("Trying to print in a function environment", line);
		printLog.add(value.getValue());
	}
	
	public List<Object> getPrintLog() {
		return printLog;
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
		for(Desertable d : stack)
			if(d instanceof Function)
				f = (Function) d;
		return f;
	}

	public void addToStack(Desertable d, SourceLocation line) throws ProgramExecutionTimeException {
		if (d == null)
			throw new ProgramExecutionTimeException("Trying to add null to the execution stack", line);
		stack.push(d);
	}

	public void breakFromCurrent(SourceLocation line) throws ProgramExecutionTimeException {
		Desertable top = null;
		do {
			if (stack.isEmpty())
				throw new ProgramExecutionTimeException("No statement to break from.", line);
			top = stack.pop();
		} while (!(top instanceof Breakable));
		setBreak();
	}
	
	public void returnFromCurrent(SourceLocation line) throws ProgramExecutionTimeException {
		Desertable top = null;
		do {
			if (stack.isEmpty())
				throw new ProgramExecutionTimeException("No function to return from.", line);
			top = stack.pop();
		} while (!(top instanceof Function));
		setReturn();
	}
	
	private Stack<Desertable> stack = new Stack<Desertable>();
	
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
		if(getCurrentFunction() == null)
			getGlobalScope().setReadOnly(false);
		returning = false;
	}
	
	private void setBreak(){
		breaking = true;
	}
	
	private void setReturn(){
		returning = true;
		breaking = true;
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
		if(getCurrentFunction() != null)
			throw new ProgramExecutionTimeException("Trying to execute action statement in function environment", line);
		executionTimeLeft -= Action.ACTION_TIME;
	}
	
	public boolean canExecuteAction(){
		return executionTimeLeft >= Action.ACTION_TIME;
	}
	
	private double executionTimeLeft;
	
	private final HashMap<BlockStatement, Stack<Integer>> blockPointers = new HashMap<>();
	
	public int getBlockPointerFor(BlockStatement b){
		if(blockPointers.containsKey(b))
			return blockPointers.get(b).peek();
		Stack<Integer> newStack = new Stack<>();
		blockPointers.put(b, newStack);
		return newStack.push(0);
	}
	
	public void setBlockPointer(BlockStatement b, int pointer, SourceLocation line){
		if(blockPointers.containsKey(b))
			blockPointers.get(b).push(pointer);
		else
			throw new ProgramExecutionTimeException("No stack for " + b.toString(), line);
	}
}
