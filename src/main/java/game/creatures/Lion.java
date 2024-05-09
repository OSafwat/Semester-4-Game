package game.creatures;

/* ISSUES:-
 *  - taking care of incrementing and decrementing abs/tws/ecs type shit
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.ArcanePrism;
import game.dice.Dice;
import game.dice.MagentaDice;
import game.dice.YellowDice;
import game.engine.Move;
import game.engine.ScoreSheet;
import game.exceptions.BonusException;
import game.exceptions.InvalidMoveException;

public class Lion extends Creature{ 
    private int[] lions;
    private int deadLions;
    private int score;
    private String scoresheet;
    private int elementalCrest;
    private int arcaneBoost;
    private int timeWarp;
    private static final HashMap<String, ArrayList<Integer>> rewardLocations = new HashMap<>(); 
    private static final String[] mappedRewardLocations = new String[11];

    public Lion(){
        arcaneBoosts= new ArrayList<>();
        timeWarps = new ArrayList<>();
        initLions();
        this.deadLions=0;
        this.score=0;
        initScoreSheet();
        this.elementalCrest=0;
        this.arcaneBoost=0;
        this.timeWarp=0;
        populateMappedRewardLocation();
    }
    public ArrayList<TimeWarp> getAllTimeWarps() {
        return timeWarps;
    }

    //A method to get all the arcane boost powers
    public ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        return arcaneBoosts;
    }
    public int[] getLions(){
        return this.lions;
    }
    private void setLions(int[] lions){
        this.lions=lions;
    }
    private void initLions(){
        setLions(new int[11]);
    }
    private void updateLions(Dice dice){
        this.lions[deadLions]=calculateScore(dice);
    }

    public int getDeadLions(){
        return this.deadLions;
    }
    private void setDeadLions (int deadLions){
        this.deadLions=deadLions;
    }
    private void updateDeadLions(){
        setDeadLions(deadLions+1);
    }

    @Override
    public int getScore(){
        return this.score;
    }
    private int calculateScore(Dice dice){
        int value=dice.getValue();
        int ans;
        if(deadLions+1==4||deadLions+1==6||deadLions+1==9){
            ans=value*2;
        }
        else if(deadLions+1==11){
            ans=value*3;
        }
        else{
        ans=value;
            }
            return ans;
    }
    private void updateScore(Dice dice){
        this.score=calculateScore(dice);
    }
    
    @Override
    public String getScoreSheet(){
        return this.scoresheet;
    }
    private void setScoreSheet(String scoreSheet){
        this.scoresheet=scoreSheet;
    }
    private void initScoreSheet(){
        StringBuilder temp= new StringBuilder("Radiant Savanna: Solar Lion (YELLOW REALM):        \n");
        temp.append("+-----------------------------------------------------------------------+\n");
        temp.append("|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n");
        temp.append("+-----------------------------------------------------------------------+\n");
        temp.append("|  H  |");
        temp.append("0    |".repeat(11));
        temp.append("\n");
        temp.append("|  M  |     |     |     |x2   |     |     |x2   |     |x2   |     |x3   |\n");
        temp.append("|  R  |");
        for (int i = 0 ; i < 11; i++) {
            String rewardToken = mappedRewardLocations[i];
            if (rewardToken == null) temp.append("     |");
            else temp.append(rewardToken).append("   |");
        }
        temp.append("+-----------------------------------------------------------------------+\n");
    }
    private void updateScoreSheet(Dice dice){
        StringBuilder temp= new StringBuilder("Radiant Savanna: Solar Lion (YELLOW REALM):        \n");
        temp.append("+-----------------------------------------------------------------------+\n");
        temp.append("|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n");
        temp.append("+-----------------------------------------------------------------------+\n");
        temp.append("|  H  |");
        for(int i=0;i<this.deadLions;i++){
            temp.append(this.lions[i]).append("    |");
        }
        int diceValue=calculateScore(dice);
        temp.append(diceValue).append("    |");
        temp.append("0    |".repeat(Math.max(0, 11 - this.deadLions)));
        temp.append("\n");
        temp.append("|  M  |     |     |     |x2   |     |     |x2   |     |x2   |     |x3   |\n");
        temp.append("|  R  |");
        for (int i = 0 ; i < 11; i++) {
            String rewardToken = mappedRewardLocations[i];
            if (rewardToken == null) temp.append("     |");
            else temp.append(rewardToken).append("   |");
        }
        temp.append("+-----------------------------------------------------------------------+\n\n");
    }
    
    public int getElementalCrest(){
        return this.elementalCrest;
    }
    private void setElementalCrest(int elementalCrest){
        this.elementalCrest=elementalCrest;
    }
    private void updateElementalCrest(){
        String rewardName = "ElementalCrest";
        ArrayList<Integer> rewardLocationsArray = rewardLocations.get(rewardName);
        int size=rewardLocationsArray.size(); // to avoid dynamic changes to the size after removing/adding
        for (Integer integer : rewardLocationsArray) {
            if (lions[integer] != 0) {
                this.elementalCrest = 1; //ISSUE if the number of elemental crests in the config file is more than one
                return;
            }
        }
    }
    
    public int getArcaneBoost(){
        return this.arcaneBoost;
    }
    private void setArcaneBoost(int arcaneBoost){
        this.arcaneBoost=arcaneBoost;
    }
    private void updateArcaneBoost(){
        String rewardName = "ArcaneBoost";
        ArrayList<Integer> rewardLocationsArray = rewardLocations.get(rewardName);
        int size=rewardLocationsArray.size(); // to avoid dynamic changes to the size after removing/adding
        for (Integer integer : rewardLocationsArray) {
            if (lions[integer] != 0) {
                this.arcaneBoost = 1; //ISSUE if the number of arcane boosts in the config file is more than one
                return;
            }
        }
    }

    public int getTimeWarp(){
        return this.timeWarp;
    }
    private void setTimeWarp(int timeWarp){
        this.timeWarp=timeWarp;
    }
    private void updateTimeWarp(){
        String rewardName = "TimeWarp";
        ArrayList<Integer> rewardLocationsArray = rewardLocations.get(rewardName);
        int size=rewardLocationsArray.size(); // to avoid dynamic changes to the size after removing/adding
        for (Integer integer : rewardLocationsArray) {
            if (lions[integer] != 0) {
                this.timeWarp = 1; //ISSUE if the number of time warps in the config file is more than one
                break;
            }
        }
    }
    
    @Override
    public boolean checkMove(Dice dice){
        int diceValue=dice.getValue();
            return(dice instanceof YellowDice || dice instanceof ArcanePrism) && diceValue <= 6 && diceValue > 0;
    }
    @Override
    public boolean makeMove(Dice dice) throws BonusException{ 
            if(!checkMove(dice)){
                System.out.print("erm what the sigma");
                return false;
            }
            updateLions(dice);
            updateScoreSheet(dice);
            updateScore(dice);
            updateDeadLions(); //leave this after the updatescoresheet method bc you change the deadlions number here
            updateElementalCrest();
            updateArcaneBoost();
            updateTimeWarp();
            return true;
        }
    @Override
    public ArrayList<Move> getAllPossibleMoves() {
    if(deadLions == 11) return new ArrayList<>();
    ArrayList<Move> possibleMoves = new ArrayList<>();
    for(int i = 0; i < 6; i++) { 
        Move idk = new Move(new YellowDice(i + 1), this);
        possibleMoves.add(idk);
    }
    return possibleMoves;
}
    
    public void populateRewardLocationFromConfigFile() {
        try (InputStream input = new FileInputStream("../../../resources/config/MysticalSkyRewards.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            ArrayList<Object> valueSet = new ArrayList<>();
            valueSet.addAll(new LinkedHashSet<>(prop.values()));
            int counter = 0;
            for (Object value : valueSet) {
                if (rewardLocations.containsKey((String) value)) {
                    rewardLocations.get((String) value).add(counter++);
                } else {
                    rewardLocations.put((String) value, new ArrayList<>(Arrays.asList(new Integer[] {counter++})));
                }
            }

        } catch (IOException ex) {
            System.out.println("config file not found crodie default config shall be put into use");
            rewardLocations.put("ElementalCrest", new ArrayList<>(List.of(7)));
            rewardLocations.put("ArcaneBoost", new ArrayList<>(List.of(5)));
            rewardLocations.put("TimeWarp", new ArrayList<>(List.of(2)));
            rewardLocations.put("RedBonus", new ArrayList<>(List.of(4)));
            rewardLocations.put("MagentaBonus", new ArrayList<>(List.of(9)));
            rewardLocations.put(null, new ArrayList<>(Arrays.asList(0, 1,3,6,8,10)));


        }
    }
    public void populateMappedRewardLocation() {
        for (Map.Entry<String, ArrayList<Integer>> entry : rewardLocations.entrySet()) {
            String key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();
            for (Integer integer : value) {
                String rewardString = switch (key) {
                    case "RedBonus" -> getRedBonusString(integer);
                    case "GreenBonus" -> getGreenBonusString(integer);
                    case "BlueBonus" -> getBlueBonusString(integer);
                    case "MagentaBonus" -> getMagentaBonusString(integer);
                    case "YellowBonus" -> getYellowBonusString(integer);
                    case "ElementalCrest" -> getElementalCrestString(integer);
                    case "ArcaneBoost" -> getArcaneBoostString(integer);
                    case "TimeWarp" -> getTimeWarpString(integer);
                    default -> null;
                };

                mappedRewardLocations[integer] = rewardString;
            }
        }
    }
    public String getRedBonusString(int n) {
        String rewardName = "RedBonus";
        return this.lions[rewardLocations.get(rewardName).get(n)] != 0 ? "X" : "RB";
    }
    public String getGreenBonusString(int n) {
        String rewardName = "GreenBonus";
        return this.lions[rewardLocations.get(rewardName).get(n)] != 0 ? "X" : "GB";
    }
    public String getBlueBonusString(int n) {
        String rewardName = "BlueBonus";
        return this.lions[rewardLocations.get(rewardName).get(n)] != 0 ? "X" : "BB";
    }
    public String getMagentaBonusString(int n) {
        String rewardName = "MagentaBonus";
        return this.lions[rewardLocations.get(rewardName).get(n)] != 0 ? "X" : "MB";
    }
    public String getYellowBonusString(int n) {
        String rewardName = "YellowBonus";
        return this.lions[rewardLocations.get(rewardName).get(n)] != 0 ? "X" : "YB";
    }
    public String getElementalCrestString(int n) {
        String rewardName = "ElementalCrest";
        return this.lions[rewardLocations.get(rewardName).get(n)] != 0 ? "X" : "EC";
    }
    public String getArcaneBoostString(int n) {
        String rewardName = "ArcaneBoost";
        return this.lions[rewardLocations.get(rewardName).get(n)] != 0 ? "X" : "AB";
    }
    public String getTimeWarpString(int n) {
        String rewardName = "TimeWarp";
        return this.lions[rewardLocations.get(rewardName).get(n)] != 0 ? "X" : "TW";
    }
}
