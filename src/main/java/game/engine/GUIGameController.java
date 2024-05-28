package game.engine;

import game.creatures.greenclasses.Gaia;
import game.dice.Dice;
import game.dice.GreenDice;
import game.engine.enums.RealmColor;
import game.exceptions.*;
import game.gui.DiceRealms;
import javafx.scene.image.ImageView;

public class GUIGameController extends CLIGameController {

    int maxRounds;
    int maxTurns;
    int currentRound;
    int currentTurn;
    Exception exception;

    public GUIGameController() {
        super();
        maxRounds = getSettings()[0];
        maxTurns = getSettings()[1];
        currentRound = 1;
        currentTurn = 1;
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
            if (!temp)
                throw new InvalidMoveException();
            else {
                player.updateGameScore();
                player.updateAllPossibleMoves();
                return true;
            }
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
    }

    public Player getPlayer1() {
        return getGameBoard().getPlayer1();
    }

    public Player getPlayer2() {
        return getGameBoard().getPlayer2();
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

    public Exception getException() {
        return new Exception(exception);
    }
}
