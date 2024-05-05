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
    public Dice getWhite(){
        return this.allDice[5];
    }
    public Dice getGreen(){
        return this.allDice[1];
    }
    //constructor
    public GameBoard(String player1name, String player2Name){
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

        player1 = new Player(PlayerStatus.ACTIVE, player1name);
        player2 = new Player(PlayerStatus.PASSIVE, player2Name);

        //this.gameStatus= <gamestatus>;

    }
    public void setGreenValue(){            //any makemove check move and so on use the real value
        ((GreenDice)this.allDice[1]).setRealValue(this.allDice[1].getValue()+this.allDice[5].getValue());
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
    public Dice [] getAllDice(){
        return this.allDice;
    } 
    public Dice[] getAvailableDice(){
        return this.availableDice.toArray(new Dice[this.availableDice.size()]);
    }
    public Dice [] getForgottenRealmDice(){
        return this.forgottenRealmDice.toArray(new Dice[this.availableDice.size()]);
    }
}
