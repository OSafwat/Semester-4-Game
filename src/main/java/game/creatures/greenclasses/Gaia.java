package game.creatures.greenclasses;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.creatures.Creature;
import game.dice.Dice;
import game.dice.GreenDice;
import game.engine.Move;
import game.engine.enums.RealmColor;
import game.engine.enums.RewardStates;
import game.exceptions.BonusException;
import game.exceptions.InvalidMoveException;


import java.io.IOException;

//Key:
//IMP = important to change
// COMPLETE = should be completed later
//EXP =explanation
//ASUM = assumption till the leader finish


public class Gaia extends Creature{

    private Guardians [][]  gaiaGuardians;
    private int alliveGuardians;
    private int deadGuardians;
   // private int score;
    private int [] scores ={1,2,4,7,11,16,22,29,37,46,56};
    private boolean [] row={false,false,false};
    private boolean [] col = {false,false,false,false};
    private String [] colreward;
    private String [] rowreward;
    private String [] defaultcolreward ={"TimeWarp","BlueBonus","MagentaBonus","ArcaneBoost","GreenBonus"};
    private String [] defaultrowreward={"YellowBonus","RedBonus","ElementalCrest"};
    //private ArrayList<TimeWarp> timeWarps ;
    //private ArrayList<ArcaneBoost> arcaneBoosts;
    private int elementalCrestCount;

    public Gaia(){
        gaiaGuardians = new Guardians[3][4];
        alliveGuardians = 11;
        deadGuardians=0;
        elementalCrestCount = 0;
        timeWarps= new ArrayList<>();
        arcaneBoosts= new ArrayList<>();
        colreward = new String[4];
        rowreward = new String[3];
       

            
            for(  int i=0;i<4;i++){
                    try {
            String filePath = "src/main/resources/config/TerrasHeartlandRewards.properties";
           Properties prop ;
           String colReward;
           prop = new Properties();
           FileInputStream ip = new FileInputStream(filePath);
           prop.load(ip);
           int real = i+1;
           String whichReward = "column"+real+"Reward";
           colReward = prop.getProperty(whichReward);
           boolean flag = false;
           for(int j =0;j<defaultcolreward.length;j++){
            if(colReward.equals(defaultcolreward[j])){
                flag = true;
                break;
            }
           }
           for(int j =0;j<defaultrowreward.length;j++){
            if(colReward.equals(defaultrowreward[j])){
                flag = true;
                break;
            }
           }
           
           
           if(flag)
           this.colreward[i] = colReward;
           else
           this.colreward[i] = this.defaultcolreward[i];

             }

           catch(IOException e){
            this.colreward[i] = this.defaultcolreward[i];
        }
            }

            for(  int i=0;i<3;i++){
                try {
        String filePath = "src/main/resources/config/TerrasHeartlandRewards.properties";
       Properties prop ;
       String rowReward;
       prop = new Properties();
       FileInputStream ip = new FileInputStream(filePath);
       prop.load(ip);
       int real = i+1;
       String whichReward = "row"+real+"Reward";
       rowReward = prop.getProperty(whichReward);
       boolean flag = false;
       for(int j =0;j<defaultcolreward.length;j++){
        if(rowReward.equals(defaultcolreward[j])){
            flag = true;
            break;
        }
       }
       for(int j =0;j<defaultrowreward.length;j++){
        if(rowReward.equals(defaultrowreward[j])){
            flag = true;
            break;
        }
       }
       
       
       if(flag)
       this.rowreward[i] = rowReward;
       else
       this.rowreward[i] = this.defaultrowreward[i];
       
                }

       catch(IOException e){
        this.rowreward[i] = this.defaultrowreward[i];
    }
        }


        
         
         
        
         
       

        int c =1;
        for(int i=0;i<gaiaGuardians.length;i++){
            for(int j=0;j<gaiaGuardians[i].length;j++){
                gaiaGuardians[i][j]= new Guardians(c);
                c++;

            }

        }
        gaiaGuardians[0][0].kill();
         
        for(int i=0;i<3;i++){
            if(this.whichCollectableRow(i).equals("TimeWarp"))
            timeWarps.add(new TimeWarp(RewardStates.UNACQUIRED));
        }
        //ASUM TimWarp class is done
         // IMP create as not accuired
         // make it unaqquired
         // ASUM waiting for set and get to be made in TimeWarp class
        for(int i=0;i<4;i++){
            if(this.whichCollectableCol(i).equals("TimeWarp"))
            timeWarps.add(new TimeWarp(RewardStates.UNACQUIRED));
        }
        
        
        for(int i=0;i<3;i++){
            if(this.whichCollectableRow(i).equals("ArcaneBoost"))
            arcaneBoosts.add(new ArcaneBoost(RewardStates.UNACQUIRED));
        }
        //ASUM ArcaneBoost class is done
         // IMP create as not accuired
         //make it unaquired
         // ASUM waiting for set and get to be made in arcaneboost class
            
        for(int i=0;i<4;i++){
            if(this.whichCollectableCol(i).equals("ArcaneBoost"))
            arcaneBoosts.add(new ArcaneBoost(RewardStates.UNACQUIRED));
        }

    }
    public int getScore(){
        return this.score;
    }


 

    // EXP method to update the score of the realm
    private void updateScore(){
        int dead = this.getDeadGuardians()-1;
        score= scores[dead];

    }



    // EXP checks if a given move is possible
    public boolean checkMove(Dice dice)throws InvalidMoveException{
        if(!(dice instanceof GreenDice))
        throw new InvalidMoveException();
        GreenDice greendie = (GreenDice) dice;
        // ASUM assuming getRealValue done in the dice class add white
        int greenValue = greendie.getValue();
        Guardians speceficGuardian = this.getGuardians(greenValue);
        if(speceficGuardian.isDead())
            return false;
        else
        return true;

    }


    private boolean checkMove1(Dice dice){
        GreenDice greendie = (GreenDice) dice;
        // ASUM assuming getRealValue done in the dice class add white
        int greenValue = greendie.getValue();
        Guardians speceficGuardian = this.getGuardians(greenValue);
        if(speceficGuardian.isDead())
            return false;
        else
        return true;

    }



// EXP gets a specific guardian in the Gaia
private Guardians getGuardians(int c){
        if(c<2 || c>12)
        return null;
        //int index =1;
        for(int i=0;i<gaiaGuardians.length;i++){
            for(int j=0;j<gaiaGuardians[i].length;j++){   
                if(c== gaiaGuardians[i][j].getGuardianValue())
                return gaiaGuardians[i][j];  
                //index++;
            }
    }
    return null;


}

// EXP gets a specific guardian row position in the Gaia
private int getGuardiansRow(int c){

    if(c<2 || c>12)//here
    return 0;
    for(int i=0;i<gaiaGuardians.length;i++){
        for(int j=0;j<gaiaGuardians[i].length;j++){
            if(c== gaiaGuardians[i][j].getGuardianValue())
            return i; 
        }
        

}
    return 0;


}


// EXP gets a specific guardian col position in the Gaia
private int getGuardiansCol(int c){

    if(c<2 || c>12)
    return 0;
    for(int i=0;i<gaiaGuardians.length;i++){
        for(int j=0;j<gaiaGuardians[i].length;j++){
            if(c== gaiaGuardians[i][j].getGuardianValue())
            return j; 
        }
        

}
    return 0;


}


//EXP  kills a a given guardian if not already killed
private void killGaiaGuardian(Guardians g){
    if(g.isDead())
    System.out.println("Invalid Allready Killed");
    else{
        g.kill();
    }
}



 
private int getDeadGuardians(){
    return deadGuardians;
}



    // EXP checks if all guardians in a given col are dead if yes then true
private boolean checkCol(int c){
    return col[c];

}

// EXP checks if all guardians in a given row are dead if yes then true
private boolean checkRow(int r){
    return row[r];

}


// EXP check if a row is already killed and update the  instance array col accordingly
private  void updateCol(int c){
    for(int i=0;i<3;i++){
        if(!gaiaGuardians[i][c].isDead())
            return;               
    }
    col[c]= true;
         
    
}

// EXP check if a row is already killed and update the  instance array row accordingly
private  void updateRow(int r){
    for(int i=0;i<4;i++){
        if(!gaiaGuardians[r][i].isDead())
            return;               
    }
    row[r]= true;
}




 // EXP gives the respective bonus for each col
    // IMP this will be changed when collectables classes are done
    // ASUM here I wrote stings but when the leader finish the classes this will be void and replace strings with method.
    private String whichCollectableCol (int c){
        return colreward[c];
 
      
    }

      // EXP gives the respective bonus for each row
    // IMP this will be changed when collectables classes are done
      private String whichCollectableRow(int r) {
        return rowreward[r];
        
}



// EXP executing a given move
     public boolean makeMove(Dice dice) throws BonusException,InvalidMoveException   {
        if(!(dice instanceof GreenDice))
        throw new InvalidMoveException();
       else  if(!checkMove1(dice))
            return false;
        else{
            alliveGuardians--;
            deadGuardians++;
            GreenDice greendie = (GreenDice) dice;
            
            // ASUM assuming getValue done in the dice class
            int greenValue = greendie.getValue();
            Guardians speceficGuardian = this.getGuardians(greenValue);
            this.killGaiaGuardian(speceficGuardian);
            score=scores[deadGuardians-1];
            int colToCheck = this.getGuardiansCol(greenValue);
            int rowToCheck = this.getGuardiansRow(greenValue);
            updateCol(colToCheck);
            updateRow(rowToCheck);
            if(!checkRow(rowToCheck)  && !checkCol(colToCheck))
            return true;
            else if(checkRow(rowToCheck)  && !checkCol(colToCheck)){
                String act = whichCollectableRow(rowToCheck);
                // ADD THE CODE OF THE BONUS OR POWER RESPECTIVELY
                // IMP this will be changed when collectables classes are done
                // I will need to change in the whichCollectableRow(rowToCheck)
               
                if(!this.applyNotBonusCollectable(act)) {
                    RealmColor  realm= this.getCorrectRealm(act);
                    throw new BonusException(realm);
                }

                
                return true;
            }
            else if(!checkRow(rowToCheck)  && checkCol(colToCheck)){
                    String act = whichCollectableCol(colToCheck);
                // ADD THE CODE OF THE BONUS OR POWER RESPECTIVELY
                // IMP this will be changed when collectables classes are done
                // I will only need to change  in the whichCollectableCol(colToCheck);
                if(!this.applyNotBonusCollectable(act)) {
                    RealmColor  realm= this.getCorrectRealm(act);
                    throw new BonusException(realm);
                }
                return true;
            }
            // EXP made to handle collisions
            else{
                String act1 = whichCollectableCol(colToCheck);
                String act2 = whichCollectableRow(rowToCheck);
                int act1Prtority = this.getPriorityValue(act1);
                int act2Prtority=this.getPriorityValue(act2);
                if(act1Prtority==1 && act2Prtority==1){
                    this.applyNotBonusCollectable(act1);
                    this.applyNotBonusCollectable(act2);
                }
                else if(act1Prtority>1 && act2Prtority==1){
                    this.applyNotBonusCollectable(act2);
                    RealmColor  realm= this.getCorrectRealm(act1);
                    throw new BonusException(realm);

                }
                else if(act1Prtority==1 && act2Prtority>1){
                    this.applyNotBonusCollectable(act1);
                    RealmColor  realm= this.getCorrectRealm(act2);
                    throw new BonusException(realm);

                }
                else if(act1Prtority>act2Prtority){
                    RealmColor realm1= this.getCorrectRealm(act1);
                    RealmColor realm2 = this.getCorrectRealm(act2);
                    throw new BonusException(realm1, realm2);
                }
                else if(act1Prtority<act2Prtority){
                    RealmColor realm1= this.getCorrectRealm(act2);
                    RealmColor realm2 = this.getCorrectRealm(act1);
                    throw new BonusException(realm1, realm2);
                }     
                return true;
            }




            

        
        }



    }

// EXP method to get all possible moves
public ArrayList<Move> getAllPossibleMoves() {

    ArrayList<Move> allMoves = new ArrayList<Move>();
    for(int i=2;i<13;i++){
        GreenDice greenDice = new GreenDice(i);
        if(checkMove1(greenDice)){
        // ASUM assuming move constructor is done
        allMoves.add( new Move(greenDice,this));
        }

    }
    return allMoves;
}



// EXP method to print the  Green creature
public String getScoreSheet(){
    String returnValue = "Terra's Heartland: Gaia Guardians (GREEN REALM):\n" +
    "+-----------------------------------+\n" +
    "|  #  |1    |2    |3    |4    |R    |\n" +
    "+-----------------------------------+\n" +
    "|  1  |X    " ;
    Guardians G2 = this.getGuardians(2);
    if(G2.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|2    ";
    Guardians G3 = this.getGuardians(3);
    if(G3.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|3    ";
    Guardians G4 = this.getGuardians(4);
    if(G4.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|4    ";
    if(checkRow(0))
    returnValue = returnValue +"|X    |\n"+"|  2  ";
    else{
        String s = this.whichCollectableRow(0);
        String f = this.getCorrectBonusInScore(s);
    returnValue = returnValue +"|"+f+"   |\n"+"|  2  ";
    }
    Guardians G5 = this.getGuardians(5);
    if(G5.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|5    ";
    Guardians G6 = this.getGuardians(6);
    if(G6.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|6    ";
    Guardians G7 = this.getGuardians(7);
    if(G7.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|7    ";
    Guardians G8 = this.getGuardians(8);
    if(G8.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|8    ";
    if(checkRow(1))
    returnValue = returnValue +"|X    |\n"+"|  3  ";
    else{
        String s = this.whichCollectableRow(1);
        String f = this.getCorrectBonusInScore(s);
    returnValue = returnValue +"|"+f+"   |\n"+"|  3  ";
    }
  
    Guardians G9 = this.getGuardians(9);
    if(G9.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|9    ";
    Guardians G10 = this.getGuardians(10);
    if(G10.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|10   ";
    Guardians G11 = this.getGuardians(11);
    if(G11.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|11   ";
    Guardians G12 = this.getGuardians(12);
    if(G12.isDead())
    returnValue = returnValue +"|X    ";
    else
    returnValue = returnValue +"|12   ";
    if(checkRow(2))
    returnValue = returnValue +"|X    |\n";
    else{
        String s = this.whichCollectableRow(2);
        String f = this.getCorrectBonusInScore(s);
    returnValue = returnValue +"|"+f+"   |\n";
    }
    returnValue=returnValue+"+-----------------------------------+\n"+"|  R  ";
    if(checkCol(0))
    returnValue = returnValue +"|X    ";
    else{
        String s = this.whichCollectableCol(0);
        String f = this.getCorrectBonusInScore(s);
    returnValue = returnValue +"|"+f+"   ";
    }
    if(checkCol(1))
    returnValue = returnValue +"|X    ";
    else{
        String s = this.whichCollectableCol(1);
        String f = this.getCorrectBonusInScore(s);
    returnValue = returnValue +"|"+f+"   ";
    }
    if(checkCol(2))
    returnValue = returnValue +"|X    ";
    else{
        String s = this.whichCollectableCol(2);
        String f = this.getCorrectBonusInScore(s);
    returnValue = returnValue +"|"+f+"   ";
    }
    if(checkCol(3))
    returnValue = returnValue +"|X    " +"|     |\n";
    else{
        String s = this.whichCollectableCol(3);
        String f = this.getCorrectBonusInScore(s);
        returnValue =returnValue+"|"+f+"   "+ "|     |\n";
    }
    returnValue =returnValue+ "+-----------------------------------------------------------------------+\n";
    returnValue =returnValue+"|  S  |1    |2    |4    |7    |11   |16   |22   |29   |37   |46   |56   |\n";
    returnValue =returnValue +"+-----------------------------------------------------------------------+\n\n";
    return returnValue;

}

  //return number of elemental crests for each realm 
  public  int getElementalCrest(){
   return elementalCrestCount;
  }

// EXP return all aquired time warp in Gaia
public  ArrayList<TimeWarp> getAllTimeWarps(){
    return this.timeWarps;
}
// EXP return all aquired  arcane boost in Gaia
public  ArrayList<ArcaneBoost> getAllArcaneBoosts(){
    return this.arcaneBoosts;
}

// EXP get correct realm where bonus should be applied
private RealmColor getCorrectRealm(String s){
    switch (s) {
        case "RedBonus": return RealmColor.RED;
        case "GreenBonus": return RealmColor.GREEN;
        case "BlueBonus": return RealmColor.BLUE;
        case "MagentaBonus": return RealmColor.MAGENTA;
        case "YellowBonus":return RealmColor.YELLOW;
        default :return null;
    }
}

// EXP return the priority of a given bonus or boost
private int getPriorityValue(String s){
    switch (s) {
        case "RedBonus": return 6;
        case "GreenBonus": return 5;
        case "BlueBonus": return 4;
        case "MagentaBonus": return 3;
        case "YellowBonus":return 2;
        default: return 1;
            
    }

}


//EXP used in the Bonus class
private String getCorrectBonusInScore(String s){
    switch (s) {
        case "RedBonus": return "RB";
        case "GreenBonus": return "GB";
        case "BlueBonus": return "BB";
        case "MagentaBonus": return "MB";
        case "YellowBonus":return "YB";
        case "TimeWarp" : return"TW";
        case "ArcaneBoost" : return"AB";
        case "ElementalCrest": return"EC";
        default: return "";
            
    }


}
//EXP apply powers 
private boolean applyNotBonusCollectable(String s){
    if(s.equals("TimeWarp")){
        Iterator it = timeWarps.iterator();
        while(it.hasNext()){
            TimeWarp t = (TimeWarp)(it.next());
            if(t.getStatus()==RewardStates.UNACQUIRED){
                t.setStatus(RewardStates.ACQUIRED);
                break;
            }

        }
        return true;
    }
    else if(s.equals("ArcaneBoost")){
        Iterator it = arcaneBoosts.iterator();
        while(it.hasNext()){
            ArcaneBoost a = (ArcaneBoost)(it.next());
            if(a.getStatus()==RewardStates.UNACQUIRED){
                a.setStatus(RewardStates.ACQUIRED);
                break;
            }

        }

        return true;
    }
    else if(s.equals("ElementalCrest")){
        elementalCrestCount++;
        return true ;

    }
    return false;

}

public static void main(String[] args) {
    Gaia gaia = new Gaia();
    System.out.println(gaia.getScoreSheet());
   
  
}

}
