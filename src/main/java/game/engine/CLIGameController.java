package game.engine;

import game.collectibles.TimeWarp;
import game.engine.enums.PlayerStatus;

public class CLIGameController {
    GameBoard gameBoard;
    

    public Move [] getAllPossibleMoves(Player player){
        return player.getAllPossiblMoves();
    }

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

    public Player getPassivePlayer(){
        if (this.gameBoard.getPlayer1().getPlayerStatus() == PlayerStatus.PASSIVE){
            return this.gameBoard.getPlayer1();
        }else{
            return this.gameBoard.getPlayer2();
        }
    }
    public ScoreSheet getScoreSheet(Player player){
        return player.getScoresheet();
    }

    public GameStatus getGameStatus(){
        return this.gameBoard.getGameStatus();
    }
    public GameScore getGameScore(Player player){
        return player.getGameScore();
    }

    public  TimeWarp[] getTimeWarpPowers(Player player){
        return player.getTimeWarps();
    }


    // public abstract boolean switchPlayer(){
    // }

}
