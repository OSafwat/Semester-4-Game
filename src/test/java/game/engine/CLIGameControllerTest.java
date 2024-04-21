package game.engine;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;

import game.dice.*;

public class CLIGameControllerTest {

    @Test
    public void testSwitchPlayer() {
        CLIGameController controller = new CLIGameController();

        assertEquals("Player 1 should be active", PlayerStatus.ACTIVE,
                controller.getGameBoard().getPlayer1().getPlayerStatus());
        assertEquals("Player 2 should be passive", PlayerStatus.PASSIVE,
                controller.getGameBoard().getPlayer2().getPlayerStatus());

        controller.switchPlayer();

        assertEquals("Player 1 should now be passive", PlayerStatus.PASSIVE,
                controller.getGameBoard().getPlayer1().getPlayerStatus());
        assertEquals("Player 2 should now be active", PlayerStatus.ACTIVE,
                controller.getGameBoard().getPlayer2().getPlayerStatus());
    }

    @Test
    public void testRollDice() {
        CLIGameController controller = new CLIGameController();
        controller.rollDice();

        Dice[] allDice = controller.getAllDice();
        Dice[] availableDice = controller.getAvailableDice();

        for (Dice die : allDice) {
            assertTrue("Dice should have a value between 1 and 6",
                    die.getValue() >= 1 && die.getValue() <= 6);
        }

        assertEquals("Number of available dice should match total number of dice",
                allDice.length, availableDice.length);

        for (Dice availableDie : availableDice) {
            boolean found = false;
            for (Dice die : allDice) {
                if (die == availableDie) {
                    found = true;
                    break;
                }
            }
            assertTrue("Available dice should be contained in all dice", found);
        }
    }

    @Test
    public void testDiceSelectionAndForgottenRealmDice() {
        CLIGameController controller = new CLIGameController();

        Dice[] dice = controller.getGameBoard().getDice();
        dice[0].setValue(2);
        dice[1].setValue(3);
        dice[2].setValue(4);
        dice[3].setValue(5);
        dice[4].setValue(6);
        dice[5].setValue(6);

        controller.selectDice(dice[4], controller.getActivePlayer());

        Dice[] testAllDice = controller.getAllDice();
        assertEquals("Should return all 6 dice", 6, testAllDice.length);

        Dice[] availableDice = controller.getAvailableDice();
        assertEquals("Should have 1 available dice", 1, availableDice.length);

        Dice[] forgottenRealmDice = controller.getForgottenRealmDice();
        assertEquals("Should have 4 dice in the Forgotten Realm", 4, forgottenRealmDice.length);
    }

    @Test
    public void testGetAllPossibleMoves() {
        CLIGameController controller = new CLIGameController();
        GameBoard gameBoard = controller.getGameBoard();
        Player player = controller.getActivePlayer();
        Move[] allPossibleMoves = controller.getAllPossibleMoves(player);

        assertEquals("There should be 41 possible moves", 41, allPossibleMoves.length);

        for (int i = 0; i < allPossibleMoves.length; i++) {
            if (i < 12) {
                assertEquals("Dice should be for the RED realm",
                        allPossibleMoves[i].getDice().getRealm(), gameBoard.getDice()[0].getRealm());
            } else if (i < 23) {
                assertEquals("Dice should be for the GREEN realm",
                        allPossibleMoves[i].getDice().getRealm(), gameBoard.getDice()[1].getRealm());
            } else if (i < 29) {
                assertEquals("Dice should be for the BLUE realm",
                        allPossibleMoves[i].getDice().getRealm(), gameBoard.getDice()[2].getRealm());
            } else if (i < 35) {
                assertEquals("Dice should be for the MAGENTA realm",
                        allPossibleMoves[i].getDice().getRealm(), gameBoard.getDice()[3].getRealm());
            } else {
                assertEquals("Dice should be for the YELLOW realm",
                        allPossibleMoves[i].getDice().getRealm(), gameBoard.getDice()[4].getRealm());
            }
        }
    }

    @Test
    public void testGetPossibleMovesForAvailableDice() {
        CLIGameController controller = new CLIGameController();
        Player player = controller.getActivePlayer();

        Dice[] dice = controller.getGameBoard().getDice();
        dice[0].setValue(2);
        dice[1].setValue(3);
        dice[2].setValue(4);
        dice[3].setValue(5);
        dice[4].setValue(6);
        dice[5].setValue(1);

        Move[] allPossibleMoves = controller.getPossibleMovesForAvailableDice(player);

        assertEquals("There should be 11 possible moves", 11, allPossibleMoves.length);

        List<Move> expectedMoves = new ArrayList<>(Arrays.asList(
                // for Dragon 1
                new Move(new RedDice(1), player.getScoreSheet().getCreatureByRealm(new RedDice(1))),
                // for Dragon 2
                new Move(new RedDice(1), player.getScoreSheet().getCreatureByRealm(new RedDice(1))),
                // for Dragon 1
                new Move(new RedDice(2), player.getScoreSheet().getCreatureByRealm(new RedDice(2))),
                // for Dragon 3
                new Move(new RedDice(2), player.getScoreSheet().getCreatureByRealm(new RedDice(2))),
                new Move(new GreenDice(4), player.getScoreSheet().getCreatureByRealm(new GreenDice(4))),
                new Move(new BlueDice(1), player.getScoreSheet().getCreatureByRealm(new BlueDice(1))),
                new Move(new BlueDice(4), player.getScoreSheet().getCreatureByRealm(new BlueDice(4))),
                new Move(new MagentaDice(1), player.getScoreSheet().getCreatureByRealm(new MagentaDice(1))),
                new Move(new MagentaDice(5), player.getScoreSheet().getCreatureByRealm(new MagentaDice(5))),
                new Move(new YellowDice(6), player.getScoreSheet().getCreatureByRealm(new YellowDice(1))),
                new Move(new YellowDice(1), player.getScoreSheet().getCreatureByRealm(new YellowDice(6)))));

        List<Move> actualMoves = new ArrayList<>(Arrays.asList(allPossibleMoves));

        List<Move> expectedMovesList = expectedMoves.stream().sorted().collect(Collectors.toList());
        List<Move> actualMovesList = actualMoves.stream().sorted().collect(Collectors.toList());

        assertEquals("The lists of expected and actual moves are not equal", expectedMovesList.toString(),
                actualMovesList.toString());
    }

    @Test
    public void testGetPossibleMovesForADie() {
        CLIGameController controller = new CLIGameController();
        GameBoard gameBoard = controller.getGameBoard();
        Player player = controller.getActivePlayer();
        Dice greenDie = controller.getGameBoard().getDice()[1];
        greenDie.setValue(2);
        Dice whiteDie = controller.getGameBoard().getDice()[5];
        whiteDie.setValue(4);
        Move[] possibleMoves = controller.getPossibleMovesForADie(player, whiteDie);

        assertEquals("There should be 6 possible moves", 6, possibleMoves.length);

        Move move = possibleMoves[0];
        Dice dice = move.getDice();
        assertEquals("Dice should be for the RED realm", dice.getRealm(), gameBoard.getDice()[0].getRealm());
        assertEquals("Dice values should be 4 for Dragon 3", 4, dice.getValue());

        move = possibleMoves[1];
        dice = move.getDice();
        assertEquals("Dice should be for the RED realm", dice.getRealm(), gameBoard.getDice()[0].getRealm());
        assertEquals("Dice values should be 4 for Dragon 4", 4, dice.getValue());

        move = possibleMoves[2];
        dice = move.getDice();
        assertEquals("Dice should be for the GREEN realm", dice.getRealm(), gameBoard.getDice()[1].getRealm());
        assertEquals("Dice values should be 6", 6, dice.getValue());

        move = possibleMoves[3];
        dice = move.getDice();
        assertEquals("Dice should be for the BLUE realm", dice.getRealm(), gameBoard.getDice()[2].getRealm());
        assertEquals("Dice values should be 4", 4, dice.getValue());

        move = possibleMoves[4];
        dice = move.getDice();
        assertEquals("Dice should be for the MAGENTA realm", dice.getRealm(), gameBoard.getDice()[3].getRealm());
        assertEquals("Dice values should be 4", 4, dice.getValue());

        move = possibleMoves[5];
        dice = move.getDice();
        assertEquals("Dice should be for the YELLOW realm", dice.getRealm(), gameBoard.getDice()[4].getRealm());
        assertEquals("Dice values should be 4", 4, dice.getValue());
    }

    @Test
    public void testScoreSheetToString() {
        CLIGameController controller = new CLIGameController();

        String emptyScoreSheet = controller.getScoreSheet(controller.getActivePlayer()).toString();

        String expectedScoreSheet = "\n\nScoreSheet\n\n" +
                "Emberfall Dominion: Pyroclast Dragon (RED REALM):\n" +
                "+-----------------------------------+\n" +
                "|  #  |D1   |D2   |D3   |D4   |R    |\n" +
                "+-----------------------------------+\n" +
                "|  F  |3    |6    |5    |X    |GB   |\n" +
                "|  W  |2    |1    |X    |5    |YB   |\n" +
                "|  T  |1    |X    |2    |4    |BB   |\n" +
                "|  H  |X    |3    |4    |6    |EC   |\n" +
                "+-----------------------------------+\n" +
                "|  S  |10   |14   |16   |20   |AB   |\n" +
                "+-----------------------------------+\n\n" +
                "\n" +
                "Terra's Heartland: Gaia Guardians (GREEN REALM):\n" +
                "+-----------------------------------+\n" +
                "|  #  |1    |2    |3    |4    |R    |\n" +
                "+-----------------------------------+\n" +
                "|  1  |X    |2    |3    |4    |YB   |\n" +
                "|  2  |5    |6    |7    |8    |RB   |\n" +
                "|  3  |9    |10   |11   |12   |EC   |\n" +
                "+-----------------------------------+\n" +
                "|  R  |TW   |BB   |MB   |AB   |     |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  S  |1    |2    |4    |7    |11   |16   |22   |29   |37   |46   |56   |\n" +
                "+-----------------------------------------------------------------------+\n\n" +
                "\n" +
                "Tide Abyss: Hydra Serpents (BLUE REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |H11  |H12  |H13  |H14  |H15  |H21  |H22  |H23  |H24  |H25  |H26  |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  H  |---  |---  |---  |---  |---  |---  |---  |---  |---  |---  |---  |\n" +
                "|  C  |≥1   |≥2   |≥3   |≥4   |≥5   |≥1   |≥2   |≥3   |≥4   |≥5   |≥6   |\n" +
                "|  R  |     |     |     |AB   |     |GB   |EC   |     |MB   |TW   |     |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  S  |1    |3    |6    |10   |15   |21   |28   |36   |45   |55   |66   |\n" +
                "+-----------------------------------------------------------------------+\n\n" +
                "\n" +
                "Mystical Sky: Majestic Phoenix (MAGENTA REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  H  |0    |0    |0    |0    |0    |0    |0    |0    |0    |0    |0    |\n" +
                "|  C  |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |\n" +
                "|  R  |     |     |TW   |GB   |AB   |RB   |EC   |TW   |BB   |YB   |AB   |\n" +
                "+-----------------------------------------------------------------------+\n\n" +
                "\n" +
                "Radiant Savanna: Solar Lion (YELLOW REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  H  |0    |0    |0    |0    |0    |0    |0    |0    |0    |0    |0    |\n" +
                "|  M  |     |     |     |x2   |     |     |x2   |     |x2   |     |x3   |\n" +
                "|  R  |     |     |TW   |     |RB   |AB   |     |EC   |     |MB   |     |\n" +
                "+-----------------------------------------------------------------------+\n\n";

        assertEquals("ScoreSheet display is wrong", expectedScoreSheet, emptyScoreSheet);
    }

    @Test
    public void testMakeMoveRedRealm() {
        CLIGameController controller = new CLIGameController();
        GameBoard gameBoard = controller.getGameBoard();
        Player player = controller.getActivePlayer();
        Dice[] dice = gameBoard.getDice();

        int[] redDiceValues = { 3, 2, 3, 1, 3 };
        int[] dragonNumber = { 1, 1, 2, 1, 3 };
        int[] expectedScores = { 0, 0, 0, 10, 10 };
        boolean[] expectedSuccess = { true, true, true, true, false };

        for (int i = 0; i < redDiceValues.length; i++) {
            dice[0].setValue(redDiceValues[i]);
            ((RedDice) dice[0]).selectsDragon(dragonNumber[i]);
            Move[] possibleMoves = controller.getPossibleMovesForADie(player, dice[0]);
            boolean hasMoves = possibleMoves.length > 0;
            boolean success = hasMoves && controller.makeMove(player, possibleMoves[0]);

            assertEquals("Expected success mismatch at move " + (i + 1), expectedSuccess[i], success);
            int actualScore = controller.getGameScore(player).getRedRealmScore();
            assertEquals("Expected score mismatch after move " + (i + 1), expectedScores[i], actualScore);
        }
    }

    @Test
    public void testMakeMoveGreenRealm() {
        CLIGameController controller = new CLIGameController();
        GameBoard gameBoard = controller.getGameBoard();
        Player player = controller.getActivePlayer();
        Dice[] dice = gameBoard.getDice();

        int[] greenDiceValues = { 2, 2, 3, 4 };
        int[] whiteDiceValues = { 4, 3, 2, 4 };
        int[] expectedScores = { 1, 2, 2, 4 };
        boolean[] expectedSuccess = { true, true, false, true };

        for (int i = 0; i < greenDiceValues.length; i++) {
            dice[1].setValue(greenDiceValues[i]);
            dice[5].setValue(whiteDiceValues[i]);
            Move[] possibleMoves = controller.getPossibleMovesForADie(player, dice[1]);
            boolean hasMoves = possibleMoves.length > 0;
            boolean success = hasMoves && controller.makeMove(player, possibleMoves[0]);

            assertEquals("Expected success mismatch at move " + (i + 1), expectedSuccess[i], success);
            int actualScore = controller.getGameScore(player).getGreenRealmScore();
            assertEquals("Expected score mismatch after move " + (i + 1), expectedScores[i], actualScore);
        }
    }

    @Test
    public void testMakeMoveBlueRealm() {
        CLIGameController controller = new CLIGameController();
        GameBoard gameBoard = controller.getGameBoard();
        Player player = controller.getActivePlayer();
        Dice[] dice = gameBoard.getDice();

        int[] blueDiceValues = { 4, 3, 2, 4 };
        int[] expectedScores = { 1, 3, 3, 6 };
        boolean[] expectedSuccess = { true, true, false, true };

        for (int i = 0; i < blueDiceValues.length; i++) {
            dice[2].setValue(blueDiceValues[i]);
            Move[] possibleMoves = controller.getPossibleMovesForADie(player, dice[2]);
            boolean hasMoves = possibleMoves.length > 0;
            boolean success = hasMoves && controller.makeMove(player, possibleMoves[0]);

            assertEquals("Expected success mismatch at move " + (i + 1), expectedSuccess[i], success);
            int actualScore = controller.getGameScore(player).getBlueRealmScore();
            assertEquals("Expected score mismatch after move " + (i + 1), expectedScores[i], actualScore);
        }
    }

    @Test
    public void testMakeMoveMagentaRealm() {
        CLIGameController controller = new CLIGameController();
        GameBoard gameBoard = controller.getGameBoard();
        Player player = controller.getActivePlayer();
        Dice[] dice = gameBoard.getDice();

        int[] magentaDiceValues = { 6, 2, 2, 4 };
        int[] expectedScores = { 6, 8, 8, 12 };
        boolean[] expectedSuccess = { true, true, false, true };

        for (int i = 0; i < magentaDiceValues.length; i++) {
            dice[3].setValue(magentaDiceValues[i]);
            Move[] possibleMoves = controller.getPossibleMovesForADie(player, dice[3]);
            boolean hasMoves = possibleMoves.length > 0;
            boolean success = hasMoves && controller.makeMove(player, possibleMoves[0]);

            assertEquals("Expected success mismatch at move " + (i + 1), expectedSuccess[i], success);
            int actualScore = controller.getGameScore(player).getMagentaRealmScore();
            assertEquals("Expected score mismatch after move " + (i + 1), expectedScores[i], actualScore);
        }
    }

    @Test
    public void testMakeMoveYellowRealm() {
        CLIGameController controller = new CLIGameController();
        GameBoard gameBoard = controller.getGameBoard();
        Player player = controller.getActivePlayer();
        Dice[] dice = gameBoard.getDice();

        int[] yellowDiceValues = { 1, 2, 3, 4 };
        int[] expectedScores = { 1, 3, 6, 14 };
        boolean[] expectedSuccess = { true, true, true, true };

        for (int i = 0; i < yellowDiceValues.length; i++) {
            dice[4].setValue(yellowDiceValues[i]);
            Move[] possibleMoves = controller.getPossibleMovesForADie(player, dice[4]);
            boolean hasMoves = possibleMoves.length > 0;
            boolean success = hasMoves && controller.makeMove(player, possibleMoves[0]);

            assertEquals("Expected success mismatch at move " + (i + 1), expectedSuccess[i], success);
            int actualScore = controller.getGameScore(player).getYellowRealmScore();
            assertEquals("Expected score mismatch after move " + (i + 1), expectedScores[i], actualScore);
        }
    }

}
