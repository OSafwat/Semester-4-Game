package game.engine;

import game.creatures.*;
import game.creatures.greenclasses.Gaia;

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

    public String getScoreSheet(){
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
        return res;

    }

}
