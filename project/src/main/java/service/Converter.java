/**
 * 
 */
package service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import unit.Measure;
import unit.Unit;

/**
 * @author Emilien
 *
 */
public class Converter {
	
	public Converter(){
		
	}
	
	/**
     * Return the result of the conversion of the measure in parameters
     *
     * @param targetedUnit the unit target to convert the measure to
     * 
     * @param toConvert the measure to convert in an other one
     *
     * @return the converted measure
     */
    public Measure convertTo(Measure toConvert, String targetedUnit) {
    	//test to ensure the parameters are not null
    	if(toConvert != null && targetedUnit != null){
    		//get the unit object targeted
    		Unit uniteAttendu = Utils.getUnit(toConvert.getUnit().getCategory(), targetedUnit);
    		if(uniteAttendu != null){
    			 Measure mesureAttendu = new Measure();
    	            BigDecimal baseValue;

    	            mesureAttendu.setUnit(uniteAttendu);

    	            // convert the measure in the standard unit
    	            baseValue = toConvert.getValue().multiply(toConvert.getUnit().getRatio()).add(toConvert.getUnit().getDecalage());
    	            //convert to the targeted unit
    	            mesureAttendu.setValue(baseValue.subtract(uniteAttendu.getDecalage()).divide(uniteAttendu.getRatio(), 4, RoundingMode.HALF_UP));

    	            return mesureAttendu;
    		}
    	}
    	return null;
    }

}
