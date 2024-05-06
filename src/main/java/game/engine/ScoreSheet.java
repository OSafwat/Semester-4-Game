package game.engine;
import game.collectibles.ArcaneBoost;
import game.creatures.Creature;
import game.creatures.Dragon;
import game.creatures.Hydra;
import game.creatures.Lion;
import game.creatures.Phoenix;
import game.creatures.greenclasses.Gaia;
import game.dice.Dice;
import game.engine.enums.RealmColor;
import java.util.ArrayList;

public class ScoreSheet {
    Hydra hydra;
    Phoenix phoenix;
    Lion lion;
    Dragon dragon;
    Gaia gaia;
    GameScore gamescore;
//red    green  blue    magenta    yellow
//dragon gaia   hydra   phoenix  lion
    public int [] getScores(){
        int [] Scores = new int [5];
        Scores[0]= this.dragon.getScore();
        Scores[1]= this.gaia.getScore();
        Scores[2]= this.hydra.getScore();
        Scores[3]= this.phoenix.getScore();
        Scores[4]= this.lion.getScore();
        return Scores;
    }
    public int getElementalCrests(){
        int total=0;
        total+=dragon.getElementalCrest();
        total+=gaia.getElementalCrest();
        total+=hydra.getElementalCrest();
        total+=phoenix.getElementalCrest();
        total+=lion.getElementalCrest();
        return total;
    }

    public void displayScoreSheet(){
        String res ="\n";
        res += dragon.getScoreSheet();
        res +="\n\n";
        res += gaia.getScoreSheet();
        res +="\n\n";
        res += hydra.getScoreSheet();
        res +="\n\n";
        res += phoenix.getScoreSheet();
        res +="\n\n";
        res += lion.getScoreSheet();
        res +="\n";
        System.out.println(res);

    }
    //red    green  blue    magenta    yellow
    //dragon gaia   hydra   phoenix  lion
    public Creature getCreatureByRealm(Dice dice){
        switch (dice.getRealm()){
            case RED: return this.dragon;
            case GREEN:  return this.gaia;
            case BLUE:  return this.hydra;
            case MAGENTA:  return this.phoenix;
            case YELLOW: return this.lion;
            default: return null;
        } 

    }
    public Creature getCreatureByColor(RealmColor color){
        switch (color){
            case RED: return this.dragon;
            case GREEN:  return this.gaia;
            case BLUE:  return this.hydra;
            case MAGENTA:  return this.phoenix;
            case YELLOW: return this.lion;
            default: return null;
        } 
    }

    // public  getAllArcaneBoosts(){
    //         // dragon.getAllArcaneBoosts().addAll(gaia.getAllArcaneBoosts().addAll(hydra.getAllArcaneBoosts().addAll(phoenix.getAllArcaneBoosts().addAll(lion.getAllArcaneBoosts()))));
    //         // ArrayList<ArcaneBoost> allBoosts = dragon.getAllArcaneBoosts();
    //         // allBoosts.addAll((ArrayList)gaia.getAllTimeWarps());
    // }

}
