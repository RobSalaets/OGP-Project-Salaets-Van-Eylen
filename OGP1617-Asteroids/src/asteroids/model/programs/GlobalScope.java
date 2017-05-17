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
		if(variableMap.containsKey(varName) && !(variableMap.get(varName) instanceof Type))
			throw new ProgramExecutionTimeException("The type of this variable is incompatible with given Type: " + value.toString(), line);
		variableMap.put(varName, value);
	}
	
	public Type getVariable(String varName, SourceLocation line) throws ExpressionEvaluationException{
		Type result = variableMap.get(varName);
		if(result == null)
			throw new ExpressionEvaluationException("No such variable: " + varName, line);
		return result;
	}
	
	public void putFunction(String functionName, Function function, SourceLocation line){
		functionMap.put(functionName, function);
	}
	
	public Function getFunction(String functionName, SourceLocation line) throws ExpressionEvaluationException{
		Function result = functionMap.get(functionName);
		if(result == null)
			throw new ExpressionEvaluationException("No such function: " + functionName, line);
		return result;
	}
	
	public void setReadOnly(boolean state){
		this.readOnly = state;
	}
	
	private boolean readOnly = false;
}
