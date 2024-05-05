package game.engine;

import game.collectibles.*;
import game.exceptions.BonusException;
import game.exceptions.InvalidMoveException;
import game.dice.*;
import game.creatures.*;
import game.engine.enums.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLIGameController {
    GameBoard gameBoard;
    String rewardsArray[];

    // constructor(s):
    public CLIGameController() {
    }
    public int [] getSettings() throws IOException{
        Scanner scanner = new Scanner(System.in);
        int numberOfRounds;
        int numebrOfTurnsPerRound;
        BufferedReader settings=null;
        //the following is taking in the game settings from the RoundsSettings file
        try {
            // opening the file
            FileReader SettingsfileReader = new FileReader(
                    "dice-realms-game-dimension/src/main/resources/RoundsSettings.properties");
            settings = new BufferedReader(SettingsfileReader);

            // taking in input from the file which is currently only 2
            String line1 = settings.readLine();
            String[] lineOfRounds = line1.split("=");
            numberOfRounds = Integer.parseInt(lineOfRounds[1]);

            String line2 = settings.readLine();
            String[] lineOfTurns = line1.split("=");
            numebrOfTurnsPerRound = Integer.parseInt(lineOfTurns[1]);
        } catch (FileNotFoundException f) {

            System.err.println("the Settings file was not able to be accessed");
            System.out.println("please enter the number of desired rounds:");
            numberOfRounds = scanner.nextInt();

            System.out.println("please enter the number of desired turns per round:");
            numebrOfTurnsPerRound = scanner.nextInt();
        } catch (IOException e) {
            System.out.println("there has been an error in IO other than fileNotFound");
            e.printStackTrace();
            numberOfRounds = 6;
            numebrOfTurnsPerRound= 3;

        } finally{
            if (settings != null)
                settings.close();
        }
        int temp [] =  {numberOfRounds, numebrOfTurnsPerRound};
        return temp;
    }

    public Reward [] getRewards(int numberOfRounds) throws IOException{

        BufferedReader rewardsFile=null;
        Reward[] rewards= new Reward [numberOfRounds] ;
        try {
            // opening the file
            FileReader rewardsFileReader = new FileReader("dice-realms-game-dimension/src/main/resources/RoundsRewards.properties");
            rewardsFile = new BufferedReader(rewardsFileReader);

            // taking in input from the file which is currently only 2
            String rewardsline ;
            int rewardsCounter = 0;
            while ((rewardsline  = rewardsFile.readLine()) != null){
                String reward = rewardsline.split("=")[1];
                switch (reward){
                    case "TimeWarp" : rewards[rewardsCounter++] = new TimeWarp();            break;
                    case "ArcaneBoost": rewards[rewardsCounter++] = new ArcaneBoost();       break;
                    case "EssenceBonus": rewards[rewardsCounter++] = new EssenceBonus();     break;
                    case "ElementalCrest": rewards[rewardsCounter++] = new ElementalCrest(); break;
                    default: rewards[rewardsCounter++] =null; 
                }
            }
        } catch (FileNotFoundException  e) {

            System.err.println("the Rewards file was not able to be accessed therefore default rewards will be used");
            rewards[0]=new TimeWarp();
            rewards[1] = new ArcaneBoost();
            rewards[2] = new TimeWarp();
            rewards[3] = new EssenceBonus();
            rewards[4] = null;
            rewards[5] = null;            
        } catch (IOException e) {
            System.out.println("there has been an error in IO other than fileNotFound");
            e.printStackTrace();
        } finally{
            if (rewardsFile != null)
                rewardsFile.close();
        }

        return rewards;
    }

    public void startGame() throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("please input the name of player 1:");
        String player1Name = scanner.nextLine();
        System.out.println("please input the name of player 2:");
        String player2Name = scanner.nextLine();
        this.gameBoard = new GameBoard(player1Name, player2Name);

        int [] temp = getSettings();
        int numberOfRounds= temp[0]; 
        int numebrOfTurnsPerRound=temp[1];
        

        System.out.println("Welcome to the mystical lands of Eldoria, \n press 'i' to get more information about the game or 'c' to continue straight away to the game");
        do {
            String choice = scanner.nextLine();
            if (choice == "i") {
                System.out.println("Description goes here");
                break;
            } else if (choice == "c") {
                break;
            }
        } while (true);

        //the following is taking in the round rewards from the properties file
        Reward rewards [] = getRewards(numberOfRounds);

        //the following is trying to start the game loop:

        for (int i=0; i<numberOfRounds*2; i++){
            
            //the following is trying to start the round loop:
            for (int j=0; j<numebrOfTurnsPerRound; j++){
                Player player1= getActivePlayer();
                //Player player2= getPassivePlayer();
                ScoreSheet scoreSheet = getScoreSheet(getActivePlayer());
                System.out.println(player1.getName()+", here is your score sheet:");
                System.out.println(getScoreSheet(player1));

                gameBoard.rollDice();

                System.out.println("Here are your rolled dice: ");
                
                Dice [] availableDice= getAvailableDice();
                int counter= 0;
                for (Dice die : availableDice) {
                    System.out.println(++counter +":"+die.getRealm()+""+die.getValue());
                }

                //  1:B5  2:W6  3:Y3 4:B
                
                Dice chosenDice=null;
                do {
                    System.out.println("please choose a number between 1 and "+ availableDice.length);
                    int choice = scanner.nextInt();
                    if (!(choice > availableDice.length || choice <= 0)){
                        chosenDice = availableDice[choice-1];
                        try{
                            if (makeMove(player1, new Move(chosenDice, scoreSheet.getCreatureByColor(chosenDice.getRealm())))){
                                break;
                            }
                        }catch(InvalidMoveException iException){
                            System.out.println("this move cannot happen as per the realms rules");
                        }
                        
                    }else {
                        System.out.println("please choose a valid move");
                    }
                } while (true);

                
            }
        }
    }

 
    // move methods
    public Move[] getAllPossibleMoves(Player player) {
        return player.getAllPossiblMoves();
    }

    // makeMove(new player(), new Move(new RedDice(), new Gaia()))
    public boolean makeMove(Player player, Move move) throws InvalidMoveException {
        try {
            // if (move.getCreature() instanceof Dragon) {
            //     System.out.println("which dragon 7adretak 3aiz temawet (choose from 1 to 4)");
            //     int dragonIndex = Integer.parseInt(System.console().readLine());
            //     Dragon dragon = ((Dragon) move.getCreature()).dragonSelector(dragonIndex);
            //     move.setCreature(dragon); // should be make move
            // } else 
            if (move.getCreature() instanceof Gaia) {
                GreenDice greenDice = (GreenDice) this.gameBoard.getWhite();
                Dice whiteDice = this.gameBoard.getGreen();
                int greenVal = greenDice.getValue();
                int whiteVal = whiteDice.getValue();
                greenDice.setRealValue(greenVal + whiteVal);
            }
            return move.getCreature().makeMove(move.getDice());

        } catch (BonusException bException) {
            RealmColor theBonusColor = bException.getRealmColor();
            System.out.println("please enter the number to attack the " + theBonusColor + " realm with: ");
            int numberToAttackWith = Integer.parseInt(System.console().readLine());
            Creature creature = player.getScoresheet().getCreatureByColor(theBonusColor);
            Move bonusmove = new Move(new Dice(numberToAttackWith), creature);
            return makeMove(player, bonusmove);
        }
    }

    // gameboard getter:
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public GameStatus getGameStatus() {
        return this.gameBoard.getGameStatus();
    }

    // dice related methods:
    public void rollDice() {
        this.gameBoard.rollDice();
    }

    public Dice[] getAllDice() {
        return this.gameBoard.getAllDice();
    }

    public Dice[] getAvailableDice() {
        return this.gameBoard.getAvailableDice();
    }

    public Dice[] getForgottenRealmDice() {
        return this.gameBoard.getForgottenRealmDice();
    }

    // player related methods:
    public boolean switchPlayer() {
        try {
            this.gameBoard.getPlayer1().switchStatus();
            this.gameBoard.getPlayer2().switchStatus();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Player getActivePlayer() {
        if (this.gameBoard.getPlayer1().getPlayerStatus() == PlayerStatus.ACTIVE) {
            return this.gameBoard.getPlayer1();
        } else {
            return this.gameBoard.getPlayer2();
        }
    }

    public Player getPassivePlayer() {
        if (this.gameBoard.getPlayer1().getPlayerStatus() == PlayerStatus.PASSIVE) {
            return this.gameBoard.getPlayer1();
        } else {
            return this.gameBoard.getPlayer2();
        }
    }

    // player attributes related methods
    public ScoreSheet getScoreSheet(Player player) {
        return player.getScoresheet();
    }

    public GameScore getGameScore(Player player) {
        return player.getGameScore();
    }

    public TimeWarp[] getTimeWarpPowers(Player player) {
        return player.getTimeWarps();
    }

    public static void main(String[] args) {
        CLIGameController controller = new CLIGameController();
    }

    // public abstract boolean switchPlayer(){
    // }

}
