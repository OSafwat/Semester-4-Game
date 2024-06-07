package game.exceptions;

public class InvalidDiceSelectionException extends Exception {

    String message;
    public InvalidDiceSelectionException() {
    }

    public InvalidDiceSelectionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
