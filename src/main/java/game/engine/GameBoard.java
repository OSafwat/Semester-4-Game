package game.engine;
import game.dice.*;
import java.util.List;
import java.util.ArrayList;
import game.engine.enums.PlayerStatus;
public class GameBoard {
    GameStatus gameStatus;
    Player player1;
    Player player2;
    Dice [] allDice;
    ArrayList<Dice> availableDice;
    ArrayList<Dice> activeArcaneDice ;
    ArrayList<Dice> forgottenRealmDice;
    ArrayList<Dice> passiveArcaneDice;
    public Dice getWhite(){
        return this.allDice[5];
    }
    public Dice getGreen(){
        return this.allDice[1];
    }
    //constructor
    public GameBoard(){
        this.allDice= new Dice [6];
        this.allDice[0]=new RedDice();
        this.allDice[1]=new GreenDice();
        this.allDice[2]=new BlueDice();
        this.allDice[3]=new MagentaDice();
        this.allDice[4]=new YellowDice();
        this.allDice[5]=new ArcanePrism();

        this.availableDice = new ArrayList<>();
        this.availableDice.add(this.allDice[0]);
        this.availableDice.add(this.allDice[1]);
        this.availableDice.add(this.allDice[2]);
        this.availableDice.add(this.allDice[3]);
        this.availableDice.add(this.allDice[4]);
        this.availableDice.add(this.allDice[5]);
        
        this.forgottenRealmDice = new ArrayList<>();
        this.activeArcaneDice = new ArrayList<>();
        this.passiveArcaneDice= new ArrayList<>();

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
    public void rollAvailableDice(){
        for (Dice die : availableDice) {
            die.rollDice();
        }
    }
    public Dice [] getAllDice(){
        return this.allDice;
    } 
    public ArrayList<Dice> getAvailableDice(){
        return this.availableDice;
    }
    public ArrayList<Dice> getActiveArcaneDice(){
        return this.activeArcaneDice;
    }
    public ArrayList<Dice> getPassiveArcaneDice(){
        return this.passiveArcaneDice;
    }
    public Dice [] getDice(){
        return getAllDice();
    }
    public Dice [] getForgottenRealmDice(){
        return this.forgottenRealmDice.toArray(new Dice[this.availableDice.size()]);
    }
    public void moveToForgottenrealm(Dice die){
        for (Dice dice : availableDice) {
            if (dice == die){
                this.availableDice.remove(dice);
                this.forgottenRealmDice.add(dice);
                break;
            }
        }
    }
    public void moveToArcaneDice(Dice chosenDice){
        for (Dice die  : availableDice) {
            if (chosenDice == die){
                this.availableDice.remove(chosenDice);
                this.activeArcaneDice.add(chosenDice);
            }
        }
    }
    public void resetForgottenRealm(){
        for (Dice die : forgottenRealmDice) {
            forgottenRealmDice.remove(die);
            availableDice.add(die);
        }
        this.activeArcaneDice = new ArrayList<>();
        this.passiveArcaneDice = new ArrayList<>();
    }
    public void removeFromAvailable(Dice die){
        for (Dice dice : availableDice) {
            if (die.compareTo(dice)==0)
                this.availableDice.remove(dice);
        }
    }

}
