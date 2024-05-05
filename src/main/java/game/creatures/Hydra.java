package game.creatures;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.Properties;
import java.util.Scanner;

public class Hydra extends Creature{
    // Create two stacks representing the two serpents, and stack that points to the current active serpent.
    private Stack<Integer>() FirstSerpent = new Stack<Integer>();
    FirstSerpent.push(5); FirstSerpent.push(4); FirstSerpent.push(3); FirstSerpent.push(2); FirstSerpent.push(1); 
    private Stack<Integer>() SecondSerpent = new Stack<Integer>();
    SecondSerpent.push(6); SecondSerpent.push(5); SecondSerpent.push(4); SecondSerpent.push(3); SecondSerpent.push(2); SecondSerpent.push(1); 
    private Stack<Integer>() CurrentSerpent;

    // Define an array containing the hit reward for each hydra head.
    private final Properties properties;

    // Define an integer indicating the number of heads killed so far, and a boolean indicating whether or not the serpent has regenerated.
    private int headsKilled;
    private boolean regenerateFlag;

    // An array of Strings that will get initialized as "---" that contain the values of the dice that were used to kill each head of the serpent.
    private String[] diceUsed;

    // Define array for the score values and an integer for the current score.
    private int[] scores = {1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66};
    private int score;

    // Constructor that initializes the score to 0 , the serpent to the first serpent with 5 heads, and sets up the properties.
    public Hydra() throws IOException {
        properties = new Properties();
        File config = new File("src/main/resources/config/TideAbyssRewards.properties");
        FileReader configReader = new FileReader(config);
        properties.load(configReader);

        this.score = 0;
        this.CurrentSerpent = this.FirstSerpent;
        this.regenerateFlag = false;

        this.diceUsed = {1,2,3,4,5,1,2,3,4,5,6};
        for(int i = 0; i < 11; i++)
            diceUsed[i] = "---";
    }

    // Method that returns the value of the bonus that should be printed in the scoresheet.
    private String getBonus(int value) {
        String reward = properties.getProperty("hit"+value+"Reward");
        if(reward.equals("null"))
            return "  ";
        else if(diceUsed[value]!=0)
            return "X ";
        else{
            if(reward.equals("ArcaneBoost")) return "AB";
            else if(reward.equals("GreenBonus")) return "GB";
            else if(reward.equals("ElementalCrest")) return "EC";
            else if(reward.equals("MagentaBonus")) return "MB";
            else if(reward.equals("TimeWarp")) return "TW";
            // Add a case for the else later.
        }
    }

    // Getter for the "score" variable.
    public int getScore() {
        return this.score;
    }

    // Setter for the "score" variable.
    public void updateScore(int score) {
        this.score += score;
    }

    // Method that returns 1 if if the second head of the regenerated serpent is killed.
    public int getElementalCrest() {
        int elementalCrestCount = 0;
        for(int i = 0; i < properties.size(); i++){
            if(properties.getProperty("hit"+i+"Reward") == "ElementalCrest") 
                elementalCrestCount = i
        }

        if((int) this.CurrentSerpent.peek() > elementalCrestCount && this.regenerateFlag == true) 
            return 1;
        else   
            return 0;
    }


    // Method that returns the part of the scoresheet that is relevant to the Blue Realm.
    public String getScoreSheet() {
        String scoreSheet = "Tide Abyss: Hydra Serpents (BLUE REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |H11  |H12  |H13  |H14  |H15  |H21  |H22  |H23  |H24  |H25  |H26  |\n" +
                "+-----------------------------------------------------------------------+\n";
                
        scoreSheet += "|  H  |" +diceUsed[0]+ " |" +diceUsed[1]+ " |" +diceUsed[2]+ " |" +diceUsed[3]+ " |" +diceUsed[4]+ 
        " |" +diceUsed[5]+ " |" +diceUsed[6]+ " |" +diceUsed[7]+ " |" +diceUsed[8]+ " |" +diceUsed[9]+ " |" +diceUsed[10]+ " |\n" 
        
        scoreSheet += "|  C  |≥1   |≥2   |≥3   |≥4   |≥5   |≥1   |≥2   |≥3   |≥4   |≥5   |≥6   |\n";

        scoreSheet += "|  R  |" +getBonus(0)+ "  |" +getBonus(1)+ "  |" +getBonus(2)+ "  |" +getBonus(3)+ "  |" +getBonus(4)+ 
        "  |" +getBonus(5)+ "  |" +getBonus(6)+ "  |" +getBonus(7)+ "  |" +getBonus(8)+ "  |" +getBonus(9)+ "  |" +getBonus(10)+ "  |\n"; 

        scoreSheet += "+-----------------------------------------------------------------------+\n
                       |  S  |1    |3    |6    |10   |15   |21   |28   |36   |45   |55   |66   |\n
                       +-----------------------------------------------------------------------+\n";
        return scoreSheet;
    }

    // Method that returns true if the move is possible.
    public boolean checkMove(Dice dice) {
        return dice.getValue() >= (int) CurrentSerpent.peek();
    }
}
