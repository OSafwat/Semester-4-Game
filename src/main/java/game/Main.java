package game;

import game.engine.*;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        System.out.println("Dice Realms: Quest for the Elemental Crests!");

        CLIGameController cliGameController = new CLIGameController();
        try {
            cliGameController.startGame();
        } catch (IOException e) {
            System.out.println("Error");
        }    
    }
}
