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
        AI ai= new AI(PlayerStatus.ACTIVE);
        Dice [] diceset = new Dice[6];
        diceset[0] = new ArcanePrism(5);
        diceset[1] = new GreenDice(4);
        diceset[2] = new BlueDice(6);
        diceset[3] = new MagentaDice(4);
        diceset[4] = new YellowDice(4);
        diceset[5] = new RedDice(5);
        Dice res = cliGameController.pickBestDice(ai, diceset,1, 3);
        System.out.println(res.getRealm()+" "+res.getValue());
    
    }
}
