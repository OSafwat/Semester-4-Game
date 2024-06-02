package game.engine;

import game.creatures.Dragon;
import game.creatures.greenclasses.Gaia;
import game.dice.Dice;
import game.dice.GreenDice;
import game.dice.RedDice;
import game.engine.enums.RealmColor;
import game.exceptions.*;
import game.gui.DiceRealms;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Objects;

public class GUIGameController extends CLIGameController {

    int maxRounds;
    int maxTurns;
    int currentRound;
    int currentTurn;
    Exception exception;
    Player currentPlayer;

    public GUIGameController() {
        super();
        maxRounds = getSettings()[0];
        maxTurns = getSettings()[1];
        currentRound = 1;
        currentTurn = 1;
        currentPlayer = getPlayer1();
    }
    @Override
    public void startGame() {}

    @Override
    public boolean makeMove(Player player, Move move) {
        try {
            Dice diceToBeMovedWith= move.getDice();
            if (move.getCreature() instanceof Gaia) {
                GreenDice greenDice = (GreenDice) gameBoard.getGreen();
                Dice arcanePrism = gameBoard.getWhite();
                int greenVal = greenDice.getValue();
                int whiteVal = arcanePrism.getValue();
                diceToBeMovedWith = new GreenDice(greenVal+whiteVal);
            }
            boolean temp = player.getScoreSheet().getCreatureByColor(move.getDice().getRealm()).makeMove(diceToBeMovedWith);
            player.updateGameScore();
            player.updateAllPossibleMoves();
            if (!temp)
                throw new InvalidMoveException();
        } catch (BonusException bException) {
            player.updateGameScore();
            player.updateAllPossibleMoves();
            exception = bException;
            return false;
        }
        catch (InvalidMoveException Im){
            exception = Im;
            return false;
        }
        return true;
    }

    public boolean makeBonusMove(Player player, Dice dice) {
        Move[] allPossibleMoves = getAllPossibleMoves(getActivePlayer());
        Move move = null;
        for (int i = 0; i < allPossibleMoves.length; i++) {
            if (allPossibleMoves[i].compareTo(dice) == 0) {
                move = allPossibleMoves[i];
            }
        }
        if (Objects.equals(move, null)) {
            exception = new InvalidMoveException();
        }
        return makeMove(player, move);
    }

    public Player getPlayer1() {
        return getGameBoard().getPlayer1();
    }

    public Player getPlayer2() {
        return getGameBoard().getPlayer2();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public int[] getSettings() {
        return super.getSettings();
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setSelectedDragon(int dragon) {
        ((RedDice)getAllDice()[0]).selectsDragon(dragon);
    }

    public int getSelectedDragon() {
        return ((RedDice)getAllDice()[0]).getDragonNumber()+1;
    }

    public int getValue(String part) {
        int dragonValue = ((RedDice)getAllDice()[0]).getDragonNumber();
        Dragon dragon = ((Dragon)currentPlayer.getScoreSheet().getCreatureByColor(RealmColor.RED)).getDragons()[dragonValue];
        int result = -1;
        switch (part) {
            case "face": result = Objects.equals(dragon.getFace(), null) ? -1 : dragon.getFace(); break;
            case "wings": result = Objects.equals(dragon.getWings(), null) ? -1 : dragon.getWings(); break;
            case "tail": result = Objects.equals(dragon.getTail(), null) ? -1 : dragon.getTail(); break;
            case "heart": result = Objects.equals(dragon.getHeart(), null) ? -1 : dragon.getHeart(); break;
            default: ;
        }
        return result;
    }

    public Exception getException() {
        return new Exception(exception);
    }

    public void incrementTurnCount () {
        if (currentTurn == -1) {
            switchPlayer();
            currentPlayer = getActivePlayer();
            if (currentPlayer.getPlayerStatus() == getPlayer1().getPlayerStatus())
                incrementRoundCount();
            currentTurn = 1;
            return;
        }
        currentTurn++;
        if (currentTurn % (maxTurns+1) == 0) {
            currentTurn = -1;
            currentPlayer = getPassivePlayer();
        }
    }

    public void incrementRoundCount() {
        currentRound++;
    }

    public String[] getDragonPaths() {
        Dragon dragon = (Dragon) currentPlayer.getScoreSheet().getCreatureByColor(RealmColor.RED);
        Dragon[] dragons = dragon.getDragons();
        //images/RedRealmImages/face.png
        String[] paths = new String[4];
        for (int i = 0; i < 4; i++) {
            StringBuilder y = new StringBuilder("/images/RedRealmImages/");
            if (Objects.equals(dragons[i].getFace(), null))
                y.append("face-");
            if (Objects.equals(dragons[i].getWings(), null))
                y.append("wings-");
            if (Objects.equals(dragons[i].getTail(), null))
                y.append("tail-");
            if (Objects.equals(dragons[i].getHeart(),null))
                y.append("heart-");
            paths[i] = y.substring(0,y.length()-1) + ".png";
        }
        return paths;
    }

    public int getGreenCount() {
        Move[] moves = currentPlayer.getAllPossibleMoves();
        int count = 0;
        for (int i = 0; i < moves.length; i++) {
            if (moves[i].getDice().getRealm().equals(RealmColor.GREEN))
                count++;
        }
        return count;
    }

    public int getYellowCount() {
        Move[] moves = currentPlayer.getAllPossibleMoves();
        for (int i = 0; i < moves.length; i++) {
            if (moves[i].getDice().getRealm().equals(RealmColor.YELLOW))
                return 1;
        }
        return 0;
    }

    public int getMagentaCount() {
        Move[] moves = currentPlayer.getAllPossibleMoves();
        for (int i = 0; i < moves.length; i++) {
            if (moves[i].getDice().getRealm().equals(RealmColor.MAGENTA))
                return 1;
        }
        return 0;
    }

    public int getMaxRounds() {
        return maxRounds;
    }
}
