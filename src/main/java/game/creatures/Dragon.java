package game.creatures;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.dice.RedDice;
import game.engine.Move;
import game.engine.enums.DragonNumber;
import game.engine.enums.RealmColor;
import game.exceptions.BonusException;
import game.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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

    private Dragon(Integer face, Integer wings, Integer tail, Integer heart, DragonNumber dragonNumber) {
        this.face = face;
        this.wings = wings;
        this.tail = tail;
        this.heart = heart;
        this.dragonNumber = dragonNumber;
    }

    public void initPossibleMoves() {
        allPossibleMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            if (Dragons[i].face != null) {
                allPossibleMoves.add(new Move(new RedDice(Dragons[i].face), Dragons[i]));
            }
            if (Dragons[i].wings != null) {
                allPossibleMoves.add(new Move(new RedDice(Dragons[i].wings), Dragons[i]));
            }
            if (Dragons[i].tail != null) {
                allPossibleMoves.add(new Move(new RedDice(Dragons[i].tail), Dragons[i]));
            }
            if (Dragons[i].heart != null) {
                allPossibleMoves.add(new Move(new RedDice(Dragons[i].heart), Dragons[i]));
            }
        }
    }

    public void initTimeWarps () {
        timeWarps = new ArrayList<>();
        timeWarps.add(new TimeWarp());
    }

    public void initArcaneBoosts() {
        arcaneBoosts = new ArrayList<>();

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

    public ArrayList<TimeWarp> getAllTimeWarps() {
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

    public boolean makeMove(Dice dice) throws BonusException, InvalidMoveException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Which dragon would you like to attack?\nPlease enter a number from 1 to 4 to indicate which dragon you would like to attack.");
        int dragonIndex = sc.nextInt();
        while (dragonIndex < 1 || dragonIndex > 4) {
            System.out.println("The value you have entered is invalid.\nPlease enter a number from 1 to 4 to indicate which dragon you would like to attack.");
            dragonIndex = sc.nextInt();
        }
        Dragon targetDragon = Dragons[dragonIndex-1];
        boolean valid = false;
        try {
            valid = targetDragon.checkMove(dice);
        } catch (InvalidMoveException e) {
            throw e;
            return false;
        }
        int targetValue = dice.getValue();
        String oldGreenBoost = getGreenBoostString();
        String oldBlueBoost = getBlueBoostString();
        String oldYellowBoost = getYellowBoostString();
        targetDragon.moveHelper(targetValue, true);
        Move move = new Move(dice, targetDragon);
        for (int i = 0, size = allPossibleMoves.size(); i < size; i++) {
            if (allPossibleMoves.get(i).equals(move)) {
                allPossibleMoves.remove(i);
                break;
            }
        }
        String newGreenBoost = getGreenBoostString();
        String newBlueBoost = getBlueBoostString();
        String newYellowBoost = getYellowBoostString();
        if (!oldGreenBoost.equals(newGreenBoost)) {
            throw new BonusException(RealmColor.GREEN);
        }
        if (!oldBlueBoost.equals(newBlueBoost)) {
            throw new BonusException(RealmColor.BLUE);
        }
        if (!oldYellowBoost.equals(newYellowBoost)) {
            throw new BonusException(RealmColor.YELLOW);
        }
        return true;
    }

    public boolean checkMove(Dice dice) throws InvalidMoveException {
        int targetValue = dice.getValue();
        try {
            moveHelper(targetValue, false)
        }
        return ;
    }

    public boolean moveHelper(int targetValue, boolean doMove) throws InvalidMoveException {
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
        //If !doMove is true, then this was called from checkMove, and hence should throw InvalidMoveException
        if (!doMove) {
            throw new InvalidMoveException();
        }
        return valid;
    }

    @Override
    public String getScoreSheet() {
        StringBuilder scoreSheet =  new StringBuilder("+-----------------------------------+\n");
        scoreSheet.append("|  #  |D1   |D2   |D3   |D4   |R    |\n");
        scoreSheet.append("+-----------------------------------+\n");
        scoreSheet.append("|  F  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(face)).append("    |");
        }
        scoreSheet.append(getGreenBoostString()).append("   |\n");
        scoreSheet.append("|  W  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(wings)).append("    |");
        }
        scoreSheet.append(getYellowBoostString()).append("   |\n");
        scoreSheet.append("|  T  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(tail)).append("    |");
        }
        scoreSheet.append(getBlueBoostString()).append("   |\n");
        scoreSheet.append("|  W  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(heart)).append("    |");
        }
        scoreSheet.append(getElementalCrestString()).append("   |\n");
        scoreSheet.append("+-----------------------------------+\n");
        scoreSheet.append("|  S  |10   |14   |16   |20   |").append(getArcaneBoostString()).append("   |\n");
        scoreSheet.append("+-----------------------------------+");
        return scoreSheet.toString();
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

    public Move[] getAllPossibleMoves() {
        Move[] returnedArray = new Move[allPossibleMoves.size()];
        return allPossibleMoves.toArray(returnedArray);
    }
}
