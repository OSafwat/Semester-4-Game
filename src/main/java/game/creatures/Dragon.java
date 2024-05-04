package game.creatures;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.dice.RedDice;
import game.engine.Move;
import game.engine.enums.DragonNumber;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class Dragon extends Creature {
    public Integer face;
    public Integer wings;
    public Integer tail;
    public Integer heart;
    public DragonNumber dragonNumber;
    public Dragon[] Dragons;
    public HashMap<DragonNumber, Integer> pointMap;
    public ArrayList<Move> allPossibleMoves;
    public ArrayList<TimeWarp> timeWarps;
    public ArrayList<ArcaneBoost> arcaneBoosts;


    public Dragon() {
        Dragons = new Dragon[4];
        Dragons[0] = new Dragon(3, 2, 1, null, DragonNumber.Dragon1);
        Dragons[1] = new Dragon(6, 1, null, 3, DragonNumber.Dragon2);
        Dragons[2] = new Dragon(5, null, 2, 4, DragonNumber.Dragon3);
        Dragons[3] = new Dragon(null, 5, 4, 6, DragonNumber.Dragon4);
        initPointMap();
        initPossibleMoves();
        initTimeWarps();
        initArcaneBoosts();
    }

    public void initPossibleMoves() {
        allPossibleMoves = new ArrayList<>();
        allPossibleMoves.add(new Move(new RedDice(1), Dragons[0]));
        allPossibleMoves.add(new Move(new RedDice(1), Dragons[1]));
        allPossibleMoves.add(new Move(new RedDice(2), Dragons[0]));
        allPossibleMoves.add(new Move(new RedDice(2), Dragons[2]));
        allPossibleMoves.add(new Move(new RedDice(3), Dragons[0]));
        allPossibleMoves.add(new Move(new RedDice(3), Dragons[1]));
        allPossibleMoves.add(new Move(new RedDice(4), Dragons[2]));
        allPossibleMoves.add(new Move(new RedDice(4), Dragons[3]));
        allPossibleMoves.add(new Move(new RedDice(5), Dragons[2]));
        allPossibleMoves.add(new Move(new RedDice(5), Dragons[3]));
        allPossibleMoves.add(new Move(new RedDice(6), Dragons[1]));
        allPossibleMoves.add(new Move(new RedDice(6), Dragons[3]));
    }

    public void initTimeWarps () {
        timeWarps = new ArrayList<>();
        timeWarps.add(new TimeWarp());
    }

    public void initArcaneBoosts() {
        arcaneBoosts = new ArrayList<>();

    }

    private Dragon(Integer face, Integer wings, Integer tail, Integer heart, DragonNumber dragonNumber) {
        this.face = face;
        this.wings = wings;
        this.tail = tail;
        this.heart = heart;
        this.dragonNumber = dragonNumber;
    }

    public ArrayList<TimeWarp> getAllTimewarps() {
        return timeWarps;
    }

    public ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        return arcaneBoosts;
    }

    public DragonNumber getDragonNumber() {
        return this.dragonNumber;
    }

    public void initPointMap() {
        pointMap = new HashMap<>();
        pointMap.put(DragonNumber.Dragon1, 10);
        pointMap.put(DragonNumber.Dragon2, 14);
        pointMap.put(DragonNumber.Dragon3, 16);
        pointMap.put(DragonNumber.Dragon4, 20);
    }

    public boolean isDead() {
        return face == null && wings == null && heart == null && tail == null;
    }

    public boolean makeMove(Dice dice) {
        boolean valid = checkMove(dice);
        if (!valid){
            return false;
        }
        int targetValue = dice.getValue();
        moveHelper(targetValue, true);
        Move move = new Move(dice, this);
        for (int i = 0, size = allPossibleMoves.size(); i < size; i++) {
            if (allPossibleMoves.get(i).equals(move)) {
                allPossibleMoves.remove(i);
                break;
            }
        }
        return true;
    }

    public boolean checkMove(Dice dice) {
        //implement exception handling
        int targetValue = dice.getValue();
        return moveHelper(targetValue, false);
    }

    public boolean moveHelper(int targetValue, boolean doMove) {
        boolean valid = false;
        if (dragonNumber.equals(DragonNumber.Dragon1)) {
            if (targetValue == 3 && face != null) {
                valid = true;
                if (doMove)
                    face = null;
            }
            else if (targetValue == 2 && wings != null) {
                valid = true;
                if (doMove)
                    wings = null;
            }
            else if (targetValue == 1 && tail != null) {
                valid = true;
                if (doMove)
                    tail = null;
            }
        }
        else if (dragonNumber.equals(DragonNumber.Dragon2)) {
            if (targetValue == 6 && face != null) {
                valid = true;
                if (doMove)
                    face = null;
            }
            else if (targetValue == 1 && wings != null) {
                valid = true;
                if (doMove)
                    wings = null;
            }
            else if (targetValue == 3 && heart != null) {
                valid = true;
                if (doMove)
                    heart = null;
            }
        }
        else if (dragonNumber.equals(DragonNumber.Dragon3)) {
            if (targetValue == 5 && face != null) {
                valid = true;
                if (doMove)
                    face = null;
            }
            else if (targetValue == 2 && tail != null) {
                valid = true;
                if (doMove)
                    tail = null;
            }
            else if (targetValue == 4 && heart != null) {
                valid = true;
                if (doMove)
                    heart = null;
            }
        }
        else {
            if (targetValue == 5 && wings != null) {
                valid = true;
                if (doMove)
                    wings = null;
            }
            else if (targetValue == 4 && tail != null) {
                valid = true;
                if (doMove)
                    tail = null;
            }
            else if (targetValue == 6 && heart != null) {
                valid = true;
                if (doMove)
                    heart = null;
            }
        }
        return valid;
    }

    public Dragon dragonSelector(int index) {
        return Dragons[index-1];
    }

    @Override
    public int getScore() {
        int score = 0;
        for (int i = 0; i < 4; i++) {
            score += Dragons[i].isDead() ? pointMap.get(Dragons[i].getDragonNumber()) : 0;
        }
        return score;
    }

    @Override
    public int getElementalCrest() {
        return getElementalCrestString().equals("X") ? 1 : 0;
    }

    public int getArcaneBoostPower() {
        return allPossibleMoves.isEmpty() ? 1 : 0;
    }

    @Override
    public String getScoreSheet() {
        String scoreSheet =  "+-----------------------------------+\n";
        scoreSheet += "|  #  |D1   |D2   |D3   |D4   |R    |\n";
        scoreSheet += "+-----------------------------------+\n";
        scoreSheet += "|  F  |" + Dragons[0].changeToString(face) + "    |" + Dragons[1].changeToString(face) + "    |" + Dragons[2].changeToString(face) + "    |"+ Dragons[3].changeToString(face) + "    |" + getGreenBoostString() + "   |\n";
        scoreSheet += "|  W  |" + Dragons[0].changeToString(wings) + "    |" + Dragons[1].changeToString(wings) + "    |" + Dragons[2].changeToString(wings) + "    |"+ Dragons[3].changeToString(wings) + "    |" + getYellowBoostString() + "   |\n";
        scoreSheet += "|  T  |" + Dragons[0].changeToString(heart) + "    |" + Dragons[1].changeToString(heart) + "    |" + Dragons[2].changeToString(heart) + "    |"+ Dragons[3].changeToString(heart) + "    |" + getBlueBoostString() + "   |\n";
        scoreSheet += "|  H  |" + Dragons[0].changeToString(tail) + "    |" + Dragons[1].changeToString(tail) + "    |" + Dragons[2].changeToString(tail) + "    |"+ Dragons[3].changeToString(tail) + "    |" + getElementalCrestString() + "   |\n";
        scoreSheet += "+-----------------------------------+\n";
        scoreSheet += "|  S  |10   |14   |16   |20   |" + getArcaneBoostString() + "   |\n";
        scoreSheet += "+-----------------------------------+";
        return scoreSheet;
    }

    public Move[] getAllPossibleMoves() {
        Move[] returnedArray = new Move[allPossibleMoves.size()];
        return allPossibleMoves.toArray(returnedArray);
    }

    public String changeToString(Integer integer) {
        return integer == null ? "X" : "" + integer;
    }

    public String getGreenBoostString() {
        return Dragons[0].face == null && Dragons[1].face == null && Dragons[2].face == null ? "X" : "GB";
    }

    public String getYellowBoostString() {
        return Dragons[0].wings == null && Dragons[1].wings == null && Dragons[3].wings == null ? "X" : "YB";
    }

    public String getBlueBoostString() {
        return Dragons[0].tail == null && Dragons[2].tail == null && Dragons[3].tail == null ? "X" : "BB";
    }

    public String getElementalCrestString() {
        return Dragons[1].heart == null && Dragons[2].heart == null && Dragons[3].heart == null ? "X" : "EC";
    }

    public String getArcaneBoostString() {
        return getArcaneBoostPower() == 1 ? "X" : "AB";
    }
}
