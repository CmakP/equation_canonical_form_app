package canonical.test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import canonical.ApplicationException;
import canonical.EquationTransformer;

/**
 * Project: Siamak_Pourian_Test_Task_Solution
 * File: CanonicalJUnitTest.java
 * Date: May 4, 2017
 * Time: 5:53:32 PM
 */

/**
 * @author Siamak Pourian
 *
 * CanonicalJUnitTest Class
 */
public class EquationTransformerTest {

	private static EquationTransformer transformer;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		transformer = new EquationTransformer();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		transformer = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCanonicalForm() {
		try {
			assertEquals(transformer.toCanonicalForm("(2x-(-(y+z)+4v^3-1))=(2)-(3x)+(-4z)"), "+5x+5z+y-1-4v^3=0");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCanonicalFormWithExponents() {
		try {
			assertEquals(transformer.toCanonicalForm("0.9x^3 - 62.49 = 3z^6 - 1.8x^3"), "+2.7x^3-3z^6-62.49=0");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCanonicalFormWithBrackets() {
		try {
			assertEquals(transformer.toCanonicalForm("((-(-(x-((-x+(y)))))))=0"), "+2x-y=0");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCanonicalFormWithFractions() {
		try {
			assertEquals(transformer.toCanonicalForm("(1/x+(-4/y))=-(-3/y)+9.5z"), "+1/x-7/y-9.5z=0");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCalculationResultingZero() {
		try {
			assertEquals(transformer.toCanonicalForm("y-(-(-y))=0"), "0=0");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoveBracketsEven() {
		try {
			assertEquals(transformer.removeBrackets("((x+y))"), "+x+y");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoveBracketsUneven() {
		try {
			transformer.removeBrackets("((x+y)");
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Invalid equation! Uneven bracket quantities detected.");
		}
	}
	
	@Test
	public void testInvalidSummand() {
		try {
			transformer.toCanonicalForm("2((x+y))=3s");
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Invalid equation! There must be '+' or '-' between digit and '('.\n Examle: '2+('");
		}
	}
	
	@Test
	public void testToCanonicalFormUnevenBrackets() {
		try {
			transformer.toCanonicalForm("x+y)=3j");
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Invalid equation! Uneven bracket quantities detected.");
		}
	}
	
	@Test
	public void testInvalidCharInEquation() {
		try {
			transformer.toCanonicalForm("x+y+@2=3j");
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Equation contains illegal characters!");
		}
	}
	
	@Test
	public void testInvalidCharBeforeExponent() {
		try {
			transformer.toCanonicalForm("3d-^x+y+2=0");
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Invalid summand! There must be a number/letter before exponent!\n Example: 'x^2'");
		}
	}
	
	@Test
	public void testInvalidCharAfterExponent() {
		try {
			transformer.toCanonicalForm("3d-x^3+k^+h=2");
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Invalid summand! There must be a number after exponent!\n Example: 'x^2'");
		}
	}
	
	@Test
	public void testInvalidCharBeforeDouble() {
		try {
			transformer.toCanonicalForm("3d-.3b+k^+h=2");
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Invalid double! There must be a number before floating point!\n Example: '2.45'");
		}
	}
	
	@Test
	public void testInvalidCharAfterDouble() {
		try {
			transformer.toCanonicalForm("3d-4.3b+k^3+h=2.+h");
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Invalid double! There must be a number after floating point!\n Example: '2.45'");
		}
	}
	
	@Test
	public void testInvalidCharBeforeDivision() {
		try {
			transformer.toCanonicalForm("3d-s/s+h=2.6+h");
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Invalid/Not supported summand! There must be a number before division!\n Example: '1/x'");
		}
	}
	
	@Test
	public void testInvalidCharAfterDivision() {
		try {
			transformer.toCanonicalForm("3d-1/+h=2.9+h");
		} catch (ApplicationException e) {
			assertEquals(e.getMessage(), "Invalid/Not supported summand! There must be a variable after division!\n Example: '1/x'");
		}
	}
}
