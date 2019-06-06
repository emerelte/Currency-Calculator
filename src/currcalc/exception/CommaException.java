package currcalc.exception;

public class CommaException extends IllegalArgumentException{
    public CommaException(String errorMessage){
        super("Comma exception: " + errorMessage);
    }
}