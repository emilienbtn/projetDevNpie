/**
 * 
 */
package unit;

import java.math.BigDecimal;

/**
 * @author Emilien
 *
 */
public class Unit {
	
	private String name;
	private String category;
	private String system;
	private BigDecimal ratio;
	private BigDecimal decalage;
	
	/**
	 * Default Constructor
	 */
	public Unit(){
		
	}
	
	/**
	 * Unit Constructor
	 * @param name the name of the unit
	 * @param category category of the unit
	 * @param system system of the unit
	 * @param ratio ratio of the unit
	 * @param decalage gap of the unit
	 */
	public Unit(String name, String category, String system, BigDecimal ratio, BigDecimal decalage){
		 this.name = name;
		 this.category = category;
	     this.system = system;
	     this.ratio = ratio;
	     this.decalage = decalage;
		
	}
	
	/**
     * Return the name of the unit
     * @return the name of the unit
     */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
     * Return the ratio of the unit
     * @return the ratio of the unit
     */
	public BigDecimal getRatio() {
		return ratio;
	}
	
	/**
     * Edit the ratio of the unit
     * @param ratio the new ratio of the unit
     */
	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}
	
	/**
     * Return the gap of the unit
     * @return the gap of the unit
     */
	public BigDecimal getDecalage() {
		return decalage;
	}
	
	/**
     * Edit the gap of the unit
     * @param decalage the new gap of the unit
     */
	public void setDecalage(BigDecimal decalage) {
		this.decalage = decalage;
	}
	
	/**
     * Return the name of the unit
     * @return the name of the unit
     */
	public String getCategory() {
		return category;
	}
	
	/**
     * Edit the category of the unit
     * @param category the new category of the unit
     */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
     * Return the system of the unit
     * @return the system of the unit
     */
	public String getSystem() {
		return system;
	}
	
	/**
     * Edit the system of the unit
     * @param system the new system of the unit
     */
	public void setSystem(String system) {
		this.system = system;
	}

}
