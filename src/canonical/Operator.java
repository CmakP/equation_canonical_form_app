/**
 * Project: Siamak_Pourian_Test_Task_Solution
 * File: Operators.java
 * Date: May 2, 2017
 * Time: 8:00:00 PM
 */
package canonical;

/**
 * @author Siamak Pourian
 *
 * Operator Class
 */
public enum Operator {

	ADDITION('+'), SUBTRACTION('-'), DIVISION('/'), ASSIGNMENT('='), FLOATING_POINT('.'), BRACKET_OPEN('('), BRACKET_CLOSE(')'), BITWISE('^');
	
	private char operator;
	
	/**
	 * Overloaded constructor
	 * 
	 * @param operator the operator
	 */
	Operator(char operator) {
		this.operator = operator;
	}

	/**
	 * @return the operator
	 */
	public char getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(char operator) {
		this.operator = operator;
	}
}
