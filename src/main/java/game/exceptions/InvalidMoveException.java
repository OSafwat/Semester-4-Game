package game.exceptions;

public class InvalidMoveException extends Exception{

    String message;
    public InvalidMoveException() {
    }

    public InvalidMoveException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
