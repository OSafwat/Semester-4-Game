package game;

import game.engine.*;
public class Main {
    public static void main(String[] args) {
        System.out.println("Dice Realms: Quest for the Elemental Crests!");
        int pls = -1;
        CLIGameController cliGameController = new CLIGameController();
        cliGameController.startGame();
    }
}
