package game.engine;

import game.collectibles.*;
import game.exceptions.*;
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
    public void handleRoundRewards(Player player, String reward) {
        switch (reward){
            case "ArcaneBoost": player.getArcaneBoosts().add(new ArcaneBoost(RewardStates.ACQUIRED)); break;
            case "TimeWarp":   player.getTimeWarps().add(new TimeWarp(RewardStates.ACQUIRED)); break;
            case "EssenceBonus": handleBonus(player, RealmColor.WHITE); break;
            case "RedBonus":    handleBonus(player, RealmColor.RED); break;
            case "GreenBonus": handleBonus(player, RealmColor.GREEN); break;
            case "BlueBonus": handleBonus(player, RealmColor.BLUE); break;
            case "MagentaBonus": handleBonus(player,RealmColor.MAGENTA); break;
            case "YellowBonus": handleBonus(player, RealmColor.YELLOW); break;
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
        Player player2= gameBoard.getPlayer2();
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
    }

    public void playForgottenTurn(Player player) {
        player.getScoreSheet().displayColoredScoreSheet();
        System.out.println("Here is your scoresheet, " + player.getName() + " :\n");
        System.out.println("It is currently the " + "PASSIVE" + " player's turn.");
        boolean valid = false;
        while(!valid) {
            try {
                valid = turnCompletion(player);
            } catch (NoAvailableMovesException e) {
                System.out.println("Hm.. it seems that this set of forgotten dice will not allow you to play any move.\nBetter luck next time!");
                return;
            }
        }
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
        boolean useTimeWarp = handleTimeWarps(player.getTimeWarps());
        if (useTimeWarp) {
            return playTurn(player, true);
        }
        boolean valid = false;
        while(!valid) {
            try {
                valid = turnCompletion(player);
            } catch (NoAvailableMovesException e) {
                System.out.println("Hmm... it seems that this set of dice will not allow you to play any move against any of your Realms.");
                useTimeWarp = handleTimeWarps(player.getTimeWarps());
                if (useTimeWarp) {
                    return playTurn(player, true);
                }
                return false;
            }
        }
        return true;
    }

    public void resetRed() {
        if(getAvailableDice().length != 0 && getAvailableDice()[0] instanceof RedDice) {
            ((RedDice) getAvailableDice()[0]).selectsDragon(0);
        }
    }

    public boolean turnCompletion(Player player) throws NoAvailableMovesException{
        Dice[] diceSet = player.getPlayerStatus() == PlayerStatus.ACTIVE ? getAvailableDice() : getForgottenRealmDice();
        getAllPossibleMovesForDiceSet(player, diceSet);
        Arrays.sort(diceSet);
        boolean valid = false;
        Dice finalDie;
        while (!valid) {
            int indicator = player.getPlayerStatus() == PlayerStatus.ACTIVE ? 0 : 1;
            handleDiceDisplay(diceSet, indicator);
            Dice chosenDie;
            while (true) {
                try {
                    resetRed();
                    chosenDie = handleDiceSelection(player, diceSet);
                } catch (InvalidDiceSelectionException e) {
                    System.out.println("I will now give you a chance to select properly.");
                    handleDiceDisplay(diceSet, indicator);
                    continue;
                } catch (NoAvailableMovesException e) {
                    System.out.println("Hmm.. It seems that this die does not have any valid moves.\nI will now rewind time to give you a chance to reselect your die.\nGood luck!");
                    handleDiceDisplay(diceSet, indicator);
                    continue;
                }
                break;
            }
            finalDie = null;
            if (chosenDie instanceof ArcanePrism) {
                while (Objects.equals(finalDie, null)) {
                    finalDie = handleArcanePrism(chosenDie, player);
                }
            } else
                finalDie = chosenDie;
            if (finalDie instanceof RedDice) {
                boolean valid2 = false;
                while (!valid2) {
                    try {
                        finalDie = handleRedDice((RedDice) finalDie);
                    } catch (InvalidDiceSelectionException e) {
                        System.out.println("Invalid input.\nPlease try again.");
                        continue;
                    }
                    valid2 = true;
                }
            }
            valid = makeMove(player, new Move(finalDie, getScoreSheet(player).getCreatureByColor(finalDie.getRealm())));
            if (valid)
            {
                if (chosenDie instanceof ArcanePrism)
                    selectDice(chosenDie, player);
                else
                    selectDice(finalDie, player);
            }
        }
        player.getScoreSheet().displayColoredScoreSheet();
        System.out.println("Here is your score sheet after your move, " + player.getName() + " : ");
        return true;
    }

    public Dice[] getArcaneBoostDice() {
        Dice[] possibleDice = getAllDice();
        ArrayList<Dice> diceExcludingPreviouslySelectedByArcaneBoosts = new ArrayList<>();
        ArrayList<Dice> bannedDice = gameBoard.getArcaneDice();
        outer: for (Dice die: possibleDice) {
            if (!bannedDice.contains(die))
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
        try {
            getAllPossibleMovesForDiceSet(player, availableDice);
        } catch (NoAvailableMovesException e) {
            System.out.println("Hmm.. this is terrible. It seems that you have wasted your Arcane Boost. Better luck next time!");
            return;
        }
        Dice chosenDie;
        boolean valid = false;
        while (!valid) {
            try {
                chosenDie = handleDiceSelection(player, availableDice);
            } catch (InvalidDiceSelectionException e) {
                System.out.println("Invalid input.\nI will now give you a chance to select properly.");
                handleDiceDisplay(availableDice, 2);
                continue;
            } catch (NoAvailableMovesException e) {
                System.out.println("Hmm.. It seems that this die does not have any valid moves.\nI will now rewind time to give you a chance to reselect your die.\nGood luck!");
                handleDiceDisplay(availableDice, 2);
                continue;
            }
            Dice finalDie = null;
            if (chosenDie instanceof ArcanePrism) {
                while (Objects.equals(finalDie, null)) {
                    finalDie = handleArcanePrism(chosenDie, player);
                }
            } else
                finalDie = chosenDie;
            if (finalDie instanceof RedDice) {
                boolean valid2 = false;
                while (!valid2) {
                    try {
                        finalDie = handleRedDice((RedDice) finalDie);
                    } catch (InvalidDiceSelectionException e) {
                        System.out.println("Invalid input.\nPlease try again.");
                        continue;
                    }
                    valid2 = true;
                }
            }
            valid = makeMove(player, new Move(finalDie, getScoreSheet(player).getCreatureByColor(finalDie.getRealm())));
            if (valid)
                gameBoard.moveToArcaneDice(finalDie);
        }
        player.getScoreSheet().displayColoredScoreSheet();
    }

    public RedDice handleRedDice(RedDice finalDie) throws InvalidDiceSelectionException{
        System.out.println("Since you have chosen to attack the Red Realm, you must also select which Dragon you would like to attack.");
        System.out.println("Please select a number between 1 and 4 to indicate which Dragon you would like to attack!");
        int selectedDragon = -1;
        String input = scanner.next();
        while (input.isEmpty()) {
            input = scanner.next();
        }
        if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4"))
            throw new InvalidDiceSelectionException();
        selectedDragon = Integer.parseInt(input);
        finalDie.selectsDragon(selectedDragon);
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
    public Dice handleDiceSelection(Player player, Dice[] diceSet) throws InvalidDiceSelectionException, NoAvailableMovesException{
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
                throw new InvalidDiceSelectionException();
            }
        }
        Dice chosenDie = diceSet[chosenDiceIndex-1];
        Move[] moveList = getPossibleMovesForADie(player, chosenDie);
        if (moveList.length == 0) {
            throw new NoAvailableMovesException();
        }
        return chosenDie;
    }

    public Dice handleArcanePrism(Dice chosenDie, Player player) {
        System.out.println("You have chosen to play with the Arcane Prism! This dice can be used to attack any realm.");
        System.out.println("Please enter a number from 1 to 5 to choose the realm you would like to attack.");
        System.out.println("1. \u001B[31m" + "Red Realm " + "\u001B[0m" + "\n" +
                "2. \u001B[32m" + "Green Realm" + "  (" + (gameBoard.getWhite().getValue() + gameBoard.getGreen().getValue()) + ")\u001B[0m" + "\n" +
                "3. \u001B[34m" + "Blue Realm" + "\u001B[0m" + "\n" +
                "4. \u001B[35m" + "Magenta Realm" + "\u001B[0m" + "\n" +
                "5. \u001B[33m" + "Yellow Realm" + "\u001B[0m" + "\n");
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
            case GREEN: System.out.print("\u001B[32m" + dice.getRealm() + "    " + dice.getValue() + "  (" + (dice.getValue() + gameBoard.getWhite().getValue()) + ")\u001B[0m"); break;
            case BLUE: System.out.print("\u001B[34m" + dice.getRealm() + "     " + dice.getValue() + "\u001B[0m"); break;
            case MAGENTA: System.out.print("\u001B[35m" + dice.getRealm() + "  " + dice.getValue() + "\u001B[0m"); break;
            case YELLOW: System.out.print("\u001B[33m" + dice.getRealm() + "   " + dice.getValue() + "\u001B[0m"); break;
            case WHITE: System.out.print("\u001B[37m" + dice.getRealm() + "    " + dice.getValue() + "\u001B[32m  (" + (dice.getValue() + gameBoard.getGreen().getValue()) + ")\u001B[0m"); break;
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
    public void handleBonus(Player player, RealmColor realmColor){
        Dice chosenDie;
        boolean valid = false;
        while (!valid) {
            try {
                chosenDie = handleColorBonusException(realmColor, player);
            } catch (NoAvailableMovesException e) {
                System.out.println("Hmm.. it seems that the " + realmColor + " Bonus that you have obtained will not allow you to play any moves. Better luck next time!");
                return;
            } catch (InvalidBonusSelection e) {
                System.out.println("The bonus color that you have chosen unfortunately has no moves. I will now give you another shot at morphing your WHITE bonus.\nGood luck!");
                continue;
            }
            catch (InvalidDiceSelectionException e) {
                continue;
            }
            valid = makeMove(player, new Move(chosenDie, getScoreSheet(player).getCreatureByColor(chosenDie.getRealm())));
        }
        player.updateGameScore();
        player.updateAllPossibleMoves();
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
                handleDiceDisplay(getAvailableDice(), 0);
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
                        return false;
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
        Move[] moveSet = new Move[0];
        try {
            ArrayList<Dice> availableDice = gameBoard.getAvailableDice();
            Dice[] dice = new Dice[availableDice.size()];
            for (int i = 0; i < availableDice.size(); i++)
                dice[i] = availableDice.get(i);
            moveSet = getAllPossibleMovesForDiceSet(player, dice);
        }
        catch (NoAvailableMovesException e)
        {
            return moveSet;
        }
        return moveSet;
    }

    public Move[] getAllPossibleMovesForDiceSet (Player player, Dice[] dice) throws NoAvailableMovesException{
        ArrayList<Move> moveSet = new ArrayList<>();
        for (Dice die: dice) {
            moveSet.addAll(Arrays.asList(getPossibleMovesForADie(player, die)));
        }
        removeGreenDuplicate(moveSet);
        int moveSetSize = moveSet.size();
        if (moveSetSize == 0)
            throw new NoAvailableMovesException();
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
        gameBoard.setWhite();
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
            if (!temp)
                throw new InvalidMoveException();
            else
                return true;
        } catch (BonusException bException) {
            RealmColor realmColor1 = bException.getRealmColor1();
            Dice chosenDie;
            boolean valid = false;
            while (!valid) {
                try {
                    chosenDie = handleColorBonusException(realmColor1, player);
                } catch (NoAvailableMovesException e) {
                    System.out.println("Hmm.. it seems that the " + realmColor1 + " Bonus that you have obtained will not allow you to play any moves. Better luck next time!");
                    return true;
                } catch (InvalidBonusSelection e) {
                    System.out.println("The bonus color that you have chosen unfortunately has no moves. I will now give you another shot at morphing your WHITE bonus.\nGood luck!");
                    continue;
                } catch (InvalidDiceSelectionException e) {
                    continue;
                }
                valid = makeMove(player, new Move(chosenDie, getScoreSheet(player).getCreatureByColor(chosenDie.getRealm())));
            }
            player.updateGameScore();
            player.updateAllPossibleMoves();
            if (bException.getRealmColor2() != RealmColor.PARENT) {
                RealmColor realmColor2 = bException.getRealmColor2();
                valid = false;
                while (!valid) {
                    try {
                        chosenDie = handleColorBonusException(realmColor2, player);
                    } catch (NoAvailableMovesException e) {
                        System.out.println("Hmm.. it seems that the " + realmColor2 + " Bonus that you have obtained will not allow you to play any moves. Better luck next time!");
                        return true;
                    } catch (InvalidBonusSelection e) {
                        System.out.println("The bonus color that you have chosen unfortunately has no moves. I will now give you another shot at morphing your WHITE bonus.\nGood luck!");
                        continue;
                    } catch (InvalidDiceSelectionException e) {
                        continue;
                    }
                    valid = makeMove(player, new Move(chosenDie, getScoreSheet(player).getCreatureByColor(chosenDie.getRealm())));
                }
                player.updateGameScore();
                player.updateAllPossibleMoves();
            }
            return true;
        }
        catch (InvalidMoveException Im){
            System.out.println("It seems that this move is invalid.\nPlease try again.");
            return false;
        }
    }

    public Dice handleColorBonusException(RealmColor color, Player player) throws NoAvailableMovesException, InvalidBonusSelection, InvalidDiceSelectionException{
        gameBoard.setWhite();
        Dice finalDie = null;
        String input = "";
        player.getScoreSheet().displayColoredScoreSheet();
        if (color == RealmColor.WHITE) {
            Move[] possibleMoves = getAllPossibleMoves(player);
            if (possibleMoves.length == 0)
                throw new NoAvailableMovesException();
            while (input.isEmpty()) {
                System.out.println("You have just obtained an Essence Bonus! This will allow you to play any Move against any Realm you want!");
                System.out.println("Please enter a number from 1 to 5 to choose the Color that you want to morph your Essence Bonus into.");
                System.out.println("1. \u001B[31m" +  "Red Realm " + "\u001B[0m" + "\n" +
                        "2. \u001B[32m" + "Green Realm" + "\u001B[0m" + "\n" +
                        "3. \u001B[34m" + "Blue Realm" + "\u001B[0m" + "\n" +
                        "4. \u001B[35m" + "Magenta Realm" + "\u001B[0m" + "\n" +
                        "5. \u001B[33m" + "Yellow Realm" + "\u001B[0m" + "\n");
                input = scanner.next();
                boolean validInput = false;
                for (int i = 1; i <= 5 && !validInput; i++)
                    validInput = input.equals("" + i);
                if (!validInput) {
                    input = "";
                    System.out.println("This is not a valid Realm...\nI will now give you another chance to select properly.\n");
                }
            }
            int value = Integer.parseInt(input);
            switch (value) {
                case 1: color = RealmColor.RED; break;
                case 2: color = RealmColor.GREEN; break;
                case 3: color = RealmColor.BLUE; break;
                case 4: color = RealmColor.MAGENTA; break;
                case 5: color = RealmColor.YELLOW; break;
            }
            possibleMoves = getAllPossibleMoves(player);
            boolean canYouUseThisBonus = false;
            for (Move move: possibleMoves) {
                canYouUseThisBonus = canYouUseThisBonus || move.getDice().getRealm().equals(color);
            }
            if (!canYouUseThisBonus) {
                throw new InvalidBonusSelection();
            }
        }
        System.out.println("You have just obtained a " + color + " Bonus (Or you have morphed your Essence Bonus into a " + color + " Bonus)!\n");
        Move[] possibleMoves = getAllPossibleMoves(player);
        boolean canYouUseThisBonus = false;
        for (Move move: possibleMoves) {
            canYouUseThisBonus = canYouUseThisBonus || move.getDice().getRealm().equals(color);
        }
        if (!canYouUseThisBonus) {
            throw new NoAvailableMovesException();
        }
        if (color == RealmColor.GREEN) {
            System.out.println("Please input a value between 2-12 that you would like to use to attack the GREEN Realm with!");
            input = scanner.next();
            boolean validInput = false;
            for (int i = 2; i <= 12 && !validInput; i++)
                validInput = input.equals("" + i);
            if (!validInput) {
                System.out.println("This is not a valid input... \nI will give you another chance to select properly.");
                throw new InvalidDiceSelectionException();
            }
            int value = Integer.parseInt(input);
            finalDie = new GreenDice(value);
            gameBoard.setGreen(value);
        }
        else if (color == RealmColor.RED) {
            System.out.println("Please input a value between 1-6 that you would like to use to attack the RED Realm with!");
            input = scanner.next();
            boolean validInput = false;
            for (int i = 1; i <= 6 && !validInput; i++)
                validInput = input.equals("" + i);
            if (!validInput) {
                System.out.println("This is not a valid input... \nI will give you another chance to select properly.");
                throw new InvalidDiceSelectionException();
            }
            int value = Integer.parseInt(input);
            finalDie = new RedDice(value);
            System.out.println("Since you have chosen to attack the Red Realm, you must also select which Dragon you would like to attack.");
            System.out.println("Please select a number between 1 and 4 to indicate which Dragon you would like to attack!");
            input = scanner.next();
            while (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")) {
                System.out.println("That dragon does not exist.\nI will now give you another chance to select properly.\n");
                System.out.println("Please select a number between 1 and 4 to indicate which Dragon you would like to attack!");
                input = scanner.next();
            }
            ((RedDice)finalDie).selectsDragon(Integer.parseInt(input));
        }
        else {
            System.out.println("Please input a value between 1-6 that you would like to use to attack the " + color + " Realm with!");
            input = scanner.next();
            boolean validInput = false;
            for (int i = 1; i <= 6 && !validInput; i++)
                validInput = input.equals("" + i);
            if (!validInput) {
                System.out.println("This is not a valid input... \nI will give you another chance to select properly.");
                throw new InvalidDiceSelectionException();
            }
            int value = Integer.parseInt(input);
            switch (color) {
                case BLUE: finalDie= new BlueDice(value); break;
                case MAGENTA: finalDie = new MagentaDice(value); break;
                case YELLOW: finalDie = new YellowDice(value);
            };
        }
        return finalDie;
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
        gameBoard.rollAvailableDice();
        Dice[] dice = new Dice[gameBoard.getAvailableDice().size()];
        for (int i = 0; i < dice.length; i++)
            dice[i] = gameBoard.getAvailableDice().get(i);
        return dice;
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
        ((RedDice)cli.getAllDice()[0]).selectsDragon(1);
        cli.makeMove(cli.getGameBoard().getPlayer1(), new Move(cli.getAllDice()[0], cli.getGameBoard().getPlayer1().getScoreSheet().dragon));
        System.out.println(cli.getGameBoard().getPlayer1().getScoreSheet().dragon.getAllPossibleMoves());
    }

}


