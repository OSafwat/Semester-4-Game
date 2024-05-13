package game.engine;
import game.dice.*;

import java.util.Arrays;
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
    ArrayList<Dice> arcaneDice;
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
        this.arcaneDice = new ArrayList<>();

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
    public ArrayList<Dice> getArcaneDice() {
        return arcaneDice;
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
        availableDice.remove(die);
        forgottenRealmDice.add(die);
    }
    public void moveToArcaneDice(Dice chosenDice){
        arcaneDice.add(chosenDice);
    }
    public void resetAllDice(){
        forgottenRealmDice.clear();
        availableDice.clear();
        activeArcaneDice.clear();;
        arcaneDice.clear();
        passiveArcaneDice.clear();
        getPlayer1().getPlayedDice().clear();
        getPlayer2().getPlayedDice().clear();
        availableDice.addAll(Arrays.asList(allDice));
    }
    public void removeFromAvailable(Dice die){
        availableDice.remove(die);
    }

}
