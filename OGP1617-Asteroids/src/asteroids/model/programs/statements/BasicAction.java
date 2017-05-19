package asteroids.model.programs.statements;

import asteroids.model.programs.ExecutionContext;
import asteroids.model.programs.exceptions.ProgramExecutionTimeException;
import asteroids.part3.programs.SourceLocation;

public class BasicAction extends Action {

	public BasicAction(SourceLocation location, ActionType actionType) {
		super(location);
		this.actionType = actionType;
	}
	
	private final ActionType actionType;
	
	@Override
	public boolean execute(ExecutionContext context) throws ProgramExecutionTimeException {
		if(context.canExecuteAction()){
			context.decrementExecutionTime(getSourceLocation());
			switch(actionType){
			case FIRE_BULLET:
				context.getExecutor().fireBullet();
				break;
			case SKIP:
				break;
			case THRUST_OFF:
				context.getExecutor().thrustOff();
				break;
			case THRUST_ON:
				context.getExecutor().thrustOn();
				break;
			default:
				break;
			}
			return true;
		}
		throw new ProgramExecutionTimeException("Executing action with not enough time left", getSourceLocation());
	}

}
