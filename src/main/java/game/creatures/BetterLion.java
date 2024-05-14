package game.creatures;

import java.util.ArrayList;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.engine.Move;
import game.exceptions.BonusException;
import game.exceptions.InvalidMoveException;

public class BetterLion extends Creature{
    private final Properties properties;

    private int[] scores;
    private int score;
    private int lionsKilled;

    public BetterLion() {
        this.properties = new Properties();
        try {
            File config = new File("src/main/resources/config/RadiantSavannaRewards.properties");
            FileReader configReader = new FileReader(config);
            this.properties.load(configReader);
        } catch (IOException e) {
            System.out.println("Properties file reading failed.");
            this.properties.setProperty("hit1Reward", null);
            this.properties.setProperty("hit2Reward", null);
            this.properties.setProperty("hit3Reward", "TimeWarp");
            this.properties.setProperty("hit4Reward", null);
            this.properties.setProperty("hit5Reward", "RedBonus");
            this.properties.setProperty("hit6Reward", "ArcaneBoost");
            this.properties.setProperty("hit7Reward", null);
            this.properties.setProperty("hit8Reward", "ElementalCrest");
            this.properties.setProperty("hit9Reward", null);
            this.properties.setProperty("hit10Reward", "MagentaBonus");
            this.properties.setProperty("hit11Reward", null);
        }

        scores = new int[11];
        score = 0;
    }
    @Override
    public int getElementalCrest() {

    }

    @Override
    public String getScoreSheet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getScoreSheet'");
    }

    @Override
    public boolean checkMove(Dice dice) throws InvalidMoveException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkMove'");
    }

    @Override
    public boolean makeMove(Dice dice) throws BonusException, InvalidMoveException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeMove'");
    }

    @Override
    public ArrayList<TimeWarp> getAllTimeWarps() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTimeWarps'");
    }

    @Override
    public ArrayList<ArcaneBoost> getAllArcaneBoosts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllArcaneBoosts'");
    }

    @Override
    public ArrayList<Move> getAllPossibleMoves() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPossibleMoves'");
    }

}
