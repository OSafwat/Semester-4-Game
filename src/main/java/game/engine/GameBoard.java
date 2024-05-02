package game.engine;
import game.dice.*;
public class GameBoard {
    GameStatus gameStatus;
    Player player1;
    Player player2;
    Dice [] allDice;
    public GameBoard(){
        this.allDice= new Dice [6];
        this.allDice[0]=new RedDice();
        
    } 
}
