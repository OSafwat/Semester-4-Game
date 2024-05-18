package game.creatures;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.ArcanePrism;
import game.dice.Dice;
import game.dice.MagentaDice;
import game.engine.Move;
import game.engine.enums.RealmColor;
import game.exceptions.BonusException;
import game.engine.enums.RewardStates;
import game.exceptions.InvalidMoveException;

public class Phoenix extends Creature{
    private Integer[] phoenixes;
    private int killedPhoenixes;
    private ArrayList<Move> allPossibleMoves;
    // A hash map that maps the rewards to their respective phoenix's death amounts
    private static HashMap<String, ArrayList<Integer>> rewardLocations = new HashMap<>();
    // A String array that stores the mapping from the Hash Map rewardLocations for easier and faster accessing
    private String[] mappedRewardLocations = new String[11];

    public Phoenix() {
        phoenixes = new Integer[11];
        killedPhoenixes = 0;
        timeWarps = new ArrayList<>();
        arcaneBoosts = new ArrayList<>();
        allPossibleMoves = new ArrayList<>();
        initPossibleMoves();
        populateRewardLocationFromConfigFile();
        initRewards();
    }

    private void initRewards() {
        ArrayList<Integer> TimeWarpArrayList = rewardLocations.get("TimeWarp");
        ArrayList<Integer> ArcaneBoostArrayList = rewardLocations.get("ArcaneBoost");

        if(TimeWarpArrayList!=null)
        for (int i = 0; i < TimeWarpArrayList.size(); i++)
            timeWarps.add(new TimeWarp());

        if(ArcaneBoostArrayList!=null)    
        for (int i = 0; i < ArcaneBoostArrayList.size(); i++)
            arcaneBoosts.add(new ArcaneBoost());
    }

    @Override
    public int getElementalCrest() {
        String rewardName = "ElementalCrest";
        ArrayList<Integer> rewardLocationsArray = rewardLocations.get(rewardName);

        int counter = 0;
        if(rewardLocationsArray!=null)
        for (int i = 0; i < rewardLocationsArray.size(); i++) {
            if (phoenixes[rewardLocationsArray.get(i)] != null) counter++;
        }

        return counter;
    }

    @Override
    public String getScoreSheet() {
        StringBuffer sb = new StringBuffer();
        sb.append("Mystical Sky: Majestic Phoenix (MAGENTA REALM):\n");
        sb.append("+-----------------------------------------------------------------------+\n");
        sb.append("|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n");
        sb.append("+-----------------------------------------------------------------------+\n");

        sb.append("|  H  |");
        // for loop to loop on the phoenixes array which stored the hits received by each pheonix
        for (int i = 0; i < 11; i++) {
            if (phoenixes[i] == null) sb.append("0    |");
            else sb.append(phoenixes[i]).append("    |");
        }
        sb.append("\n");

        sb.append("|  C  |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |\n");
        sb.append("|  R  |");

        for (int i = 0 ; i < 11; i++) {
            String rewardToken = mappedRewardLocations[i];
            if (rewardToken == "") sb.append("     |");
            else sb.append(rewardToken + "   |");
        }

        sb.append("\n");

        sb.append("+-----------------------------------------------------------------------+\n\n");

        return sb.toString();
    }

    @Override
    protected boolean checkMove(Dice dice) {
        int diceValue = dice.getValue();
        if ((dice instanceof MagentaDice || dice instanceof ArcanePrism) && diceValue <= 6 && diceValue > 0) {
            if (killedPhoenixes >= 11) {
                allPossibleMoves.clear();
                return false;
            }
            if (killedPhoenixes == 0 || phoenixes[killedPhoenixes - 1] == 6 || diceValue > phoenixes[killedPhoenixes - 1]) return killedPhoenixes < 11;
        }

        return false;
    }

    @Override
    public boolean makeMove(Dice dice) throws InvalidMoveException, BonusException {
        if (checkMove(dice)) {
            int diceValue = dice.getValue();
            phoenixes[killedPhoenixes++] = diceValue;
            score += diceValue;

            ArrayList<Integer> TimeWarpArrayList = rewardLocations.get("TimeWarp");
            ArrayList<Integer> ArcaneBoostArrayList = rewardLocations.get("ArcaneBoost");

            if(TimeWarpArrayList!=null)
            for (int i = 0; i < TimeWarpArrayList.size(); i++) {
                if (TimeWarpArrayList.get(i) == killedPhoenixes - 1) {
                    timeWarps.get(0).setStatus(RewardStates.ACQUIRED);
                    timeWarps.remove(0);
                    break;
                }
            }
            
            if(ArcaneBoostArrayList!=null)
            for (int i = 0; i < ArcaneBoostArrayList.size(); i++) {
                if (ArcaneBoostArrayList.get(i) == killedPhoenixes - 1){
                    arcaneBoosts.get(0).setStatus(RewardStates.ACQUIRED);
                    arcaneBoosts.remove(0);
                    break;
                };
            }

            String rewardString = mappedRewardLocations[killedPhoenixes - 1];
            RealmColor bonusColor = RealmColor.PARENT;
            Boolean notBonus = false;
            if (!rewardString.equals("")) {
                switch (rewardString) {
                    case "RB":
                        bonusColor = RealmColor.RED;
                        break;

                    case "GB":
                        bonusColor = RealmColor.GREEN;
                        break;

                    case "BB":
                        bonusColor = RealmColor.BLUE;
                        break;

                    case "MB":
                        bonusColor = RealmColor.MAGENTA;
                        break;

                    case "YB":
                        bonusColor = RealmColor.YELLOW;
                        break;
                
                    case "EB":
                        bonusColor = RealmColor.WHITE;
                        break;
                    default:
                        notBonus = true;
                        break;
                }
            }

            updateAllPossibleMoves();
            populateMappedRewardLocation();

            if (!notBonus && bonusColor != RealmColor.PARENT) throw new BonusException(bonusColor);

            return true;
        }

        return false;
    }

    @Override
    public ArrayList<Move> getAllPossibleMoves() {
        updateAllPossibleMoves();
        return allPossibleMoves;
    }

    @Override
    public ArrayList<TimeWarp> getAllTimeWarps() {
        return timeWarps;
    }

    @Override
    public ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        return arcaneBoosts;
    }

    public void initPossibleMoves() {
        for (int i = 0; i < 6; i++) {
            allPossibleMoves.add(new Move(new MagentaDice(i + 1), this));
        }
    }

    //implementing the config file reading
    private void populateRewardLocationFromConfigFile() {
        // Trying to read from the config (.properties file) the realm configuration
        try {
            File config = new File("src/main/resources/config/MysticalSkyRewards.properties");
            FileReader configReader = new FileReader(config);

            Properties prop = new Properties();

            // load a properties file
            prop.load(configReader);

            // get the property value and store them in the HashMap rewardLocation
            if (prop.isEmpty()) throw new IOException("Properties file is empty properties");

            for (int i = 0; i < 11; i++) {
                String rewardString = prop.getProperty("hit" + (i + 1) + "Reward");
                if (rewardString == null) {
                    switch (i) {
                        case 0:
                            rewardLocations.put("", new ArrayList<>(Arrays.asList(new Integer[] {0})));
                            break;

                        case 1:
                            rewardLocations.get("").add(1);
                            break;

                        case 2:
                            rewardLocations.put("TimeWarp", new ArrayList<>(Arrays.asList(new Integer[] {2})));
                            break;

                        case 3:
                            rewardLocations.put("GreenBonus", new ArrayList<>(Arrays.asList(new Integer[] {3})));
                            break;

                        case 4:
                            rewardLocations.put("ArcaneBoost", new ArrayList<>(Arrays.asList(new Integer[] {4})));
                            break;

                        case 5:
                            rewardLocations.put("RedBonus", new ArrayList<>(Arrays.asList(new Integer[] {5})));
                            break;
                            
                        case 6:
                            rewardLocations.put("ElementalCrest", new ArrayList<>(Arrays.asList(new Integer[] {6})));
                            break;
                        
                        case 7:
                            rewardLocations.get("TimeWarp").add(7);
                            break;
                            
                        case 8:
                            rewardLocations.put("BlueBonus", new ArrayList<>(Arrays.asList(new Integer[] {8})));
                            break;
                        
                        case 9:
                            rewardLocations.put("YellowBonus", new ArrayList<>(Arrays.asList(new Integer[] {9})));
                            break;
                            
                        case 10:
                            rewardLocations.get("ArcaneBoost").add(10);
                            break;
                            
                        default:
                            break;
                    }
                } else if (rewardString == "null") {
                    if (!rewardLocations.containsKey("")) rewardLocations.put("", new ArrayList<>(Arrays.asList(new Integer[] {i})));
                    else rewardLocations.get("").add(i);
                } else if (rewardLocations.containsKey(rewardString)) {
                    rewardLocations.get(rewardString).add(i);
                } else {
                    rewardLocations.put(rewardString, new ArrayList<>(Arrays.asList(new Integer[] {i})));
                }
            }

        } catch (IOException ex) {
            // Printing out a meaningful message to let the user know what will happen
            System.out.println(ex.getMessage());

            // Actual population of the HashMap
            rewardLocations.put("", new ArrayList<>(Arrays.asList(new Integer[] {0, 1})));
            rewardLocations.put("TimeWarp", new ArrayList<>(Arrays.asList(new Integer[] {2, 7})));
            rewardLocations.put("GreenBonus", new ArrayList<>(Arrays.asList(new Integer[] {3})));
            rewardLocations.put("ArcaneBoost", new ArrayList<>(Arrays.asList(new Integer[] {4, 10})));
            rewardLocations.put("RedBonus", new ArrayList<>(Arrays.asList(new Integer[] {5})));
            rewardLocations.put("ElementalCrest", new ArrayList<>(Arrays.asList(new Integer[] {6})));
            rewardLocations.put("BlueBonus", new ArrayList<>(Arrays.asList(new Integer[] {8})));
            rewardLocations.put("YellowBonus", new ArrayList<>(Arrays.asList(new Integer[] {9})));
        }

        populateMappedRewardLocation();
    }

    // This method is used to populate the MappedRewardLocation Array for faster and easier accessing of the "hit reward(s)" indices
    private void populateMappedRewardLocation() {
        // Iterate over the key-value pairs in the rewardLocations HashMap
        for (Map.Entry<String, ArrayList<Integer>> entry : rewardLocations.entrySet()) {
            String key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();

            for (int i = 0; i < value.size(); i++) {
                String rewardString;
                switch(key) {
                    case "RedBonus":
                        rewardString = getRewardString(key, value.get(i));
                        break;
                    case "GreenBonus":
                        rewardString = getRewardString(key, value.get(i));
                        break;
                    case "BlueBonus":
                        rewardString = getRewardString(key, value.get(i));
                        break;
                    case "MagentaBonus":
                        rewardString = getRewardString(key, value.get(i));
                        break;
                    case "YellowBonus":
                        rewardString = getRewardString(key, value.get(i));
                        break;
                    case "EssenceBonus":
                        rewardString = getRewardString(key, value.get(i));
                        break;
                    case "ElementalCrest":
                        rewardString = getRewardString(key, value.get(i));
                        break;
                    case "ArcaneBoost":
                        rewardString = getRewardString(key, value.get(i));
                        break;
                    case "TimeWarp":
                        rewardString = getRewardString(key, value.get(i));
                        break;
                    default:
                        rewardString = "";
                }

                mappedRewardLocations[value.get(i)] = rewardString;
            }
        }
    }

    private void updateAllPossibleMoves() {
        allPossibleMoves.clear();
        if (killedPhoenixes >= 11) {
            return;
        }
        int latestReceivedHit = phoenixes[killedPhoenixes - 1] % 6;

        for (int i = latestReceivedHit + 1; i <= 6; i++) {
            allPossibleMoves.add(new Move(new MagentaDice(i), this));
        }
    }

    private String getRewardString(String rewardName, int n) {
        String output = "X ";
        switch (rewardName) {
            case "RedBonus":
                for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
                    if (rewardLocations.get(rewardName).get(i) == n) {
                        output = phoenixes[rewardLocations.get(rewardName).get(i)] != null ? "X " : "RB";
                    }
                }
                break;
            
            case "GreenBonus":
                for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
                    if (rewardLocations.get(rewardName).get(i) == n) {
                        output = phoenixes[rewardLocations.get(rewardName).get(i)] != null ? "X " : "GB";
                    }
                }
                break;

            case "BlueBonus":
                for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
                    if (rewardLocations.get(rewardName).get(i) == n) {
                        output = phoenixes[rewardLocations.get(rewardName).get(i)] != null ? "X " : "BB";
                    }
                }
                break;

            case "MagentaBonus":
                for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
                    if (rewardLocations.get(rewardName).get(i) == n) {
                        output = phoenixes[rewardLocations.get(rewardName).get(i)] != null ? "X " : "MB";
                    }
                }
                break;

            case "YellowBonus":
                for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
                    if (rewardLocations.get(rewardName).get(i) == n) {
                        output = phoenixes[rewardLocations.get(rewardName).get(i)] != null ? "X " : "YB";
                    }
                }
                break;
            
            case "EssenceBonus":
                for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
                    if (rewardLocations.get(rewardName).get(i) == n) {
                        output = phoenixes[rewardLocations.get(rewardName).get(i)] != null ? "X " : "EB";
                    }
                }
                break;

            case "ElementalCrest":
                for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
                    if (rewardLocations.get(rewardName).get(i) == n) {
                        output = phoenixes[rewardLocations.get(rewardName).get(i)] != null ? "X " : "EC";
                    }
                }
                break;
        
            case "ArcaneBoost":
                for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
                    if (rewardLocations.get(rewardName).get(i) == n) {
                        output = phoenixes[rewardLocations.get(rewardName).get(i)] != null ? "X " : "AB";
                    }
                }
                break;
            
            case "TimeWarp":
                for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
                    if (rewardLocations.get(rewardName).get(i) == n) {
                        output = phoenixes[rewardLocations.get(rewardName).get(i)] != null ? "X " : "TW";
                    }
                }
                break;
            
            default:
                output = "X ";
        }

        return output;
    }
}
