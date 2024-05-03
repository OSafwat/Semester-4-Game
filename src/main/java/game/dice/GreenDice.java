//KEY
// IMP = important to change
// COMPLETE =  should be completed later
// EXP = explanation
// ASUM  = assumption till the leader finish



















package game.dice;
import game.creatures.*;
import game.creatures.ExtraClasses.Guardians;
public class GreenDice extends Dice {
    private int score;
    private Gaia gaia;
    private int [] scores ={1,2,4,7,11,16,22,29,37,46,56};

    private boolean [] row={false,false,false};
    private boolean [] col = {false,false,false,false};

    public GreenDice(){
        gaia = new Gaia();
        score=0;
    }


    // EXP mehtod to get the score of the realm
    public int getScore(){
        return score;
    }

    // EXP methos to update the score of the realm
    private void updateScore(){
        int dead = gaia.getDeadGuardians()-1;
        score= scores[dead];

    }


// EXP checks if a given move is possible
    public boolean checkMove(Dice dice, Creature creature){
        GreenDice greendie = (GreenDice) dice;
        Gaia gaiaCreature =this.gaia;
        // ASUM assuming getValue done in the dice class
        int greenValue = greendie.getValue();
        Guardians speceficGuardian = gaiaCreature.getGuardians(greenValue);
        if(speceficGuardian.isDead())
            return false;
        else
        return true;

    }


// EXP  update the  instance  variable col accordingly
    private  void checkcol(int c){
         
        if (this.gaia.checkCol(c))
            col[c]= true;
    }
        
        
// EXP check if a row is already killed and update the  instance  variable col accordingly
    private  void checkrow(int r){
         
        if (this.gaia.checkRow(r)){    
            row[r]= true;
         
        }
                   
    }


// EXP executing a given move
    public boolean makeMove(Dice dice, Creature creature){
        
        if(!checkMove(dice, creature))
            return false;
        else{
            GreenDice greendie = (GreenDice) dice;
            Gaia gaiaCreature = this.gaia;
            // ASUM assuming getValue done in the dice class
            int greenValue = greendie.getValue();
            Guardians speceficGuardian = gaiaCreature.getGuardians(greenValue);
            gaiaCreature.killGaiaGuardian(speceficGuardian);
            updateScore();
            int colToCheck = gaiaCreature.getGuardiansCol(greenValue);
            int rowToCheck = gaiaCreature.getGuardiansRow(greenValue);
            checkcol(colToCheck);
            checkrow(rowToCheck);
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







































































}

