package game.engine;

public class GameScore {
    private int allScores[];
    private int elementalCrestCounter;

    public GameScore(){
        allScores= new int[5];
        elementalCrestCounter =0;
    }

    void updateScores(int [] scores, int elementalCrests ){
        this.allScores = scores;
        this.elementalCrestCounter=elementalCrests;
    }
    int getElementalCrestCounter() {
        return elementalCrestCounter;
    }

    int getRedRealmScore() {
        return allScores[0];
    }

    int getGreenRealmScore() {
        return allScores[1];
    }

    int getBlueRealmScore() {
        return allScores[2];
    }

    int getMagentaRealmScore() {
        return allScores[3];
    }

    int getYellowRealmScore() {
        return allScores[4];
    }

    int getTotalScore(){
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
        res+=("+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+\n");
        // Print header row
        res+=("  |   Red Realm   |   Green Realm   |  Blue Realm   |  Magenta Realm  |  Yellow Realm  |  Total  |\n");
        // Print middle border
        res+=("+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+\n");
        // Print values with vertical lines and tabs to align them as a table
        res+=("  |       " + allScores[0] + "       |        " +  allScores[1] + "        |       " +  allScores[2] + "       |        " +  allScores[3] + "        |       " +  allScores[4] + "        |    " +
                (getTotalScore()) + "    | \n");
        // Print bottom border
        res+=("+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+--------+");
        return res;
    }

    int[] getAllScores() {
        return allScores;
    }
}
