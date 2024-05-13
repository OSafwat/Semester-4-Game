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
import java.util.*;

public class MyCLIGameController {
    GameBoard gameBoard;
    String rewardsArray[];
    Scanner scanner;

    // constructor(s):
    public MyCLIGameController() {
        this.gameBoard= new GameBoard();
        scanner = new Scanner(System.in);
    }
    public int [] getSettings(){
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
    public void handleRoundRewards(Player currentActivePlayer, String reward) {
        switch (reward){
            case "ArcaneBoost": currentActivePlayer.getArcaneBoosts().add(new ArcaneBoost(RewardStates.ACQUIRED)); break;
            case "TimeWarp":   currentActivePlayer.getTimeWarps().add(new TimeWarp(RewardStates.ACQUIRED)); break;
            case "EssenceBonus":
                int realmChosen;
                System.out.println("Please enter a number from 1 to 5 to choose the realm you would like to attack.");
                System.out.println("\u001B[31m" + "1. Red Realm " + "\u001B[0m" + "\n" +
                        "\u001B[32m" + "2. Green Realm" + "\u001B[0m" + "\n" +
                        "\u001B[34m" + "3. Blue Realm" + "\u001B[0m" + "\n" +
                        "\u001B[35m" + "4. Magenta Realm" + "\u001B[0m" + "\n" +
                        "\u001B[33m" + "5. Yellow Realm" + "\u001B[0m" + "\n");
                String input = scanner.next();
                while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5")) {
                    System.out.println("Invalid input.");
                    System.out.println("Please enter a number from 1 to 5 to choose the realm you would like to attack.");
                    input = scanner.next();
                }
                realmChosen = Integer.parseInt(input);
                handleBonus(realmChosen); break;
            case "RedBonus":    handleBonus(1); break;
            case "GreenBonus": handleBonus(2); break;
            case "BlueBonus": handleBonus(3); break;
            case "MagentaBonus": handleBonus(4); break;
            case "YellowBonus": handleBonus(5); break;
            default: System.out.println("7azak en el round da mafhoosh bonus");
        }
    }

    public void startGame(){
        System.out.println("please input the name of player 1:");
        String player1Name = scanner.nextLine();
        getActivePlayer().setName(player1Name);
        System.out.println("please input the name of player 2:");
        String player2Name = scanner.nextLine();
        getPassivePlayer().setName(player2Name);

        int [] temp = getSettings();
        int numberOfRounds= temp[0];
        int numebrOfTurnsPerRound=temp[1];


        System.out.println("Welcome to the mystical lands of Eldoria! \nPress 'i' to get more information about the game or 'c' to continue straight away to the game");
        do {
            String choice = scanner.nextLine();
            if (!choice.isEmpty() && 'i' == choice.charAt(0)) {
                System.out.println("Description goes here.\n");
                System.out.println("Press 'i' to get more information about the game or 'c' to continue straight away to the game.\n");
            } else if (!choice.isEmpty() && choice.charAt(0)=='c')
                break;
            else
                System.out.println("Please choose sth correct\n");
        } while (true);

        //the following is taking in the round rewards from the properties file
        String rewards [] = getRewards(numberOfRounds);

        //the following is trying to start the game loop:
        for (int round = 0; round < numberOfRounds; round++) {
            System.out.println();
            System.out.println("IT IS CURRENTLY ROUND: " + (round+1));
            playRound(getActivePlayer(), rewards[round], numebrOfTurnsPerRound);
            moveAllIntoForgotten();
            playForgottenTurn(getPassivePlayer());
            gameBoard.resetAllDice();
            switchPlayer();
            System.out.println();
            System.out.println("IT IS CURRENTLY ROUND: " + (round+1));
            playRound(getActivePlayer(), rewards[round], numebrOfTurnsPerRound);
            moveAllIntoForgotten();
            playForgottenTurn(getPassivePlayer());
            gameBoard.resetAllDice();
            switchPlayer();
        }
        Player player1= gameBoard.getPlayer1();
        Player player2= gameBoard.getPlayer1();
        System.out.println("the scoresheet of "+ player1.getName()+" is the following:");
        player1.getScoreSheet().displayColoredScoreSheet();
        System.out.println( player1.getGameScore().toString());
        int player1Score= player1.getGameScore().getTotalScore();

        System.out.println("the scoresheet of "+ player2.getName()+" is the following:");
        player2.getScoreSheet().displayColoredScoreSheet();
        System.out.println( player2.getGameScore().toString());
        int player2Score= player2.getGameScore().getTotalScore();

        if (player1Score > player2Score)
            System.out.println("Congratulations "+player1.getName()+" you have emerged victorious in this wonderful conquest and have shown your absolute superiority when compared to the other noob wannabe-wizard in my opinion "+ player2.getName()+ " should just go and kill himself for wasting his life away like that\n anyway thanks you for playing and we hope you dont come again after all u just wasted like 30 mins of your life for nothing unlike me who just wasted 10 hours at least 😭");
        else
            System.out.println("Congratulations "+player2.getName()+" you have emerged victorious in this wonderful conquest and have shown your absolute superiority when compared to the other noob wannabe-wizard in my opinion "+ player1.getName()+ " should just go and kill himself for wasting his life away like that\n anyway thanks you for playing and we hope you dont come again after all u just wasted like 30 mins of your life for nothing unlike me who just wasted 10 hours at least 😭");
        scanner.close();
        System.out.print("\033[H\033[2J");
    }

    public void playForgottenTurn(Player player) {
        Dice[] forgottenDice = gameBoard.getForgottenRealmDice();
        int diceCount = forgottenDice.length;
        player.getScoreSheet().displayColoredScoreSheet();
        System.out.println("Here is your scoresheet, " + player.getName() + " :\n");
        System.out.println("It is currently the " + "PASSIVE" + " player's turn.");
        handleDiceDisplay(getForgottenRealmDice(), 1);
        Move[] availableMoves = getAllPossibleMovesForDiceSet(player, getForgottenRealmDice());
        if (availableMoves.length == 0) {
            System.out.println("Hmm... it seems that this set of dice will not allow you to play any move against any of your Realms. Better luck next time!");
            return;
        }
        while(turnCompletion(diceCount, player));
        boolean usedArcaneBoost = handleArcaneBoost(player.getArcaneBoosts());
        while (usedArcaneBoost) {
            handleArcaneBoostCall(player);
            usedArcaneBoost = handleArcaneBoost(player.getArcaneBoosts());
        }
    }

    public void playRound(Player player, String reward, int turnCount) {
        handleRoundRewards(player, reward);
        for (int turn = 0; turn < turnCount && getAvailableDice().length != 0; turn++) {
            System.out.println();
            System.out.println("IT IS CURRENTLY TURN: " + (turn+1));
            boolean valid = playTurn(player, false);
            if (!valid)
                break;
        }
        boolean usedArcaneBoost = handleArcaneBoost(player.getArcaneBoosts());
        while (usedArcaneBoost) {
            handleArcaneBoostCall(player);
            usedArcaneBoost = handleArcaneBoost(player.getArcaneBoosts());
        }
    }

    //If this is a timewarp reroll call, there is no need to redisplay the score sheet and the "We will now roll the dice" message
    public boolean playTurn(Player player, boolean isThisATimeWarpRerollCall) {
        if (!isThisATimeWarpRerollCall) {
            player.getScoreSheet().displayColoredScoreSheet();
            System.out.println("Here is your scoresheet, " + player.getName() + " :\n");
            System.out.println("It is currently the " + "ACTIVE" + " player's turn.");
            System.out.println("I will now roll the dice...");
        }
        if (isThisATimeWarpRerollCall)
            System.out.println("I will now reroll the dice...");
        rollDice();
        int diceCount = getAvailableDice().length;
        handleDiceDisplay(getAvailableDice(), 0);
        Move[] availableMoves = getAllPossibleMovesForDiceSet(player, getAvailableDice());
        if (availableMoves.length == 0) {
            System.out.println("Hmm... it seems that this set of dice will not allow you to play any move against any of your Realms.");
            boolean useTimeWarp = handleTimeWarps(player.getTimeWarps());
            if (useTimeWarp) {
                return playTurn(player, true);
            }
            return false;
        }
        boolean useTimeWarp = handleTimeWarps(player.getTimeWarps());
        if (useTimeWarp) {
            return playTurn(player, true);
        }
        while (turnCompletion(diceCount, player));
        return true;
    }

    public boolean turnCompletion(int diceCount, Player player) {
        Dice[] diceSet = player.getPlayerStatus() == PlayerStatus.ACTIVE ? getAvailableDice() : getForgottenRealmDice();
        Arrays.sort(diceSet);
        Dice chosenDie = handleDiceSelection(player, diceSet);
        int indicator = player.getPlayerStatus() == PlayerStatus.ACTIVE ? 0 : 1;
        while (Objects.equals(chosenDie, null)) {
            handleDiceDisplay(diceSet, indicator);
            chosenDie = handleDiceSelection(player, diceSet);
        }
        Move[] moveSet = getPossibleMovesForADie(player, chosenDie);
        if (moveSet.length == 0) {
            System.out.println("This die does not have any valid moves.");
            System.out.println("I will allow you to reselect the die you would like to attack with.");
            return true;
        }
        Dice finalDie = null;
        if (chosenDie instanceof ArcanePrism) {
            while (Objects.equals(finalDie, null)) {
                finalDie = handleArcanePrism(chosenDie, player);
            }
        }
        else
            finalDie = chosenDie;
        if (finalDie instanceof RedDice) {
            RedDice dummyFinalDie = new RedDice(finalDie.getValue());
            finalDie = handleRedDice(dummyFinalDie, player);
            while (Objects.equals(finalDie, null)) {
                finalDie = handleRedDice(dummyFinalDie, player);
            }
        }
        selectDice(chosenDie, player);
        makeMove(player, new Move(finalDie, getScoreSheet(player).getCreatureByColor(finalDie.getRealm())));
        player.getScoreSheet().displayColoredScoreSheet();
        System.out.println("Here is your score sheet after your move, " + player.getName() + " : ");
        return false;
    }

    public Dice[] getArcaneBoostDice() {
        Dice[] possibleDice = getAllDice();
        ArrayList<Dice> diceExcludingPreviouslySelectedByArcaneBoosts = new ArrayList<>();
        for (Dice die: possibleDice) {
            if (!gameBoard.getActiveArcaneDice().contains(die)) {
                diceExcludingPreviouslySelectedByArcaneBoosts.add(die);
        }
        int size = diceExcludingPreviouslySelectedByArcaneBoosts.size();
        Dice[] availableDice = new Dice[size];
        for (int index = 1; index <= size; index++) {
            availableDice[index-1] = diceExcludingPreviouslySelectedByArcaneBoosts.get(index-1);
        }
        return availableDice;
    }
    public void handleArcaneBoostCall(Player player) {
        Dice[] availableDice = getArcaneBoostDice();
        Arrays.sort(availableDice);
        handleDiceDisplay(availableDice, 2);
        Move[] moveSet = getAllPossibleMovesForDiceSet(player, availableDice);
        if (moveSet.length == 0) {
            System.out.println("Hmm.. this is terrible. It seems that you have wasted your Arcane Boost. Better luck next time!");
            return;
        }
        Dice chosenDie = handleDiceSelection(player, availableDice);
        while (Objects.equals(chosenDie, null)) {
            handleDiceDisplay(availableDice, 2);
            chosenDie = handleDiceSelection(player, availableDice);
        }
        Dice finalDie = null;
        if (chosenDie instanceof ArcanePrism) {
            while (Objects.equals(finalDie, null)) {
                finalDie = handleArcanePrism(chosenDie, player);
            }
        }
        else
            finalDie = chosenDie;
        if (finalDie instanceof RedDice) {
            RedDice dummyFinalDie = new RedDice(finalDie.getValue());
            finalDie = handleRedDice(dummyFinalDie, player);
            while (Objects.equals(finalDie, null)) {
                finalDie = handleRedDice(dummyFinalDie, player);
            }
        }
        makeMove(player, new Move(finalDie, getScoreSheet(player).getCreatureByColor(finalDie.getRealm())));
        selectDice(finalDie, player);
        gameBoard.moveToArcaneDice(finalDie);
    }

    public RedDice handleRedDice(RedDice finalDie, Player player) {
        System.out.println("Since you have chosen to attack the Red Realm, you must also select which Dragon you would like to attack.");
        System.out.println("Please select a number between 1 and 4 to indicate which Dragon you would like to attack!");
        int selectedDragon = -1;
        String input = scanner.next();
        while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")) {
            System.out.println("That dragon does not exist.");
            System.out.println("Please select a number between 1 and 4 to indicate which Dragon you would like to attack!");
            input = scanner.next();
        }
        selectedDragon = Integer.parseInt(input);
        finalDie.selectsDragon(selectedDragon);
        Move[] moveList = getPossibleMovesForADie(player, finalDie);
        if (moveList.length == 0) {
            System.out.println("Hmm... it seems that this specific Dragon cannot be attacked by this Die.");
            System.out.println("Alright, I shall rewind time to give you another shot at selecting the Dragon you would like to attack. Please be careful this time.");
            System.out.println("Good luck!");
            return null;
        }
        return finalDie;
    }
    //indicator = 0 -> Active player call
    //indicator = 1 -> Passive player call
    //indicator = 2 -> Post-Arcane Boost call
    //indicator = 3 -> Pre-Arcane Boost call
    public void handleDiceDisplay(Dice[] diceSet, int indicator) {
        int diceCount = diceSet.length;
        Arrays.sort(diceSet);
        if (indicator == 0 || indicator == 2) {
            System.out.println("Here are the available dice: \n");
            for (int diceIndex = 1; diceIndex <= diceCount; diceIndex++) {
                System.out.print(diceIndex + ". ");
                displayDice(diceSet[diceIndex-1]);
                System.out.println();
            }
        }
        else if (indicator == 1){
            System.out.println("Here are the forgotten dice: \n");
            for (int diceIndex = 1; diceIndex <= diceCount; diceIndex++) {
                System.out.print(diceIndex + ". ");
                displayDice(diceSet[diceIndex-1]);
                System.out.println();
            }
        }
        else {
            System.out.println("Here are the dice that you would be able to use if you use your Arcane Boost: \n");
            for (int diceIndex = 1; diceIndex <= diceCount; diceIndex++) {
                System.out.print(diceIndex + ". ");
                displayDice(diceSet[diceIndex-1]);
                System.out.println();
            }
        }
    }
    public Dice handleDiceSelection(Player player, Dice[] diceSet) {
        Arrays.sort(diceSet);
        int diceCount = diceSet.length;
        System.out.println("Please enter a number from 1 to " + diceCount + " which indicates which dice you would like to use.");
        int chosenDiceIndex = -1;
        while (true) {
            String input = scanner.next();
            boolean validInput = false;
            for (int i = 1; i <= diceCount; i++) {
                validInput = validInput || input.equals("" + i);
            }
            if (validInput) {
                chosenDiceIndex = Integer.parseInt(input);
                break;
            }
            else {
                System.out.println("Invalid input, please try again.");
                System.out.println("Please enter a number from 1 to " + diceCount + " which indicates which dice you would like to use.");
            }
        }
        Dice chosenDie = diceSet[chosenDiceIndex-1];
        Move[] moveList = getPossibleMovesForADie(player, chosenDie);
        if (moveList.length == 0) {
            System.out.println("Unfortunately, the die you have chosen does not have any possible moves available.\nI will now rewind time to give you a chance to select another die. Good luck!");
            chosenDie = null;
        }
        return chosenDie;
    }

    public Dice handleArcanePrism(Dice chosenDie, Player player) {
        System.out.println("You have chosen to play with the Arcane Prism! This dice can be used to attack any realm.");
        System.out.println("Please enter a number from 1 to 5 to choose the realm you would like to attack.");
        System.out.println("\u001B[31m" + "1. Red Realm " + "\u001B[0m" + "\n" +
                "\u001B[32m" + "2. Green Realm" + "  (" + (gameBoard.getWhite().getValue() + gameBoard.getGreen().getValue()) + ")\u001B[0m" + "\n" +
                "\u001B[34m" + "3. Blue Realm" + "\u001B[0m" + "\n" +
                "\u001B[35m" + "4. Magenta Realm" + "\u001B[0m" + "\n" +
                "\u001B[33m" + "5. Yellow Realm" + "\u001B[0m" + "\n");
        int realmChosen;
        String input = scanner.next();
        while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5")) {
            System.out.println("Invalid input.");
            System.out.println("Please enter a number from 1 to 5 to choose the realm you would like to attack.");
            input = scanner.next();
        }
        realmChosen = Integer.parseInt(input);
        Dice diceAfterSelection = new Dice();
        switch (realmChosen) {
            case 1: diceAfterSelection = new RedDice(chosenDie.getValue()); break;
            case 2: diceAfterSelection = gameBoard.getGreen(); break;
            case 3: diceAfterSelection = new BlueDice(chosenDie.getValue()); break;
            case 4: diceAfterSelection = new MagentaDice(chosenDie.getValue()); break;
            case 5: diceAfterSelection = new YellowDice(chosenDie.getValue());
        }
        Move[] moveList = getPossibleMovesForADie(player, diceAfterSelection);
        if (moveList.length == 0) {
            System.out.println("Unfortunately, you can not cast the Arcane Prism into this form because it has no valid moves.");
            System.out.println("I shall now rewind time to give you another chance at casting your Arcane Prism. Good luck!");
            return null;
        }
        return diceAfterSelection;
    }
    public void displayDice (Dice dice) {
        switch (dice.getRealm()) {
            case RED: System.out.print("\u001B[31m" + dice.getRealm() + "      " + dice.getValue() + "\u001B[0m"); break;
            case GREEN: System.out.print("\u001B[32m" + dice.getRealm() + "    " + dice.getValue() + "  " + "(" + (dice.getValue() + gameBoard.getWhite().getValue()) + ")" + "\u001B[0m"); break;
            case BLUE: System.out.print("\u001B[34m" + dice.getRealm() + "     " + dice.getValue() + "\u001B[0m"); break;
            case MAGENTA: System.out.print("\u001B[35m" + dice.getRealm() + "  " + dice.getValue() + "\u001B[0m"); break;
            case YELLOW: System.out.print("\u001B[33m" + dice.getRealm() + "   " + dice.getValue() + "\u001B[0m"); break;
            case WHITE: System.out.print("\u001B[37m" + dice.getRealm() + "    " + dice.getValue() + "\u001B[0m"); break;
        }
    }
    public boolean handleArcaneBoost(ArrayList<ArcaneBoost> arcaneBoosts){
        if (arcaneBoosts.isEmpty())
            return false;
        // System.out.println("are you disatisfied by such rotten luck and would like to get another roll at your fate (this will use one of your aqcuired timewarps becuase nothing in this life is for free)\n (press 'y' or 'y' because no one is satisfied aslan no just kidding ");
        //dummy value initialization for loop entry
        char c = 'a';
        int arcaneBoostCount = 0;
        for (ArcaneBoost arcaneBoost: arcaneBoosts) {
            if (arcaneBoost.getStatus() == RewardStates.ACQUIRED)
                arcaneBoostCount++;
        }
        for (ArcaneBoost arcaneBoost: arcaneBoosts) {
            if (arcaneBoost.getStatus() == RewardStates.ACQUIRED) {
                System.out.println("You have available Arcane Boosts! Would you like to use one of them to attack one of your Realms again?");
                System.out.println("You have a total of " + arcaneBoostCount + " Arcane Boost(s).");
                Dice[] arcaneBoostDice = getArcaneBoostDice();
                handleDiceDisplay(arcaneBoostDice, 4);
                System.out.println("Please enter 'y' if you want to use an Arcane Boost, or 'n' if you don't want to.");
                do {
                    String input = scanner.next();
                    if (!input.isEmpty())
                        c = input.charAt(0);
                    if (c == 'y') {
                        System.out.println("Alright, you will get a chance to attack your Realms again.\n");
                    }
                    else if (c == 'n') {
                        System.out.println("Alright, you will not get a chance to attack your Realms again.\n");
                    }
                    else {
                        System.out.println("Invalid input, please try again.");
                        System.out.println("Please enter 'y' if you want to use an Arcane Boost, or 'n' if you don't want to.");
                    }

                } while (c != 'y' && c != 'n');
                if (c == 'y') {
                    arcaneBoost.setStatus(RewardStates.USED);
                }
                break;
            }
        }
        return c == 'y';
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
        Player currentActivePlayer= getActivePlayer();

        Creature creature=getCreatureToAttacByColor(realmChoice, getScoreSheet(getActivePlayer()));
        do {
            try {
                int numChoice =0;
                String input;
                do{
                    System.out.println("please choose a number from 1-6 to attack with");
                    input = scanner.next();
                }while(!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5") && !input.equals("6"));
                numChoice = Integer.parseInt(input);
                if (makeMove(currentActivePlayer, new Move(new Dice(numChoice), creature)))
                    break;
                System.out.println("please enter try another move that will be valid ");
            } catch (Exception e) {
                System.out.println("batal estehbal we have an unknown Exception");
            }
        } while (true);
    }
    public boolean handleTimeWarps(ArrayList<TimeWarp> timewarps){
        if (timewarps.isEmpty())
            return false;
        // System.out.println("are you disatisfied by such rotten luck and would like to get another roll at your fate (this will use one of your aqcuired timewarps becuase nothing in this life is for free)\n (press 'y' or 'y' because no one is satisfied aslan no just kidding ");
        //dummy value initialization for loop entry
        char c = 'a';
        int timeWarpCount = 0;
        for (TimeWarp timeWarp: timewarps) {
            if (timeWarp.getStatus() == RewardStates.ACQUIRED)
                timeWarpCount++;
        }
        for (TimeWarp timeWarp: timewarps) {
            if (timeWarp.getStatus() == RewardStates.ACQUIRED) {
                System.out.println("You have available Time Warps! Would you like to use one of them to rewind time and reroll your dice?");
                System.out.println("You have a total of " + timeWarpCount + " Time Warp(s).");
                System.out.println("Please enter 'y' if you want to use a Time Warp, or 'n' if you don't want to.");
                do {
                    String input = scanner.next();
                    if (!input.isEmpty())
                        c = input.charAt(0);
                    if (c == 'y') {
                        System.out.println("Alright, the dice shall be rerolled!\n");
                    }
                    else if (c == 'n') {
                        System.out.println("Alright, the dice shall not be rerolled.");
                    }
                    else {
                        System.out.println("Invalid input, please try again.");
                        System.out.println("Please enter 'y' if you want to use a Time Warp, or 'n' if you don't want to.");
                    }
                } while (c != 'y' && c != 'n');
                if (c == 'y') {
                    timeWarp.setStatus(RewardStates.USED);
                }
                break;
            }
        }
        return c == 'y';
    }

    // move methods
    public Move[] getAllPossibleMoves(Player player) {
        return player.getAllPossibleMoves();
    }
    public Move [] getPossibleMovesForAvailableDice(Player player){
        return getAllPossibleMovesForDiceSet(player, getAvailableDice());
    }

    public Move[] getAllPossibleMovesForDiceSet(Player player, Dice[] dice) {
        ArrayList<Move> moveSet = new ArrayList<>();
        for (Dice die: dice) {
            moveSet.addAll(Arrays.asList(getPossibleMovesForADie(player, die)));
        }
        removeGreenDuplicate(moveSet);
        int moveSetSize = moveSet.size();
        Move[] moves = new Move[moveSetSize];
        for (int index = 0; index < moveSetSize; index++) {
            moves[index] = moveSet.get(index);
        }
        Arrays.sort(moves);
        return moves;
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
        //System.out.println(playerAllMoves.length);
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
        try {
            Dice diceToBeMovedWith= move.getDice();
            if (move.getCreature() instanceof Gaia) {
                GreenDice greenDice = (GreenDice) gameBoard.getGreen();
                Dice arcanePrism = gameBoard.getWhite();
                int greenVal = greenDice.getValue();
                int whiteVal = arcanePrism.getValue();
                diceToBeMovedWith = new GreenDice(greenVal+whiteVal);
            }
            boolean temp = player.getScoreSheet().getCreatureByColor(move.getDice().getRealm()).makeMove(diceToBeMovedWith);
            player.updateAllPossibleMoves();
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
            player.updateAllPossibleMoves();
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

    public static void main (String[] args) {
        MyCLIGameController cli = new MyCLIGameController();
        for (Dice dice : cli.getGameBoard().availableDice) {
            System.out.println(dice);
        }
        cli.selectDice(cli.getGameBoard().availableDice.get(0), cli.getActivePlayer());
        System.out.println();
        for (Dice dice : cli.getGameBoard().availableDice) {
            System.out.println(dice);
        }
        System.out.println();
        for (Dice dice : cli.getGameBoard().forgottenRealmDice) {
            System.out.println(dice);
        }
        System.out.println();
        cli.getGameBoard().resetAllDice();
        for (Dice dice : cli.getGameBoard().availableDice) {
            System.out.println(dice);
        }
        System.out.println();
        for (Dice dice : cli.getGameBoard().forgottenRealmDice) {
            System.out.println(dice);
        }
    }

}


