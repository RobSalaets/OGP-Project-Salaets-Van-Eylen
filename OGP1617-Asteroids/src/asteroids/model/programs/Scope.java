package asteroids.model.programs;

import java.util.HashMap;

import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class Scope {

	private HashMap<String, Function> functionMap = new HashMap<String, Function>();
	private HashMap<String, Type> variableMap = new HashMap<String, Type>();
	
	public void putVariable(String varName, Type value, SourceLocation line) throws ProgramExecutionTimeException{
		if(readOnly)
			throw new ProgramExecutionTimeException("Unable to write variables to this Scope", line);
		if(functionMap.containsKey(varName))
			throw new ProgramExecutionTimeException("Duplicate name with a function.", line);
		if(variableMap.containsKey(varName) && !(variableMap.get(varName) instanceof Type))
			throw new ProgramExecutionTimeException("The type of this variable is incompatible with given Type: " + value.toString(), line);
		variableMap.put(varName, value);
	}
	
	public Type getVariable(String varName){
		return variableMap.get(varName);
	}
	
	public void putFunction(String functionName, Function value){
		functionMap.put(functionName, value);
	}
	
	public Function getFunction(String functionName){
		return functionMap.get(functionName);
	}
	
	public void setReadOnly(boolean state){
		this.readOnly = state;
	}
	
	private boolean readOnly = false;
}