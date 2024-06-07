package game.exceptions;

public class NoAvailableMovesException extends Exception{

    String message;
    public NoAvailableMovesException() {
    }

    public NoAvailableMovesException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
