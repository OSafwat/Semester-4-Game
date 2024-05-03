package game.creatures;

public class Dragon extends Creature{
    public Integer face;
    public Integer wings;
    public Integer tail;
    public Integer heart;
    public Dragon[] Dragon;
    public Integer[] pointMap;

    public Dragon(){
        Dragon = new Dragon[4];
        Dragon[0] = new Dragon(3, 2, 1, null);
        Dragon[1] = new Dragon(6, 1, null, 3);
        Dragon[2] = new Dragon(5, null, 2, 4);
        Dragon[3] = new Dragon(null, 5, 4, 6);
        initPointMap();
    }

    public Dragon (Integer face, Integer wings, Integer tail, Integer heart){
        this.face = face;
        this.wings = wings;
        this.tail = tail;
        this.heart = heart;
    }

    public void initPointMap(){
        pointMap = new Integer[4];
        pointMap[0] = 10;
        pointMap[1] = 14;
        pointMap[2] = 16;
        pointMap[3] = 20;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int getElementalCrest() {
        return 0;
    }

    @Override
    public String getScoreSheet() {
        return null;
    }
}
