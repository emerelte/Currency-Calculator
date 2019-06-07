package currcalc.exception;

/**
 * Exception signalling that the string can be converted to double,
 * but it does not make sense to use the double value as the quantity
 */
public class NotAllowedCalculations extends IllegalArgumentException{
    /**
     * Constructor
     * @param errorMessage
     */
    public NotAllowedCalculations(String errorMessage){
        super("NotAllowedCalculations: " + errorMessage);
    }
}
