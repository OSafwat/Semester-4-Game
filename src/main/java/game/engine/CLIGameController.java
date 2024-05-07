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
            scanner.close();
        }
        int temp [] =  {numberOfRounds, numebrOfTurnsPerRound};
        return temp;
    }

    public String [] getRewards(int numberOfRounds) throws IOException{

        BufferedReader rewardsFile=null;
        String[] rewards= new String [numberOfRounds] ;
        try {
            // opening the file
            FileReader rewardsFileReader = new FileReader("dice-realms-game-dimension/src/main/resources/RoundsRewards.properties");
            rewardsFile = new BufferedReader(rewardsFileReader);

            // taking in input from the file which is currently only 2
            String rewardsline ;
            int rewardsCounter = 0;
            for  ( ; rewardsCounter< numberOfRounds && (rewardsline  = rewardsFile.readLine()) != null; rewardsCounter++){
                rewards[rewardsCounter] = rewardsline.split("=")[1];            // had to make it a string array cuz i cant switch case in the startGame() method when i should be making such decisions including the possibility of a colored bonus being included
                // switch (reward){
                //     case "TimeWarp" : rewards[rewardsCounter] = new TimeWarp();            break;
                //     case "ArcaneBoost": rewards[rewardsCounter] = new ArcaneBoost();       break;
                //     case "EssenceBonus": rewards[rewardsCounter] = new EssenceBonus();     break;
                //     case "ElementalCrest": rewards[rewardsCounter] = new ElementalCrest(); break;
                //     default: rewards[rewardsCounter] =null; 
                // }
            }
        } catch (FileNotFoundException  e) {

            System.err.println("the Rewards file was not able to be accessed therefore default rewards will be used"); 
            rewards[0]="TimeWarp";           //new TimeWarp();
            rewards[1] = "ArcaneBoost";      //new ArcaneBoost();
            rewards[2] ="TimeWarp";          //new TimeWarp();
            rewards[3] = "EssenceBonus";     //new EssenceBonus();
            rewards[4] = "";
            rewards[5] = "";            
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
        String rewards [] = getRewards(numberOfRounds);

        //the following is trying to start the game loop:

        for (int i=0; i<numberOfRounds; i++){
            for (int k = 0; k < 2; k++) {
                //the following is playing some number of rounds with the active player then 1 round with the passive player
                Player currentActivePlayer= getActivePlayer();
                for (int j=0; j<numebrOfTurnsPerRound && getAvailableDice().length > 0; j++){
                    playOneTurn(this, currentActivePlayer, this.gameBoard, getAvailableDice(), PlayerStatus.ACTIVE ,currentActivePlayer.getTimeWarps());     //playing an active turn
                }
                playOneTurn(this, getPassivePlayer(), gameBoard, getForgottenRealmDice(), PlayerStatus.PASSIVE, currentActivePlayer.getTimeWarps());        //playing a passive turn
                //the following is resetting the dice:
                
                // should assign the round rewards as well as use the arcaneboosts and time warps
                // the following handles what to do with the rewards taken from the config file
                switch (rewards[i]){
                    case "ArcaneBoost": currentActivePlayer.getArcaneBoosts().add(new ArcaneBoost(RewardStates.ACQUIRED)); break;
                    case "TimeWarp":   currentActivePlayer.getTimeWarps().add(new TimeWarp(RewardStates.ACQUIRED)); break;
                    case "EssenceBonus": 
                        int realmChoice =0;
                        do{
                            System.out.println("please choose a realm to attack:\n 1-Red 2-Green 3-Blue 4-Magenta 5-Yellow ");
                            realmChoice= scanner.nextInt();
                            if (realmChoice >=1 && realmChoice <= 5)
                                break;
                            System.out.println("momken nebatal estehbal");
                        }while(true);
                        handleBonus(realmChoice);
                    case "RedBonus":    handleBonus(1);
                    case "GreenBonus": handleBonus(2);
                    case "BlueBonus": handleBonus(3);
                    case "MagentaBonus": handleBonus(4);
                    case "YellowBonus": handleBonus(5);
                    default: System.out.println("7azak en el round da mafhoosh bonus");
                }
                ArrayList<ArcaneBoost> currentPlayersArcaneBoosts = currentActivePlayer.getArcaneBoosts();
                handleArcaneBoost(currentActivePlayer, currentPlayersArcaneBoosts);         //  1 method to handle having wanting an arcane boost 


                
                gameBoard.resetForgottenRealm();
                switchPlayer();
            }

        }   
        scanner.close();
    }

    public void handleArcaneBoost(Player currentActivePlayer,ArrayList<ArcaneBoost> currentPlayersArcaneBoosts){
        Scanner scanner = new Scanner(System.in);

        for (int arcaneBoostsIndex=0; arcaneBoostsIndex < currentPlayersArcaneBoosts.size(); arcaneBoostsIndex++){
            if (currentPlayersArcaneBoosts.get(arcaneBoostsIndex).getStatus() == RewardStates.ACQUIRED){
                System.out.println("Would you like to use an arcane Boost (enter 'y' or 'n')");
                char choice = '7';
                do {
                    choice =scanner.nextLine().charAt(0);
                    if (choice == 'y' || choice == 'n')
                        break;
                } while (true);
                
                if (choice == 'n')
                    break;
                if (choice=='y'){
                    currentPlayersArcaneBoosts.get(arcaneBoostsIndex).setStatus(RewardStates.USED);
                    //the functionality of getting an arcane boost goes here
                    Dice [] alldice= getAllDice();
                    System.out.println("choose from the following dice one of them to make a move with");
                    for (int diceIndex=0; diceIndex < alldice.length; diceIndex++){
                        System.out.println(diceIndex +":"+alldice[diceIndex].getRealm()+alldice[diceIndex].getValue());
                    }
                    
                    while (true) {
                        try {
                            int arcaneboostChoice=0;
                            do {
                                arcaneboostChoice= scanner.nextInt();
                                if (arcaneboostChoice >=1 && arcaneboostChoice <= 6)
                                    break;
                            } while (true);
                            if (makeMove(currentActivePlayer, new Move(alldice[arcaneboostChoice],currentActivePlayer.getScoresheet().getCreatureByColor(alldice[arcaneboostChoice].getRealm()))))
                                break;
                        
                        } catch (InvalidMoveException e) {
                            System.out.println("sadly you will need to choose another move that is gonna be more correct we law enta zehe2t men kol el error checking da fa ana zehe2t aktar");
                        }
                    }

                }
            }
        }   
        scanner.close();
    }
    public Creature getCreatureToAttacByColor(int choice, ScoreSheet scoresheet){
        switch (choice){
            case 1: return scoresheet.getCreatureByColor(RealmColor.RED);
            case 2: return scoresheet.getCreatureByColor(RealmColor.GREEN);
            case 3: return scoresheet.getCreatureByColor(RealmColor.BLUE);
            case 4: return scoresheet.getCreatureByColor(RealmColor.MAGENTA);
            case 5: return scoresheet.getCreatureByColor(RealmColor.YELLOW);
            default: System.out.println("ok ana mesh 3aref law dakhalna hena han7elaha ezay");
                    return scoresheet.getCreatureByColor(RealmColor.RED);

        }

    }
    public void handleBonus(int realmChoice){
        Scanner scanner = new Scanner(System.in);
        Player currentActivePlayer= getActivePlayer();
        
        Creature creature=getCreatureToAttacByColor(realmChoice, getScoreSheet(getActivePlayer()));
        do {
            try {
                int numChoice =0;
                do{
                    System.out.println("please choose a number from 1-6 to attack with");
                    numChoice= scanner.nextInt();
                    if (realmChoice >=1 && realmChoice <= 5)
                        break;
                }while(true);
                if (makeMove(currentActivePlayer, new Move(new Dice(numChoice), creature)))
                    break;
                System.out.println("please enter try another move that will be valid ");
            } catch (InvalidMoveException e) {
                System.out.println("batal estehbal -> invalid move");
            }
        } while (true);
        scanner.close();
    }
    public void handleTimeWarps(ArrayList<TimeWarp> timewarps){
        Scanner scanner = new Scanner(System.in);
        if (timewarps.size()==0)
            return;
        System.out.println("are you disatisfied by such rotten luck and would like to get another roll at your fate (this will use one of your aqcuired timewarps becuase nothing in this life is for free)\n (press 'y' or 'y' because no one is satisfied aslan no just kidding ");         
        for (int index = 0; index < timewarps.size(); index++) {
            if (timewarps.get(index).getStatus()==RewardStates.ACQUIRED){

                System.out.println("choose whether you would like to use a timeWarp to reroll or not (enter 'y' or 'n')");
                char choice='4';
                do {
                    choice = scanner.next().charAt(0);
                    if (choice == 'y' || choice == 'n')
                        break;
                    System.out.println("please enter a valid choice ba2a");
                } while (true );
                if (choice == 'n')
                    return;

                timewarps.get(index).setStatus(RewardStates.USED);
                gameBoard.rollAvailableDice();
                System.out.println("Here are your rolled dice: ");
                int counter=0;
                for (Dice die : getAvailableDice()) {
                    System.out.println(++counter +":"+die.getRealm()+""+die.getValue());
                }
            }
        }
        scanner.close();
    }
    public static void playOneTurn(CLIGameController controller, Player player, GameBoard gameBoard, Dice [] diceToBePlayedwith, PlayerStatus playerStatus, ArrayList<TimeWarp> timewarps){
        Scanner scanner = new Scanner(System.in);
        ScoreSheet scoreSheet = controller.getScoreSheet(player);
        System.out.println(player.getName()+", here is your score sheet:");
        scoreSheet.displayScoreSheet();

        gameBoard.rollDice();

        System.out.println("Here are your rolled dice: ");
        
        int counter= 0;
        for (Dice die : diceToBePlayedwith) {
            System.out.println(++counter +":"+die.getRealm()+""+die.getValue());
        }
        if (playerStatus==PlayerStatus.ACTIVE)
            controller.handleTimeWarps(timewarps);
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
        scanner.close();
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
        return (TimeWarp [])player.getTimeWarps().toArray();
    }

    public static void main(String[] args) {
        CLIGameController controller = new CLIGameController();
    }

    // public abstract boolean switchPlayer(){
    // }

}
