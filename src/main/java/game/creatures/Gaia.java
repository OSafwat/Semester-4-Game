package game.creatures;

import game.creatures.ExtraClasses.Guardians;

public class Gaia extends Creature{

    Guardians [][]  gaiaGuardians;

    public Gaia(){
        gaiaGuardians = new Guardians[3][4];

        int c =1;
        for(int i=0;i<gaiaGuardians.length;i++){
            for(int j=0;j<gaiaGuardians[i].length;j++){
                gaiaGuardians[i][j]= new Guardians(c);
                c++;

            }

        }
        gaiaGuardians[0][0].kill();


    }












    
}
