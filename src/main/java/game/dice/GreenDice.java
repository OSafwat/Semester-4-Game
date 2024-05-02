package game.dice;
import game.creatures.*;
import game.creatures.ExtraClasses.Guardians;
public class GreenDice extends Dice {
    private int score;
    //private Gaia gaia;
    private int [] scores ={1,2,4,7,11,16,22,29,37,46,56};

    private boolean [] checkrow={false,false,false};
    private boolean [] checkcol = {false,false,false,false};

    public GreenDice(){
        //gaia = new Gaia();
        score=0;
    }

    public int getScore(){
        return score;
    }



    public boolean checkMove(Dice dice, Creature creature){
        GreenDice greendie = (GreenDice) dice;
        Gaia gaiaCreature =(Gaia) creature;
        // assuming getValue done in the dice class
        int greenValue = greendie.getValue();
        Guardians speceficGuardian = gaiaCreature.getGuardians(greenValue);
        if(speceficGuardian.isDead())
            return false;
        else
        return true;

    }



    private boolean checkcol(int c, Gaia gaia){







    }












    
}
