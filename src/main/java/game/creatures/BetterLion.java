package game.creatures;

import java.util.ArrayList;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.engine.Move;
import game.engine.enums.RealmColor;
import game.engine.enums.RewardStates;
import game.exceptions.BonusException;
import game.exceptions.InvalidMoveException;

public class BetterLion extends Creature{
    private final Properties properties;

    private int[] scores;
    private int lionsKilled;

    private int arcaneBoostsUsed;
    private int timeWarpsUsed;

    public BetterLion() {
        this.properties = new Properties();
        try {
            File config = new File("src/main/resources/config/RadiantSavannaRewards.properties");
            FileReader configReader = new FileReader(config);
            this.properties.load(configReader);
        } catch (IOException e) {
            System.out.println("Properties file reading failed.");
            this.properties.setProperty("hit1Reward", null);
            this.properties.setProperty("hit2Reward", null);
            this.properties.setProperty("hit3Reward", "TimeWarp");
            this.properties.setProperty("hit4Reward", null);
            this.properties.setProperty("hit5Reward", "RedBonus");
            this.properties.setProperty("hit6Reward", "ArcaneBoost");
            this.properties.setProperty("hit7Reward", null);
            this.properties.setProperty("hit8Reward", "ElementalCrest");
            this.properties.setProperty("hit9Reward", null);
            this.properties.setProperty("hit10Reward", "MagentaBonus");
            this.properties.setProperty("hit11Reward", null);
        }

        this.scores = new int[11];
        this.score = 0;

        this.arcaneBoosts = new ArrayList<ArcaneBoost>();
        this.arcaneBoostsUsed = 0;
        this.timeWarps = new ArrayList<TimeWarp>();
        this.timeWarpsUsed = 0;
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
        for(int i = 0; i < 11; i++) { scoresheet += scores[i]+"    |"; }

        scoresheet += "\n|  M  |";
        for(int i = 0; i < 11; i++) { scoresheet += getMultiplier(i)+"   |"; }

        scoresheet += "\n|  R  |";
        for(int i = 0; i < 11; i++) { scoresheet += getBonus(i)+"   |"; }

        scoresheet += "\n+-----------------------------------------------------------------------+\n\n";

        return scoresheet;
    }

    private String getMultiplier(int value) {
        return (properties.getProperty("hit"+value+"Multiplier").equals("1"))? "  ": "x"+scores[value];
    }

    private String getBonus(int value) {
        String[] defaultValues = {"  ", "  ", "TW", "  ", "RB", "AB", "  ", "EC", "  ", "MB", "  "};
        String reward = properties.getProperty("hit"+value+"Reward");
        if(reward == null)
            return "  ";
        else if(!(this.scores[value]==0))
            return "X ";
        else{
            switch (reward) {
                case "ArcaneBoost": return "AB";
                case "RedBonus": return "RB";
                case "GreenBonus": return "GB";
                case "BlueBonus": return "BB";
                case "MagentaBonus": return "MB";
                case "YellowBonus": return "YB";
                case "ElementalCrest": return "EC";
                case "TimeWarp": return "TW";
                default: return defaultValues[value];
            }
        }
    }
    @Override
    public boolean checkMove(Dice dice) throws InvalidMoveException {
        return (scores[10]==0);
    }

    @Override
    public boolean makeMove(Dice dice) throws BonusException, InvalidMoveException {
        int diceValue = dice.getValue();

        if(!checkMove(dice)) return false;

        this.scores[lionsKilled] = diceValue;
        this.score += diceValue*Integer.parseInt(properties.getProperty("hit"+lionsKilled+"Multiplier"));
        
        switch(properties.getProperty("hit"+lionsKilled+"Reward")){
            case "ArcaneBoost": this.arcaneBoosts.set(this.arcaneBoostsUsed++, new ArcaneBoost(RewardStates.ACQUIRED)); break;
            case "TimeWarp": this.timeWarps.set(this.timeWarpsUsed++, new TimeWarp(RewardStates.ACQUIRED)); break;
            case "GreenBonus": throw new BonusException(RealmColor.GREEN);
            case "RedBonus": throw new BonusException(RealmColor.RED);
            case "BlueBonus": throw new BonusException(RealmColor.BLUE);
            case "MagentaBonus": throw new BonusException(RealmColor.MAGENTA);
            case "YellowBonus": throw new BonusException(RealmColor.YELLOW);
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPossibleMoves'");
    }

}
