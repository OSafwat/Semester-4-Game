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

import java.io.*;

import java.io.FileReader;
import java.util.*;
import java.util.function.Supplier;

private class Dragon extends Creature {
    private Integer face;
    private Integer wings;
    private Integer tail;
    private Integer heart;
    private DragonNumber dragonNumber;
    private Dragon[] Dragons;
    private int[] pointMap;
    private ArrayList<Move> allPossibleMoves;
    private ArrayList<TimeWarp> timeWarps;
    private ArrayList<ArcaneBoost> arcaneBoosts;
    private String[] rewards;
    private Supplier<String>[] suppliers;
    private int elementalCrestCount;


    private Dragon() {
        Dragons = new Dragon[4];
        Dragons[0] = new Dragon(3, 2, 1, null, DragonNumber.Dragon1);
        Dragons[1] = new Dragon(6, 1, null, 3, DragonNumber.Dragon2);
        Dragons[2] = new Dragon(5, null, 2, 4, DragonNumber.Dragon3);
        Dragons[3] = new Dragon(null, 5, 4, 6, DragonNumber.Dragon4);
        elementalCrestCount = 0;
        initialization();
    }

    private Dragon(Integer face, Integer wings, Integer tail, Integer heart, DragonNumber dragonNumber) {
        this.face = face;
        this.wings = wings;
        this.tail = tail;
        this.heart = heart;
        this.dragonNumber = dragonNumber;
    }

    private void initialization() {
        initPointMap();
        initPossibleMoves();
        initRewards();
        initSuppliers();
        initTimeWarpsAndArcaneBoosts();
    }

    private void initRewards() {
        rewards = new String[5];
        File file = new File("../../../main/resources/config/EmberFallDominionRewards.properties");
        try (FileReader fr = new FileReader(file)) {
            Properties properties = new Properties();
            properties.load(fr);
            ArrayList<Object> temporaryRewards = new ArrayList<>(new LinkedHashSet<>(properties.values()));
            for (int i = 0, size = temporaryRewards.size(); i < size; i++) {
                rewards[i] = (String)temporaryRewards.get(i);
            }
        } catch (IOException e) {
            rewards = new String[]{"GreenBonus", "YellowBonus", "BlueBonus", "ElementalCrest", "ArcaneBoost"};
        }
    }

    private void initTimeWarpsAndArcaneBoosts () {
        for (int i = 0; i < 5; i++) {
            String current = suppliers[i].get();
            if (current.equals("TW")) {
                timeWarps.add(new TimeWarp());
            }
            if (current.equals("AB")) {
                arcaneBoosts.add(new ArcaneBoost());
            }
        }
    }

    private void initPossibleMoves() {
        allPossibleMoves = new ArrayList<>();
        for (int i = 0; i < 4; i++)
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

    @Override
    public int getScore() {
        int score = 0;
        for (int i = 0; i < 4; i++) {
            score += Dragons[i].isDead() ? pointMap[i] : 0;
        }
        return score;
    }

    @Override
    public int getElementalCrest() {
        return elementalCrestCount;
    }

    private int getArcaneBoostPower() {
        return allPossibleMoves.isEmpty() ? 1 : 0;
    }

    public Move[] getAllPossibleMoves() {
        Move[] returnedArray = new Move[allPossibleMoves.size()];
        return allPossibleMoves.toArray(returnedArray);
    }

    private ArrayList<TimeWarp> getAllTimeWarps() {
        return timeWarps;
    }

    private ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        return arcaneBoosts;
    }

    private DragonNumber getDragonNumber() {
        return this.dragonNumber;
    }

    private void initPointMap() {
        pointMap = new int[]{10, 14, 16, 20};
    }

    private boolean isDead() {
        return face == null && wings == null && heart == null && tail == null;
    }

    private boolean allDead() {
        boolean dead = true;
        for (int i = 0; i < 4; i++)
            dead = dead && Dragons[i].isDead();
        return dead;
    }

    public boolean makeMove(Dice dice) throws BonusException, InvalidMoveException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Which dragon would you like to attack?\nPlease enter a number from 1 to 4 to indicate which dragon you would like to attack.");
        int dragonIndex = sc.nextInt();
        while (dragonIndex < 1 || dragonIndex > 4) {
            System.out.println("The value you have entered is invalid.\nPlease enter a number from 1 to 4 to indicate which dragon you would like to attack.");
            dragonIndex = sc.nextInt();
        }
        sc.close();
        Dragon targetDragon = Dragons[dragonIndex-1];
        targetDragon.checkMove(dice);
        int targetValue = dice.getValue();

        String[] oldRewardStatus = new String[5];
        for (int i = 0; i < 5; i++) {
            oldRewardStatus[i] = suppliers[i].get();
        }
        targetDragon.moveHelper(targetValue, true);
        Move move = new Move(dice, targetDragon);
        moveAfterMath(oldRewardStatus, move);
        return true;
    }

    private void moveAfterMath (String[] oldRewardStatus, Move move) throws BonusException {
        for (int i = 0, size = allPossibleMoves.size(); i < size; i++) {
            if (allPossibleMoves.get(i).equals(move)) {
                allPossibleMoves.remove(i);
                break;
            }
        }
        for (int i = 0; i < 5; i++) {
            String newRewardStatus = suppliers[i].get();
            if (!oldRewardStatus[i].equals(newRewardStatus)) {
                if (oldRewardStatus[i].contains("C")) {
                    elementalCrestCount++;
                }
                else if (oldRewardStatus[i].charAt(1) == 'B' && oldRewardStatus[i].charAt(i) != 'A') {
                    throw new BonusException(decodeLetterToRealmColor(oldRewardStatus[i].charAt(0)));
                }
                else if (oldRewardStatus[i].equals("TW")) {
                    initNextTimeWarp();
                }
                else if (oldRewardStatus[i].equals("AB")) {
                    initNextArcaneBoost();
                }
            }
        }
    }

    private void initNextTimeWarp() {
        //Is supposed to change the enum for the timewarp obtained
    }

    private void initNextArcaneBoost() {
        //Is supposed to change the enum for the arcane boost obtained
    }

    private void initSuppliers () {
        suppliers = new Supplier[]{
                this::getFirstRowRewardString,
                this::getSecondRowRewardString,
                this::getThirdRowRewardString,
                this::getFourthRowRewardString,
                this::getCornerRewardString,
        };
    }

    private RealmColor decodeLetterToRealmColor (char c) {
        return switch (c) {
            case 'G' -> RealmColor.GREEN;
            case 'B' -> RealmColor.BLUE;
            case 'R' -> RealmColor.RED;
            case 'M' -> RealmColor.MAGENTA;
            case 'E' -> RealmColor.WHITE;
            case 'Y' -> RealmColor.YELLOW;
            default -> null;
        };
    }

    public boolean checkMove(Dice dice) throws InvalidMoveException {
        int targetValue = dice.getValue();
        moveHelper(targetValue, false);
        return true;
    }

    private boolean moveHelper(int targetValue, boolean doMove) throws InvalidMoveException {
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
            throw new InvalidMoveException("message");
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
        scoreSheet.append(getFirstRowRewardString()).append("   |\n");
        scoreSheet.append("|  W  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(wings)).append("    |");
        }
        scoreSheet.append(getSecondRowRewardString()).append("   |\n");
        scoreSheet.append("|  T  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(tail)).append("    |");
        }
        scoreSheet.append(getThirdRowRewardString()).append("   |\n");
        scoreSheet.append("|  W  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(heart)).append("    |");
        }
        scoreSheet.append(getFourthRowRewardString()).append("   |\n");
        scoreSheet.append("+-----------------------------------+\n");
        scoreSheet.append("|  S  |10   |14   |16   |20   |").append(getCornerRewardString()).append("   |\n");
        scoreSheet.append("+-----------------------------------+");
        return scoreSheet.toString();
    }

    private String changeToString(Integer integer) {
        return integer == null ? "X" : "" + integer;
    }

    private String getFirstRowRewardString() {
        return Dragons[0].face == null && Dragons[1].face == null && Dragons[2].face == null ? "X" : encode(rewards[0]);
    }

    private String getSecondRowRewardString() {
        return Dragons[0].wings == null && Dragons[1].wings == null && Dragons[3].wings == null ? "X" : encode(rewards[1]);
    }

    private String getThirdRowRewardString() {
        return Dragons[0].tail == null && Dragons[2].tail == null && Dragons[3].tail == null ? "X" : encode(rewards[2]);
    }

    private String getFourthRowRewardString() {
        return Dragons[1].heart == null && Dragons[2].heart == null && Dragons[3].heart == null ? "X" : encode(rewards[3]);
    }

    private String getCornerRewardString() {
        return allDead() ? "X" : encode(rewards[4]);
    }

    private String encode (String reward) {
        return extractCapitalLetters(reward);
    }

    private static String extractCapitalLetters(String reward) {
        return reward.replaceAll("[^A-Z]", "");
    }
}