package game.exceptions;

public class InvalidMoveException extends Exception{

    private String message;

    public InvalidMoveException() {
        message = "";
    }

    public InvalidMoveException(String message) {
        this.message = message;
    }

}
