package game.creatures;

import game.engine.ScoreSheet;

public class Lion extends Creature{

    private int[] lions;
    private int deadLions;
    private int score;
    private String scoresheet;

    public Lion(){
        this.lions=new int[11];
        this.deadLions=0;
        this.score=0;
        initScoreSheet();
    }

    public int[] getLions(){
        return this.lions;
    }
    private void setLions(int[] lions){
        this.lions=lions;
    }
    private void updateLions(Dice dice){
        this.lions[deadLions]=dice.getValue();
    }

    public int getDeadLions(){
        return this.deadLions;
    }
    private void setDeadLions (int deadLions){
        this.deadLions=deadLions;
    }
    private void updateDeadLions(Dice dice){
        setDeadLions(deadLions+1);
    }

    public int getScore(){
        return this.score;
    }
    private void updateScore(Dice dice){
        this.score+=dice.getValue();
    }
        
    public String getScoreSheet(){
        return this.scoresheet;
    }

    private void initScoreSheet(){
        String temp="+-----------------------------------------------------------------------+\n";
        temp+="|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n";
        temp+="+-----------------------------------------------------------------------+\n";
        temp+="|  H  |0    |0    |0    |0    |0    |0    |0    |0    |0    |0    |0    |\n";
        temp+="|  M  |     |     |     |x2   |     |     |x2   |     |x2   |     |x3   |\n";
        temp+="|  R  |     |     |TW   |     |RB   |AB   |     |EC   |     |MB   |     |\n";
        temp+="+-----------------------------------------------------------------------+\n";
        this.scoresheet=temp;
    }



    private void setScoreSheet(String scoreSheet){
        this.scoresheet=scoreSheet;
    }

    private void updateScoreSheet(Dice dice){
        String temp="+-----------------------------------------------------------------------+\n";
        temp+="|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n";
        temp+="+-----------------------------------------------------------------------+\n";
        temp+="|  H  |";
        for(int i=0;i<this.deadLions;i++){
            temp+="0    |";
        }
        int diceValue=dice.getValue();
        temp+=Integer.toString(diceValue)+"    |";
        for(int i=0;i<11-this.deadLions;i++){
            temp+="0    |";
        }
        temp+="\n";
        temp+="|  M  |     |     |     |x2   |     |     |x2   |     |x2   |     |x3   |\n";
        temp+="|  R  |     |     |TW   |     |RB   |AB   |     |EC   |     |MB   |     |\n";
        temp+="+-----------------------------------------------------------------------+\n";
    }

    public void makeMove(Dice dice,Creature Lion){ 
            if(!checkMove(dice,Lion)){
                System.out.print("erm what the sigma");
                return;
            }
            updateScoreSheet(dice);
            updateScore(dice);
            updateDeadLions(dice); //leave this after the updatescoresheet method bc youre changing the deadlions number
        }

    public boolean checkMove(Dice dice,Creature Lion){
        return true;
    }

    
}
