package game.exceptions;

public class ExhaustedResourceException extends Exception{
    String message;
    public ExhaustedResourceException (String message) {
        this.message = message;
    }
}
