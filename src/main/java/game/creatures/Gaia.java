package game.creatures;

import game.creatures.ExtraClasses.Guardians;

public class Gaia extends Creature{

    private Guardians [][]  gaiaGuardians;
    private int alliveGuardians;

    public Gaia(){
        gaiaGuardians = new Guardians[3][4];
        alliveGuardians = 11;

        int c =1;
        for(int i=0;i<gaiaGuardians.length;i++){
            for(int j=0;j<gaiaGuardians[i].length;j++){
                gaiaGuardians[i][j]= new Guardians(c);
                c++;

            }

        }
        gaiaGuardians[0][0].kill();



    }




// gets a specific guardian in the Gaia
    public Guardians getGuardians(int c){

        int row =0;
        int col =0;
        if(c<2 || c>12)
        return null;
        int index =1;
        for(int i=0;i<gaiaGuardians.length;i++){
            for(int j=0;j<gaiaGuardians[i].length;j++){
                if(c==index){
                    row=i;
                    col=j;
                    break;

                }
                
                index++;
            }
            

    }
    return gaiaGuardians[row][col];


}

// kills a a given guardian if not already killed
public void killGaiaGuardian(Guardians g){
    if(g.isDead())
    System.out.println("Invalid Allready Killed");
    else{
        g.kill();
        alliveGuardians--;
    }
}

// gets the number of  still allive guradians
public int getAlliveGuardians(){
    return alliveGuardians;
}
 

// checks if all gueadiiand in a given col are dead if yes then true
public boolean checkCol(int col){
    for(int i=0;i<3;i++){
        if(!gaiaGuardians[i][col].isDead())
            return false;
    

    }
    return true;
}

// checks if all gueadiiand in a given row are dead if yes then true
public boolean checkRow(int row){
    for(int i=0;i<4;i++){
        if(!gaiaGuardians[row][i].isDead())
            return false;
    

    }
    return true;
}










}
