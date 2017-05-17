/**
 * Project: Siamak_Pourian_Test_Task_Solution
 * File: SortCanonicalForm.java
 * Date: May 4, 2017
 * Time: 1:38:56 PM
 */
package canonical.util;

import java.util.LinkedHashMap;
import java.util.Map;

import canonical.Operator;

/**
 * @author Siamak Pourian
 *
 * SortCanonicalForm Class
 */
public class SortCanonicalForm {

	/**
	 * Default constructor
	 */
	public SortCanonicalForm() {}

	public static String sortMap(Map<String, Double> canonicalFormMap) {
		
		if(canonicalFormMap.size() == 0) {
			return "0=0";
		}
		StringBuilder canonicalForm = new StringBuilder();
		
		// Sorting the Map based on coefficient values
		Map<String, Double> sortedMap = new LinkedHashMap<>();
		canonicalFormMap.entrySet().stream()
        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
          
        // Trimming the summands
        sortedMap.forEach((k, v) -> {
         	int value = (int) Math.round(Double.valueOf(v));
        	if(Math.abs(Double.valueOf(v)) > 1 && Double.valueOf(v) % 1 == 0) {
        		String temp = "";
        		if(k.contains(String.valueOf(Operator.DIVISION.getOperator()))) {
        			temp = k.replaceFirst("1", String.valueOf(value));
        			canonicalForm.append(String.format("%s%s", value > 0 ? "+" : "", temp));        
            	} else { 
            		canonicalForm.append(String.format("%s%s%s", value > 0 ? "+" : "", value, k));        
                }    		
            } else if(Math.abs(Double.valueOf(v)) == 1 && !k.equals("")) {
            	canonicalForm.append(String.format("%s%s", Double.valueOf(v) > 0 ? "+" : "-", k));
            } else if(Math.abs(Double.valueOf(v)) == 1 && k.equals("")) {
            	canonicalForm.append(String.format("%s1%s", Double.valueOf(v) > 0 ? "+" : "-", k));
            } else {
            	canonicalForm.append(String.format("%s%s%s", Double.valueOf(v) > 0 ? "+" : "", v, k));            
            }
        });
        canonicalForm.append("=0");
		return canonicalForm.toString();
	}
}
