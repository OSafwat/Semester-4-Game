package game.creatures;

import game.creatures.ExtraClasses.Guardians;
import game.dice.Dice;
import game.dice.GreenDice;
import game.engine.Move;

public class Gaia extends Creature{

    private Guardians [][]  gaiaGuardians;
    private int alliveGuardians;
    private int deadGuardians;
    private int score;
    private int [] scores ={1,2,4,7,11,16,22,29,37,46,56};
    private boolean [] row={false,false,false};
    private boolean [] col = {false,false,false,false};

    public Gaia(){
        gaiaGuardians = new Guardians[3][4];
        alliveGuardians = 11;
        deadGuardians=0;

        int c =1;
        for(int i=0;i<gaiaGuardians.length;i++){
            for(int j=0;j<gaiaGuardians[i].length;j++){
                gaiaGuardians[i][j]= new Guardians(c);
                c++;

            }

        }
        gaiaGuardians[0][0].kill();

    }


     // EXP mehtod to get the score of the realm
     public int getScore(){
        return score;
    }

    // EXP methos to update the score of the realm
    private void updateScore(){
        int dead = this.getDeadGuardians()-1;
        score= scores[dead];

    }



    // EXP checks if a given move is possible
    public boolean checkMove(Dice dice, Creature creature){
        GreenDice greendie = (GreenDice) dice;
        // ASUM assuming getValue done in the dice class add white
        int greenValue = greendie.getValue();
        Guardians speceficGuardian = this.getGuardians(greenValue);
        if(speceficGuardian.isDead())
            return false;
        else
        return true;

    }



// EXP gets a specific guardian in the Gaia
    public Guardians getGuardians(int c){

        int row =0;
        int col =0;
        if(c<2 || c>12)
        return null;
        int index =1;
        for(int i=0;i<gaiaGuardians.length;i++){
            for(int j=0;j<gaiaGuardians[i].length;j++){
                if(c==index){
                    row=i;
                    col=j;
                    break;

                }
                
                index++;
            }
            

    }
    return gaiaGuardians[row][col];


}

// EXP gets a specific guardian row position in the Gaia
public int getGuardiansRow(int c){

    int row =0;
    if(c<2 || c>12)
    return 0;
    int index =1;
    for(int i=0;i<gaiaGuardians.length;i++){
        for(int j=0;j<gaiaGuardians[i].length;j++){
            if(c==index){
                row=i;
                break;

            }
            
            index++;
        }
        

}
    return row;


}


// EXP gets a specific guardian col position in the Gaia
public int getGuardiansCol(int c){

    int col =0;
    if(c<2 || c>12)
    return 0;
    int index =1;
    for(int i=0;i<gaiaGuardians.length;i++){
        for(int j=0;j<gaiaGuardians[i].length;j++){
            if(c==index){
                col=j;
                break;

            }
            
            index++;
        }
        

}
    return col;


}


//EXP  kills a a given guardian if not already killed
public void killGaiaGuardian(Guardians g){
    if(g.isDead())
    System.out.println("Invalid Allready Killed");
    else{
        g.kill();
        alliveGuardians--;
        deadGuardians++;
    }
}

//EXP  gets the number of  still allive guradians
public int getAlliveGuardians(){
    return alliveGuardians;
}
 
public int getDeadGuardians(){
    return deadGuardians;
}



    // EXP checks if all guardians in a given col are dead if yes then true
public boolean checkCol(int col){
    for(int i=0;i<3;i++){
        if(!gaiaGuardians[i][col].isDead())
            return false;
    

    }
    return true;
}

// EXP checks if all guardians in a given row are dead if yes then true
public boolean checkRow(int row){
    for(int i=0;i<4;i++){
        if(!gaiaGuardians[row][i].isDead())
            return false;
    

    }
    return true;
}


// EXP  update the  instance  variable col accordingly
private  void updateCol(int c){
         
    if (this.checkCol(c))
        col[c]= true;
}

// EXP check if a row is already killed and update the  instance  variable col accordingly
private  void updateRow(int r){
     
    if (this.checkRow(r)){    
        row[r]= true;
     
    }
               
}




 // EXP gives the respective bonus for each col
    // IMP this will be changed when collectables classes are done
    // ASUM here I wrote stings but when the leader finish the classes this will be void and replace strings with method.
    private String whichCollectableCol(int c){
        switch (c) {
            case 0: return "Time Warp";
            case 1: return "Blue_Bonus";
            case 2 : return "Magenta_Bonus";
            case 3 : return "Arcane_Power";
            default:
                return "Invalid";
        }

    }

      // EXP gives the respective bonus for each roe
    // IMP this will be changed when collectables classes are done
      private String whichCollectableRow(int r){
        switch (r) {
            case 0: return "Yellow_Bonus";
            case 1: return "Red_Bonus";
            case 2 : return "Elemntal_Crest";
            default:
                return "Invalid";
        }

   
  
}



// EXP executing a given move
    public boolean makeMove(Dice dice, Creature creature){
        
        if(!checkMove(dice, creature))
            return false;
        else{
            alliveGuardians--;
            deadGuardians++;
            GreenDice greendie = (GreenDice) dice;
            
            // ASUM assuming getValue done in the dice class
            int greenValue = greendie.getValue();
            Guardians speceficGuardian = this.getGuardians(greenValue);
            this.killGaiaGuardian(speceficGuardian);
            updateScore();
            int colToCheck = this.getGuardiansCol(greenValue);
            int rowToCheck = this.getGuardiansRow(greenValue);
            updateCol(colToCheck);
            updateRow(rowToCheck);
            if(row[rowToCheck]== false && col[colToCheck]==false)
            return true;
            else if(row[rowToCheck]== true && col[colToCheck]==false){
                String act = whichCollectableRow(rowToCheck);
                // ADD THE CODE OF THE BONUS OR POWER RESPECTIVELY
                // IMP this will be changed when collectables classes are done
                // I will need to change in the whichCollectableRow(rowToCheck)
                return true;
            }
            else if(row[rowToCheck]== false && col[colToCheck]==true){
                String act = whichCollectableCol(colToCheck);
                // ADD THE CODE OF THE BONUS OR POWER RESPECTIVELY
                // IMP this will be changed when collectables classes are done
                // I will only need to change  in the whichCollectableCol(colToCheck);
                return true;
            }
            else{
                //COMPLETE  create  prioirity method which excutes based on priority 
                //and modify  whichCollectableCol accordingly

                return true;
            }




            

        
        }



    }

// EXP method to get all possible moves
public Move[] getAllPossibleMoves( Dice dice,Creature creature){

    GreenDice greeDice = (GreenDice) dice;
    Move [] allMoves = new Move[alliveGuardians];
    int c=0;
    for(int i=2;i<13;i++){
        if(checkMove(greeDice, this)){
        // ASUM assuming move constructor is done
        allMoves[c]= new Move(greeDice,this);
        c++;
        }

    }
    return allMoves;
}





   




















































}
