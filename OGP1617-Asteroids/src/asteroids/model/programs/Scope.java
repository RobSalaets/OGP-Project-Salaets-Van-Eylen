package asteroids.model.programs;

import java.util.HashMap;

import asteroids.model.programs.expressions.Type;

public class Scope {

	private HashMap<String, Function> functionMap;
	private HashMap<String, Type> variableMap;
	
	public void putVariable(String varName, Type value) throws IllegalStateException{
		if(readOnly)
			throw new IllegalStateException();
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
