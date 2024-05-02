package game.engine;
import game.dice.*;
import game.engine.enums.PlayerStatus;
public class GameBoard {
    GameStatus gameStatus;
    Player player1;
    Player player2;
    Dice [] allDice;
    
    public GameBoard(){
        this.allDice= new Dice [6];
        this.allDice[0]=new RedDice();
        this.allDice[1]=new GreenDice();
        this.allDice[2]=new RedDice();
        this.allDice[3]=new MagentaDice();
        this.allDice[4]=new YellowDice();
        this.allDice[5]=new ArcanePrism();

        player1 = new Player(PlayerStatus.ACTIVE);
        player2 = new Player(PlayerStatus.PASSIVE);

    } 
}
