package game.engine;

import game.engine.enums.PlayerStatus;

public class CLIGameController {
    GameBoard gameBoard;

    public GameBoard getGameBoard() {
        return gameBoard;
    }
    public boolean switchPlayer(){
        try{
            this.gameBoard.getPlayer1().switchStatus();
            this.gameBoard.getPlayer2().switchStatus();
            return true;
        } 
        catch(Exception e){
            return false;
        }
    }

    public Player getActivePlayer(){
        if (this.gameBoard.getPlayer1().getPlayerStatus() == PlayerStatus.ACTIVE){
            return this.gameBoard.getPlayer1();
        }else{
            return this.gameBoard.getPlayer2();
        }
    }


    // public abstract boolean switchPlayer(){
    // }

}
