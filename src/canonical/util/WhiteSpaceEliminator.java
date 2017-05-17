/**
 * Project: LetterSentenceCounter
 * File: DeleteWhiteSpace.java
 * Date: May 4, 2017
 * Time: 4:19:43 PM
 */
package canonical.util;

/**
 * @author Siamak Pourian
 *
 * DeleteWhiteSpace Class - Eliminates the white space characters of a String
 */
public class WhiteSpaceEliminator {

	/**
	 * Default constructor
	 */
	public WhiteSpaceEliminator() {}

	/**
	 * Parses the given String and eliminates the white space characters
	 * 
	 * @param input the input to parse
	 * @return String without white space characters
	 */
	public static String eliminateWhiteSpace(String input) {
		
		int i = 0;
		char nextChar;
		char[] temp = new char[input.length()];
		
		for (int j = 0; j < input.length(); j++) {
			nextChar = input.charAt(j);
			if (!Character.isWhitespace(nextChar)) {
				temp[i++] = nextChar;
			}
		}
		
		return new String(temp).trim().intern();
	}
}
