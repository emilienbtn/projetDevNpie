/**
 * 
 */
package project;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import service.Converter;
import service.Utils;
import unit.Measure;
import unit.Unit;

/**
 * @author Emilien
 *
 */
public class TestConverter {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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

	/**
	 * Test method for {@link service.Converter#convertTo()}.
	 */
	@Test
	public final void testConvertToWithoutShit() {
		String unitTargeted = "kilometre";
		Unit unit = new Unit("metre", "LONGUEUR", "metrique", new BigDecimal(1), new BigDecimal(0));
        Measure mesure = new Measure(unit, new BigDecimal(1));
        BigDecimal expectedResult = new BigDecimal("0.0010");
        Converter converter = new Converter();
        Measure result = converter.convertTo(mesure, unitTargeted);
        assertEquals(expectedResult, result.getValue());
	}
	
	/**
	 * Test method for {@link service.Converter#convertTo()}.
	 */
	@Test
	public final void testConvertToWithShit() {
		String unitTargeted = "fahrenheit";
		Unit unit = Utils.getUnit("TEMPERATURE", "celsius");
        Measure mesure = new Measure(unit, new BigDecimal(1));
        BigDecimal expectedResult = new BigDecimal("33.8000");
        Converter converter = new Converter();
        Measure result = converter.convertTo(mesure, unitTargeted);
        assertEquals(expectedResult, result.getValue());
	}

}
