package asteroids.model.programs;

import asteroids.model.programs.expressions.ExpressionEvaluationException;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class GlobalScope extends Scope {
	
	public void putVariable(String varName, Type value, SourceLocation line) throws ProgramExecutionTimeException{
		if(readOnly)
			throw new ProgramExecutionTimeException("Unable to write variables to this Scope", line);
		if(functionMap.containsKey(varName))
			throw new ProgramExecutionTimeException("Duplicate name with a function.", line);
		if(variableMap.containsKey(varName) && !(variableMap.get(varName).getClass().isAssignableFrom(value.getClass())))
			throw new ProgramExecutionTimeException("The type of this variable is incompatible with given Type: " + value.getClass().toString(), line);
		variableMap.put(varName, value);
	}
	
	public Type getVariable(String varName, SourceLocation line) throws ExpressionEvaluationException{
		if(!hasAsVariable(varName))
			throw new ExpressionEvaluationException("No such variable: " + varName, line);
		return variableMap.get(varName);
	}
	
	public void putFunction(String functionName, Function function, SourceLocation line) throws ProgramExecutionTimeException{
		if(function == null)
			throw new ProgramExecutionTimeException("Trying to add null to function scope", line);
		functionMap.put(functionName, function);
	}
	
	public Function getFunction(String functionName, SourceLocation line) throws ExpressionEvaluationException{
		if(!functionMap.containsKey(functionName))
			throw new ExpressionEvaluationException("No such function: " + functionName, line);
		return functionMap.get(functionName);
	}
	
	public void setReadOnly(boolean state){
		this.readOnly = state;
	}
	
	private boolean readOnly = false;
}
