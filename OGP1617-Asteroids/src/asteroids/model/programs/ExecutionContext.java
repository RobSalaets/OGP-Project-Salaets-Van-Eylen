package asteroids.model.programs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import asteroids.model.programs.expressions.Type;
import asteroids.part3.programs.SourceLocation;

public class ExecutionContext {

	public ExecutionContext(/*Program program*/) {
//		this.program = program;
	}

//	private Program program;
	private List<Type> printLog = new ArrayList<Type>();
	private Stack<Desertable> stack = new Stack<Desertable>();

	public void addToPrintLog(Type value, SourceLocation line) throws ProgramExecutionTimeException {
		if (value == null)
			throw new ProgramExecutionTimeException("Trying to add null to the print log", line);
		printLog.add(value);
	}

	public void addToStack(Desertable d, SourceLocation line) throws ProgramExecutionTimeException {
		if (d == null)
			throw new ProgramExecutionTimeException("Trying to add null to the execution stack", line);
		stack.push(d);
	}

	public void breakFromCurrent(SourceLocation line) throws ProgramExecutionTimeException {
		if (stack.isEmpty() || !(stack.peek() instanceof Breakable))
			throw new ProgramExecutionTimeException("No statement to break from.", line);
		stack.pop();
		setBreak();
	}
	
	public void returnFromCurrent(SourceLocation line) throws ProgramExecutionTimeException {
		Desertable top = null;
		do {
			if (stack.isEmpty())
				throw new ProgramExecutionTimeException("No function to return from.", line);
			top = stack.pop();
			setReturn();
		} while (!(top instanceof Function));
	}
	
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
		returning = false;
		breaking = false;
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
}
