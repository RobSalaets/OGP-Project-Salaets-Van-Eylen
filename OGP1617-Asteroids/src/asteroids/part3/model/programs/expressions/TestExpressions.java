package asteroids.model.programs.expressions;

public class TestExpressions {

	public static void main(String[] args) {
		VariableExpression<DoubleLiteral> varA = new VariableExpression<DoubleLiteral>(new DoubleLiteral(1.0));
		VariableExpression<DoubleLiteral> varB = new VariableExpression<DoubleLiteral>(new DoubleLiteral(3.0));
		BinaryArithmeticExpression ae = new BinaryArithmeticExpression(varA, varB, BinaryArithmeticOperation.MULTIPLY);
		if(varA instanceof Expression){
			System.out.println("varA inst of expression");
		}
		if(varA instanceof Expression<?>){
			System.out.println("varA inst of expression?");
		}
		if(ae instanceof Expression){
			System.out.println("ae inst of expression");
		}
		if(ae instanceof BinaryExpression){
			System.out.println("ae inst of binexpression");
		}
		if(ae instanceof BinaryArithmeticExpression){
			System.out.println("ae inst of arexpression");
		}
	}
}
