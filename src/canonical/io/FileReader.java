/**
 * Project: Siamak_Pourian_Test_Task_Solution
 * File: FileReader.java
 * Date: May 4, 2017
 * Time: 11:22:18 AM
 */
package canonical.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import canonical.ApplicationException;

/**
 * @author Siamak Pourian
 *
 * FileReader Class - Reads file contents and returns them in a list.
 */
public class FileReader {

	/**
	 * Default constructor
	 */
	public FileReader() {}
	
	/**
	 * Retrieves the given file name. Reads each line of the file and stores them in a list.
	 * 
	 * @param fileName the file name to read
	 * @return the list of file contents
	 * @throws ApplicationException 
	 */
	public static List<String> readFile(String fileName) throws ApplicationException {
	
		File file = new File(fileName);
		List<String> equationList = new ArrayList<String>();
		
		try (Scanner scanner = new Scanner(file)) {	 
			while (scanner.hasNext()) {
				String row = scanner.nextLine();
				equationList.add(row);
			}
		} catch (FileNotFoundException e) {
			throw new ApplicationException(String.format("File '%s' does not exist!", fileName));
		}
		return equationList;
	}	
}
