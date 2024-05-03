package game.creatures;

import game.dice.Dice;
import game.engine.enums.DragonNumber;

import java.util.HashMap;

public class Dragon extends Creature{
    public Integer face;
    public Integer wings;
    public Integer tail;
    public Integer heart;
    public DragonNumber dragonNumber;
    public Dragon[] Dragons;
    public HashMap<DragonNumber, Integer> pointMap;


    public Dragon(){
        Dragons = new Dragon[4];
        Dragons[0] = new Dragon(3, 2, 1, null, DragonNumber.Dragon1);
        Dragons[1] = new Dragon(6, 1, null, 3, DragonNumber.Dragon2);
        Dragons[2] = new Dragon(5, null, 2, 4, DragonNumber.Dragon3);
        Dragons[3] = new Dragon(null, 5, 4, 6, DragonNumber.Dragon4);
        initPointMap();
    }

    public Dragon (Integer face, Integer wings, Integer tail, Integer heart, DragonNumber dragonNumber){
        this.face = face;
        this.wings = wings;
        this.tail = tail;
        this.heart = heart;
        this.dragonNumber = dragonNumber;
    }

    public DragonNumber getDragonNumber(){
        return this.dragonNumber;
    }

    public void initPointMap(){
        pointMap = new HashMap<>();
        pointMap.put(DragonNumber.Dragon1, 10);
        pointMap.put(DragonNumber.Dragon2, 14);
        pointMap.put(DragonNumber.Dragon3, 16);
        pointMap.put(DragonNumber.Dragon4, 20);
    }

    public boolean isDead(){
        return face.equals(null) && wings.equals(null) && heart.equals(null) && tail.equals(null);
    }

    public Dragon dragonSelector (int index){
        return Dragons[index-1];
    }

    @Override
    public int getScore() {
        int score = 0;
        for (int i = 0; i < 4; i++)
        {
            score += Dragons[i].isDead() ? pointMap.get(Dragons[i].getDragonNumber()) : 0;
        }
        return score;
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
