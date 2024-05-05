package game.creatures;

import java.io.*;
import java.util.*;

import game.dice.Dice;
import game.dice.MagentaDice;
import game.engine.Move;

public class Phoenix extends Creature{
    public Integer[] phoenixsReceivedHP;
    public ArrayList<Move> allPossibleMoves;
    public static HashMap<String, Integer> rewardLocations = new HashMap<>();

    public Phoenix() {
        phoenixsReceivedHP = new Integer[11];
        initPossibleMoves();
        populateRewardLocationFromConfigFile();
    }

    @Override
    public int getElementalCrest() {
        String boostName = "ElementalCrest";
        return phoenixsReceivedHP[rewardLocations.get(boostName)] != null ? 1 : 0;
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
        sb.append("|  R  |     |     |TW   |GB   |AB   |RB   |EC   |TW   |BB   |YB   |AB   |\n");
        sb.append("+-----------------------------------------------------------------------+\n\n");

        return sb.toString();
    }

    @Override
    public boolean checkMove(Dice dice) {
    }

    @Override
    public boolean makeMove(Dice dice) {
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

    public String getRedBoostString() {
        String boostName = "RedBonus";
        return phoenixsReceivedHP[rewardLocations.get(boostName)] != null ? "X" : "RB";
    }

    public String getGreenBoostString() {
        String boostName = "GreenBonus";
        return phoenixsReceivedHP[rewardLocations.get(boostName)] != null ? "X" : "GB";
    }

    public String getBlueBoostString() {
        String boostName = "GreenBonus";
        return phoenixsReceivedHP[rewardLocations.get(boostName)] != null ? "X" : "BB";
    }

    public String getMagentaBoostString() {
        String boostName = "MagentaBonus";
        return phoenixsReceivedHP[rewardLocations.get(boostName)] != null ? "X" : "MB";
    }

    public String getYellowBoostString() {
        String boostName = "YellowBonus";
        return phoenixsReceivedHP[rewardLocations.get(boostName)] != null ? "X" : "YB";
    }

    public String getElementalCrestString() {
        String boostName = "ElementalCrest";
        return phoenixsReceivedHP[rewardLocations.get(boostName)] != null ? "X" : "EC";
    }

    public String getArcaneBoostString(int n) {
        String boostName = "ArcaneBoost";
        return phoenixsReceivedHP[rewardLocations.get(boostName)] != null ? "X" : "AB";
    }

    public String getTimeWarpString(int n) {
        String boostName = "TimeWarp";
        return phoenixsReceivedHP[rewardLocations.get(boostName)] != null ? "X" : "TW";
    }
}
