package asteroids.model.programs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import asteroids.model.programs.expressions.Type;

public class ExecutionContext {

	public ExecutionContext(/*Program program*/) {
//		this.program = program;
	}

//	private Program program;
	private List<Type> printLog = new ArrayList<Type>();
	private Stack<Desertable> stack = new Stack<Desertable>();
	
	private boolean breaking = false;

	public void addToPrintLog(Type value) throws ProgramExecutionTimeException {
		if (value == null)
			throw new ProgramExecutionTimeException("Trying to add null to the print log");
		printLog.add(value);
	}

	public void addToStack(Desertable d) throws ProgramExecutionTimeException {
		if (d == null)
			throw new ProgramExecutionTimeException("Trying to add null to the execution stack");
		stack.push(d);
	}

	public void breakFromCurrent() throws ProgramExecutionTimeException {
		if (stack.isEmpty() || !(stack.peek() instanceof Breakable))
			throw new ProgramExecutionTimeException("No statement to break from.");
		stack.pop();
		breaking = true;
	}
	
	public boolean isBreaking(){
		return breaking;
	}
	
	public void stopBreaking(){
		breaking = false;
	}

	public void returnFromCurrent() throws ProgramExecutionTimeException {
		Desertable top = null;
		do {
			if (stack.isEmpty())
				throw new ProgramExecutionTimeException("No function to return from.");
			top = stack.pop();
			top.handleEscape();// TODO Probleem met isBreaking()
		} while (!(top instanceof Function));
	}
}
