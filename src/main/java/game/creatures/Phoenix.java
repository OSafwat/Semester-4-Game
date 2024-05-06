package game.creatures;

import java.io.*;
import java.util.*;
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
    public static HashMap<String, ArrayList<Integer>> rewardLocations = new HashMap<>();
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
    public boolean checkMove(Dice dice) throws InvalidDiceSelectionException, InvalidMoveException {
        int diceValue = dice.getValue();
        if ((dice instanceof MagentaDice || dice instanceof ArcanePrism) && diceValue <= 6 && diceValue > 0) {
            if (killedPhoenixes == 0) {
                return true;
            } else {
                if (diceValue > phoenixsReceivedHP[killedPhoenixes - 1]) {
                    return true;
                }
                else throw new InvalidMoveException("Invalid Move Exception");
            }
        } else throw new InvalidDiceSelectionException("Invalid Dice used for the Magenta Class");
    }

    @Override
    public boolean makeMove(Dice dice) throws InvalidDiceSelectionException, InvalidMoveException {
        if (checkMove(dice)) {
            int diceValue = dice.getValue();
            phoenixsReceivedHP[killedPhoenixes++] = diceValue;

            ArrayList<Integer> TimeWarpArrayList = rewardLocations.get("TimeWarp");
            ArrayList<Integer> ArcaneBoostArrayList = rewardLocations.get("ArcaneBoost");
            
            for (int i = 0; i < TimeWarpArrayList.size(); i++) {
                if (TimeWarpArrayList.get(i) == killedPhoenixes) allTimeWarps.add(new TimeWarp());
            }

            for (int i = 0; i < TimeWarpArrayList.size(); i++) {
                if (TimeWarpArrayList.get(i) == killedPhoenixes) allArcaneBoosts.add(new ArcaneBoost());
            }

            return true;
        }

        return false;
    }

    @Override
    public Move[] getAllPossibleMoves() {
        Move[] returnedArray = new Move[allPossibleMoves.size()];
        return allPossibleMoves.toArray(returnedArray);
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
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/MysticalSkyRewards.properties")) {
            if (input == null) throw new IOException("Config file not found, default configuration will be used");

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and store them in the HashSet rewardLocation
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

    public void populateMappedRewardLocation() {
        // Iterate over the key-value pairs in the rewardLocations HashMap
        for (Map.Entry<String, ArrayList<Integer>> entry : rewardLocations.entrySet()) {
            String key = entry.getKey();
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
        return phoenixsReceivedHP[rewardLocations.get(rewardName).get(n)] != null ? "X " : "RB";
    }

    public String getGreenBonusString(int n) {
        String rewardName = "GreenBonus";
        return phoenixsReceivedHP[rewardLocations.get(rewardName).get(n)] != null ? "X " : "GB";
    }

    public String getBlueBonusString(int n) {
        String rewardName = "BlueBonus";
        return phoenixsReceivedHP[rewardLocations.get(rewardName).get(n)] != null ? "X " : "BB";
    }

    public String getMagentaBonusString(int n) {
        String rewardName = "MagentaBonus";
        return phoenixsReceivedHP[rewardLocations.get(rewardName).get(n)] != null ? "X " : "MB";
    }

    public String getYellowBonusString(int n) {
        String rewardName = "YellowBonus";
        return phoenixsReceivedHP[rewardLocations.get(rewardName).get(n)] != null ? "X " : "YB";
    }

    public String getElementalCrestString(int n) {
        String rewardName = "ElementalCrest";
        return phoenixsReceivedHP[rewardLocations.get(rewardName).get(n)] != null ? "X " : "EC";
    }

    public String getArcaneBoostString(int n) {
        String rewardName = "ArcaneBoost";
        return phoenixsReceivedHP[rewardLocations.get(rewardName).get(n)] != null ? "X " : "AB";
    }

    public String getTimeWarpString(int n) {
        String rewardName = "TimeWarp";
        return phoenixsReceivedHP[rewardLocations.get(rewardName).get(n)] != null ? "X " : "TW";
    }
}
