/**
 * Project: Siamak_Pourian_Test_Task_Solution
 * File: EquationTransformer.java
 * Date: May 2, 2017
 * Time: 7:51:39 PM
 */
package canonical;

import java.util.HashMap;
import java.util.Map;

import canonical.util.SortCanonicalForm;
import canonical.util.Validator;
import canonical.util.WhiteSpaceEliminator;

/**
 * @author Siamak Pourian
 *
 * EquationTransformer Class - Transforms an equation containing any amount of variables or brackets
 * into canonical form.
 */
public class EquationTransformer {

	/**
	 * Default constructor
	 */
	public EquationTransformer() {}
	
	/**
	 * This is a driver method for this class. Processes an equation and returns it's 
	 * canonical form.
	 * 
	 * @param input the equation to transform
	 * @return the canonical form of the equation
	 * @throws ApplicationException 
	 */
    public String toCanonicalForm(String input) throws ApplicationException {
   
    	String equation = WhiteSpaceEliminator.eliminateWhiteSpace(input);
    	if(!Validator.validate(equation)) {
    		throw new ApplicationException("Equation contains illegal characters!");
    	}
    	
    	Map<String, Double> lhsSummands = new HashMap<String, Double>(); 	
    	Map<String, Double> rhsSummands = new HashMap<String, Double>(); 	
    	
    	String canonicalForm = "";
    	String[] equations = equation.split("=");
    	
    	String rhsEquation = removeBrackets(equations[0]);
        String lhsEquation = removeBrackets(equations[1]);
        rhsSummands = addToMap(rhsEquation);
        lhsSummands = addToMap(lhsEquation);
        Map<String, Double> canonicalFormMap = calculateFinalCanonicalForm(rhsSummands, lhsSummands);
        canonicalForm = SortCanonicalForm.sortMap(canonicalFormMap);  
        
  	    return canonicalForm;
	}

    /**
     * Creates a complete canonical form transformation of the left hand side and right hand side 
     * of the equation.
     * 
     * @param rhsSummands the right hand side equation canonical form
     * @param lhsSummands the left hand side equation canonical form
     * @return the canonical form
     */
	private Map<String, Double> calculateFinalCanonicalForm(Map<String, Double> rhsSummands, Map<String, Double> lhsSummands) {
		lhsSummands.forEach((k, v) -> {
			if(rhsSummands.containsKey(k)) {
				double sum = rhsSummands.get(k) - v;
				if(sum == 0) {
					rhsSummands.remove(k);
				} else {
					rhsSummands.put(k, sum);	
				}
		    } else {
		    	v *= -1;
				rhsSummands.put(k, v);	
		    }
		});
		return rhsSummands;
	}

	/**
	 * Removes the brackets for the given equation side. For each bracket removed, the corresponding 
	 * sign is applied to each summand within that bracket.
	 * 
	 * @param input one side of the equation
	 * @return equation with no brackets
	 * @throws ApplicationException 
	 */
	public String removeBrackets(String input) throws ApplicationException {
		
		int summandLastIndex, summandFirstIndex;
		int i = 0;
		char nextChar;
		String equation = null;
		String summand = "";
		if(input.length() == 1 && input.equals("0")) {
			return input;
		} 		
		do {
			if(i < input.length()) {
				nextChar = input.charAt(i);
				if(nextChar == Operator.BRACKET_OPEN.getOperator()) {	
					summandLastIndex = input.indexOf(Operator.BRACKET_CLOSE.getOperator());
					// If there are no close brackets matching the open bracket which was just parsed
					if(summandLastIndex == -1) {
						throw new ApplicationException("Invalid equation! Uneven bracket quantities detected."); 			
					}
					summandFirstIndex = input.lastIndexOf(Operator.BRACKET_OPEN.getOperator(), summandLastIndex) + 1;
					if((summandFirstIndex - 2) >= 0 && Character.isDigit(input.charAt(summandFirstIndex - 2))) {
						throw new ApplicationException("Invalid equation! There must be '+' or '-' between digit and '('.\n Examle: '2+('"); 			
					}
					summand = input.substring(summandFirstIndex, summandLastIndex);
					summand = String.format("%s%s", summand.charAt(0) != Operator.ADDITION.getOperator() 
					    && summand.charAt(0) != Operator.SUBTRACTION.getOperator() ? Operator.ADDITION.getOperator() : "", summand);
					
					boolean sign = true;
					char c = ' ';
					if(summandFirstIndex - 2 >= 0) {
						c = input.charAt(summandFirstIndex - 2);
					} 
					if(c == '-') {
						sign = false;
					} else if(c == '('){
						summandFirstIndex++;
					}
					char[] summandCharArray = summand.toCharArray();
					// Applying the negative bracket sign to the corresponding summands
				    if(!sign) {
				    	for(int j = 0; j < summand.length(); j++) {
			    	        if(summandCharArray[j] == Operator.ADDITION.getOperator()) {
				        	    summandCharArray[j] = Operator.SUBTRACTION.getOperator();
				            } else if (summandCharArray[j] == Operator.SUBTRACTION.getOperator()) {
				    	        summandCharArray[j] = Operator.ADDITION.getOperator();
				            }
				        }
				    }	
				    summand = new String(summandCharArray).intern();
					StringBuilder result = new StringBuilder();
		    		if(summandFirstIndex != 1) {
						result.append(input.substring(0, summandFirstIndex - 2));	
					}
					result.append(summand);
				    result.append(input.substring(summandLastIndex + 1, input.length()));
				    input = result.toString();
				    equation = removeBrackets(input);	
				} else {
					i++;
				} 	
			} else {
				if(input.contains(String.valueOf(Operator.BRACKET_CLOSE.getOperator())) || input.contains(String.valueOf(Operator.BRACKET_OPEN.getOperator()))) {
					throw new ApplicationException("Invalid equation! Uneven bracket quantities detected."); 
				}
				return input;
			}
		} while(null == equation || equation.contains(String.valueOf(Operator.BRACKET_OPEN.getOperator())));
		return equation;
	}

	/**
	 * Solves given equation side and transforms it into canonical form. Finally, each summand is
	 * saved in a map.
	 * 
	 * @param equation the equation side to solve
	 * @return the Map containing the summands coefficients and variables
	 * @throws ApplicationException 
	 */
	private Map<String, Double> addToMap(String equation) throws ApplicationException {
		
		Map<String, Double> summandsMap = new HashMap<String, Double>();
		
		double coefficient;
		boolean sign = false; 
		char currentChar;
		
		String strCoefficient, variable;
		
		if(Character.isLetterOrDigit(equation.charAt(0))) {
			equation = String.format("+%s", equation);
		}
		int size = equation.length();
		for(int i = 0; i < size; i++) {	
			coefficient = 0;
			strCoefficient = "";
			variable = "";
			currentChar = equation.charAt(i);
			if(currentChar == Operator.ADDITION.getOperator()) {
				sign = true;
			} else if(currentChar == Operator.SUBTRACTION.getOperator()) {
				sign = false;
			} else {
				while(true) {
					if(Character.isLetter(currentChar)) {
						variable += Character.toString(currentChar);	
					} else if(currentChar == Operator.DIVISION.getOperator()) {
					    if((i - 1) < 0 || !Character.isDigit(equation.charAt(i - 1))) {
							throw new ApplicationException("Invalid/Not supported summand! There must be a number before division!\n Example: '1/x'");    	
						} else if((i + 1) >= size || !Character.isLetter(equation.charAt(i + 1))) {
							throw new ApplicationException("Invalid/Not supported summand! There must be a variable after division!\n Example: '1/x'");
						}
						variable = "1/";	
					} else if(currentChar == Operator.BITWISE.getOperator()) {
						if((i - 1) < 0 || !Character.isLetterOrDigit(equation.charAt(i - 1))) {
							throw new ApplicationException("Invalid summand! There must be a number/letter before exponent!\n Example: 'x^2'");
						} else if((i + 1) >= size || !Character.isDigit(equation.charAt(i + 1))) {
							throw new ApplicationException("Invalid summand! There must be a number after exponent!\n Example: 'x^2'");
						}
						do {
							variable += Character.toString(currentChar);
							if(i + 1 >= size || equation.charAt(i + 1) == Operator.SUBTRACTION.getOperator() || equation.charAt(i + 1) == Operator.ADDITION.getOperator()) {		
							    break;
							} else {
								currentChar = equation.charAt(++i);
							}
						} while(true);
					} else {
						if(currentChar == Operator.FLOATING_POINT.getOperator()) {
							if((i - 1) < 0 || !Character.isDigit(equation.charAt(i - 1))) {  
								throw new ApplicationException("Invalid double! There must be a number before floating point!\n Example: '2.45'");				
							} else if((i + 1) >= size || !Character.isDigit(equation.charAt(i + 1))) {
								throw new ApplicationException("Invalid double! There must be a number after floating point!\n Example: '2.45'");
							}
						}
						strCoefficient += Character.toString(currentChar);
					}
					if(i + 1 >= size || equation.charAt(i + 1) == Operator.SUBTRACTION.getOperator() || equation.charAt(i + 1) == Operator.ADDITION.getOperator()) {		
					    break;
					} else {
						currentChar = equation.charAt(++i);
					}
			    }
				coefficient = strCoefficient.equals("") ? 1 : Double.parseDouble(strCoefficient);
				if(variable.contains(String.valueOf(Operator.BRACKET_OPEN.getOperator())) || variable.contains(String.valueOf(Operator.BRACKET_CLOSE.getOperator()))) {
					throw new ApplicationException("Invalid characters in variable! '(' or ')' Can NOT be part of varible!"); 
				}
				if(coefficient != 0) {
					if(!sign) {
						coefficient *= -1;
					}
					if (summandsMap.containsKey(variable)) {
						double sum = summandsMap.get(variable) + coefficient;
						if(sum == 0) {
							summandsMap.remove(variable);
						} else {
							summandsMap.put(variable, sum); 
						}
					} else {
						summandsMap.put(variable, coefficient);
					}	
				}
			}
		}
		return summandsMap;
	}
}
