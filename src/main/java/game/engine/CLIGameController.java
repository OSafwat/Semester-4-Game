package game.engine;

import game.collectibles.*;
import game.exceptions.BonusException;
import game.exceptions.BonusTwoException;
import game.exceptions.InvalidMoveException;
import game.dice.*;
import game.creatures.*;
import game.creatures.greenclasses.Gaia;
import game.engine.enums.*;

///import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class CLIGameController extends GameController {
    GameBoard gameBoard;
    String rewardsArray[];

    // constructor(s):
    public CLIGameController() {
        this.gameBoard= new GameBoard();
    }
    public int [] getSettings(){
        Scanner scanner = new Scanner(System.in);
        int numberOfRounds;
        int numebrOfTurnsPerRound;
        BufferedReader settings=null;
        //the following is taking in the game settings from the RoundsSettings file
        try {
            // opening the file
            FileReader SettingsfileReader = new FileReader(
                    "src/main/resources/config/RoundsSettings.properties");
            settings = new BufferedReader(SettingsfileReader);

            // taking in input from the file which is currently only 2
            String line1 = settings.readLine();
            String[] lineOfRounds = line1.split("=");
            numberOfRounds = Integer.parseInt(lineOfRounds[1]);

            String line2 = settings.readLine();
            String[] lineOfTurns = line2.split("=");
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
            if (settings != null) {
                try {
                    settings.close();
                } catch (IOException e) {
                    //Unreachable code
                }
            }
        }
        int temp [] =  {numberOfRounds, numebrOfTurnsPerRound};
        return temp;
    }

    public String [] getRewards(int numberOfRounds){

        BufferedReader rewardsFile=null;
        String[] rewards= new String [numberOfRounds] ;
        Arrays.fill(rewards, "");
        try {
            // opening the file
            FileReader rewardsFileReader = new FileReader("src/main/resources/config/RoundsRewards.properties");
            rewardsFile = new BufferedReader(rewardsFileReader);

            // taking in input from the file which is currently only 2
            String rewardsline ;
            int rewardsCounter = 0;
            rewardsline = rewardsFile.readLine();
            for  ( ; rewardsCounter< numberOfRounds ; rewardsCounter++){
                rewardsline = rewardsFile.readLine();
                if ( rewardsline != null){

                    String temp [] = rewardsline.split("=");
                    for (int i = 0; i < temp.length ; i++) {
                        //   System.out.println(temp[i]);
                        rewards[rewardsCounter] = temp[1];
                    }
                }
                else{
                    rewards[rewardsCounter] = "null2";
                }
                //System.out.println(rewards[rewardsCounter]);
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
            if (rewardsFile != null) {
                try {
                    rewardsFile.close();
                } catch (IOException e) {
                    //Unreachable code
                }
            }
        }

        return rewards;
    }

    public void startGame(){
        Scanner scanner = new Scanner(System.in);   //scanner is here
        System.out.println("please input the name of player 1:");
        String player1Name = scanner.nextLine();
        getActivePlayer().setName(player1Name);
        System.out.println("please input the name of player 2:");
        String player2Name = scanner.nextLine();
        getPassivePlayer().setName(player2Name);

        int [] temp = getSettings();
        int numberOfRounds= temp[0];
        int numebrOfTurnsPerRound=temp[1];


        System.out.println("Welcome to the mystical lands of Eldoria, \n press 'i' to get more information about the game or 'c' to continue straight away to the game");
        do {
            String choice = scanner.nextLine();
            if (choice.length() !=0 && 'i' == choice.charAt(0)) {
                System.out.println("Description goes here\n");
            } else if (choice.charAt(0)=='c')
                break;
            else
                System.out.println("Please choose sth correct\n");
        } while (true);

        //the following is taking in the round rewards from the properties file
        String rewards [] = getRewards(numberOfRounds);

        //the following is trying to start the game loop:

        for (int i=0; i<numberOfRounds; i++){
            for (int k = 0; k < 2; k++) {
                //the following is playing some number of rounds with the active player then 1 round with the passive player
                Player currentActivePlayer= getActivePlayer();
                rollDice();
                for (int j=0; j<numebrOfTurnsPerRound && getAvailableDice().length > 0; j++){
                    playOneTurn(this, currentActivePlayer, this.gameBoard, getAvailableDice(), PlayerStatus.ACTIVE ,currentActivePlayer.getTimeWarps());     //playing an active turn
                }
                moveAllIntoForgotten();
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

                handleArcaneBoost(getPassivePlayer(), currentPlayersArcaneBoosts);
                System.out.println("weselna hena ");
                gameBoard.resetAllDice();// this moves all thats in the forgotten realm to the available dice and empties the activeArcaneDice and passiveArcaneDice
                switchPlayer();
            }

        }
        Player player1= gameBoard.getPlayer1();
        Player player2= gameBoard.getPlayer1();
        System.out.println("the scoresheet of "+ player1.getName()+" is the following:");
        player1.getScoreSheet().displayScoreSheet();
        System.out.println( player1.getGameScore().toString());
        int player1Score= player1.getGameScore().getTotalScore();

        System.out.println("the scoresheet of "+ player2.getName()+" is the following:");
        player2.getScoreSheet().displayScoreSheet();
        System.out.println( player2.getGameScore().toString());
        int player2Score= player2.getGameScore().getTotalScore();

        if (player1Score > player2Score)
            System.out.println("Congratulations "+player1.getName()+" you have emerged victorious in this wonderful conquest and have shown your absolute superiority when compared to the other noob wannabe-wizard in my opinion "+ player2.getName()+ " should just go and kill himself for wasting his life away like that\n anyway thanks you for playing and we hope you dont come again after all u just wasted like 30 mins of your life for nothing unlike me who just wasted 10 hours at least 😭");
        else
            System.out.println("Congratulations "+player2.getName()+" you have emerged victorious in this wonderful conquest and have shown your absolute superiority when compared to the other noob wannabe-wizard in my opinion "+ player1.getName()+ " should just go and kill himself for wasting his life away like that\n anyway thanks you for playing and we hope you dont come again after all u just wasted like 30 mins of your life for nothing unlike me who just wasted 10 hours at least 😭");

        //scanner.close();
    }

    public void handleArcaneBoost(Player player,ArrayList<ArcaneBoost> currentPlayersArcaneBoosts){
        Scanner scanner = new Scanner(System.in);

        for (int arcaneBoostsIndex=0; arcaneBoostsIndex < currentPlayersArcaneBoosts.size(); arcaneBoostsIndex++){
            if (currentPlayersArcaneBoosts.get(arcaneBoostsIndex).getStatus() == RewardStates.ACQUIRED){
                System.out.println("Would you like to use an arcane Boost (enter 'y' or 'n')");
                char choice = '7';
                do {
                    choice =scanner.nextLine().charAt(0);
                    if (choice == 'y' || choice == 'n')
                        break;
                    else
                        System.out.println("please enter a valid input");
                } while (true);

                if (choice == 'n')
                    break;
                if (choice=='y'){
                    currentPlayersArcaneBoosts.get(arcaneBoostsIndex).setStatus(RewardStates.USED);
                    //the functionality of getting an arcane boost goes here
                    //meow meow meow meow
                    Dice [] alldice= getAllDice();
                    ArrayList<Dice> activeArcaneDice = gameBoard.getActiveArcaneDice();
                    ArrayList<Dice> passivePlayerDice = gameBoard.getPassiveArcaneDice();
                    System.out.println("choose from the following dice one of them to make a move with");
                    HashSet<Integer> hs = new HashSet<>();
                    for (int diceIndex=0; diceIndex < alldice.length ; diceIndex++){
                        if (player == getActivePlayer()){
                            if (!activeArcaneDice.contains(alldice[diceIndex])){
                                hs.add(diceIndex);
                                System.out.println(diceIndex +":"+alldice[diceIndex].getRealm()+alldice[diceIndex].getValue());
                            }
                        }else {
                            if (!passivePlayerDice.contains(alldice[diceIndex])){
                                hs.add(diceIndex);
                                System.out.println(diceIndex +":"+alldice[diceIndex].getRealm()+" "+alldice[diceIndex].getValue());
                            }
                        }
                    }

                    while (true) {
                        try {
                            int arcaneboostChoice=0;
                            do {
                                arcaneboostChoice= scanner.nextInt();
                                if (hs.contains(arcaneboostChoice))
                                    break;
                                else System.out.println("please input one of the possible dice (note the inconsistent numbers are just to keep you on edge akeeeeeeed ana mesh mekasel akteb code yegeeb el arqam men 0 le7ad their number)");
                            } while (true);
                            if(player == getActivePlayer()){
                                activeArcaneDice.add(alldice[arcaneboostChoice]);
                            }else {
                                gameBoard.getPassiveArcaneDice().add(alldice[arcaneboostChoice]);
                            }
                            if (makeMove(player, new Move(alldice[arcaneboostChoice],player.getScoreSheet().getCreatureByColor(alldice[arcaneboostChoice].getRealm()))))
                                break;

                        } catch (Exception e) {
                            System.out.println("we have an unknown");
                        }
                    }

                }
            }
        }
        //scanner.close();
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
            } catch (Exception e) {
                System.out.println("batal estehbal we have an unknown Exception");
            }
        } while (true);
        //scanner.close();
    }
    public void handleTimeWarps(ArrayList<TimeWarp> timewarps){
        if (timewarps.size()==0)
            return;
        Scanner scanner = new Scanner(System.in);

        // System.out.println("are you disatisfied by such rotten luck and would like to get another roll at your fate (this will use one of your aqcuired timewarps becuase nothing in this life is for free)\n (press 'y' or 'y' because no one is satisfied aslan no just kidding ");
        for (int index = 0; index < timewarps.size(); index++) {
            //  System.out.println("are you disatisfied by such rotten luck and would like to get another roll at your fate (this will use one of your aqcuired timewarps becuase nothing in this life is for free)\n (press 'y' or 'y' because no one is satisfied aslan no just kidding ");
            if (timewarps.get(index).getStatus()==RewardStates.ACQUIRED){
                System.out.println("choose whether you would like to use a timeWarp to reroll or not (enter 'y' or 'n')");
                char choice='4';
                do {
                    choice = scanner.next().charAt(0);
                    if (choice == 'y' || choice == 'n')
                        break;
                    System.out.println("please enter a valid choice ba2a");
                } while (true );
                if (choice == 'n'){
                    //scanner.close();
                    return;}

                timewarps.get(index).setStatus(RewardStates.USED);
                gameBoard.rollAvailableDice();
                System.out.println("Here are your rolled dice: ");
                int counter=0;
                for (Dice die : getAvailableDice()) {
                    System.out.println(++counter +":"+die.getRealm()+" "+die.getValue());
                }
            }
        }
        //scanner.close();
    }
    public static void playOneTurn(CLIGameController controller, Player player, GameBoard gameBoard, Dice [] diceToBePlayedwith, PlayerStatus playerStatus, ArrayList<TimeWarp> timewarps){
        Scanner scanner = new Scanner(System.in);
        ScoreSheet scoreSheet = controller.getScoreSheet(player);
        System.out.println(player.getName()+", here is your score sheet:");
        scoreSheet.displayScoreSheet();


        gameBoard.rollAvailableDice();

        if (player == controller.getActivePlayer())
            System.out.println(player.getName()+", Here are your rolled dice: ");
        else System.out.println(player.getName()+", Here are your passive turn dice: ");
        int counter= 0;
        for (Dice die : diceToBePlayedwith) {
            if (die == null) {
                return;
            }
            System.out.println(++counter +":"+die.getRealm()+" "+die.getValue());
        }
        if (playerStatus==PlayerStatus.ACTIVE)
            controller.handleTimeWarps(timewarps);
        //the following is choosing a correct valid move
        Dice chosenDice=null;
        boolean alreadySelected = false;
        outer: do {
            System.out.println("Please choose a number between 1 and "+ diceToBePlayedwith.length);
            int choice = scanner.nextInt();
            if (!(choice > diceToBePlayedwith.length || choice <= 0)){
                chosenDice = diceToBePlayedwith[choice-1];
                try{
                    if(chosenDice instanceof ArcanePrism){
                        System.out.println("Please a realm to attack:");
                        int realmChoice = -1;
                        do{
                            System.out.println("please choose a realm to attack:\n 1-Red 2-Green 3-Blue 4-Magenta 5-Yellow ");
                            realmChoice= scanner.nextInt();
                            if (realmChoice >=1 && realmChoice <= 5)
                                break;
                            System.out.println("momken nebatal estehbal");
                        }while(true);
                        if (player.getPlayerStatus() == PlayerStatus.ACTIVE) {
                            controller.selectDice(chosenDice, player);
                            alreadySelected = true;
                        }
                        switch (realmChoice) {
                            case 1: chosenDice = new RedDice(chosenDice.getValue()); break;
                            case 2: chosenDice = new GreenDice(gameBoard.getGreen().getValue()); break;
                            case 3: chosenDice = new BlueDice(chosenDice.getValue()); break;
                            case 4: chosenDice = new MagentaDice(chosenDice.getValue()); break;
                            case 5: chosenDice = new YellowDice(chosenDice.getValue()); break;
                        }
                    }
                    if (chosenDice instanceof RedDice){
                        int dragonChoice = 0;
                        do {
                            System.out.println("Please choose a proper dragon to attack in the Red Realm");
                            dragonChoice = scanner.nextInt();
                            if (dragonChoice>= 1 && dragonChoice <= 4){
                                ((RedDice)chosenDice).selectsDragon(dragonChoice);
                                if (controller.makeMove(player, new Move(chosenDice, scoreSheet.getCreatureByColor(chosenDice.getRealm()))))
                                    break outer;
                            }
                        } while (true);
                    }
                    else if (controller.makeMove(player, new Move(chosenDice, scoreSheet.getCreatureByColor(chosenDice.getRealm())))){
                        break;
                    }
                }catch(Exception e){
                    System.out.println();
                }

            }else {
                System.out.println("Please choose a valid move");
            }
        } while (true);

        //System.out.println("here is your new scoresheet");

        //changing the available dice
        if (playerStatus == PlayerStatus.ACTIVE && !alreadySelected){
            controller.selectDice(chosenDice, player);
        }
        System.out.println("Here is your new score sheet  ==>");
        scoreSheet.displayScoreSheet();
        //scanner.close();
    }
    // move methods
    public Move[] getAllPossibleMoves(Player player) {
        return player.getAllPossibleMoves();
    }
    public Move [] getPossibleMovesForAvailableDice(Player player){
        ArrayList<Move> result = new ArrayList<>();
        Dice [] allDice = getAvailableDice();
        outer: for (Dice die : allDice) {
            result.addAll(Arrays.asList(getPossibleMovesForADie(player, die)));
        }
        removeGreenDuplicate(result);
        Move [] temp = new Move[result.size()];
        for (int index = 0; index < result.size(); index++) {
            temp[index]= result.get(index);
        }
        Arrays.sort(temp);
        return temp;
    }
    public void removeGreenDuplicate(ArrayList<Move> result) {
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getDice().getRealm() == RealmColor.GREEN)
            {
                if (index1 == -1)
                    index1 = i;
                else
                    index2 = i;
            }
        }
        if (index1 != -1 && index2 != -1) {
            result.remove(index2);
        }
    }
    public Move[] getPossibleMovesForADie(Player player, Dice dice){    // here
        if (dice instanceof GreenDice) {
            GreenDice correctedDice = new GreenDice(dice.getValue() + gameBoard.getWhite().getValue());
            dice = correctedDice;
        }
        Move[] playerAllMoves= player.getAllPossibleMoves();
        System.out.println(playerAllMoves.length);
        ArrayList<Move> result = new ArrayList<>();
        if (dice instanceof ArcanePrism){
            Dice[] possibleDice = {new RedDice(dice.getValue()), gameBoard.getGreen(), new BlueDice(dice.getValue()), new MagentaDice(dice.getValue()), new YellowDice(dice.getValue())};
            for (int i = 0; i < 5; i++) {
                Move[] thisDiceMoves = getPossibleMovesForADie(player, possibleDice[i]);
                result.addAll(Arrays.asList(thisDiceMoves));
            }
            Move [] finalResult = new Move[result.size()];
            for (int i=0; i<result.size(); i++) {
                finalResult[i] = result.get(i);
            }
            return finalResult;
        }
        else{
            for (int i = 0; i < playerAllMoves.length; i++) {
                if (playerAllMoves[i].compareTo(dice)==0){
                    result.add(playerAllMoves[i]);
                }
            }
            Move [] finalResult = new Move[result.size()];
            for (int i=0; i<result.size(); i++) {
                finalResult[i] = result.get(i);
            }
            return finalResult;
        }
    }


    // makeMove(new player(), new Move(new RedDice(), new Gaia()))
    public boolean makeMove(Player player, Move move)  {
        player.updateAllPossibleMoves();
        try {
            Dice diceToBeMovedWith= move.getDice();
            if (move.getCreature() instanceof Gaia) {
                GreenDice greenDice = (GreenDice) gameBoard.getGreen();
                Dice arcanePrism = (ArcanePrism) gameBoard.getWhite();
                int greenVal = greenDice.getValue();
                int whiteVal = arcanePrism.getValue();
                diceToBeMovedWith = new GreenDice(greenVal+whiteVal);
            }
            boolean temp = move.getCreature().makeMove(diceToBeMovedWith);
            player.updateGameScore();
            return temp;
        } catch (BonusException bException) {
            RealmColor theBonusColor = bException.getRealmColor();
            int numberToAttackWith =0;
            do{
                System.out.println("Please enter the number to attack the " + theBonusColor + " realm with: ");
                numberToAttackWith = Integer.parseInt(System.console().readLine()); // NEED TO VALIDATE THE INPUT
                if (!(numberToAttackWith > 6 || numberToAttackWith < 1)){
                    Creature firstCreature = player.getScoreSheet().getCreatureByColor(theBonusColor);
                    Move firstBonusmove = new Move(new Dice(numberToAttackWith), firstCreature);
                    boolean result = makeMove(player, firstBonusmove);
                    player.updateGameScore();
                    return result;
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
                    Creature firstCreature = player.getScoreSheet().getCreatureByColor(theFirstBonusColor);
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
                    Creature secondCreature = player.getScoreSheet().getCreatureByColor(theSecondBonusColor);
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
            player.updateGameScore();
            return true;
        }
        catch (InvalidMoveException Im){
            System.out.println("i dont get why we would get here");
            return false;
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
    public Dice [] rollDice() {
        Dice[] temp = gameBoard.getAllDice();
        gameBoard.rollDice();
        return temp;
    }

    public Dice[] getAllDice() {
        return gameBoard.getAllDice();
    }

    public Dice[] getAvailableDice() {
        ArrayList<Dice> availableDiceAsList = gameBoard.getAvailableDice();
        Dice[] availableDiceAsArray = new Dice[availableDiceAsList.size()];
        for (int i = 0, size = availableDiceAsList.size(); i < size; i++) {
            availableDiceAsArray[i] = availableDiceAsList.get(i);
        }
        Arrays.sort(availableDiceAsArray);
        return availableDiceAsArray;
    }

    public Dice[] getForgottenRealmDice() {
        return gameBoard.getForgottenRealmDice();
    }

    // player related methods:
    public boolean switchPlayer() {
        try {
            gameBoard.getPlayer1().switchStatus();
            gameBoard.getPlayer2().switchStatus();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Player getActivePlayer() {
        return gameBoard.getPlayer1().getPlayerStatus() == PlayerStatus.ACTIVE ? gameBoard.getPlayer1() : gameBoard.getPlayer2();
    }

    public Player getPassivePlayer() {
        return gameBoard.getPlayer1().getPlayerStatus() == PlayerStatus.PASSIVE ? gameBoard.getPlayer1() : gameBoard.getPlayer2();
    }

    // player attributes related methods
    public ScoreSheet getScoreSheet(Player player) {
        return player.getScoreSheet();
    }

    public GameScore getGameScore(Player player) {
        return player.getGameScore();
    }

    public TimeWarp[] getTimeWarpPowers(Player player) {
        ArrayList<TimeWarp> timeWarpsAsList = player.getTimeWarps();
        TimeWarp[] timeWarpsAsArray = new TimeWarp[timeWarpsAsList.size()];
        for (int i = 0, size = timeWarpsAsList.size(); i < size; i++) {
            timeWarpsAsArray[i] = timeWarpsAsList.get(i);
        }
        return timeWarpsAsArray;
    }
    public ArcaneBoost[] getArcaneBoostPowers(Player player){
        ArrayList<ArcaneBoost> arcaneBoostsAsList = player.getArcaneBoosts();
        ArcaneBoost[] arcaneBoostsAsArray = new ArcaneBoost[arcaneBoostsAsList.size()];
        for (int i = 0, size = arcaneBoostsAsList.size(); i < size; i++) {
            arcaneBoostsAsArray[i] = arcaneBoostsAsList.get(i);
        }
        return arcaneBoostsAsArray;
    }
    public boolean selectDice(Dice dice, Player player){
        try{
            player.selectDice(dice);
            gameBoard.removeFromAvailable(dice);
            Dice[] availableDice = getAvailableDice();
            for (int i = 0, size = availableDice.length; i < size; i++) {
                if (availableDice[i].getValue() < dice.getValue())
                    gameBoard.moveToForgottenrealm(availableDice[i]);
            }
            return true;
        }catch (Exception e ){return false;}
    }

    //new method
    public void moveAllIntoForgotten() {
        for (Dice die: getAvailableDice()) {
            gameBoard.removeFromAvailable(die);
            gameBoard.moveToForgottenrealm(die);
        }
    }

    public static void main(String[] args)  {
        CLIGameController controller = new CLIGameController();
        controller.startGame();
    }

}


