package asteroids.model.programs.statements;

import asteroids.part3.programs.SourceLocation;

public abstract class Action extends Statement {
	
	public Action(SourceLocation location){
		super(location);
	}
	
	public static final double ACTION_TIME = 0.2;
}
