/**
 * 
 */
package unit;

import java.math.BigDecimal;

/**
 * @author Emilien
 *
 */
public class Measure {
	
	/**
	 * Measure's Unit
	 */
	private Unit unit;
	
	/**
	 * Measure's Value
	 */
	private BigDecimal value;
	
	
	/**
	 * Default constructor
	 */
	public Measure(){
		
	}
	
	/**
	 * Measure constructor
	 * 
	 * @param unite unit of the measure
	 * @param value value of the measure
	 */
	public Measure(Unit unit, BigDecimal value){
		this.unit = unit;
		this.value = value;
	}
	
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public String toString(){
		return "Mesure = " + unit.getName() + " Valeur = " + value;
	}
	
	

}
