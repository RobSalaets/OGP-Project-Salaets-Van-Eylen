package asteroids.model.programs;

import java.util.HashMap;

import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public abstract class Scope {

	protected HashMap<String, Function> functionMap = new HashMap<String, Function>();
	protected HashMap<String, Type> variableMap = new HashMap<String, Type>();
	
	public abstract void putVariable(String varName, Type value, SourceLocation line) throws ProgramExecutionTimeException;
	
	public abstract Type getVariable(String varName, SourceLocation line);
	
	public abstract Function getFunction(String functionName, SourceLocation line);
	
}
