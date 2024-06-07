package game;

import game.dice.ArcanePrism;
import game.dice.BlueDice;
import game.dice.Dice;
import game.dice.GreenDice;
import game.dice.MagentaDice;
import game.dice.RedDice;
import game.dice.YellowDice;
import game.engine.*;
public class Main {
    public static void main(String[] args) {
        System.out.println("Dice Realms: Quest for the Elemental Crests!");
        CLIGameController cliGameController = new CLIGameController();
        cliGameController.startGame();
        System.out.println(cliGameController.getActivePlayer().getArcaneBoostsNum());
    }
}
