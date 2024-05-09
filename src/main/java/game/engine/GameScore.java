package game.engine;

public class GameScore {
    int allScores[];
    int elementalCrestCounter;

    public GameScore(){
        allScores= new int[5];
        elementalCrestCounter =0;
    }
    
    public void updateScores(int [] scores, int elementalCrests ){
        this.allScores = scores;
        this.elementalCrestCounter=elementalCrests;
    }

    public int getRedRealmScore() {
        return allScores[0];
    }

    public int getGreenRealmScore() {
        return allScores[1];
    }

    public int getBlueRealmScore() {
        return allScores[2];
    }

    public int getMagentaRealmScore() {
        return allScores[3];
    }

    public int getYellowRealmScore() {
        return allScores[4];
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
    
    public String toString(){
        String res="";
        // Print top border
        res+=("+--------+--------+-------+-------+------+-------+");
        // Print header row
        res+=("| Dragon | Phoenix| Lion  | Hydra | Gaia | Total |");
        // Print middle border
        res+=("+--------+--------+-------+-------+------+-------+");
        // Print values with vertical lines and tabs to align them as a table
        res+=("|   " + allScores[0] + "    |   " +  allScores[1] + "    |   " +  allScores[2] + "   |   " +  allScores[3] + "   |  " +  allScores[4] + "   |   " +
                (getTotalScore()) + "   |");
        // Print bottom border
        res+=("+--------+--------+-------+-------+------+-------+"); 
        return res;
    }
}
