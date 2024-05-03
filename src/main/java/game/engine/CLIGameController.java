package game.engine;

import game.collectibles.TimeWarp;
import game.engine.enums.PlayerStatus;
import game.dice.*;
public class CLIGameController {
    GameBoard gameBoard;
    

    //constructor(s):
    public CLIGameController(){
        this.gameBoard = new GameBoard();
    }

    // move methods
    public Move [] getAllPossibleMoves(Player player){
        return player.getAllPossiblMoves();
    }
    //gameboard getter:
    public GameBoard getGameBoard() {   
        return gameBoard;
    }
    public GameStatus getGameStatus(){
        return this.gameBoard.getGameStatus();
    }
    // dice related methods:
    public void rollDice(){
        this.gameBoard.rollDice();
    }
    public Dice [] getAllDice(){
        return this.gameBoard.getAllDice();
    }
    public Dice [] getAvailableDice(){
        return this.gameBoard.getAvailableDice();
    }
    public Dice [] getForgottenRealmDice(){
        return this.gameBoard.getForgottenRealmDice();
    }
    
    
    //player related methods: 
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

    //player attributes related methods
    public ScoreSheet getScoreSheet(Player player){
        return player.getScoresheet();
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
