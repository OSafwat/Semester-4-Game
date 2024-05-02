package game.creatures.ExtraClasses;

public class Guardians {

    private  int guardianValue;
    private boolean guardianStatus;

    public Guardians(int guardianValue){
        this.guardianValue=guardianValue;
        this.guardianStatus=true;
    }


    public int getGuardianValue(){
    return guardianValue;
}

public boolean isDead(){

    return !guardianStatus;
}

public void  kill(){
    guardianStatus=false;

}












    
}
