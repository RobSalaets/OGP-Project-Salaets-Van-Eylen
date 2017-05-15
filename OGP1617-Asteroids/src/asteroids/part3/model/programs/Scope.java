package asteroids.part3.model.programs;

import java.util.HashMap;

import asteroids.part3.model.programs.expressions.Expression;
import asteroids.part3.model.programs.expressions.Type;

public class Scope {
	
	//heeft opsplitsen zin? //TODO
//	private HashMap<String, Expression<? extends Type>> variableMap;
//	private HashMap<String, Expression<Type>> variableMap;
	private HashMap<String, Type> variableMap;
}
