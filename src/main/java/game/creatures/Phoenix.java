package game.creatures;

import java.util.ArrayList;

import game.dice.Dice;
import game.dice.MagentaDice;
import game.engine.Move;

public class Phoenix extends Creature{
    public Integer[] phoenixsReceivedHP;
    public ArrayList<Move> allPossibleMoves;

    public Phoenix() {
        phoenixsReceivedHP = new Integer[11];
        initPossibleMoves();
    }

    @Override
    public int getElementalCrest() {
    }

    @Override
    public String getScoreSheet() {
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

    public String getGreenBoostString() {
        return phoenixsReceivedHP[3] != null ? "X" : "GB";
    }

    public String getYellowBoostString() {
        return phoenixsReceivedHP[9] != null ? "X" : "YB";
    }

    public String getBlueBoostString() {
        return phoenixsReceivedHP[8] != null ? "X" : "BB";
    }

    public String getElementalCrestString() {
        return phoenixsReceivedHP[6] != null ? "X" : "EC";
    }

    public String getArcaneBoostString(int n) {
        return phoenixsReceivedHP[n] != null ? "X" : "AB";
    }
}
