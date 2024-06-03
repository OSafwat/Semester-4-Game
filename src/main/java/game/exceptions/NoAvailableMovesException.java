package game.exceptions;

public class NoAvailableMovesException extends Exception{

    private String message;

    public NoAvailableMovesException() {
        message = "";
    }

    public NoAvailableMovesException(String message) {
        this.message = message;
    }


}
