package game.engine;
import game.collectibles.*;
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
    public ScoreSheet(){
        hydra= new Hydra();
        phoenix = new Phoenix();
        lion = new Lion();
        dragon= new Dragon();
        gaia = new Gaia();
        gamescore = new GameScore();
    }
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
        String res ="\n\nScoreSheet\n\n";
        res += dragon.getScoreSheet();
        res +="\n";
        res += gaia.getScoreSheet();
        res +="\n";
        res += hydra.getScoreSheet();
        res +="\n";
        res += phoenix.getScoreSheet();
        res +="\n";
        res += lion.getScoreSheet();
        System.out.print(res);
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

    public ArrayList<ArcaneBoost> getAllArcaneBoosts(){
            ArrayList<ArcaneBoost> allArcaneboosts= dragon.getAllArcaneBoosts();
            allArcaneboosts.addAll(gaia.getAllArcaneBoosts());
            allArcaneboosts.addAll(hydra.getAllArcaneBoosts());
            allArcaneboosts.addAll(phoenix.getAllArcaneBoosts());
            allArcaneboosts.addAll(lion.getAllArcaneBoosts());
            return allArcaneboosts;
    }
    public ArrayList<TimeWarp> getAllTimeWarps(){
        ArrayList<TimeWarp> allTimwarps= dragon.getAllTimeWarps();
        allTimwarps.addAll(gaia.getAllTimeWarps());
        allTimwarps.addAll(hydra.getAllTimeWarps());
        allTimwarps.addAll(phoenix.getAllTimeWarps());
        allTimwarps.addAll(lion.getAllTimeWarps());
        return allTimwarps;
}


}
