/**
 * Project: Siamak_Pourian_Test_Task_Solution
 * File: Validator.java
 * Date: May 4, 2017
 * Time: 5:54:44 PM
 */
package canonical.util;

import canonical.Operator;

/**
 * @author Siamak Pourian
 *
 * Validator Class - Validates the input String
 */
public class Validator {

	/**
	 * Default constructor
	 */
	public Validator() {}

	/**
	 * Validates the input String for any non digit, letter, non defined operators
	 * for this application.
	 * 
	 * @param inout the input String
	 * @return true if the String is valid and false otherwise
	 */
	public static boolean validate(String input) {
		
		boolean valid;
		char[] temp = input.toCharArray();
		Operator[] operators = Operator.values();
		
		int i = 0;
		do {
			valid = false;
			if(Character.isLetterOrDigit(temp[i])) {
				valid = true;
			} else {
				for(Operator operator : operators) {
					if(temp[i] == operator.getOperator()) {
						valid = true;
						break;
					}
				}
			}
			i++;
		} while (valid && i < temp.length);
		return valid;
	}
}
