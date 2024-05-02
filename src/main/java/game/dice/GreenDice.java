package game.dice;
import game.creatures.*;
import game.creatures.ExtraClasses.Guardians;
public class GreenDice extends Dice {
    private int score;
    private Gaia gaia;

    public GreenDice(){
        gaia = new Gaia();
        score=0;
    }

    public int getScore(){
        return score;
    }



    public boolean checkMove(Dice dice, Creature creature){
        GreenDice greendie = (GreenDice) dice;
        Gaia gaiaCreature =(Gaia) creature;
        int greenValue = greendie.getValue();
        Guardians speceficGuardian = gaiaCreature.getGuardians(greenValue);
        if(speceficGuardian.isDead())
            return false;
        else
        return true;

    }










    
}
