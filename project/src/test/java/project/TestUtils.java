/**
 * 
 */
package project;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import service.Utils;
import unit.Unit;

/**
 * @author Emilien
 *
 */
public class TestUtils {

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
	 * Test method for {@link service.Utils#getListeUnitsByCategory(java.lang.String)}.
	 */
	@Test
	public final void testGetListeUnits() {
		String category = "LONGUEUR";
		//recuperation of the unit list of category LONGUEUR
		ArrayList<Unit> unitList = Utils.getListeUnitsByCategory(category);
		boolean listOk = true;
		//if all the unit have for category "LONGUEUR" then the list is OK
		for(Unit unit : unitList){
			if(!unit.getCategory().equals(category)){
				listOk = false;
				break;
			}
		}
		assertEquals(true,listOk);
	}

	/**
	 * Test method for {@link service.Utils#getUnit(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testGetUnit() {
		String category = "LONGUEUR";
		String expectedUnit = "metre";
		Unit unit = Utils.getUnit(category, expectedUnit);
		assertEquals(expectedUnit, unit.getName());
	}

	/**
	 * Test method for {@link service.Utils#addCategory(java.lang.String)}.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public final void testAddCategory() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		String category = "CATADD";
		Utils.addCategory(category);
		assertEquals(true,Utils.existCategory(category));
	}

	/**
	 * Test method for {@link service.Utils#addUnit(unit.Unit)}.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public final void testAddUnit() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		Unit unitAdd = new Unit("unitAdd", "LONGUEUR", "metrique", new BigDecimal(10), new BigDecimal(0));
		Utils.addUnit(unitAdd);
		assertEquals(true,Utils.validUnit(unitAdd));
	}

	/**
	 * Test method for {@link service.Utils#existCategory(java.lang.String)}.
	 */
	@Test
	public final void testExistCategory() {
		String category = "LONGUEUR";
		assertEquals(true,Utils.existCategory(category));
	}

	/**
	 * Test method for {@link service.Utils#validUnit(unit.Unit)}.
	 */
	@Test
	public final void testValidUnit() {
		Unit unitToTest = new Unit();
		unitToTest.setCategory("LONGUEUR");
		unitToTest.setName("metre");
		//false "metre" already exist
		assertEquals(false,Utils.validUnit(unitToTest));
	}

	/**
	 * Test method for {@link service.Utils#validUnit(unit.Unit)}.
	 */
	@Test
	public final void testExistUnit() {
		Unit unitToTest = new Unit();
		unitToTest.setCategory("LONGUEUR");
		unitToTest.setName("metre");
		//false "metre" already exist
		assertEquals(true,Utils.existUnit(unitToTest));
	}

	/**
	 * Test method for {@link service.Utils#deleteUnit(unit.Unit)}.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public final void testDeleteUnit() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		Unit unitToDelete = new Unit();
		unitToDelete.setCategory("MASSE");
		unitToDelete.setName("test");
		Utils.deleteUnit(unitToDelete);
		assertEquals(false, Utils.validUnit(unitToDelete));
	}

	/**
	 * Test method for {@link service.Utils#deleteCategory(category.String)}.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public final void testDeleteCategory() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
		String categoryToDelete = "TEST";
		Utils.deleteCategory(categoryToDelete);
		assertEquals(false, Utils.existCategory(categoryToDelete));
	}

}