package game.creatures;

import java.io.File;

/* ISSUES:-
 *  - taking care of incrementing and decrementing abs/tws/ecs type shit
 */

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.ArcanePrism;
import game.dice.Dice;
import game.dice.MagentaDice;
import game.dice.YellowDice;
import game.engine.Move;
import game.engine.ScoreSheet;
import game.engine.enums.RealmColor;
import game.engine.enums.RewardStates;
import game.exceptions.BonusException;
import game.exceptions.InvalidMoveException;

public class Lion extends Creature{
    private int[] lions;
    private int deadLions;
    private int score;
    private String scoresheet;
    private int elementalCrest;
    private static final HashMap<String, ArrayList<Integer>> rewardLocations = new HashMap<>();
    private static final String[] mappedRewardLocations = new String[11];
    private final Properties properties;

    public Lion(){
        arcaneBoosts= new ArrayList<>();
        timeWarps = new ArrayList<>();
        initLions();
        this.deadLions=0;
        this.score=0;
        initScoreSheet();
        this.elementalCrest=0;
        populateRewardLocationFromConfigFile();
        populateMappedRewardLocation();

        properties = new Properties();
        try {
            File config = new File("src/main/resources/config/RadiantSvannaRewards.properties");
            FileReader configReader = new FileReader(config);
            properties.load(configReader);
        } catch (IOException e) {
            properties.setProperty("hit1Reward", "null");
            properties.setProperty("hit2Reward", "null");
            properties.setProperty("hit3Reward", "TimeWarp");
            properties.setProperty("hit4Reward", "null");
            properties.setProperty("hit5Reward", "RedBonus");
            properties.setProperty("hit6Reward", "ArcaneBoost");
            properties.setProperty("hit7Reward", "null");
            properties.setProperty("hit8Reward", "ElementalCrest");
            properties.setProperty("hit9Reward", "null");
            properties.setProperty("hit10Reward", "MagentaBonus");
            properties.setProperty("hit11Reward", "null");
        }
        for(int i = 0; i < 11; i++) {
            if(Objects.equals(properties.getProperty("hit" + i + "Reward"), "ArcaneBoost")){
                ArcaneBoost ac = new ArcaneBoost(RewardStates.UNACQUIRED);
                arcaneBoosts.add(ac);
            }
            if(Objects.equals(properties.getProperty("hit" + i + "Reward"), "TimeWarp")) {
                TimeWarp tw = new TimeWarp(RewardStates.UNACQUIRED);
                timeWarps.add(tw);
            }
        }

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
        this.score+=calculateScore(dice);
    }

    @Override
    public String getScoreSheet(){
        StringBuilder sb= new StringBuilder("Radiant Savanna: Solar Lion (YELLOW REALM):\n");
        sb.append("+-----------------------------------------------------------------------+\n");
        sb.append("|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n");
        sb.append("+-----------------------------------------------------------------------+\n");

        sb.append("|  H  |");
        for (int i = 0; i < 11; i++) {
            if (this.lions[i] == 0) sb.append("0    |");
            else sb.append(lions[i]).append("    |");
        }
        sb.append("\n");
        sb.append("|  M  |     |     |     |x2   |     |     |x2   |     |x2   |     |x3   |\n");
        sb.append("|  R  |");

        for (int i = 0 ; i < 11; i++) {
            String rewardToken = mappedRewardLocations[i];
            if (rewardToken == null) sb.append("     |");
            else sb.append(rewardToken).append("   |");
        }

        sb.append("\n");

        sb.append("+-----------------------------------------------------------------------+\n\n");

        return (sb.toString());
    }
    private void setScoreSheet(String scoreSheet){
        this.scoresheet=scoreSheet;
    }
    private void initScoreSheet(){
        StringBuilder temp= new StringBuilder("Radiant Savanna: Solar Lion (YELLOW REALM):\n");
        temp.append("+-----------------------------------------------------------------------+\n");
        temp.append("|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n");
        temp.append("+-----------------------------------------------------------------------+\n");
        temp.append("|  H  |");
        for (int i = 0; i < 11; i++)
            temp.append("0    |");
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

    @Override
    public int getElementalCrest() {
        String rewardName = "ElementalCrest";
        ArrayList<Integer> rewardLocationsArray = rewardLocations.get(rewardName);

        int counter = 0;
        for (int i = 0; i < rewardLocationsArray.size(); i++) {
            if (lions[rewardLocationsArray.get(i)] != 0) counter++;
        }

        return counter;
    }

    @Override
    public boolean checkMove(Dice dice){
        int diceValue=dice.getValue();
        return(dice instanceof YellowDice || dice instanceof ArcanePrism) && diceValue <= 6 && diceValue > 0 && deadLions<11;
    }
    @Override
    public boolean makeMove(Dice dice) throws BonusException{
        if(!checkMove(dice)){
            System.out.print("erm what the sigma");
            return false;
        }
        updateLions(dice);
        updateScore(dice);
        updateDeadLions(); //leave this after the updatescoresheet method bc you change the deadlions number here
        ArrayList<Integer> TimeWarpArrayList = rewardLocations.get("TimeWarp");
        ArrayList<Integer> ArcaneBoostArrayList = rewardLocations.get("ArcaneBoost");

        for (int i = 0; i < TimeWarpArrayList.size(); i++) {
            if (TimeWarpArrayList.get(i) == deadLions) timeWarps.add(new TimeWarp());
        }

        for (int i = 0; i < ArcaneBoostArrayList.size(); i++) {
            if (ArcaneBoostArrayList.get(i) == deadLions) arcaneBoosts.add(new ArcaneBoost());
        }
        switch(properties.getProperty("hit"+deadLions+"Reward")){
            case "GreenBonus": throw new BonusException(RealmColor.GREEN);
            case "RedBonus": throw new BonusException(RealmColor.RED);
            case "BlueBonus": throw new BonusException(RealmColor.BLUE);
            case "MagentaBonus": throw new BonusException(RealmColor.MAGENTA);
            case "YellowBonus": throw new BonusException(RealmColor.YELLOW);
            default: return true;
        }

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
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/RadiantSvannaRewards.properties")) {
            if (input == null) throw new IOException("config file not found crodie default config shall be put into use");
            Properties prop = new Properties();
            prop.load(input);
            if (prop.isEmpty() || prop.size() < 11) throw new IOException("Properties file is empty or contains fewer than 11 properties");
            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(key);
                int index = 0;
                while (matcher.find()) {
                    String number = matcher.group();
                    index = Integer.parseInt(number) - 1;
                }
                if (rewardLocations.containsKey((String) value)) {
                    rewardLocations.get((String) value).add(index);
                } else {
                    rewardLocations.put((String) value, new ArrayList<>(Arrays.asList(new Integer[] {index})));
                }
            }


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            rewardLocations.put(null, new ArrayList<>(Arrays.asList(new Integer[] {1,2,4,7,9,11})));
            rewardLocations.put("TimeWarp", new ArrayList<>(Arrays.asList(new Integer[] {3})));
            rewardLocations.put("ArcaneBoost", new ArrayList<>(Arrays.asList(new Integer[] {6})));
            rewardLocations.put("RedBonus", new ArrayList<>(Arrays.asList(new Integer[] {5})));
            rewardLocations.put("ElementalCrest", new ArrayList<>(Arrays.asList(new Integer[] {8})));
            rewardLocations.put("MagentaBonus", new ArrayList<>(Arrays.asList(new Integer[] {10})));
        }
    }
    public void populateMappedRewardLocation() {
        for (Map.Entry<String, ArrayList<Integer>> entry : rewardLocations.entrySet()) {
            String key = entry.getKey();
            //System.out.println(key);
            ArrayList<Integer> value = entry.getValue();

            for (int i = 0; i < value.size(); i++) {
                String rewardString;
                switch(key) {
                    case "RedBonus":
                        rewardString = getRedBonusString(value.get(i));
                        break;
                    case "GreenBonus":
                        rewardString = getGreenBonusString(value.get(i));
                        break;
                    case "BlueBonus":
                        rewardString = getBlueBonusString(value.get(i));
                        break;
                    case "MagentaBonus":
                        rewardString = getMagentaBonusString(value.get(i));
                        break;
                    case "YellowBonus":
                        rewardString = getYellowBonusString(value.get(i));
                        break;
                    case "ElementalCrest":
                        rewardString = getElementalCrestString(value.get(i));
                        break;
                    case "ArcaneBoost":
                        rewardString = getArcaneBoostString(value.get(i));
                        break;
                    case "TimeWarp":
                        rewardString = getTimeWarpString(value.get(i));
                        break;
                    default:
                        rewardString = null;
                }

                mappedRewardLocations[value.get(i)] = rewardString;
            }
        }

    }
    public String getRedBonusString(int n) {
        String rewardName = "RedBonus";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = this.lions[rewardLocations.get(rewardName).get(i)] != 0 ? "X " : "RB";
            }
        }
        return output;
    }

    public String getGreenBonusString(int n) {
        String rewardName = "GreenBonus";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = this.lions[rewardLocations.get(rewardName).get(i)] != 0 ? "X " : "GB";
            }
        }
        return output;
    }

    public String getBlueBonusString(int n) {
        String rewardName = "BlueBonus";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = this.lions[rewardLocations.get(rewardName).get(i)] != 0 ? "X " : "BB";
            }
        }
        return output;
    }

    public String getMagentaBonusString(int n) {
        String rewardName = "MagentaBonus";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = this.lions[rewardLocations.get(rewardName).get(i)] != 0 ? "X " : "MB";
            }
        }
        return output;
    }

    public String getYellowBonusString(int n) {
        String rewardName = "YellowBonus";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = this.lions[rewardLocations.get(rewardName).get(i)] != 0 ? "X " : "YB";
            }
        }
        return output;
    }

    public String getElementalCrestString(int n) {
        String rewardName = "ElementalCrest";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = this.lions[rewardLocations.get(rewardName).get(i)] != 0 ? "X " : "EC";
            }
        }
        return output;
    }

    public String getArcaneBoostString(int n) {
        String rewardName = "ArcaneBoost";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = this.lions[rewardLocations.get(rewardName).get(i)] != 0 ? "X " : "AB";
            }
        }
        return output;
    }

    public String getTimeWarpString(int n) {
        String rewardName = "TimeWarp";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = this.lions[rewardLocations.get(rewardName).get(i)] != 0 ? "X " : "TW";
            }
        }
        return output;
    }
}
