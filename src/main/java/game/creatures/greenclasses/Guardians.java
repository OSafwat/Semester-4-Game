package game.creatures.greenclasses;

public class Guardians {

    public static final int length = 0;
    private  int guardianValue;
    private boolean guardianStatus;

    protected Guardians(int guardianValue){
        this.guardianValue=guardianValue;
        this.guardianStatus=true;
    }


    protected int getGuardianValue(){
    return guardianValue;
}

public boolean isDead(){

    return !guardianStatus;
}

protected void  kill(){
    guardianStatus=false;

}

public String toString(){
    return "Value:" + this.guardianValue + "Status" +this.guardianStatus;
}   
}
