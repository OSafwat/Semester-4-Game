package game.creatures;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.dice.RedDice;
import game.engine.Move;
import game.engine.enums.DragonNumber;
import game.engine.enums.RealmColor;
import game.engine.enums.RewardStates;
import game.exceptions.BonusException;
import game.exceptions.InvalidMoveException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Supplier;

public class Dragon extends Creature {
    public Integer face;
    public Integer wings;
    public Integer tail;
    public Integer heart;
    public DragonNumber dragonNumber;
    public Dragon[] Dragons;
    public int[] pointMap;
    public ArrayList<Move> allPossibleMoves;
    public ArrayList<TimeWarp> timeWarps;
    public ArrayList<ArcaneBoost> arcaneBoosts;
    public String[] rewards;
    public Supplier<String>[] suppliers;
    public int elementalCrestCount;


    //Constructor to be used in the CLIcontroller to initialize the Dragon array
    public Dragon() {
        Dragons = new Dragon[4];
        Dragons[0] = new Dragon(3, 2, 1, null, DragonNumber.Dragon1);
        Dragons[1] = new Dragon(6, 1, null, 3, DragonNumber.Dragon2);
        Dragons[2] = new Dragon(5, null, 2, 4, DragonNumber.Dragon3);
        Dragons[3] = new Dragon(null, 5, 4, 6, DragonNumber.Dragon4);
        elementalCrestCount = 0;
        initialization();
    }

    //Constructor used inside the first one to initialize the actual Dragons themselves
    public Dragon(Integer face, Integer wings, Integer tail, Integer heart, DragonNumber dragonNumber) {
        this.face = face;
        this.wings = wings;
        this.tail = tail;
        this.heart = heart;
        this.dragonNumber = dragonNumber;
    }

    //Method that contains all initialization methods to reduce the amount of code written in the first constructor
    public void initialization() {
        initPointMap();
        initPossibleMoves();
        initRewards();
        initSuppliers();
        initTimeWarpsAndArcaneBoosts();
    }

    //Method that reads the row and corner rewards from the EmberfallDominionRewards.properties file
    public void initRewards() {
        rewards = new String[5];
        int pointer = 0;
        String filePath = "../../../main/resources/config/EmberFallDominionRewards.properties";
        try (BufferedReader br = new BufferedReader( new FileReader(filePath))) {
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                nextLine = nextLine.trim();
                if (!nextLine.isEmpty() && !nextLine.startsWith("#")) {
                    int separatorIndex = nextLine.indexOf('=');
                    if (separatorIndex != -1) {
                        String value = nextLine.substring(separatorIndex + 1).trim();
                        rewards[pointer++] = value;
                    }
                }
            }
        } catch (IOException e) {
            rewards = new String[]{"GreenBonus", "YellowBonus", "BlueBonus", "ElementalCrest", "ArcaneBoost"};
        }
    }

    //A method to initialize the pointMap instance variable, which is used in score calculation
    public void initPointMap() {
        pointMap = new int[]{10, 14, 16, 20};
    }

    //Method that uses the suppliers array and the methods inside them to initialize some number of ArcaneBoosts and TimeWarps
    public void initTimeWarpsAndArcaneBoosts () {
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

    //Method that goes over all the dragons and fills up an arraylist with all the possible moves that can be done against these dragons
    public void initPossibleMoves() {
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

    //Method that calculates the score at any point in the game
    @Override
    public int getScore() {
        int score = 0;
        for (int i = 0; i < 4; i++) {
            score += Dragons[i].isDead() ? pointMap[i] : 0;
        }
        return score;
    }

    //Method to get the elemental crest count
    @Override
    public int getElementalCrest() {
        return elementalCrestCount;
    }

    //Method used to get all possible moves at any stage in the game
    public ArrayList<Move> getAllPossibleMoves() {
        return allPossibleMoves;
    }

    //A method to get all the time warp powers
    public ArrayList<TimeWarp> getAllTimeWarps() {
        return timeWarps;
    }

    //A method to get all the arcane boost powers
    public ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        return arcaneBoosts;
    }

    //A method to get the dragon number attribute
    public DragonNumber getDragonNumber() {
        return this.dragonNumber;
    }

    //A method that selects the dragon
    public Dragon selectsDragon(int number) {
        return Dragons[number-1];
    }

    //A method used to know whether a Dragon is dead or not
    public boolean isDead() {
        return face == null && wings == null && heart == null && tail == null;
    }

    //A method used to know whether all Dragons in the Dragon array are dead or not
    public boolean allDead() {
        boolean dead = true;
        for (int i = 0; i < 4; i++)
            dead = dead && Dragons[i].isDead();
        return dead;
    }

    //A method that (attempts) to make a move, throwing any exceptions while doing so, and returns true if the move succeeds
    public boolean makeMove(Dice inputDice) throws BonusException {
        RedDice dice = (RedDice)inputDice;
        int dragonIndex = dice.getDragonNumber();
        Dragon targetDragon = Dragons[dragonIndex-1];
        boolean valid = targetDragon.checkMove(dice);
        if (!valid)
            return false;
        int targetValue = dice.getValue();
        String[] oldRewardStatus = new String[5];
        for (int i = 0; i < 5; i++) {
            oldRewardStatus[i] = suppliers[i].get();
        }
        targetDragon.moveHelper(targetValue, true);
        Move move = new Move(dice, targetDragon);
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
        return true;
    }

    public boolean equals(Object obj) {
        Dragon dragon = (Dragon) obj;
        return dragon.heart.equals(heart) && dragon.face.equals(face) && dragon.wings.equals(wings) && dragon.tail.equals(tail);
    }

    //Method that updates TimeWarps
    public void initNextTimeWarp() {
        TimeWarp currentTimewarp = timeWarps.get(0);
        currentTimewarp.setStatus(RewardStates.ACQUIRED);
        timeWarps.remove(currentTimewarp);
    }

    //Method that updates ArcaneBoosts
    public void initNextArcaneBoost() {
        ArcaneBoost currentArcaneBoost = arcaneBoosts.get(0);
        currentArcaneBoost.setStatus(RewardStates.ACQUIRED);
        arcaneBoosts.remove(currentArcaneBoost);
    }

    //Method that initializes the suppliers instance variables to make some method calls easier and decrease code
    public void initSuppliers () {
        suppliers = new Supplier[]{
                this::getFirstRowRewardString,
                this::getSecondRowRewardString,
                this::getThirdRowRewardString,
                this::getFourthRowRewardString,
                this::getCornerRewardString,
        };
    }

    //Method that, using a character, can identify what realm a boost belongs to
    public RealmColor decodeLetterToRealmColor (char c) {
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

    //Method that checks if a move can be done
    public boolean checkMove(Dice dice) {
        int targetValue = dice.getValue();
        return moveHelper(targetValue, false);
    }

    //Method to reduce code redundancy
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

    //Method that returns the scoreSheet at any point in the game
    @Override
    public String getScoreSheet() {
        StringBuilder scoreSheet =  new StringBuilder("+-----------------------------------+\n");
        scoreSheet.append("|  #  |D1   |D2   |D3   |D4   |R    |\n");
        scoreSheet.append("+-----------------------------------+\n");
        scoreSheet.append("|  F  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(face)).append("    |");
        }
        scoreSheet.append(suppliers[0].get()).append("   |\n");
        scoreSheet.append("|  W  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(wings)).append("    |");
        }
        scoreSheet.append(suppliers[1].get()).append("   |\n");
        scoreSheet.append("|  T  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(tail)).append("    |");
        }
        scoreSheet.append(suppliers[2].get()).append("   |\n");
        scoreSheet.append("|  W  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(Dragons[0].changeToString(heart)).append("    |");
        }
        scoreSheet.append(suppliers[3].get()).append("   |\n");
        scoreSheet.append("+-----------------------------------+\n").append("|  S  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(pointMap[i]).append("  |");
        }
        scoreSheet.append(suppliers[4].get()).append("   |\n");
        scoreSheet.append("+-----------------------------------+");
        return scoreSheet.toString();
    }

    //This and the methods below it assist in the scoresheet and other methods
    public String changeToString(Integer integer) {
        return integer == null ? "X" : "" + integer;
    }

    public String getFirstRowRewardString() {
        return Dragons[0].face == null && Dragons[1].face == null && Dragons[2].face == null ? "X" : encode(rewards[0]);
    }

    public String getSecondRowRewardString() {
        return Dragons[0].wings == null && Dragons[1].wings == null && Dragons[3].wings == null ? "X" : encode(rewards[1]);
    }

    public String getThirdRowRewardString() {
        return Dragons[0].tail == null && Dragons[2].tail == null && Dragons[3].tail == null ? "X" : encode(rewards[2]);
    }

    public String getFourthRowRewardString() {
        return Dragons[1].heart == null && Dragons[2].heart == null && Dragons[3].heart == null ? "X" : encode(rewards[3]);
    }

    public String getCornerRewardString() {
        return allDead() ? "X" : encode(rewards[4]);
    }

    //Method that changes the name of the row and corner rewards to their abbreviation
    public String encode (String reward) {
        return reward.replaceAll("[^A-Z]", "");
    }
}