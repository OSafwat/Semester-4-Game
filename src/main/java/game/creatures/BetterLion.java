package game.creatures;

import java.util.ArrayList;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.dice.Dice;
import game.engine.Move;
import game.exceptions.BonusException;
import game.exceptions.InvalidMoveException;

public class BetterLion extends Creature{

    @Override
    public int getElementalCrest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getElementalCrest'");
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
