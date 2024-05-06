package game.engine;

import game.collectibles.*;
import game.exceptions.BonusException;
import game.exceptions.BonusTwoException;
import game.exceptions.InvalidMoveException;
import game.dice.*;
import game.creatures.*;
import game.creatures.greenclasses.Gaia;
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
            for  ( ; rewardsCounter< numberOfRounds && (rewardsline  = rewardsFile.readLine()) != null; rewardsCounter++){
                String reward = rewardsline.split("=")[1];
                switch (reward){
                    case "TimeWarp" : rewards[rewardsCounter] = new TimeWarp();            break;
                    case "ArcaneBoost": rewards[rewardsCounter] = new ArcaneBoost();       break;
                    case "EssenceBonus": rewards[rewardsCounter] = new EssenceBonus();     break;
                    case "ElementalCrest": rewards[rewardsCounter] = new ElementalCrest(); break;
                    default: rewards[rewardsCounter] =null; 
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
            
            //the following is playing 3 rounds with the eactive player then 1 round with the passive player:
            for (int j=0; j<numebrOfTurnsPerRound && getAvailableDice().length > 0; j++){
                Dice [] availableDice= getAvailableDice();
                playOneTurn(this, getActivePlayer(), this.gameBoard, getAvailableDice(), PlayerStatus.ACTIVE );     //playing an active turn
            }
            playOneTurn(this, getPassivePlayer(), gameBoard, getForgottenRealmDice(), PlayerStatus.PASSIVE);        //playing a passive turn

            //the following is resetting the dice:
            gameBoard.resetForgottenRealm();

        }
    }

    public static void playOneTurn(CLIGameController controller, Player player, GameBoard gameBoard, Dice [] diceToBePlayedwith, PlayerStatus playerStatus){
        Scanner scanner = new Scanner(System.in);
                //Player player2= getPassivePlayer();
                ScoreSheet scoreSheet = controller.getScoreSheet(player);
                System.out.println(player.getName()+", here is your score sheet:");
                scoreSheet.displayScoreSheet();

                gameBoard.rollDice();

                System.out.println("Here are your rolled dice: ");
                
                int counter= 0;
                for (Dice die : diceToBePlayedwith) {
                    System.out.println(++counter +":"+die.getRealm()+""+die.getValue());
                }

                //the following is choosing an correct valid move  
                Dice chosenDice=null;
                do {
                    System.out.println("please choose a number between 1 and "+ diceToBePlayedwith.length);
                    int choice = scanner.nextInt();
                    if (!(choice > diceToBePlayedwith.length || choice <= 0)){
                        chosenDice = diceToBePlayedwith[choice-1];
                        try{
                            if (controller.makeMove(player, new Move(chosenDice, scoreSheet.getCreatureByColor(chosenDice.getRealm())))){
                                break;
                            }
                        }catch(InvalidMoveException iException){
                            System.out.println("this move cannot happen as per the realms rules // invalid move exception");
                        }
                        
                    }else {
                        System.out.println("please choose a valid move");
                    }
                } while (true);

                System.out.println("here is your new scoresheet");

                //changing the available dice 
                if (playerStatus== PlayerStatus.ACTIVE){
                    for (Dice die : diceToBePlayedwith) {
                        if ( chosenDice.getValue() > die.getValue()){
                            gameBoard.moveToForgottenrealm(die);
                        }
                    }
                }
                scoreSheet.displayScoreSheet();
    }
    // move methods
    public Move[] getAllPossibleMoves(Player player) {
        return player.getAllPossiblMoves();
    }

    // makeMove(new player(), new Move(new RedDice(), new Gaia()))
    public boolean makeMove(Player player, Move move) throws InvalidMoveException {
        try {
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
            int numberToAttackWith =0;
            do{
                System.out.println("please enter the number to attack the " + theBonusColor + " realm with: "); 
                numberToAttackWith = Integer.parseInt(System.console().readLine()); // NEED TO VALIDATE THE INPUT
                if (!(numberToAttackWith > 6 || numberToAttackWith < 1)){
                    Creature firstCreature = player.getScoresheet().getCreatureByColor(theBonusColor);
                    Move firstBonusmove = new Move(new Dice(numberToAttackWith), firstCreature);
                    return makeMove(player, firstBonusmove);    
                }else{
                    System.out.println("please enter a valid number");
                }
            } while (true);

            Creature creature = player.getScoresheet().getCreatureByColor(theBonusColor);
            Move bonusmove = new Move(new Dice(numberToAttackWith), creature);
            return makeMove(player, bonusmove);
        }catch (BonusTwoException bonus2exception){
            RealmColor theFirstBonusColor = bonus2exception.getBothRealmColors()[0];
            RealmColor theSecondBonusColor = bonus2exception.getBothRealmColors()[1];

            int firstNumberToAttackWith=0;
            do{
                System.out.println("please enter the number to attack the " + theFirstBonusColor + " realm with: ");
                firstNumberToAttackWith = Integer.parseInt(System.console().readLine());
                if (!(firstNumberToAttackWith > 6 || firstNumberToAttackWith < 1)){
                    Creature firstCreature = player.getScoresheet().getCreatureByColor(theFirstBonusColor);
                    Move firstBonusmove = new Move(new Dice(firstNumberToAttackWith), firstCreature);
                    if (makeMove(player, firstBonusmove))
                        break;
                    else{
                        System.out.println("please choose a valid move");
                    }
                    
                }else{
                    System.out.println("please enter a valid number");
                }
            } while (true);

            int secondNumberToAttackWith =0;
            do{
                System.out.println("please enter the number to attack the " + theSecondBonusColor + " realm with: ");
                secondNumberToAttackWith = Integer.parseInt(System.console().readLine());
                if (!(firstNumberToAttackWith > 6 || firstNumberToAttackWith < 1)){
                    Creature secondCreature = player.getScoresheet().getCreatureByColor(theSecondBonusColor);
                    Move secondBonusmove = new Move(new Dice(secondNumberToAttackWith), secondCreature);
                    if (makeMove(player, secondBonusmove))
                        break;
                    else{
                        System.out.println("please choose again but a valid move");
                    }
                    
                }else{
                    System.out.println("please enter a valid number");
                }
            } while (true);
            return true;
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
