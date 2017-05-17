/**
 * Project: Siamak_Pourian_Test_Task_Solution
 * File: FileWriter.java
 * Date: May 4, 2017
 * Time: 12:42:27 PM
 */
package canonical.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import canonical.ApplicationException;

/**
 * @author Siamak Pourian
 *
 * FileWriter Class - Writes elements in a list to a file.
 */
public class FileWriter {

	/**
	 * Default constructor
	 */
	public FileWriter() {}

	/**
	 * Writes each element of the given list to an output file.
	 * 
	 * @param canonicalFormList
	 * @throws ApplicationException
	 */
    public static void writeToOutputFile(List<String> canonicalFormList) throws ApplicationException {
    	
    	File outputFile = new File("CanonicalForms.out");
		
		try (PrintStream out = new PrintStream(new FileOutputStream(outputFile))) {
			for(String canonicalForm : canonicalFormList) {
				out.println(canonicalForm);
			}
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);
		}
    }
}
