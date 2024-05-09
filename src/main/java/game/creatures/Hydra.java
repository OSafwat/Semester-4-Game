package game.creatures;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.engine.Move;
import game.exceptions.BonusException;
import game.exceptions.BonusTwoException;
import game.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Stack;
import java.util.Scanner;

public class Hydra extends Creature{
    // Create two stacks representing the two serpents, and stack that points to the current active serpent.
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

    // Constructor that initializes the score to 0 , the serpent to the first serpent with 5 heads, and sets up the properties.
    public Hydra() throws IOException {
        serpent.push(5); serpent.push(4); serpent.push(3); serpent.push(2); serpent.push(1); 

        properties = new Properties();
        File config = new File("src/main/resources/config/TideAbyssRewards.properties");
        FileReader configReader = new FileReader(config);
        properties.load(configReader);

        this.score = 0;
        this.regenerateFlag = false;

        headsKilled = 0;
        this.diceUsed = new String[11];
        for(int i = 0; i < 11; i++)
            diceUsed[i] = "---";
    }

    // Method that returns the value of the bonus that should be printed in the scoresheet.
    private String getBonus(int value) {
        String[] defaultProperties = {"  ", "  ", "  ", "AB", "  ", "GB", "EC", "  ", "MB", "TW", "  "};
        String reward = properties.getProperty("hit"+value+"Reward");
        if(reward.equals("null"))
            return "  ";
        else if(Integer.parseInt(diceUsed[value])!=0)
            return "X ";
        else{
            if(reward.equals("ArcaneBoost")) return "AB";
            else if(reward.equals("GreenBonus")) return "GB";
            else if(reward.equals("ElementalCrest")) return "EC";
            else if(reward.equals("MagentaBonus")) return "MB";
            else if(reward.equals("TimeWarp")) return "TW";
            else return defaultProperties[value];
        }
    }

    // Setter for the "score" variable.
    public void updateScore(int score) {
        this.score += score;
    }

    // Method that returns 1 if if the second head of the regenerated serpent is killed.
    @Override
    public int getElementalCrest() {
        int elementalCrestCount = 0;
        for(int i = 0; i < properties.size(); i++){
            if(properties.getProperty("hit"+i+"Reward") == "ElementalCrest") 
                elementalCrestCount = i;
        }

        if(this.serpent.peek() > elementalCrestCount && this.regenerateFlag == true) 
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
                
        scoreSheet += "|  H  |" +diceUsed[0]+ " |" +diceUsed[1]+ " |" +diceUsed[2]+ " |" +diceUsed[3]+ " |" +diceUsed[4]+ 
        " |" +diceUsed[5]+ " |" +diceUsed[6]+ " |" +diceUsed[7]+ " |" +diceUsed[8]+ " |" +diceUsed[9]+ " |" +diceUsed[10]+ " |\n";
        
        scoreSheet += "|  C  |≥1   |≥2   |≥3   |≥4   |≥5   |≥1   |≥2   |≥3   |≥4   |≥5   |≥6   |\n";

        scoreSheet += "|  R  |" +getBonus(0)+ "  |" +getBonus(1)+ "  |" +getBonus(2)+ "  |" +getBonus(3)+ "  |" +getBonus(4)+ 
        "  |" +getBonus(5)+ "  |" +getBonus(6)+ "  |" +getBonus(7)+ "  |" +getBonus(8)+ "  |" +getBonus(9)+ "  |" +getBonus(10)+ "  |\n"; 

        scoreSheet += "+-----------------------------------------------------------------------+\n" +
                      "|  S  |1    |3    |6    |10   |15   |21   |28   |36   |45   |55   |66   |\n" +
                      "+-----------------------------------------------------------------------+\n";
        return scoreSheet;
    }

    // Method that returns true if the move is possible and throws an exception if the move on the dice isn't possible.
    @Override
    public boolean checkMove(Dice dice) {
        return dice.getValue() >= serpent.peek();
    }

    @Override
    public boolean makeMove(Dice dice) throws BonusException{
        int diceValue = dice.getValue();

        if(!checkMove(dice) || serpent.isEmpty()) {
            return false;
        }

        serpent.pop();
        diceUsed[headsKilled++] = "" + diceValue;

        if(serpent.isEmpty()) {
            regenerateSerpent();
        }
        return true;
    }

    // Method that adds 6 new heads onto the serpent to "regenerate" it, should be called after the 5 heads of the first serpent all die.
    private void regenerateSerpent() {
        // This is just in case this method gets called when the serpent still has heads, in theory this block should never activate.
        while(!serpent.isEmpty()) {
            serpent.pop();
        }

        serpent.push(6); serpent.push(5); serpent.push(4); serpent.push(3); serpent.push(2); serpent.push(1); 
        regenerateFlag = true;
    }

    @Override
    public ArrayList<TimeWarp> getAllTimeWarps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTimeWarps'");
    }

    @Override
    public ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllArcaneBoosts'");
    }

    @Override
    public ArrayList<Move> getAllPossibleMoves() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPossibleMoves'");
    }
    
}
