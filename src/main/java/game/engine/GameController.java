package game.engine;

import game.dice.*;
import game.creatures.*;
import game.collectibles.*;

/**
 * Abstract class representing the controller for the game.
 * This class defines the common blueprint for different controllers.
 */
public abstract class GameController {

    /**
     * Initializes necessary components and starts the game loop.
     */
    public abstract void startGame();

    /**
     * Switches the role of the current active player to passive and vice versa,
     * ensuring that the turn-taking mechanism functions correctly.
     *
     * @return {@code true} if the switch was successful,
     *         {@code false} otherwise.
     */
    public abstract boolean switchPlayer();

    /**
     * Rolls all available dice for the current turn, assigning each a random
     * number from 1 to 6.
     * 
     * @return An array of the currently rolled {@code Dice}.
     */
    public abstract Dice[] rollDice();

    /**
     * Gets the dice available for rolling or rerolling.
     * 
     * @return An array of {@code Dice} available for the current turn.
     */
    public abstract Dice[] getAvailableDice();

    /**
     * Gets all six dice, providing their current state and value within the
     * game regardless of their location or status. The dice could be in various
     * states, such as currently rolled and awaiting selection by the active player,
     * in the Forgotten Realm awaiting selection by the passive player, or already
     * assigned to a specific turn by the active player.
     *
     * @return An array of all six {@code Dice}, with each die's state and value.
     */
    public abstract Dice[] getAllDice();

    /**
     * Gets the dice currently available in the Forgotten Realm.
     *
     * @return An array of {@code Dice} that are currently in the Forgotten Realm.
     */
    public abstract Dice[] getForgottenRealmDice();

    /**
     * Gets all possible moves for all current rolled dice for the active player.
     * 
     * @return An array of all possible {@code Move} for all rolled dice.
     */
    public abstract Move[] getAllPossibleMoves();

    /**
     * Gets all possible moves for a given dice for the active player.
     * 
     * @param dice The dice to determine possible moves for.
     * @return An array of possible {@code Move} for the given dice.
     */
    public abstract Move[] getPossibleMoves(Dice dice);

    /**
     * Gets the current game board, including all players and all score sheets.
     * 
     * @return The current {@code GameBoard} object.
     */
    public abstract GameBoard getGameBoard();

    /**
     * Gets the current active player's information.
     * 
     * @return The active {@code Player} object.
     */
    public abstract Player getPlayer();

    /**
     * Gets the score sheet for the current active player.
     * 
     * @return The {@code ScoreSheet} object for the current active player.
     */
    public abstract ScoreSheet getScoreSheet();

    /**
     * Gets the current game status, including round and turn information for the
     * current active player.
     * 
     * @return The current {@code GameStatus} object.
     */
    public abstract GameStatus getGameStatus();

    /**
     * Gets the current score of the game, including scores in each realm, number of
     * elemental crests, and the total score for the current active player.
     * 
     * @return The current {@code GameScore} object.
     */
    public abstract GameScore getGameScore();

    /**
     * Gets the number of TimeWarp powers and their status for the active player.
     *
     * @return An array of {@code TimeWarp} objects representing the TimeWarp powers
     *         of the active player.
     */
    public abstract TimeWarp[] getTimeWarpPowers();

    /**
     * Gets the number of ArcaneBoost powers and their status for the active player.
     *
     * @return An array of {@code ArcaneBoost} objects representing the ArcaneBoost
     *         powers of the active player.
     */
    public abstract ArcaneBoost[] getArcaneBoostPowers();

    /**
     * Selects a dice and adds it to the current turn of the active player, moves
     * all other dice with less value to the Forgotten Realm.
     * 
     * @param dice The dice to be selected.
     * @return {@code true} if the selection was successful,
     *         {@code false} otherwise.
     */
    public abstract boolean selectDice(Dice dice);

    /**
     * Executes a move using the selected dice on a specified creature.
     *
     * @param dice     The dice selected by the active player for the move.
     * @param creature The target creature that the move is against.
     * @return {@code true} if the move is successfully completed,
     *         {@code false} otherwise.
     */
    public abstract boolean makeMove(Dice dice, Creature creature);

}
