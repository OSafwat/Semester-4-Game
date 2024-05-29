package game.exceptions;

public class ExhaustedResourceException extends PlayerActionException{
    public ExhaustedResourceException (String message) {
        super (message);
    }
}
