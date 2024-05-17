package game.creatures;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.dice.YellowDice;
import game.engine.Move;
import game.engine.enums.RealmColor;
import game.engine.enums.RewardStates;
import game.exceptions.BonusException;

public class Lion extends Creature{
    private final Properties properties;

    public Lion(){
        arcaneBoosts= new ArrayList<>();
        timeWarps = new ArrayList<>();
        initLions();
        this.deadLions = 0;
        this.score = 0;
        this.elementalCrest = 0;
        populateRewardLocationFromConfigFile();
        populateMappedRewardLocation();

        properties = new Properties();
        try {
            File config = new File("src/main/resources/config/RadiantSvannaRewards.properties");
            FileReader configReader = new FileReader(config);
            this.properties.load(configReader);
        } catch (IOException e) {
            System.out.println("Properties file reading failed.");
            this.properties.setProperty("hit1Reward", "null");
            this.properties.setProperty("hit2Reward", "null");
            this.properties.setProperty("hit3Reward", "TimeWarp");
            this.properties.setProperty("hit4Reward", "null");
            this.properties.setProperty("hit5Reward", "RedBonus");
            this.properties.setProperty("hit6Reward", "ArcaneBoost");
            this.properties.setProperty("hit7Reward", "null");
            this.properties.setProperty("hit8Reward", "ElementalCrest");
            this.properties.setProperty("hit9Reward", "null");
            this.properties.setProperty("hit10Reward", "MagentaBonus");
            this.properties.setProperty("hit11Reward", "null");
        }

        for(int i = 1; i <= 11; i++) {
            if(Objects.equals(properties.getProperty("hit" + i + "Reward"), "ArcaneBoost")){
                ArcaneBoost ac = new ArcaneBoost(RewardStates.UNACQUIRED);
                this.arcaneBoosts.add(ac);
            }

            if(properties.getProperty("hit"+(i+1)+"Reward").equals("TimeWarp")) {
                TimeWarp tw = new TimeWarp(RewardStates.UNACQUIRED);
                timeWarps.add(tw);
            }
        }
                initScoreSheet();

    }

    public ArrayList<TimeWarp> getAllTimeWarps() {
        return timeWarps;
    }

    public ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        return arcaneBoosts;
    }

    public int[] getLions(){
        return this.lions;
    }

    private void setLions(int[] lions){
        this.lions=lions;
    }

    private void initLions(){
        setLions(new int[11]);
    }

    private void updateLions(Dice dice){
        this.lions[deadLions] = calculateScore(dice);
    }

    public int getDeadLions(){
        return this.deadLions;
    }

    private void setDeadLions (int deadLions){
        this.deadLions = deadLions;
    }

    private void updateDeadLions(){
        setDeadLions(deadLions + 1);
    }

    @Override
    public int getElementalCrest() {
        int elementalCrestCount = 0;
        for(int i = 1; i < 11; i++) {
            if(properties.getProperty("hit"+i+"Reward").equals("ElementalCrest") && scores[i-1]!=0) {
                elementalCrestCount++;
            }
        }
        return elementalCrestCount;
    }

    @Override
    public String getScoreSheet() {
        String scoresheet = "Radiant Savanna: Solar Lion (YELLOW REALM):\n" +
                         "+-----------------------------------------------------------------------+\n" +
                         "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n" +
                         "+-----------------------------------------------------------------------+\n";

        scoresheet += "|  H  |";
        for(int i = 0; i < 11; i++) { scoresheet += this.scores[i] * Integer.parseInt(properties.getProperty("hit" + (i + 1) + "Multiplier").charAt(0) + "") +"    |"; }

        scoresheet += "\n|  M  |";
        for(int i = 1; i <= 11; i++) { scoresheet += getMultiplier(i)+"   |"; }

        scoresheet += "\n|  R  |";
        for(int i = 1; i <= 11; i++) { scoresheet += getBonus(i)+"   |"; }

        scoresheet += "\n+-----------------------------------------------------------------------+\n\n";

        return scoresheet;
    }

    private String getMultiplier(int value) {
        return (properties.getProperty("hit"+value+"Multiplier").equals("1"))? "  ": "x"+properties.getProperty("hit"+value+"Multiplier");
    }

    private void initScoreSheet() {
        StringBuilder temp = new StringBuilder("Radiant Savanna: Solar Lion (YELLOW REALM):\n");
        temp.append("+-----------------------------------------------------------------------+\n");
        temp.append("|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n");
        temp.append("+-----------------------------------------------------------------------+\n");

        temp.append("|  H  |");

        for (int i = 0; i < 11; i++) temp.append("0    |");
            
        temp.append("\n");

        temp.append("|  M  |");
        for(int i = 1; i <= 11; i++) { 
             temp.append(getMultiplier(i)+"   |"); 
        }
        temp.append("\n");

        temp.append("|  R  |");

        for (int i = 0 ; i < 11; i++) {
            String rewardToken = mappedRewardLocations[i];
            if (rewardToken == "") temp.append("     |");
            else temp.append(rewardToken).append("   |");
        }

        temp.append("+-----------------------------------------------------------------------+\n\n");
    }

    @Override
    public boolean checkMove(Dice dice) {
        return (this.scores[10]==0);
    }

    @Override
    public boolean makeMove(Dice dice) throws BonusException {
        int diceValue = dice.getValue();

        if(!checkMove(dice)) return false;

        updateLions(dice);
        updateScore(dice);
        updateDeadLions();

        ArrayList<Integer> TimeWarpArrayList = rewardLocations.get("TimeWarp");
        ArrayList<Integer> ArcaneBoostArrayList = rewardLocations.get("ArcaneBoost");

        if(TimeWarpArrayList!=null)
        for (int i = 0; i < TimeWarpArrayList.size(); i++) {
            if (TimeWarpArrayList.get(i) == deadLions-1){
                this.timeWarps.get(0).setStatus(RewardStates.ACQUIRED);
                this.timeWarps.remove(0);
                break;
            }
        }
        if(ArcaneBoostArrayList!=null)
        for (int i = 0; i < ArcaneBoostArrayList.size(); i++) {
            if (ArcaneBoostArrayList.get(i) == deadLions-1) {
                this.arcaneBoosts.get(0).setStatus(RewardStates.ACQUIRED);
                this.arcaneBoosts.remove(0);
                break;
            }
        }
        
        switch(properties.getProperty("hit" + deadLions + "Reward")){
            case "RedBonus": throw new BonusException(RealmColor.RED);
            case "GreenBonus": throw new BonusException(RealmColor.GREEN);
            case "RedBonus": throw new BonusException(RealmColor.RED);
            case "BlueBonus": throw new BonusException(RealmColor.BLUE);
            case "MagentaBonus": throw new BonusException(RealmColor.MAGENTA);
            case "YellowBonus": throw new BonusException(RealmColor.YELLOW);
            case "EssenceBonus": throw new BonusException(RealmColor.WHITE);
        }
        return true;
    }


    @Override
    public ArrayList<TimeWarp> getAllTimeWarps() {
        return timeWarps;
    }

    @Override
    public ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        return arcaneBoosts;
    }

    @Override
    public ArrayList<Move> getAllPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();

        for(int i = 1; i <= 6; i++) {
            YellowDice dice = new YellowDice(i);
            if(checkMove(dice)){
                Move move = new Move(dice, this);
                moves.add(move);
            }
        }
        return moves;
    }
}
