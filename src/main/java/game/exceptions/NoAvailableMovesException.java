package game.exceptions;

public class NoAvailableMovesException extends PlayerActionException{

    public NoAvailableMovesException(String s) {
        super(s);
    }

}
