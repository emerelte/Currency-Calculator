package currcalc.exception;

/**
 * Custom exception thrown when the user entered
 * a wrong string that would be right if
 * there was a dot in place of comma
 */
public class CommaException extends IllegalArgumentException{
    /**
     * Constructor
     * @param errorMessage
     */
    public CommaException(String errorMessage){
        super("Comma exception: " + errorMessage);
    }
}