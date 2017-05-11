package asteroids.model.programs;

import java.util.HashMap;

import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.Type;

public class Scope {
	
	//heeft opsplitsen zin? //TODO
//	private HashMap<String, Expression<? extends Type>> variableMap;
//	private HashMap<String, Expression<Type>> variableMap;
	private HashMap<String, Type> variableMap;
}
