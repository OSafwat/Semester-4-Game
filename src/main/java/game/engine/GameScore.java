package game.engine;

import game.creatures.Gaia;
import game.creatures.Lion;

public class GameScore {
    int allScores[];
    int elementalCrestCounter;
    public GameScore(){
        allScores= new int[6];
        elementalCrestCounter =0;
    }
    
public void updateScores(Lion lion, Gaia gaia, ){
    allScores[0]= lion.getScore();
}

    public int getTotalScore(){
        int total=0;
        int min= Integer.MAX_VALUE;
        for (int i=0; i< this.allScores.length; i++){
            total += allScores[i];
            if (min > allScores[i])
                min= allScores[i];
        }
        total+= elementalCrestCounter*min;
        return total;
    }
    

}
