package asteroids.model.programs;

import asteroids.model.programs.expressions.ExpressionEvaluationException;
import asteroids.model.programs.expressions.types.Type;
import asteroids.part3.programs.SourceLocation;

public class LocalScope extends Scope{
	

	public LocalScope(Scope global){
		this.global = global;
	}

	private final Scope global;
	
	public void putVariable(String varName, Type value, SourceLocation line) throws ProgramExecutionTimeException{
		if(variableMap.containsKey(varName) && !(variableMap.get(varName) instanceof Type))
			throw new ProgramExecutionTimeException("Type Mismatch - The type of this variable is incompatible with given Type: " + value.getClass().toString(), line);
		variableMap.put(varName, value);
	}
	
	public Type getVariable(String varName, SourceLocation line) throws ExpressionEvaluationException{
		if(hasAsVariable(varName))
			return variableMap.get(varName);
		if(global.hasAsVariable(varName))
			return global.variableMap.get(varName);
		throw new ExpressionEvaluationException("No such variable: " + varName, line);
	}
	
	public Function getFunction(String functionName, SourceLocation line) throws ExpressionEvaluationException{
		Function result = global.functionMap.get(functionName);
		if(result == null)
			throw new ExpressionEvaluationException("No such function: " + functionName, line);
		return result;
	}
}
