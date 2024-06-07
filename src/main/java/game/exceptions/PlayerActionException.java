package game.exceptions;

public class PlayerActionException extends Exception{
    String message;
    public PlayerActionException() {
    }

    public PlayerActionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
