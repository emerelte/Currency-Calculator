package currcalc.exception;

public class NotAllowedCalculations extends IllegalArgumentException{
    public NotAllowedCalculations(String errorMessage){
        super(errorMessage);
    }
}
