package game.exceptions;

public class InvalidBonusSelectionException extends Exception{

    String message;
    public InvalidBonusSelectionException() {
    }

    public InvalidBonusSelectionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
