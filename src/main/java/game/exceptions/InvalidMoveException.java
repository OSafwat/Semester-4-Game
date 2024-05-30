package game.exceptions;

public class InvalidMoveException extends PlayerActionException{

    public InvalidMoveException() {
        super("This is an invalid move. Please try again.");
    }
}
