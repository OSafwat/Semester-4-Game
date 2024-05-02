package game.engine;

public class GameScore {
    int allScores[];
    int elementalCrestCounter;
    public GameScore(){
        allScores= new int[6];
        elementalCrestCounter =0;
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
