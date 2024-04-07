package game.engine;

import game.dice.*;
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
     * Gets all possible moves for a given player.
     *
     * @param player The player for whom to determine possible moves.
     * @return An array of all possible moves for all rolled dice.
     */
    public abstract Move[] getAllPossibleMoves(Player player);

    /**
     * Gets possible moves for all currently rolled dice for a given player.
     *
     * @param player The player for whom to determine possible moves.
     * @return An array of all possible moves for all rolled dice.
     */
    public abstract Move[] getPossibleMovesForAvailableDice(Player player);

    /**
     * Gets all possible moves for a given die for a given player.
     *
     * @param player The player for whom to determine possible moves.
     * @param dice   The dice to determine possible moves for.
     * @return An array of possible moves for the given dice.
     */
    public abstract Move[] getPossibleMovesForADie(Player player, Dice dice);

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
    public abstract Player getActivePlayer();

    /**
     * Gets the current passive player's information.
     * 
     * @return The passive {@code Player} object.
     */
    public abstract Player getPassivePlayer();

    /**
     * Gets the score sheet for a given player.
     * 
     * @param player The player to get the current score sheet for.
     * @return The {@code ScoreSheet} object for a given player.
     */
    public abstract ScoreSheet getScoreSheet(Player player);

    /**
     * Gets the current game status, including round and turn information for the
     * current active player.
     * 
     * @return The current {@code GameStatus} object.
     */
    public abstract GameStatus getGameStatus();

    /**
     * Gets the current score of the game, including scores in each realm, number of
     * elemental crests, and the total score for a given player.
     * 
     * @param player The player to determine current score for.
     * @return The current {@code GameScore} object.
     */
    public abstract GameScore getGameScore(Player player);

    /**
     * Gets the array of TimeWarp powers and their status for a given player.
     *
     * @param player The player to get the current TimeWarp powers for.
     * @return An array of {@code TimeWarp} objects representing the TimeWarp powers
     *         for a given player.
     */
    public abstract TimeWarp[] getTimeWarpPowers(Player player);

    /**
     * Gets the array of ArcaneBoost powers and their status for a given player.
     *
     * @param player The player to get the current ArcaneBoost powers for.
     * @return An array of {@code ArcaneBoost} objects representing the ArcaneBoost
     *         powers for a given player.
     */
    public abstract ArcaneBoost[] getArcaneBoostPowers(Player player);

    /**
     * Selects a die and adds it to the player class, then moves
     * all other dice with less value to the Forgotten Realm.
     * 
     * @param player The player who selected the die.
     * @param dice   The dice to be selected.
     * @return {@code true} if the selection was successful,
     *         {@code false} otherwise.
     */
    public abstract boolean selectDice(Dice dice, Player player);

    /**
     * Executes a move using the selected dice on a specified creature.
     *
     * @param player The player who wants to make the move.
     * @param move   The move to be executed, including the selected dice and
     *               target creature.
     * @return {@code true} if the move is successfully completed,
     *         {@code false} otherwise.
     */
    public abstract boolean makeMove(Player player, Move move);
}
