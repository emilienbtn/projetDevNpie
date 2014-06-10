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
	 * Unite de la mesure
	 */
	private Unit unit;
	
	/**
	 * Valuer de la mesure
	 */
	private BigDecimal value;
	
	
	/**
	 * Constructeur par défaut
	 */
	public Measure(){
		
	}
	
	/**
	 * Constructeur de la mesure
	 * 
	 * @param unite unite de la mesure a instancier
	 * @param value valeur de la mesure a instancier
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
