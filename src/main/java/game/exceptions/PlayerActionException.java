package game.exceptions;

public class PlayerActionException extends Exception{
    private String message;

    public PlayerActionException() {
        message = "";
    }

    public PlayerActionException(String message) {
        this.message = message;
    }
}
