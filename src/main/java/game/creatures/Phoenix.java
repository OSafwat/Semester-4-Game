package game.creatures;

import java.io.*;
import java.util.*;

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
    public static HashMap<String, Integer> rewardLocations = new HashMap<>();
    public static String[] mappedRewardLocations = new String[11];

    public Phoenix() {
        phoenixsReceivedHP = new Integer[11];
        killedPhoenixes = 0;
        initPossibleMoves();
        populateRewardLocationFromConfigFile();
    }

    @Override
    public int getElementalCrest() {
        String rewardName = "ElementalCrest";
        return phoenixsReceivedHP[rewardLocations.get(rewardName)] != null ? 1 : 0;
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
            if (rewardLocations == null) sb.append("     |");
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
        }
    }

    @Override
    public Move[] getAllPossibleMoves() {
        Move[] returnedArray = new Move[allPossibleMoves.size()];
        return allPossibleMoves.toArray(returnedArray);
    }

    public void initPossibleMoves() {
        for (int i = 0; i < 6; i++) {
            allPossibleMoves.add(new Move(new MagentaDice(i + 1), this));
        }
    }

    //implementing the config file reading
    public void populateRewardLocationFromConfigFile() {
        try (InputStream input = new FileInputStream("../../../resources/config/MysticalSkyRewards.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and store them in the HashSet rewardLocation
            ArrayList<Object> valueSet = new ArrayList<>();
            valueSet.addAll(new LinkedHashSet<>(prop.values()));
            int counter = 0;
            for (Object value : valueSet) {
                rewardLocations.put((String) value, counter++);
            }

        } catch (IOException ex) {
            // Printing out a meaningful message to let the user know what will happen
            System.out.println("Config file not found, deafult configuration will be used");

            // Actual population of the HashMap
            rewardLocations.put(null, 0);
            rewardLocations.put(null, 1);
            rewardLocations.put("TimeWarp", 2);
            rewardLocations.put("GreenBonus", 3);
            rewardLocations.put("ArcaneBoost", 4);
            rewardLocations.put("RedBonus", 5);
            rewardLocations.put("ElementalCrest", 6);
            rewardLocations.put("TimeWarp", 7);
            rewardLocations.put("BlueBonus", 8);
            rewardLocations.put("YellowBonus", 9);
            rewardLocations.put("ArcaneBoost", 10);
        }
    }

    public void populateMappedRewardLocation() {
        // Iterate over the key-value pairs in the rewardLocations HashMap
        for (Map.Entry<String, Integer> entry : rewardLocations.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            
            String rewardString;
            switch(key) {
                case "RedBonus":
                    rewardString = getRedBonusString();
                    break;
                case "GreenBonus":
                    rewardString = getGreenBonusString();
                    break;
                case "BlueBonus":
                    rewardString = getBlueBonusString();
                    break;
                case "MagentaBonus":
                    rewardString = getMagentaBonusString();
                    break;
                case "YellowBonus":
                    rewardString = getYellowBonusString();
                    break;
                case "ElementalCrest":
                    rewardString = getElementalCrestString();
                    break;
                case "ArcaneBoost":
                    rewardString = getArcaneBoostString(value);
                    break;
                case "TimeWarp":
                    rewardString = getTimeWarpString(value);
                    break;
                default:
                    rewardString = null;
            }

            mappedRewardLocations[value] = rewardString;
        }
    }

    public String getRedBonusString() {
        String rewardName = "RedBonus";
        return phoenixsReceivedHP[rewardLocations.get(rewardName)] != null ? "X" : "RB";
    }

    public String getGreenBonusString() {
        String rewardName = "GreenBonus";
        return phoenixsReceivedHP[rewardLocations.get(rewardName)] != null ? "X" : "GB";
    }

    public String getBlueBonusString() {
        String rewardName = "BlueBonus";
        return phoenixsReceivedHP[rewardLocations.get(rewardName)] != null ? "X" : "BB";
    }

    public String getMagentaBonusString() {
        String rewardName = "MagentaBonus";
        return phoenixsReceivedHP[rewardLocations.get(rewardName)] != null ? "X" : "MB";
    }

    public String getYellowBonusString() {
        String rewardName = "YellowBonus";
        return phoenixsReceivedHP[rewardLocations.get(rewardName)] != null ? "X" : "YB";
    }

    public String getElementalCrestString() {
        String rewardName = "ElementalCrest";
        return phoenixsReceivedHP[rewardLocations.get(rewardName)] != null ? "X" : "EC";
    }

    public String getArcaneBoostString(int n) {
        String rewardName = "ArcaneBoost";
        return phoenixsReceivedHP[rewardLocations.get(rewardName)] != null ? "X" : "AB";
    }

    public String getTimeWarpString(int n) {
        String rewardName = "TimeWarp";
        return phoenixsReceivedHP[rewardLocations.get(rewardName)] != null ? "X" : "TW";
    }
}
