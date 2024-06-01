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
import game.exceptions.RewardException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Dragon extends Creature {
    private Integer face;
    private Integer wings;
    private Integer tail;
    private Integer heart;
    private DragonNumber dragonNumber;
    private Dragon[] dragons;
    private int[] pointMap;
    private ArrayList<Move> allPossibleMoves;
    private ArrayList<TimeWarp> timeWarps;
    private ArrayList<ArcaneBoost> arcaneBoosts;
    private String[] rewards;
    private int elementalCrestCount;


    //Constructor to be used in the CLIcontroller to initialize the Dragon array
    public Dragon() {
        dragons = new Dragon[4];
        dragons[0] = new Dragon(3, 2, 1, null, DragonNumber.Dragon1);
        dragons[1] = new Dragon(6, 1, null, 3, DragonNumber.Dragon2);
        dragons[2] = new Dragon(5, null, 2, 4, DragonNumber.Dragon3);
        dragons[3] = new Dragon(null, 5, 4, 6, DragonNumber.Dragon4);
        elementalCrestCount = 0;
        initialization();
    }

    //Constructor used inside the first one to initialize the actual dragons themselves
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
        initTimeWarpsAndArcaneBoosts();
    }

    //Method that reads the row and corner rewards from the EmberfallDominionRewards.properties file
    public void initRewards() {
        rewards = new String[5];
        int pointer = 0;
        String[] defaultRewards = new String[]{"GreenBonus", "YellowBonus", "BlueBonus", "ElementalCrest", "ArcaneBoost"};
        String filePath = "src/main/resources/config/EmberfallDominionRewards.properties";
        try (BufferedReader br = new BufferedReader( new FileReader(filePath))) {
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                nextLine = nextLine.trim();
                if (!nextLine.isEmpty() && !nextLine.startsWith("#")) {
                    int separatorIndex = nextLine.indexOf('=');
                    if (separatorIndex != -1) {
                        String value = nextLine.substring(separatorIndex + 1).trim();
                        if (value.isEmpty())
                            rewards[pointer] = defaultRewards[pointer++];
                        else if (checkValidityOfReward(value))
                            rewards[pointer++] = value;
                        else
                        {
                            throw new RewardException();
                        }
                    }
                }
            }
        } catch (IOException | RewardException e) {
            rewards = defaultRewards;
        }
    }

    private boolean checkValidityOfReward(String reward) {
        switch (reward) {
            case "ArcaneBoost":
            case "GreenBonus":
            case "YellowBonus":
            case "BlueBonus":
            case "ElementalCrest":
            case "MagentaBonus":
            case "RedBonus":
            case "TimeWarp":
            case "EssenceBonus":break;
            default: return false;
        }
        return true;
    }

    //A method to initialize the pointMap instance variable, which is used in score calculation
    private void initPointMap() {
        pointMap = new int[]{10, 14, 16, 20};
    }

    //Method that uses the suppliers array and the methods inside them to initialize some number of ArcaneBoosts and TimeWarps
    private void initTimeWarpsAndArcaneBoosts () {
        arcaneBoosts = new ArrayList<>();
        timeWarps = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String current = getRewardStringDependingOnIndex(i);
            if (current.equals("TW")) {
                timeWarps.add(new TimeWarp());
            }
            if (current.equals("AB")) {
                arcaneBoosts.add(new ArcaneBoost());
            }
        }
    }

    //Method that goes over all the dragons and fills up an arraylist with all the possible moves that can be done against these dragons
    private void initPossibleMoves() {
        allPossibleMoves = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            if (!Objects.equals(dragons[i].face, null)) {
                allPossibleMoves.add(new Move(new RedDice(dragons[i].face, i), this));
            }
            if (!Objects.equals(dragons[i].wings, null)) {
                allPossibleMoves.add(new Move(new RedDice(dragons[i].wings, i), this));
            }
            if (!Objects.equals(dragons[i].tail, null)) {
                allPossibleMoves.add(new Move(new RedDice(dragons[i].tail, i), this));
            }
            if (!Objects.equals(dragons[i].heart, null)) {
                allPossibleMoves.add(new Move(new RedDice(dragons[i].heart, i), this));
            }
        }
    }

    //Method that calculates the score at any point in the game
    @Override
    public int getScore() {
        int score = 0;
        for (int i = 0; i < 4; i++) {
            score += dragons[i].isDead() ? pointMap[i] : 0;
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
        initPossibleMoves();
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

    //A method used to know whether a Dragon is dead or not
    private boolean isDead() {
        return face == null && wings == null && heart == null && tail == null;
    }

    public String getRewardStringDependingOnIndex(int index){
        //0 -> FirstRow
        //1 -> SecondRow
        //2 -> ThirdRow
        //3 -> FourthRow
        //4 -> Corner
        String current;
        switch (index) {
            case 0: current = getFirstRowRewardString(); break;
            case 1: current = getSecondRowRewardString(); break;
            case 2: current = getThirdRowRewardString(); break;
            case 3: current = getFourthRowRewardString(); break;
            case 4: current = getCornerRewardString(); break;
            default: current = "X"; //We shouldn't reach this point
        }
        return current;
    }

    //A method that (attempts) to make a move, throwing any exceptions while doing so, and returns true if the move succeeds
    public boolean makeMove(Dice inputDice) throws BonusException {
        RedDice dice = (RedDice)inputDice;
        int dragonIndex = dice.getDragonNumber();
        Dragon targetDragon = dragons[dragonIndex];
        boolean valid = targetDragon.checkMove(dice);
        if (!valid)
            return false;
        int targetValue = dice.getValue();
        String[] oldRewardStatus = new String[5];
        for (int i = 0; i < 5; i++) {
            oldRewardStatus[i] = getRewardStringDependingOnIndex(i);
        }
        targetDragon.moveHelper(targetValue, true);
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < 5; i++) {
            String newRewardStatus = getRewardStringDependingOnIndex(i);
            if (!oldRewardStatus[i].equals(newRewardStatus)) {
                if (oldRewardStatus[i].contains("C")) {
                    elementalCrestCount++;
                }
                else if (oldRewardStatus[i].charAt(1) == 'B' && oldRewardStatus[i].charAt(0) != 'A') {
                    if (index1 == -1)
                        index1 = i;
                    else
                        index2 = i;
                }
                else if (oldRewardStatus[i].equals("TW")) {
                    initNextTimeWarp();
                }
                else if (oldRewardStatus[i].equals("AB")) {
                    initNextArcaneBoost();
                }
            }
        }
        //index1 being != -1 means there was at least one bonus, and index2 being != -1 means there were no bonuses
        if (index1 != -1)
        {
            if (index2 == -1) {
                initPossibleMoves();
                throw new BonusException(decodeLetterToRealmColor(oldRewardStatus[index1].charAt(0)));
            }
            else
            {
                RealmColor firstBonus = decodeLetterToRealmColor(oldRewardStatus[index1].charAt(0));
                RealmColor secondBonus = decodeLetterToRealmColor(oldRewardStatus[index2].charAt(0));
                //.ordinal() returns the index of the enum in the enum list in the class
                //since red is of highest prio, and it has ordinal 0, then the one with the LESSER ordinal should be applied first
                //so, if firstBonus had a higher ordinal, it's switch with secondBonus such that firstBonus has the lower ordinal (and thus higher prio)
                if (firstBonus.ordinal() > secondBonus.ordinal()) {
                    RealmColor temporary = firstBonus;
                    firstBonus = secondBonus;
                    secondBonus = temporary;
                }
                initPossibleMoves();
                throw new BonusException(firstBonus, secondBonus);
            }
        }
        initPossibleMoves();
        return true;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Dragon) {
            Dragon dragon = (Dragon) obj;
            return Objects.equals(dragon.heart, heart) && Objects.equals(dragon.face, face) && Objects.equals(dragon.wings, wings) && Objects.equals(dragon.tail, tail);
        }
        return false;
    }

    //Method that updates TimeWarps
    private void initNextTimeWarp() {
        TimeWarp currentTimewarp = timeWarps.get(0);
        currentTimewarp.setStatus(RewardStates.ACQUIRED);
        timeWarps.remove(currentTimewarp);
    }

    //Method that updates ArcaneBoosts
    private void initNextArcaneBoost() {
        ArcaneBoost currentArcaneBoost = arcaneBoosts.get(0);
        currentArcaneBoost.setStatus(RewardStates.ACQUIRED);
        arcaneBoosts.remove(currentArcaneBoost);
    }

    //Method that, using a character, can identify what realm a boost belongs to
    private RealmColor decodeLetterToRealmColor (char c) {
        RealmColor result;
        switch (c) {
            case 'G': result =  RealmColor.GREEN; break;
            case 'B': result = RealmColor.BLUE; break;
            case 'R': result = RealmColor.RED; break;
            case 'M': result = RealmColor.MAGENTA; break;
            case 'E': result = RealmColor.WHITE; break;
            case 'Y': result = RealmColor.YELLOW; break;
            default: result = null;
        }
        return result;
    }

    //Method that checks if a move can be done
    public boolean checkMove(Dice dice) {
        int targetValue = dice.getValue();
        return moveHelper(targetValue, false);
    }

    //Method to reduce code redundancy
    private boolean moveHelper(int targetValue, boolean doMove) {
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
        StringBuilder scoreSheet =  new StringBuilder("Emberfall Dominion: Pyroclast Dragon (RED REALM):\n");
        scoreSheet.append("+-----------------------------------+\n");
        scoreSheet.append("|  #  |D1   |D2   |D3   |D4   |R    |\n");
        scoreSheet.append("+-----------------------------------+\n");
        scoreSheet.append("|  F  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(changeToString(dragons[i].face)).append("    |");
        }
        scoreSheet.append(getRewardStringDependingOnIndex(0)).append("   |\n");
        scoreSheet.append("|  W  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(changeToString(dragons[i].wings)).append("    |");
        }
        scoreSheet.append(getRewardStringDependingOnIndex(1)).append("   |\n");
        scoreSheet.append("|  T  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(changeToString(dragons[i].tail)).append("    |");
        }
        scoreSheet.append(getRewardStringDependingOnIndex(2)).append("   |\n");
        scoreSheet.append("|  H  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(changeToString(dragons[i].heart)).append("    |");
        }
        scoreSheet.append(getRewardStringDependingOnIndex(3)).append("   |\n");
        scoreSheet.append("+-----------------------------------+\n").append("|  S  |");
        for (int i = 0; i < 4; i++) {
            scoreSheet.append(pointMap[i]).append("   |");
        }
        scoreSheet.append(getRewardStringDependingOnIndex(4)).append("   |\n");
        scoreSheet.append("+-----------------------------------+\n\n");
        return scoreSheet.toString();
    }

    //This and the methods below it assist in the scoresheet and other methods
    private String changeToString(Integer integer) {
        return  Objects.equals(null, integer) ? "X" : "" + integer;
    }

    private String getFirstRowRewardString() {
        return dragons[0].face == null && dragons[1].face == null && dragons[2].face == null ? "X " : encode(rewards[0]);
    }

    private String getSecondRowRewardString() {
        return dragons[0].wings == null && dragons[1].wings == null && dragons[3].wings == null ? "X " : encode(rewards[1]);
    }

    private String getThirdRowRewardString() {
        return dragons[0].tail == null && dragons[2].tail == null && dragons[3].tail == null ? "X " : encode(rewards[2]);
    }

    private String getFourthRowRewardString() {
        return dragons[1].heart == null && dragons[2].heart == null && dragons[3].heart == null ? "X " : encode(rewards[3]);
    }

    private String getCornerRewardString() {
        return dragons[0].face == null && dragons[1].wings == null && dragons[2].tail == null && dragons[3].heart == null ? "X " : encode(rewards[4]);
    }

    //Method that changes the name of the row and corner rewards to their abbreviation
    private String encode (String reward) {
        return reward.replaceAll("[^A-Z]", "");
    }

    public String getImage(int dragonIndex) {
        StringBuilder string = new StringBuilder("/images/RedRealmImages/");
        Dragon dragon = dragons[dragonIndex];
    //new Image(getClass().getResourceAsStream("/images/Main Screen.png"))
        if (Objects.equals(dragon.face, null))
            string.append("face-");
        if (Objects.equals(dragon.wings, null))
            string.append("wings-");
        if (Objects.equals(dragon.tail, null))
            string.append("tail-");
        if (Objects.equals(dragon.heart, null))
            string.append("heart-");
        return string.deleteCharAt(string.length() - 1).append(".png").toString();
    }

    public Integer getFace() {
        return face;
    }

    public Integer getWings() {
        return wings;
    }

    public Integer getTail() {
        return tail;
    }

    public Integer getHeart() {
        return heart;
    }

    public Dragon[] getDragons() {
        return dragons;
    }
}