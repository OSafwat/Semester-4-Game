package game.engine;
import game.dice.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import game.engine.enums.RealmColor;

public class GameBoard {
    private GameStatus gameStatus;
    private Player player1;
    private Player player2;
    private Dice [] allDice;
    private ArrayList<Dice> availableDice;
    private ArrayList<Dice> forgottenRealmDice;
    private int whiteValue;
    private int greenValue;
    public Dice getWhite(){
        for (Dice die: allDice) {
            if (die.getRealm().equals(RealmColor.WHITE))
                return die;
        }
        return null;
    }
    public Dice getGreen(){
        for (Dice die: allDice) {
            if (die.getRealm().equals(RealmColor.GREEN))
                return die;
        }
        return null;
    }

    void setGreenForColorBonus(int greenValue) {
        for (int index = 0; index < 6; index++) {
            if (allDice[index].getRealm().equals(RealmColor.GREEN)) {
                this.greenValue = allDice[index].getValue();
                allDice[index].setValue(greenValue);
            }
            else if (allDice[index].getRealm().equals(RealmColor.WHITE)) {
                whiteValue = allDice[index].getValue();
                allDice[index].setValue(0);
            }
        }
    }

    void resetGreenPostColorBonus() {
        for (int index = 0; index < 6; index++) {
            if (allDice[index].getRealm().equals(RealmColor.GREEN)) {
                allDice[index].setValue(greenValue == -1 ? allDice[index].getValue() : greenValue);
            }
            else if (allDice[index].getRealm().equals(RealmColor.WHITE)) {
                allDice[index].setValue(whiteValue == -1 ? allDice[index].getValue() : whiteValue);
            }
        }
        this.greenValue = -1;
        this.whiteValue = -1;
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

        player1 = new Player(PlayerStatus.ACTIVE);
        player2 = new Player(PlayerStatus.PASSIVE);

        this.whiteValue = -1;
        this.greenValue = -1;
        //this.gameStatus= <gamestatus>;

    }
    //player methods:
    Player getPlayer1() {
        return player1;
    }

    Player getPlayer2() {
        return player2;
    }

    //game status getter
    GameStatus getGameStatus(){
        return this.gameStatus;
    }

    //dice related methods
    void rollDice(){
        for (Dice die : allDice) {
            die.rollDice();
        }
    }
    void rollAvailableDice(){
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
    public Dice [] getDice(){
        return getAllDice();
    }
    public Dice [] getForgottenRealmDice(){
        return this.forgottenRealmDice.toArray(new Dice[this.availableDice.size()]);
    }
    void moveToForgottenrealm(Dice die){
        availableDice.remove(die);
        forgottenRealmDice.add(die);
    }
    public void resetAllDice(){
        forgottenRealmDice.clear();
        availableDice.clear();
        player1.resetUsedArcaneDice();
        player2.resetUsedArcaneDice();
        getPlayer1().getPlayedDice().clear();
        getPlayer2().getPlayedDice().clear();
        availableDice.addAll(Arrays.asList(allDice));
    }
    void removeFromAvailable(Dice die){
        availableDice.remove(die);
    }

}
