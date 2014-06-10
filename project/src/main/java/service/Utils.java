/**
 * 
 */
package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import unit.Unit;


/**
 * @author Emilien
 *
 */
public class Utils {
	
	/**
	 * map which contains all the units classed by their category
	 */
	private static Map<String, ArrayList<Unit>> properties;
	
	/**
	 * initialization of the map properties
	 */
	private static void initCategory() {

        DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document dom;
        Node node;
        XPath xPath;
        String category;
        NodeList categoryList, unitList;
        Unit unitTemp;
        ArrayList<Unit> listUnits;
        
        if (properties == null) {
        	properties = new HashMap<>();
            try{
                dbf = DocumentBuilderFactory.newInstance();
                db = dbf.newDocumentBuilder();
                dom = db.parse(new FileInputStream("src/main/resources/Properties.xml"));
                xPath = XPathFactory.newInstance().newXPath();

                categoryList = (NodeList) xPath.compile("/liste_grandeurs/grandeur").evaluate(dom, XPathConstants.NODESET);

                for (int i = 0; i <= categoryList.getLength() - 1; i++) {
                    listUnits = new ArrayList<>();
                    node = categoryList.item(i);
                    category = node.getAttributes().getNamedItem("name").getNodeValue();
                    unitList = (NodeList) xPath.compile("./liste_units/unit").evaluate(node, XPathConstants.NODESET);
                    for (int j = 0; j <= unitList.getLength() - 1; j++) {

                        unitTemp = new Unit();
                        node = unitList.item(j);
                        unitTemp.setCategory(category);
                        unitTemp.setName(node.getAttributes().getNamedItem("name").getNodeValue());
                        BigDecimal bdTemp = new BigDecimal(node.getAttributes().getNamedItem("coeff").getNodeValue());
                        unitTemp.setRatio(bdTemp);
                        if(node.getAttributes().getNamedItem("decalage")==null){
                        	bdTemp = new BigDecimal("0");
                        }else{
                        	bdTemp = new BigDecimal(node.getAttributes().getNamedItem("decalage").getNodeValue());
                        }
                        unitTemp.setDecalage(bdTemp);
                        listUnits.add(unitTemp);
                    }
                    properties.put(category, listUnits);
                }
            
            } catch (FileNotFoundException ex) {
            	System.out.println("File Not Found!");
            } catch (IOException | ParserConfigurationException | XPathExpressionException | DOMException | SAXException ex) {
            	System.out.println(ex.toString());
            }
        }
    }
    
	/**
	 * Return the unit list of a category
	 * @param category
	 * @return the unit list of a category
	 */
    public static ArrayList<Unit> getListeUnitsByCategory(String category) {
        if (properties == null || properties.isEmpty()) {
        	initCategory();
        }
        return properties.get(category);
    }
    
    /**
     * Return the unit in function of a category and a unit name
     * @param category
     * @param UnitName
     * @return the wished unit
     */
    public static Unit getUnit(String category, String unitName) {
        if (unitName != null) {
        	ArrayList<Unit> listeUnits = Utils.getListeUnitsByCategory(category);
            for (Unit unitTemp : listeUnits) {
                if (unitTemp.getName().equalsIgnoreCase(unitName)) {
                    return unitTemp;
                }
            }
        }
        return null;
    }
    
    
    /**
     * Function using dom to add a category in the xml file
     * @param category
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws FileNotFoundException 
     */
    public static void addCategory(String category) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException{
    	DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document dom;
        
    	if(!existCategory(category)){
    		dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            dom = db.parse(new FileInputStream("src/main/resources/Properties.xml"));
            NodeList nodeListGrandeur = dom.getElementsByTagName("liste_grandeurs");
    		Element newGrandeur = dom.createElement("grandeur");
    		nodeListGrandeur.item(0).appendChild(newGrandeur);
    		newGrandeur.setAttribute("name", category.toUpperCase());
    		Element newListUnit = dom.createElement("liste_units");
    		newGrandeur.appendChild(newListUnit);
           
            // on considère le document "doc" comme étant la source d'une 
            // transformation XML
            Source source = new DOMSource(dom);
             
            // le résultat de cette transformation sera un flux d'écriture dans
            // un fichier
            Result resultat = new StreamResult(new File("src/main/resources/Properties.xml"));
             
            // création du transformateur XML
            Transformer transfo = null;
            try {
                transfo = TransformerFactory.newInstance().newTransformer();
            } catch(TransformerConfigurationException e) {
                System.err.println("Impossible de créer un transformateur XML.");
                System.exit(1);
            }
            // configuration du transformateur
            // sortie en XML
            transfo.setOutputProperty(OutputKeys.METHOD, "xml");
            // inclut une déclaration XML (recommandé)
            transfo.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            // codage des caractères : UTF-8. Ce pourrait être également ISO-8859-1
            transfo.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            // idente le fichier XML
            transfo.setOutputProperty(OutputKeys.INDENT, "yes");
            try {
                transfo.transform(source, resultat);
            } catch(TransformerException e) {
                System.err.println("La transformation a échoué : " + e);
                System.exit(1);
            }
            properties.put(category, null);
    	}
    	initCategory();
    }
    
    /**
     * Add a unit with this parameters in the xml file
     * @param unit
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws FileNotFoundException 
     */
    public static void addUnit(Unit unit) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException{
    	DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document dom;
        
    	if(Utils.validUnit(unit)){
    		dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            dom = db.parse(new FileInputStream("src/main/resources/Properties.xml"));
            NodeList nodeListGrandeur = dom.getElementsByTagName("grandeur");
            for (int i = 0; i <= nodeListGrandeur.getLength() - 1; i++) {
            	if(nodeListGrandeur.item(i).getAttributes().getNamedItem("name").getNodeValue().equals(unit.getCategory())){
            		Element newUnit = dom.createElement("unit");
            		nodeListGrandeur.item(i).getChildNodes().item(1).appendChild(newUnit);
            		newUnit.setAttribute("name", unit.getName());
            		newUnit.setAttribute("coeff", unit.getRatio().toString());
            		newUnit.setAttribute("decalage", unit.getDecalage().toString());
            	}
            }
           
            // on considère le document "doc" comme étant la source d'une 
            // transformation XML
            Source source = new DOMSource(dom);
             
            // le résultat de cette transformation sera un flux d'écriture dans
            // un fichier
            Result resultat = new StreamResult(new File("src/main/resources/Properties.xml"));
             
            // création du transformateur XML
            Transformer transfo = null;
            try {
                transfo = TransformerFactory.newInstance().newTransformer();
            } catch(TransformerConfigurationException e) {
                System.err.println("Impossible de créer un transformateur XML.");
                System.exit(1);
            }
            // configuration du transformateur
            // sortie en XML
            transfo.setOutputProperty(OutputKeys.METHOD, "xml");
            // inclut une déclaration XML (recommandé)
            transfo.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            // codage des caractères : UTF-8. Ce pourrait être également ISO-8859-1
            transfo.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            // idente le fichier XML
            transfo.setOutputProperty(OutputKeys.INDENT, "yes");
            try {
                transfo.transform(source, resultat);
            } catch(TransformerException e) {
                System.err.println("La transformation a échoué : " + e);
                System.exit(1);
            }
    	}
    	initCategory();
    }
    
    /**
     * Say if the category exist or not
     * @param category
     * @return true if the category exist
     */
    public static boolean existCategory(String category){
    	return properties.containsKey(category);
    }
    
    /**
     * say if the unit exists or not
     * @param unit
     * @return true if the unit is valid
     */
    public static boolean existUnit(Unit unit){
    	ArrayList<Unit> listeTemp = new ArrayList<Unit>();
    	listeTemp = properties.get(unit.getCategory());
    	if (listeTemp != null){
    		for (Unit unitTemp : listeTemp){
            	if (unitTemp.getName().equalsIgnoreCase(unit.getName())){
            			return true;
            	}
            }
    	}
    	return false;
    }
    
    /**
     * say if the unit is valid or not
     * @param unit
     * @return true if the unit is valid
     */
    public static boolean validUnit(Unit unit){
    	ArrayList<Unit> listeTemp = new ArrayList<Unit>();
    	if(unit.getName() == null && unit.getName().isEmpty() && unit.getCategory() == null && unit.getCategory().isEmpty()){
    		return false;
    	}
    	else{
    		listeTemp = properties.get(unit.getCategory());
    		for (Unit unitTemp : listeTemp){
        		if (unitTemp.getName().equalsIgnoreCase(unit.getName())){
        			return false;
        		}
        	}
    		
    	}
    	return true;
    }
    
    /**
     * Delete a unit
     * @param unit
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws FileNotFoundException 
     */
    public static void deleteUnit(Unit unit) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException{
    	
    	if(existUnit(unit)){
    		
	    	DocumentBuilderFactory dbf;
	        DocumentBuilder db;
	        Document dom;
	
			dbf = DocumentBuilderFactory.newInstance();
	        db = dbf.newDocumentBuilder();
	        dom = db.parse(new FileInputStream("src/main/resources/Properties.xml"));
	        NodeList nodeListGrandeur = dom.getElementsByTagName("grandeur");
	        for (int i = 0; i <= nodeListGrandeur.getLength() - 1; i++) {
	        	if(nodeListGrandeur.item(i).getAttributes().getNamedItem("name").getNodeValue().equals(unit.getCategory())){
	        		NodeList nodeListUnit = nodeListGrandeur.item(i).getChildNodes().item(1).getChildNodes();
	        		for (int j = 0; j <= nodeListUnit.getLength() - 1; j++) {
	        			if(nodeListUnit.item(j).getNodeName().equals("unit")){
	        				if(nodeListUnit.item(j).getAttributes().getNamedItem("name").getNodeValue().equals(unit.getName())){
		                		nodeListGrandeur.item(i).getChildNodes().item(1).removeChild(nodeListUnit.item(j));
		                	}
	        			}
	                }
	        	}
	        }
	       
	        // on considère le document "doc" comme étant la source d'une 
	        // transformation XML
	        Source source = new DOMSource(dom);
	         
	        // le résultat de cette transformation sera un flux d'écriture dans
	        // un fichier
	        Result resultat = new StreamResult(new File("src/main/resources/Properties.xml"));
	         
	        // création du transformateur XML
	        Transformer transfo = null;
	        try {
	            transfo = TransformerFactory.newInstance().newTransformer();
	        } catch(TransformerConfigurationException e) {
	            System.err.println("Impossible de créer un transformateur XML.");
	            System.exit(1);
	        }
	        // configuration du transformateur
	        // sortie en XML
	        transfo.setOutputProperty(OutputKeys.METHOD, "xml");
	        // inclut une déclaration XML (recommandé)
	        transfo.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        // codage des caractères : UTF-8. Ce pourrait être également ISO-8859-1
	        transfo.setOutputProperty(OutputKeys.ENCODING, "utf-8");
	        // idente le fichier XML
	        transfo.setOutputProperty(OutputKeys.INDENT, "yes");
	        try {
	            transfo.transform(source, resultat);
	        } catch(TransformerException e) {
	            System.err.println("La transformation a échoué : " + e);
	            System.exit(1);
	        }
	        initCategory();
    	}
    }
    
    /**
     * Delete a category
     * @param category
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws FileNotFoundException 
     */
    public static void deleteCategory(String category) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException{
    	DocumentBuilderFactory dbf;
        DocumentBuilder db;
        Document dom;
        
    	if(existCategory(category)){
    		dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            dom = db.parse(new FileInputStream("src/main/resources/Properties.xml"));
            NodeList nodeListGrandeur = dom.getElementsByTagName("liste_grandeurs");
            NodeList nodeGrandeurs = dom.getElementsByTagName("grandeur");
            for (int i = 0; i <= nodeGrandeurs.getLength() - 1; i++) {
            	if(nodeGrandeurs.item(i).getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(category)){
            		nodeListGrandeur.item(0).removeChild(nodeGrandeurs.item(i));
            	}
            }
           
            // on considère le document "doc" comme étant la source d'une 
            // transformation XML
            Source source = new DOMSource(dom);
             
            // le résultat de cette transformation sera un flux d'écriture dans
            // un fichier
            Result resultat = new StreamResult(new File("src/main/resources/Properties.xml"));
             
            // création du transformateur XML
            Transformer transfo = null;
            try {
                transfo = TransformerFactory.newInstance().newTransformer();
            } catch(TransformerConfigurationException e) {
                System.err.println("Impossible de créer un transformateur XML.");
                System.exit(1);
            }
            // configuration du transformateur
            // sortie en XML
            transfo.setOutputProperty(OutputKeys.METHOD, "xml");
            // inclut une déclaration XML (recommandé)
            transfo.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            // codage des caractères : UTF-8. Ce pourrait être également ISO-8859-1
            transfo.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            // idente le fichier XML
            transfo.setOutputProperty(OutputKeys.INDENT, "yes");
            try {
                transfo.transform(source, resultat);
            } catch(TransformerException e) {
                System.err.println("La transformation a échoué : " + e);
                System.exit(1);
            }
            properties.remove(category);
    	}
    	
    }
    
}
