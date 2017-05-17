/**
 * Project: Siamak_Pourian_Test_Task_Solution
 * File: CanonicalFile.java
 * Date: May 4, 2017
 * Time: 12:26:33 PM
 */
package canonical.util;

import java.util.ArrayList;
import java.util.List;

import canonical.ApplicationException;
import canonical.EquationTransformer;

/**
 * @author Siamak Pourian
 *
 * CanonicalFile Class - This is a utility class that processes a given list and transforms
 * each element, which is a equation, into it's canonical form.
 */
public class CanonicalFile {

	/**
	 * Default constructor
	 */
	public CanonicalFile() {}
	
	public static List<String> toCanonicalFile(List<String> equationList) throws ApplicationException {
		
		List<String> canonicalFormList = new ArrayList<String>();
		EquationTransformer transformer = new EquationTransformer();
		
		for(String equation : equationList) {
			canonicalFormList.add(String.format("%s --> %s", equation, transformer.toCanonicalForm(equation)));
		}
		return canonicalFormList;
	}

}
