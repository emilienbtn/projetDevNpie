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
	 * Contructeur par défaut
	 */
	public Unit(){
		
	}
	
	/**
	 * Constructeur d'initialisation
	 * @param name
	 * @param category
	 * @param system
	 * @param ratio
	 * @param decalage
	 */
	public Unit(String name, String category, String system, BigDecimal ratio, BigDecimal decalage){
		 this.name = name;
		 this.category = category;
	     this.system = system;
	     this.ratio = ratio;
	     this.decalage = decalage;
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getRatio() {
		return ratio;
	}
	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}
	public BigDecimal getDecalage() {
		return decalage;
	}
	public void setDecalage(BigDecimal decalage) {
		this.decalage = decalage;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

}
