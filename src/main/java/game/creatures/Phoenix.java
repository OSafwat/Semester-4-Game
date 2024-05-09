package game.creatures;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.ArcanePrism;
import game.dice.Dice;
import game.dice.MagentaDice;
import game.engine.Move;
import game.exceptions.InvalidDiceSelectionException;
import game.exceptions.InvalidMoveException;

public class Phoenix extends Creature{
    public Integer[] phoenixsReceivedHP;
    int killedPhoenixes;
    public ArrayList<Move> allPossibleMoves;
    // A hash map that maps the rewards to their respective phoenix's death amounts
    public static HashMap<String, ArrayList<Integer>> rewardLocations = new HashMap<>();
    // A String array that stores the mapping from the Hash Map rewardLocations for easier and faster accessing
    public static String[] mappedRewardLocations = new String[11];
    public ArrayList<TimeWarp> allTimeWarps;
    public ArrayList<ArcaneBoost> allArcaneBoosts;

    public Phoenix() {
        phoenixsReceivedHP = new Integer[11];
        killedPhoenixes = 0;
        allTimeWarps = new ArrayList<>();
        allArcaneBoosts = new ArrayList<>();
        allPossibleMoves = new ArrayList<>();
        initPossibleMoves();
        populateRewardLocationFromConfigFile();
    }

    @Override
    public int getElementalCrest() {
        String rewardName = "ElementalCrest";
        ArrayList<Integer> rewardLocationsArray = rewardLocations.get(rewardName);

        int counter = 0;
        for (int i = 0; i < rewardLocationsArray.size(); i++) {
            if (phoenixsReceivedHP[rewardLocationsArray.get(i)] != null) counter++;
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
        // for loop to loop on the phoenixsReceivedHP array which stored the hits received by each pheonix
        for (int i = 0; i < 11; i++) {
            if (phoenixsReceivedHP[i] == null) sb.append("0    |");
            else sb.append(i + "    |" );
        }
        sb.append("\n");

        sb.append("|  C  |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |\n");
        sb.append("|  R  |");

        for (int i = 0 ; i < 11; i++) {
            String rewardToken = mappedRewardLocations[i];
            if (rewardToken == null) sb.append("     |");
            else sb.append(rewardToken + "   |");
        }

        sb.append("\n");

        sb.append("+-----------------------------------------------------------------------+\n\n");

        return sb.toString();
    }

    @Override
    public boolean checkMove(Dice dice) {
        int diceValue = dice.getValue();
        if ((dice instanceof MagentaDice || dice instanceof ArcanePrism) && diceValue <= 6 && diceValue > 0) {
            if (killedPhoenixes == 0 || diceValue > phoenixsReceivedHP[killedPhoenixes - 1]) return true;
        }

        return false;
    }

    @Override
    public boolean makeMove(Dice dice) throws InvalidMoveException {
        if (checkMove(dice)) {
            int diceValue = dice.getValue();
            phoenixsReceivedHP[killedPhoenixes++] = diceValue;

            ArrayList<Integer> TimeWarpArrayList = rewardLocations.get("TimeWarp");
            ArrayList<Integer> ArcaneBoostArrayList = rewardLocations.get("ArcaneBoost");
            
            for (int i = 0; i < TimeWarpArrayList.size(); i++) {
                if (TimeWarpArrayList.get(i) == killedPhoenixes) allTimeWarps.add(new TimeWarp());
            }

            for (int i = 0; i < ArcaneBoostArrayList.size(); i++) {
                if (ArcaneBoostArrayList.get(i) == killedPhoenixes) allArcaneBoosts.add(new ArcaneBoost());
            }

            return true;
        }

        return false;
    }

    @Override
    public ArrayList<Move> getAllPossibleMoves() {
        return allPossibleMoves;
    }

    @Override
    public ArrayList<TimeWarp> getAllTimeWarps() {
        return allTimeWarps;
    }

    @Override
    public ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        return allArcaneBoosts;
    }

    public void initPossibleMoves() {
        for (int i = 0; i < 6; i++) {
            allPossibleMoves.add(new Move(new MagentaDice(i + 1), this));
        }
    }

    //implementing the config file reading
    public void populateRewardLocationFromConfigFile() {
        // Trying to read from the config (.properties file) the realm configuration
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/MysticalSkyRewards.properties")) {
            if (input == null) throw new IOException("Config file not found, default configuration will be used");

            /* Predefined java class that makes a HashMap with String keys and String values from the config
             * file by making the keys the text before the equal sign, and the value the text after the equal
             * sign in each line of the config file
             */
            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and store them in the HashMap rewardLocation
            if (prop.isEmpty() || prop.size() < 11) throw new IOException("Properties file is empty or contains fewer than 11 properties");

            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);

                /* The Pattern and Matcher classes are predefined java classes. Pattern is a class that is used for defining regex expression
                 * that would be matched later using the Matcher class to parse strings for desired values. In this case, to avoid any conflicts
                 * upon changing the "hit Reward" identifying text in the config files, a regex expression is used to parse the text for the "hit"
                 * number, to be able to store the index in the respective HashMap / Array depending on the use
                 */
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
            // Printing out a meaningful message to let the user know what will happen
            System.out.println(ex.getMessage());

            // Actual population of the HashMap
            rewardLocations.put(null, new ArrayList<>(Arrays.asList(new Integer[] {0, 1})));
            rewardLocations.put("TimeWarp", new ArrayList<>(Arrays.asList(new Integer[] {2, 7})));
            rewardLocations.put("GreenBonus", new ArrayList<>(Arrays.asList(new Integer[] {3})));
            rewardLocations.put("ArcaneBoost", new ArrayList<>(Arrays.asList(new Integer[] {4, 10})));
            rewardLocations.put("RedBonus", new ArrayList<>(Arrays.asList(new Integer[] {5})));
            rewardLocations.put("ElementalCrest", new ArrayList<>(Arrays.asList(new Integer[] {6})));
            rewardLocations.put("BlueBonus", new ArrayList<>(Arrays.asList(new Integer[] {8})));
            rewardLocations.put("YellowBonus", new ArrayList<>(Arrays.asList(new Integer[] {9})));
        }
    }

    // This method is used to populate the MappedRewardLocation Array for faster and easier accessing of the "hit reward(s)" indices
    public void populateMappedRewardLocation() {
        // Iterate over the key-value pairs in the rewardLocations HashMap
        for (Map.Entry<String, ArrayList<Integer>> entry : rewardLocations.entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
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
                output = phoenixsReceivedHP[rewardLocations.get(rewardName).get(i)] != null ? "X " : "RB";
            }
        }
        return output;
    }

    public String getGreenBonusString(int n) {
        String rewardName = "GreenBonus";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = phoenixsReceivedHP[rewardLocations.get(rewardName).get(i)] != null ? "X " : "GB";
            }
        }
        return output;
    }

    public String getBlueBonusString(int n) {
        String rewardName = "BlueBonus";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = phoenixsReceivedHP[rewardLocations.get(rewardName).get(i)] != null ? "X " : "BB";
            }
        }
        return output;
    }

    public String getMagentaBonusString(int n) {
        String rewardName = "MagentaBonus";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = phoenixsReceivedHP[rewardLocations.get(rewardName).get(i)] != null ? "X " : "MB";
            }
        }
        return output;
    }

    public String getYellowBonusString(int n) {
        String rewardName = "YellowBonus";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = phoenixsReceivedHP[rewardLocations.get(rewardName).get(i)] != null ? "X " : "YB";
            }
        }
        return output;
    }

    public String getElementalCrestString(int n) {
        String rewardName = "ElementalCrest";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = phoenixsReceivedHP[rewardLocations.get(rewardName).get(i)] != null ? "X " : "EC";
            }
        }
        return output;
    }

    public String getArcaneBoostString(int n) {
        String rewardName = "ArcaneBoost";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = phoenixsReceivedHP[rewardLocations.get(rewardName).get(i)] != null ? "X " : "AB";
            }
        }
        return output;
    }

    public String getTimeWarpString(int n) {
        String rewardName = "TimeWarp";
        String output = "X ";
        for (int i = 0; i < rewardLocations.get(rewardName).size(); i++) {
            if (rewardLocations.get(rewardName).get(i) == n) {
                output = phoenixsReceivedHP[rewardLocations.get(rewardName).get(i)] != null ? "X " : "TW";
            }
        }
        return output;
    }
}
