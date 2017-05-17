package asteroids.model.programs;

public class Function implements Desertable{

	//error als er uit body gekomen wordt met !isReturning()
	//TODO stopReturning()
	
	private ExecutionContext context;
	
	public void setExecutionContext(ExecutionContext context) throws IllegalArgumentException{
		if(context == null)
			throw new IllegalArgumentException();
		this.context = context;
	}
}
