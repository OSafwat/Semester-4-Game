package game.exceptions;

public class InvalidDiceSelectionException extends PlayerActionException {

    public InvalidDiceSelectionException (String message) {
        super(message);
    }
}
