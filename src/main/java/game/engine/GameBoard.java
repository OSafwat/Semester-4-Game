package game.engine;
import game.dice.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import game.engine.enums.PlayerStatus;
public class GameBoard {
    GameStatus gameStatus;
    Player player1;
    Player player2;
    Dice [] allDice;
    List<Dice> availableDice;
    List <Dice> forgottenRealmDice;
    
    //constructor
    public GameBoard(){
        this.allDice= new Dice [6];
        this.allDice[0]=new RedDice();
        this.allDice[1]=new GreenDice();
        this.allDice[2]=new RedDice();
        this.allDice[3]=new MagentaDice();
        this.allDice[4]=new YellowDice();
        this.allDice[5]=new ArcanePrism();

        this.availableDice = new ArrayList<>();
        this.availableDice .add(this.allDice[0]);
        this.availableDice .add(this.allDice[1]);
        this.availableDice .add(this.allDice[2]);
        this.availableDice .add(this.allDice[3]);
        this.availableDice .add(this.allDice[4]);
        this.availableDice .add(this.allDice[5]);
        
        this.forgottenRealmDice = new ArrayList<>();

        player1 = new Player(PlayerStatus.ACTIVE);
        player2 = new Player(PlayerStatus.PASSIVE);

        //this.gameStatus= <gamestatus>;

    }
    
    //player methods:
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
    
    //game status getter
    public GameStatus getGameStatus(){
        return this.gameStatus;
    }

    //dice related methods
    public void rollDice(){
        for (Dice die : allDice) {
            die.rollDice();
        }
    }
   
    public Dice [] getDice(){
        return this.allDice;
    } 
}
