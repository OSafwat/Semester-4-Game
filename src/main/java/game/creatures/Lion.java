package game.creatures;

import game.engine.ScoreSheet;

public class Lion extends Creature{
    /* issues:-
     * - not implementing anything related to bonuses
     * - not checking for any exceptions
     */

    private int[] lions;
    private int deadLions;
    private int score;
    private String scoresheet;
    private int elementalCrest;
    private int arcaneBoost;
    private int timeWarp;

    public Lion(){
        this.lions=new int[11];
        this.deadLions=0;
        this.score=0;
        this.elementalCrest=0;
        this.arcaneBoost=0;
        this.timeWarp=0;
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
    private int calculateScore(Dice dice){
        int value=dice.getValue();
        int ans=0;
        if(deadLions+1==4||deadLions+1==6||deadLions+1==9){
            ans=value*2;
        }
        else if(deadLions+1==11){
            ans=value*3;
        }
        else{
        ans=value;
            }
            return ans;
    }
    private void updateScore(Dice dice){
        this.score=calculateScore(dice);
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
        int diceValue=calculateScore(dice);
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
            updateDeadLions(dice); //leave this after the updatescoresheet method bc youre changing the deadlions number here
            updateElementalCrest();
            updateArcaneBoost();
            updateTimeWarp();
        }

    public boolean checkMove(Dice dice,Creature Lion){
        return true;
    }

    public int getElementalCrest(){
        return this.elementalCrest;
    }
    private void setElementalCrest(int elementalCrest){
        this.elementalCrest=elementalCrest;
    }
    private void updateElementalCrest(){
        if(deadLions==8){
            elementalCrest=1;
        }
    }
    
    public int getArcaneBoost(){
        return this.arcaneBoost;
    }
    private void setArcaneBoost(int arcaneBoost){
        this.arcaneBoost=arcaneBoost;
    }
    private void updateArcaneBoost(){
        if(deadLions==6){
            arcaneBoost=1;
        }
    }

    public int getTimeWarp(){
        return this.timeWarp;
    }
    private void setTimeWarp(){
        this.timeWarp=timeWarp;
    }
    private void updateTimeWarp(){
        if(deadLions==3){
            timeWarp=1;
        }
    }

}
