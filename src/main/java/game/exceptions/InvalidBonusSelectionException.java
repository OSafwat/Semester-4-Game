package game.exceptions;

public class InvalidBonusSelectionException extends Exception{
    private String message;

    public InvalidBonusSelectionException() {
        message = "";
    }

    public InvalidBonusSelectionException(String message) {
        this.message = message;
    }

}
