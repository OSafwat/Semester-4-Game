package game.exceptions;

public class InvalidDiceSelectionException extends Exception {

    private String message;

    public InvalidDiceSelectionException() {
        message = "";
    }

    public InvalidDiceSelectionException(String message) {
        this.message = message;
    }

}
