package asteroids.model.programs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import asteroids.model.programs.expressions.Type;

public class Scope {

	private HashMap<String, Function> functionMap = new HashMap<String, Function>();
	private HashMap<String, Type> variableMap = new HashMap<String, Type>();
	
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
