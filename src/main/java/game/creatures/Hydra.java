package game.creatures;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.BlueDice;
import game.dice.Dice;
import game.engine.Move;
import game.engine.enums.RealmColor;
import game.engine.enums.RewardStates;
import game.exceptions.BonusException;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Stack;

public class Hydra extends Creature{
    // Stack that represents the hydra with the heads stored inside of it.
    private Stack<Integer> serpent;

    // Define an array containing the hit reward for each hydra head.
    private final Properties properties;

    // Define an integer indicating the number of heads killed so far, and a boolean indicating whether or not the serpent has regenerated.
    private int headsKilled;
    private boolean regenerateFlag;

    // An array of Strings that will get initialized as "---" that contain the values of the dice that were used to kill each head of the serpent.
    private String[] diceUsed;

    // Define array for the score values and an integer for the current score.
    private int[] scores = {1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66};

    // The number of arcane boost and time warps that have been used.
    private int arcaneBoostsUsed;
    private int timeWarpsUsed;

    // Constructor that initializes the score to 0 , the serpent to the first serpent with 5 heads, and sets up the properties.
    public Hydra() {
        this.serpent = new Stack<Integer>();
        this.serpent.push(5); this.serpent.push(4); this.serpent.push(3); this.serpent.push(2); this.serpent.push(1); 
        
        this.properties = new Properties();
        try {
            File config = new File("src/main/resources/config/TideAbyssRewards.properties");
            FileReader configReader = new FileReader(config);
            this.properties.load(configReader);
        } catch (IOException e) {
            System.out.println("Properties file reading failed.");
            this.properties.setProperty("hit1Reward", "null");
            this.properties.setProperty("hit2Reward", "null");
            this.properties.setProperty("hit3Reward", "null");
            this.properties.setProperty("hit4Reward", "ArcaneBoost");
            this.properties.setProperty("hit5Reward", "null");
            this.properties.setProperty("hit6Reward", "GreenBonus");
            this.properties.setProperty("hit7Reward", "ElementalCrest");
            this.properties.setProperty("hit8Reward", "null");
            this.properties.setProperty("hit9Reward", "MagentaBonus");
            this.properties.setProperty("hit10Reward", "TimeWarp");
            this.properties.setProperty("hit11Reward", "null");
        }
        
        this.score = 0;
        this.regenerateFlag = false;

        this.arcaneBoosts = new ArrayList<ArcaneBoost>();
        this.arcaneBoostsUsed = 0;
        this.timeWarps = new ArrayList<TimeWarp>();
        this.timeWarpsUsed = 0;

        this.headsKilled = 0;
        this.diceUsed = new String[11];
        for(int i = 1; i <= 11; i++) {
            String[] defaultValues = {"null","null","null","ArcaneBoost","null","GreenBonus","ElementalCrest","null","MagentaBonus","TimeWarp","null"};
            if(properties.getProperty("hit"+i+"Reward")==null) properties.setProperty("hit"+i+"Reward", defaultValues[i-1]);
        }
        for(int i = 0; i < 11; i++) {
            this.diceUsed[i] = "---";

            if(properties.getProperty("hit"+(i+1)+"Reward").equals("ArcaneBoost")){
                ArcaneBoost ac = new ArcaneBoost(RewardStates.UNACQUIRED);
                this.arcaneBoosts.add(ac);
            }

            if(properties.getProperty("hit"+(i+1)+"Reward").equals("TimeWarp")) {
                TimeWarp tw = new TimeWarp(RewardStates.UNACQUIRED);
                this.timeWarps.add(tw);
            }
        }
    }

    // Method that returns the value of the bonus that should be printed in the scoresheet.
    private String getBonus(int value) {
        String[] defaultValues = {"  ", "  ", "  ", "AB", "  ", "GB", "EC", "  ", "MB", "TW", "  "};
        String reward = properties.getProperty("hit"+value+"Reward");
        if(reward.equals("null"))
            return "  ";
        else if(!this.diceUsed[--value].equals("---"))
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

    // Setter for the "score" variable.
    public void updateScore() {
        this.score = this.scores[this.headsKilled];
    }

    // Method that checks which serpent head gives you an elemental crest and returns 1 if this head is dead and 0 otherwise.
    @Override
    public int getElementalCrest() {
        int elementalCrestCount = 0;
        boolean isRewardOnSecondHead = false;
        for(int i = 1; i < this.properties.size(); i++){
            if(properties.getProperty("hit"+i+"Reward").equals("ElementalCrest")) {
                elementalCrestCount = (i==5)? 5: i%5;
                isRewardOnSecondHead = (i>5);
            }
        }

        if(this.serpent.peek() > elementalCrestCount && isRewardOnSecondHead == this.regenerateFlag) 
            return 1;
        else   
            return 0;
    }

    // Method that returns the part of the scoresheet that is relevant to the Blue Realm.
    @Override
    public String getScoreSheet() {
        String scoreSheet = "Tide Abyss: Hydra Serpents (BLUE REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |H11  |H12  |H13  |H14  |H15  |H21  |H22  |H23  |H24  |H25  |H26  |\n" +
                "+-----------------------------------------------------------------------+\n";
                
        scoreSheet += "|  H  |" +this.diceUsed[0]+ "  |" +this.diceUsed[1]+ "  |" +this.diceUsed[2]+ "  |" +this.diceUsed[3]+ "  |" +this.diceUsed[4]+ 
        "  |" +this.diceUsed[5]+ "  |" +this.diceUsed[6]+ "  |" +this.diceUsed[7]+ "  |" +this.diceUsed[8]+ "  |" +this.diceUsed[9]+ "  |" +this.diceUsed[10]+ "  |\n";
        
        scoreSheet += "|  C  |≥1   |≥2   |≥3   |≥4   |≥5   |≥1   |≥2   |≥3   |≥4   |≥5   |≥6   |\n";

        scoreSheet += "|  R  |" +getBonus(1)+ "   |" +getBonus(2)+ "   |" +getBonus(3)+ "   |" +getBonus(4)+ "   |" +getBonus(5)+ 
        "   |" +getBonus(6)+ "   |" +getBonus(7)+ "   |" +getBonus(8)+ "   |" +getBonus(9)+ "   |" +getBonus(10)+ "   |" +getBonus(11)+ "   |\n"; 

        scoreSheet += "+-----------------------------------------------------------------------+\n" +
                      "|  S  |1    |3    |6    |10   |15   |21   |28   |36   |45   |55   |66   |\n" +
                      "+-----------------------------------------------------------------------+\n\n";
        return scoreSheet;
    }

    // Method that checks if the move is possible.
    @Override
    public boolean checkMove(Dice dice) {
        return dice.getValue() >= this.serpent.peek();
    }

    // Method that attacks the top hydra head of possible, and updates the variables of class to match that.
    @Override
    public boolean makeMove(Dice dice) throws BonusException{
        int diceValue = dice.getValue();

        if(this.serpent.isEmpty() || !checkMove(dice)) {
            return false;
        }

        this.serpent.pop();
        updateScore();
        this.diceUsed[this.headsKilled++] = diceValue + "  ";
        

        if(serpent.isEmpty() && !regenerateFlag) 
            regenerateSerpent();

        switch(properties.getProperty("hit"+headsKilled+"Reward")){
            case "ArcaneBoost": this.arcaneBoosts.get(arcaneBoostsUsed++).setStatus(RewardStates.ACQUIRED); break;
            case "TimeWarp": this.arcaneBoosts.get(arcaneBoostsUsed++).setStatus(RewardStates.ACQUIRED); break;
            case "GreenBonus": throw new BonusException(RealmColor.GREEN);
            case "RedBonus": throw new BonusException(RealmColor.RED);
            case "BlueBonus": throw new BonusException(RealmColor.BLUE);
            case "MagentaBonus": throw new BonusException(RealmColor.MAGENTA);
            case "YellowBonus": throw new BonusException(RealmColor.YELLOW);
            case "EssenceBonus": throw new BonusException(RealmColor.WHITE);
        }
        return true;
    }

    // Method that adds 6 new heads onto the serpent to "regenerate" it, should be called after the 5 heads of the first serpent all die.
    private void regenerateSerpent() {
        // This is just in case this method gets called when the serpent still has heads, in theory this block should never activate.
        while(!this.serpent.isEmpty()) {
            this.serpent.pop();
        }

        this.serpent.push(6); this.serpent.push(5); this.serpent.push(4); this.serpent.push(3); this.serpent.push(2); this.serpent.push(1); 
        this.regenerateFlag = true;
    }

    // Getter for the timeWarps ArrayList.
    @Override
    public ArrayList<TimeWarp> getAllTimeWarps() {
        return this.timeWarps;
    }
    
    // Getter for the arcaneBoosts ArrayList.
    @Override
    public ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        return this.arcaneBoosts;
    }

    // Method that return an ArrayList containing all of the moves that the player can currently do.
    @Override
    public ArrayList<Move> getAllPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        for(int i = 1; i <= 6; i++) {
            BlueDice dice = new BlueDice(i);
            if(checkMove(dice)){
                Move move = new Move(dice, this);
                moves.add(move);
            }
        }
        return moves;
    }
    public static void main(String[] args) {
        Hydra hydra = new Hydra();
        BlueDice dice = new BlueDice(6);
        ArrayList<ArcaneBoost> acs = hydra.getAllArcaneBoosts();
        for(int i = 0; i < 4; i++) {
            try{
                System.out.println(hydra.makeMove(dice));
            }
            catch(BonusException e) {
                System.out.println();
            }
        }
        for(int i = 0; i < acs.size(); i++) {
            ArcaneBoost ac = acs.get(i);
            System.out.println(ac.getStatus());
        }
        System.out.println(hydra.getScore());
    }
}
