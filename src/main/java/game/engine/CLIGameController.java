package game.engine;

import game.collectibles.*;
import game.exceptions.*;
import javafx.scene.paint.Color;
import game.dice.*;
import game.creatures.Creature;
import game.creatures.Dragon;
import game.creatures.Phoenix;
import game.creatures.greenclasses.Gaia;
import game.creatures.greenclasses.Guardians;
import game.engine.enums.*;

///import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CLIGameController {
    public GameBoard gameBoard;
    Scanner scanner;
    static final String[] magicNames = {
        "Akiramenai", "Clown", "Zephyrion", "Luminara", "Amrosgy", "Elandor", "Celestia", "Drakonis",
        "Seraphina", "Faelan", "Azura", "Eldric", "Isilme", "Badawayyy", "Aelar", "Lyra", "Vesper",
        "Dumbbelldoor", "CNC", "Boring", "Sylphine", "Zeus", "Arion", "Liora", "Valerian",
        "Esmeray", "Amara", "Kael", "MONSTER...THE DRINK", "Oberon", "Elara", "Utopia", "Morrigan",
        "Za3bola", "Kaelen", "REWE", "Dumbledore", "Fenris", "Gandalf", "Dimension6", "Arwen", "Serapis",
        "ACE", "Sixfold", "Marianna", "El Le3ba", "Za3bola", "Hooba"
    };

    // ANSI escape codes for various colors
    static final String RESET = "\u001B[0m";
    static final String[] COLORS = {
        "\u001B[31m", // Red
        "\u001B[33m", // Yellow
        "\u001B[32m", // Green
        "\u001B[36m", // Cyan
        "\u001B[34m", // Blue
        "\u001B[35m", // Magenta
    };


    // constructor(s):
    public CLIGameController() {
        this.gameBoard= new GameBoard();
        scanner = new Scanner(System.in);
    }
    public void getRewardsProp(){
        try {
            FileReader SettingsfileReader = new FileReader("src/main/resources/config/RoundsRewards.properties");
            Properties p = new Properties();
            p.load(SettingsfileReader);
            System.out.println(p.get("round1Reward")); // making sure the properties file is loaded correctly

        } catch (IOException e) {
            System.out.println("the file has not been found the default rewards will be used");
            // code to be implemented
        }

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
        return new int[]{numberOfRounds, numebrOfTurnsPerRound};
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
        System.out.println("enter 1 if you wanna play against the human and 2 if you wanna play against the computer or 3 to see ai vs ai");
        String modeChoice = scanner.nextLine();
        //TODO CHECK FOR THE VALIDITY OF THE INPUT
        while(!modeChoice.equals("1") && !modeChoice.equals("2")&&!modeChoice.equals("3")) {
            System.out.println("Invalid input. Please try again.");
            modeChoice = scanner.nextLine();
        }
        if (modeChoice.equals("1")) {
            System.out.println("please input the name of player 1:");
            String player1Name = scanner.nextLine();
            if (player1Name.trim().isEmpty()) {
                Random random = new Random();

                // Get a random index between 0 and the length of the array
                int randomIndex = random.nextInt(magicNames.length);

                // Get the random name from the array
                String randomName = magicNames[randomIndex];

                player1Name = randomName;
            }

            switch(player1Name.toLowerCase()) {
                case "dimension6":
                    printRainbowText("The Best Team");
                    player1Name = changeToRainbowText(player1Name);
                    break;
                
                case "slmat":    
                case "doctor":
                case "dr":
                case "dr.":
                case "doc":
                case "ahmed hussein":
                    player1Name = changeToRainbowText("slmat27");
                    printRainbowText("Hi slmat27");
                    break;
                
                case "noureldin":
                case "nesegemaa":
                case "mahmoud":
                case "elephant":
                case "elephanto":
                case "elephanto gyat":
                case "elephantogyat":
                case "0ping":
                case "safwat":
                case "hamed":
                case "hotdog":
                case "hotdawg":
                case "tamer":
                case "kirat":
                    player1Name = changeToRainbowText("Xx" + player1Name + "xX");
                    printRainbowText("^_^ Hello Chat. Is this W-rizz?");
                    break;
                
                case "ace":
                case "rewe":
                case "el le3ba":
                case "le3ba":
                case "dumbbeldoor":
                case "sixfold":
                case "amrosgy":
                case "utopia":
                case "akiraminai":
                case "badawayyy":
                case "zeus":
                    player1Name = changeToRainbowText(player1Name);
                    printRainbowText("=_= Hello losers.");
                    break;

                case "sharazad":
                    player1Name = changeToRainbowText(player1Name);
                    printRainbowText("Don't cry over spilled Fruit Punch");
                    break;
                
                case "giu":
                    player1Name = changeToRainbowText(player1Name);
                    System.out.println("\u001B[31m#####################\u001B[0m");
                    System.out.println("\u001B[33m#####################\u001B[0m");
                    System.out.println("\u001B[30m#####################\u001B[0m");
                    break;
                case "guc":
                    player1Name = changeToRainbowText(player1Name);
                    System.out.println("\u001B[31m#####################\u001B[0m");
                    System.out.println("\u001B[37m########\u001B[33m#####\u001B[37m########\u001B[0m");
                    System.out.println("\u001B[30m#####################\u001B[0m");
                
                case "meow":
                    player1Name = changeToRainbowText(player1Name);
                    printRainbowText("blawg is NOT a cat ");
                    
                default:
                    break;
            }
            getActivePlayer().setName(player1Name);
            System.out.println("please input the name of player 2:");
            String player2Name = scanner.nextLine();

            if (player2Name.trim().equals("")) {
                Random random = new Random();
                String randomName;

                do {
                    // Get a random index between 0 and the length of the array
                    int randomIndex = random.nextInt(magicNames.length);

                    // Get the random name from the array
                    randomName = magicNames[randomIndex];
                } while (randomName.equals(player1Name));

                player2Name = randomName;
            }

            switch(player2Name.toLowerCase()) {
                case "dimension6":
                    printRainbowText("The Best Team");
                    player2Name = changeToRainbowText(player2Name);
                    break;
                
                case "slmat":    
                case "doctor":
                case "dr":
                case "dr.":
                case "doc":
                case "ahmed hussein":
                    player2Name = changeToRainbowText("slmat27");
                    printRainbowText("Hi slmat27");
                    break;
                
                case "noureldin":
                case "nesegemaa":
                case "mahmoud":
                case "elephant":
                case "elephanto":
                case "elephanto gyat":
                case "elephantogyat":
                case "0ping":
                case "safwat":
                case "hamed":
                case "hotdog":
                case "hotdawg":
                case "tamer":
                case "kirat":
                player2Name = changeToRainbowText("Xx" + player2Name + "xX");
                    printRainbowText("^_^ Hello Chat. Is this W-rizz?");
                    break;
                
                case "ace":
                case "rewe":
                case "el le3ba":
                case "le3ba":
                case "dumbbeldoor":
                case "sixfold":
                case "amrosgy":
                case "utopia":
                case "akiraminai":
                case "badawayyy":
                case "zeus":
                player2Name = changeToRainbowText(player2Name);
                    printRainbowText("=_= Hello losers.");
                    break;

                case "sharazad":
                player2Name = changeToRainbowText(player2Name);
                    printRainbowText("Don't cry over spilled Fruit Punch");
                    break;
                
                case "giu":
                    player2Name = changeToRainbowText(player2Name);
                    System.out.println("\u001B[31m#####################\u001B[0m");
                    System.out.println("\u001B[33m#####################\u001B[0m");
                    System.out.println("\u001B[30m#####################\u001B[0m");
                    break;
                case "guc":
                    player2Name = changeToRainbowText(player2Name);
                    System.out.println("\u001B[31m#####################\u001B[0m");
                    System.out.println("\u001B[37m########\u001B[33m#####\u001B[37m########\u001B[0m");
                    System.out.println("\u001B[30m#####################\u001B[0m");
                
                case "meow":
                    player2Name = changeToRainbowText(player2Name);
                    printRainbowText("blawg is NOT a cat ");
                    
                default:
                    break;
            }
            getPassivePlayer().setName(player2Name);

            int [] temp = getSettings();
            int numberOfRounds= temp[0];    //TODO make a default in case the config is empty
            int numebrOfTurnsPerRound=temp[1];


            System.out.println("Welcome to the mystical lands of Eldoria! \nPress 'i' to get more information about the game or 'c' to continue straight away to the game");
            do {
                String choice = scanner.nextLine();
                if (!choice.isEmpty() && 'i' == choice.charAt(0)) {
                    System.out.println("\r\n" + "Welcome to the enchanting realm of Eldoria, where wizards are summoned to embark on a daring quest of conquest and elemental mastery! In this mystical land teeming with ancient magic and untamed wilderness, players will venture forth to claim the coveted Elemental Crests. These crests, symbols of unparalleled power and dominion over the elements, are scattered across the realms guarded by formidable elemental creatures.\r\n" + "\r\n" +"Prepare to encounter the blazing fury of Pyroclast Dragons, the indomitable strength of Gaia Guardians, the serpentine mysteries of Hydra Serpents, the soaring majesty of Majestic Phoenixes, and the radiant splendor of Solar Lions. As wizards, you must harness your magical prowess, exercise cunning strategy, and unleash your wits to subdue these elemental beings and seize the crests.\r\n" + "\r\n" + "Only by mastering the elements and outwitting your rivals can you ascend to become the most formidable mage in all of Eldoria. Are you ready to embark on this epic journey and claim your rightful place among the legends of magic? The fate of Eldoria awaits your command!");
                    break;
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
                playRound(getActivePlayer(), getPassivePlayer(), rewards[round], numebrOfTurnsPerRound);
                gameBoard.resetAllDice();
                switchPlayer();
                System.out.println();
                System.out.println("IT IS CURRENTLY ROUND: " + (round+1));
                playRound(getActivePlayer(), getPassivePlayer(), rewards[round].equals("ArcaneBoost") ? "skip" : rewards[round] , numebrOfTurnsPerRound);
                gameBoard.resetAllDice();
                switchPlayer();
            }
            Player player1= gameBoard.getPlayer1();
            Player player2= gameBoard.getPlayer2();
            System.out.println("The scoresheet of Player "+ player1.getName()+" is the following:");
            player1.getScoreSheet().displayColoredScoreSheet();
            System.out.println( player1.getGameScore().toString() + "\n");
            int player1Score= player1.getGameScore().getTotalScore();

            System.out.println("The scoresheet of Player "+ player2.getName()+" is the following:");
            player2.getScoreSheet().displayColoredScoreSheet();
            System.out.println( player2.getGameScore().toString() + "\n");
            int player2Score= player2.getGameScore().getTotalScore();

            if (player1Score == player2Score)
            {
                int[] player1Scores = player1.getGameScore().getAllScores();
                int[] player2Scores = player2.getGameScore().getAllScores();
                for (int i = 0; i < player2Scores.length; i++) {
                    if (player1Scores[i] > player2Scores[i]) {
                        player1Score = 100;
                        player2Score = 0;
                    }
                    else if (player1Scores[i] < player2Scores[i]) {
                        player1Score = 0;
                        player2Score = 100;
                    }
                }
            }
            if (player1Score > player2Score)
                System.out.println("Congratulations, "+player1.getName()+"! You have emerged victorious in this wonderful battle!");
            else if (player1Score < player2Score)
                System.out.println("Congratulations, "+player2.getName()+"! You have emerged victorious in this wonderful battle!");
            else {
                System.out.println("It is a draw!");
            }
            scanner.close();
        }

        else if(modeChoice.equals("2")){//ai shit
            System.out.println("please input the name of player 1:");
            String player1Name = scanner.nextLine();
            if (player1Name.trim().isEmpty()) {
                Random random = new Random();

                // Get a random index between 0 and the length of the array
                int randomIndex = random.nextInt(magicNames.length);

                // Get the random name from the array
                String randomName = magicNames[randomIndex];

                player1Name = randomName;
            }

            switch(player1Name.toLowerCase()) {
                case "dimension6":
                    printRainbowText("The Best Team");
                    player1Name = changeToRainbowText(player1Name);
                    break;
                
                case "slmat":    
                case "doctor":
                case "dr":
                case "dr.":
                case "doc":
                case "ahmed hussein":
                    player1Name = changeToRainbowText("slmat27");
                    printRainbowText("Hi slmat27");
                    break;
                
                case "noureldin":
                case "nesegemaa":
                case "mahmoud":
                case "elephant":
                case "elephanto":
                case "elephanto gyat":
                case "elephantogyat":
                case "0ping":
                case "safwat":
                case "hamed":
                case "hotdog":
                case "hotdawg":
                case "tamer":
                case "kirat":
                    player1Name = changeToRainbowText("Xx" + player1Name + "xX");
                    printRainbowText("^_^ Hello Chat. Is this W-rizz?");
                    break;
                
                case "ace":
                case "rewe":
                case "el le3ba":
                case "le3ba":
                case "dumbbeldoor":
                case "sixfold":
                case "amrosgy":
                case "utopia":
                case "akiraminai":
                case "badawayyy":
                case "zeus":
                    player1Name = changeToRainbowText(player1Name);
                    printRainbowText("=_= Hello losers.");
                    break;

                case "sharazad":
                    player1Name = changeToRainbowText(player1Name);
                    printRainbowText("Don't cry over spilled Fruit Punch");
                    break;
                
                case "giu":
                    player1Name = changeToRainbowText(player1Name);
                    System.out.println("\u001B[31m#####################\u001B[0m");
                    System.out.println("\u001B[33m#####################\u001B[0m");
                    System.out.println("\u001B[30m#####################\u001B[0m");
                    break;
                case "guc":
                    player1Name = changeToRainbowText(player1Name);
                    System.out.println("\u001B[31m#####################\u001B[0m");
                    System.out.println("\u001B[37m########\u001B[33m#####\u001B[37m########\u001B[0m");
                    System.out.println("\u001B[30m#####################\u001B[0m");
                
                case "meow":
                    player1Name = changeToRainbowText(player1Name);
                    printRainbowText("blawg is NOT a cat ");
                    
                default:
                    break;
            }
            getActivePlayer().setName(player1Name);
            String AI="AI";
            int [] temp = getSettings();
            int numberOfRounds= temp[0];    //TODO make a default in case the config is empty
            int numebrOfTurnsPerRound=temp[1];


            System.out.println("Welcome to the mystical lands of Eldoria! \nPress 'i' to get more information about the game or 'c' to continue straight away to the game");
            do {
                String choice = scanner.nextLine();
                if (!choice.isEmpty() && 'i' == choice.charAt(0)) {
                    System.out.println("\r\n" + "Welcome to the enchanting realm of Eldoria, where wizards are summoned to embark on a daring quest of conquest and elemental mastery! In this mystical land teeming with ancient magic and untamed wilderness, players will venture forth to claim the coveted Elemental Crests. These crests, symbols of unparalleled power and dominion over the elements, are scattered across the realms guarded by formidable elemental creatures.\r\n" + "\r\n" +"Prepare to encounter the blazing fury of Pyroclast Dragons, the indomitable strength of Gaia Guardians, the serpentine mysteries of Hydra Serpents, the soaring majesty of Majestic Phoenixes, and the radiant splendor of Solar Lions. As wizards, you must harness your magical prowess, exercise cunning strategy, and unleash your wits to subdue these elemental beings and seize the crests.\r\n" + "\r\n" + "Only by mastering the elements and outwitting your rivals can you ascend to become the most formidable mage in all of Eldoria. Are you ready to embark on this epic journey and claim your rightful place among the legends of magic? The fate of Eldoria awaits your command!");
                    break;
                } else if (!choice.isEmpty() && choice.charAt(0)=='c')
                    break;
                else
                    System.out.println("Please choose sth correct\n");
            } while (true);

            //the following is taking in the round rewards from the properties file
            String rewards [] = getRewards(numberOfRounds);

             //the following is trying to start the game loop: and needs to be changed
             // the scoresheet for the ai should be displayed once after the end of each round for better transparency and strategy for the human player
             for (int round = 0; round < numberOfRounds; round++) {
                System.out.println();
                System.out.println("IT IS CURRENTLY ROUND: " + (round+1));
                playRoundHuman(gameBoard.getPlayer1(), gameBoard.getAi(), rewards[round], numebrOfTurnsPerRound);    //TODO NEEDS TO BE CHANGED
                gameBoard.resetAllDice();
                switchPlayerAI();
                System.out.println();
                System.out.println("IT IS CURRENTLY ROUND: " + (round+1)+"\n AI TURN ");
                playRoundAI(gameBoard.getAi(), gameBoard.getPlayer1(), rewards[round], numebrOfTurnsPerRound);
                gameBoard.resetAllDice();
                switchPlayerAI();
            }
            Player player1= gameBoard.getPlayer1();
            Player aiPlayer= gameBoard.getAi();
            System.out.println("The scoresheet of Player "+ player1.getName()+" is the following:");
            player1.getScoreSheet().displayColoredScoreSheet();
            System.out.println( player1.getGameScore().toString() + "\n");
            int player1Score= player1.getGameScore().getTotalScore();

            System.out.println("The scoresheet of the Ai is the following:");
            aiPlayer.getScoreSheet().displayColoredScoreSheet();
            System.out.println( aiPlayer.getGameScore().toString() + "\n");
            int aiPlayerScore= aiPlayer.getGameScore().getTotalScore();

            if (player1Score == aiPlayerScore)
            {
                int[] player1Scores = player1.getGameScore().getAllScores();
                int[] player2Scores = aiPlayer.getGameScore().getAllScores();
                for (int i = 0; i < player2Scores.length; i++) {
                    if (player1Scores[i] > player2Scores[i]) {
                        player1Score = 100;
                        aiPlayerScore = 0;
                    }
                    else if (player1Scores[i] < player2Scores[i]) {
                        player1Score = 0;
                        aiPlayerScore = 100;
                    }
                }
            }
            if (player1Score > aiPlayerScore)
                System.out.println("Congratulations, "+player1.getName()+"! You have emerged victorious in this wonderful battle!");
            else if (player1Score < aiPlayerScore)
                System.out.println("Congratulations, "+AI+"! You have emerged victorious in this wonderful battle!");
            else {
                System.out.println("It is a draw!");
            }
            scanner.close();
            
        }
        else{

            int [] temp = getSettings();
            int numberOfRounds= temp[0];    //TODO make a default in case the config is empty
            int numebrOfTurnsPerRound=temp[1];


            System.out.println("Welcome to the mystical lands of Eldoria! \nI will now simulate two different AIs playing");

            //the following is taking in the round rewards from the properties file
            String rewards [] = getRewards(numberOfRounds);

             //the following is trying to start the game loop: and needs to be changed
             // the scoresheet for the ai should be displayed once after the end of each round for better transparency and strategy for the human player
             for (int round = 0; round < numberOfRounds; round++) {
                System.out.println();
                System.out.println("IT IS CURRENTLY ROUND: " + (round+1)+" AI1 TURN");
                playRoundAI(gameBoard.getAi1(), gameBoard.getAi2(), rewards[round], numebrOfTurnsPerRound);    //TODO NEEDS TO BE CHANGED
                gameBoard.resetAllDice();
                switchPlayerAI();
                System.out.println();
                System.out.println("IT IS CURRENTLY ROUND: " + (round+1)+"\n AI2 TURN ");
                playRoundAI(gameBoard.getAi2(), gameBoard.getAi1(), rewards[round], numebrOfTurnsPerRound);
                gameBoard.resetAllDice();
                switchPlayerAI();
            }
            AI aiPlayer1= gameBoard.getAi1();
            AI aiPlayer2= gameBoard.getAi2();
            System.out.println("The scoresheet of Player "+ aiPlayer1.getName()+" is the following:");
            aiPlayer1.getScoreSheet().displayColoredScoreSheet();
            System.out.println( aiPlayer1.getGameScore().toString() + "\n");
            int aiPlayer1Score= aiPlayer1.getGameScore().getTotalScore();

            System.out.println("The scoresheet of the Ai is the following:");
            aiPlayer2.getScoreSheet().displayColoredScoreSheet();
            System.out.println( aiPlayer2.getGameScore().toString() + "\n");
            int aiPlayerScore2= aiPlayer2.getGameScore().getTotalScore();

            if (aiPlayer1Score == aiPlayerScore2)
            {
                int[] player1Scores = aiPlayer1.getGameScore().getAllScores();
                int[] player2Scores = aiPlayer2.getGameScore().getAllScores();
                for (int i = 0; i < player2Scores.length; i++) {
                    if (player1Scores[i] > player2Scores[i]) {
                        aiPlayer1Score = 100;
                        aiPlayerScore2 = 0;
                    }
                    else if (player1Scores[i] < player2Scores[i]) {
                        aiPlayer1Score = 0;
                        aiPlayerScore2 = 100;
                    }
                }
            }
            if (aiPlayer1Score > aiPlayerScore2)
                System.out.println("Congratulations, AI1! You have emerged victorious in this wonderful battle!");
            else if (aiPlayer1Score < aiPlayerScore2)
                System.out.println("Congratulations, AI2! You have emerged victorious in this wonderful battle!");
            else {
                System.out.println("It is a draw!");
            }
            System.out.println(aiPlayer1.getTurnsPlayed());
            System.out.println(aiPlayer2.getTurnsPlayed());
            /*System.out.println(aiPlayer1.getArcaneBoostsNum());
            for(ArcaneBoost arcaneBoost: aiPlayer1.getArcaneBoosts()){          //problem here
                System.out.println(arcaneBoost.getStatus());
            }*/
            scanner.close();

        }
    }

    public void playForgottenTurn(Player player) {
        gameBoard.resetGreenPostColorBonus();
        player.getScoreSheet().displayColoredScoreSheet();
        System.out.println("Here is your scoresheet, " + player.getName() + " :\n");
        System.out.println("It is currently the " + "PASSIVE" + " player's turn.");
        boolean valid = false;
        while(!valid) {
            try {
                valid = turnCompletion(player);
            } catch (NoAvailableMovesException e) {
                handleDiceDisplay(gameBoard.getForgottenRealmDice(), 1);
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    public void playRound(Player activePlayer, Player passivePlayer, String reward, int turnCount) {
        gameBoard.resetGreenPostColorBonus();
        if (!reward.equals("skip"))
            handleRoundRewards(activePlayer, reward);
        for (int turn = 0; turn < turnCount && getAvailableDice().length != 0; turn++) {
            System.out.println();
            System.out.println("IT IS CURRENTLY TURN: " + (turn+1));
            boolean valid = playTurn(activePlayer, false);
            if (!valid)
                break;
        }
        moveAllIntoForgotten();
        playForgottenTurn(passivePlayer);
        boolean usedArcaneBoost = true;
        while (usedArcaneBoost) {
            try {
                usedArcaneBoost = handleArcaneBoost(getArcaneBoostPowers(activePlayer), activePlayer);
            } catch (ExhaustedResourceException e) {
                System.out.println(e.getMessage());
                usedArcaneBoost = false;
            }
            if (usedArcaneBoost) {
                handleArcaneBoostCall(activePlayer);
            }
        }
        usedArcaneBoost = true;
        while (usedArcaneBoost) {
            try {
                usedArcaneBoost = handleArcaneBoost(getArcaneBoostPowers(passivePlayer), passivePlayer);
            } catch (ExhaustedResourceException e) {
                System.out.println(e.getMessage());
                usedArcaneBoost = false;
            }
            if (usedArcaneBoost) {
                handleArcaneBoostCall(passivePlayer);
            }
        }
    }

    //If this is a timewarp reroll call, there is no need to redisplay the score sheet and the "We will now roll the dice" message
    public boolean playTurn(Player player, boolean isThisATimeWarpRerollCall) {
        gameBoard.resetGreenPostColorBonus();
        if (!isThisATimeWarpRerollCall) {
            player.getScoreSheet().displayColoredScoreSheet();
            System.out.println("Here is your scoresheet, " + player.getName() + " :\n");
            System.out.println("It is currently the " + "ACTIVE" + " player's turn.");
            System.out.println("I will now roll the dice...");
        }
        if (isThisATimeWarpRerollCall)
            System.out.println("I will now reroll the dice...");
        rollDice();
        boolean useTimeWarp;
        try {
           useTimeWarp = handleTimeWarps(getTimeWarpPowers(player));
        } catch (ExhaustedResourceException e) {
            System.out.println(e.getMessage());
            useTimeWarp = false;
        }
        if (useTimeWarp) {
            return playTurn(player, true);
        }
        boolean valid = false;
        while(!valid) {
            try {
                valid = turnCompletion(player);
            } catch (NoAvailableMovesException e) {
                ArrayList<Dice> availableDice = gameBoard.getAvailableDice();
                Dice[] diceSet = new Dice[availableDice.size()];
                for (int i = 0; i < availableDice.size(); i++)
                    diceSet[i] = availableDice.get(i);
                handleDiceDisplay(diceSet, 0);
                System.out.println("Hmm... it seems that this set of dice will not allow you to play any move against any of your Realms.");
                try {
                    useTimeWarp = handleTimeWarps(getTimeWarpPowers(player));
                } catch (ExhaustedResourceException f) {
                    System.out.println(f.getMessage());
                }
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
        gameBoard.resetGreenPostColorBonus();
        Dice[] diceSet = player.getPlayerStatus() == PlayerStatus.ACTIVE ? getAvailableDice() : getForgottenRealmDice();
        Move[] moveSet = getAllPossibleMovesForDiceSet(player, diceSet);
        if (moveSet.length == 0)
            throw new NoAvailableMovesException("Hmm, it seems that this set of dice has no possible moves. How unfortunate.");
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
                } catch (InvalidDiceSelectionException | NoAvailableMovesException e) {
                    System.out.println(e.getMessage());
                    handleDiceDisplay(diceSet, indicator);
                    continue;
                }
                break;
            }
            finalDie = null;
            if (chosenDie instanceof ArcanePrism) {
                while (true) {
                    try {
                        finalDie = handleArcanePrism(chosenDie, player);
                    } catch (NoAvailableMovesException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
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

    public Dice[] getArcaneBoostDice(Player player) {
        Dice[] possibleDice = getAllDice();
        ArrayList<Dice> diceExcludingPreviouslySelectedByArcaneBoosts = new ArrayList<>();
        ArrayList<Dice> bannedDice = player.getUsedArcaneDice();
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
        gameBoard.resetGreenPostColorBonus();
        Dice[] availableDice = getArcaneBoostDice(player);
        Arrays.sort(availableDice);
        handleDiceDisplay(availableDice, 2);
        try {
            getAllPossibleMovesForDiceSet(player, availableDice);
        } catch (NoAvailableMovesException e) {
            System.out.println("Hmm.. this is terrible. It seems that this Arcane Boost is useless. Be careful next time.");
            //resetting the arcane boost to be acquired
            ArcaneBoost[] playerArcaneBoosts = getArcaneBoostPowers(player);
            for (ArcaneBoost arcaneBoost: playerArcaneBoosts) {
                if (arcaneBoost.getStatus().equals(RewardStates.USED)) {
                    arcaneBoost.setStatus(RewardStates.ACQUIRED);
                    break;
                }
            }
            return;
        }
        Dice chosenDie;
        boolean valid = false;
        while (!valid) {
            try {
                chosenDie = handleDiceSelection(player, availableDice);
            } catch (InvalidDiceSelectionException | NoAvailableMovesException e) {
                System.out.println(e.getMessage());
                handleDiceDisplay(availableDice, 2);
                continue;
            }
            Dice finalDie = null;
            if (chosenDie instanceof ArcanePrism) {
                while (true) {
                    try {
                        finalDie = handleArcanePrism(chosenDie, player);
                    } catch (NoAvailableMovesException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                }
            } else
                finalDie = chosenDie;
            if (finalDie instanceof RedDice) {
                boolean valid2 = false;
                while (!valid2) {
                    try {
                        finalDie = handleRedDice((RedDice) finalDie);
                    } catch (InvalidDiceSelectionException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    valid2 = true;
                }
            }
            valid = makeMove(player, new Move(finalDie, getScoreSheet(player).getCreatureByColor(finalDie.getRealm())));
            if (valid) {
                player.addToUsedArcaneDice(finalDie);
            }
        }
        player.getScoreSheet().displayColoredScoreSheet();
    }

    public RedDice handleRedDice(RedDice finalDie) throws InvalidDiceSelectionException{
        System.out.println("Since you have chosen to attack the Red Realm, you must also select which Dragon you would like to attack.");
        System.out.println("Please select a number between 1 and 4 to indicate which Dragon you would like to attack!");
        int selectedDragon;
        String input = scanner.next();
        while (input.isEmpty()) {
            input = scanner.next();
        }
        if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4"))
            throw new InvalidDiceSelectionException("This dragon does not exist, please try again.");
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
        gameBoard.resetGreenPostColorBonus();
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
                throw new InvalidDiceSelectionException("Invalid input, please try again.");
            }
        }
        Dice chosenDie = diceSet[chosenDiceIndex-1];
        Move[] moveList = getPossibleMovesForADie(player, chosenDie);
        if (moveList.length == 0) {
            throw new NoAvailableMovesException("This die has no possible moves. Please select another die to play with.");
        }
        return chosenDie;
    }

    public Dice handleArcanePrism(Dice chosenDie, Player player) throws NoAvailableMovesException {
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
            throw new NoAvailableMovesException("Unfortunately, you cannot cast the Arcane Prism into this form because it has no possible moves.\nPlease try again.");
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
    public boolean handleArcaneBoost(ArcaneBoost[] arcaneBoosts, Player player) throws ExhaustedResourceException{
        if (arcaneBoosts.length == 0)
            return false;
        // System.out.println("are you disatisfied by such rotten luck and would like to get another roll at your fate (this will use one of your aqcuired timewarps becuase nothing in this life is for free)\n (press 'y' or 'y' because no one is satisfied aslan no just kidding ");
        //dummy value initialization for loop entry
        char c = 'a';
        int arcaneBoostCount = 0;
        for (ArcaneBoost arcaneBoost: arcaneBoosts) {
            if (arcaneBoost.getStatus() == RewardStates.ACQUIRED)
                arcaneBoostCount++;
        }
        if (arcaneBoostCount == 0)
            throw new ExhaustedResourceException("You have no available Time Warps to use.");
        for (ArcaneBoost arcaneBoost: arcaneBoosts) {
            if (arcaneBoost.getStatus() == RewardStates.ACQUIRED) {
                System.out.println("Hey, " + player.getName() + "!");
                System.out.println("You have available Arcane Boosts! Would you like to use one of them to attack one of your Realms again?" );
                System.out.println("You have a total of " + arcaneBoostCount + " Arcane Boost(s).");
                Dice[] arcaneBoostDice = getArcaneBoostDice(player);
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
                        return false;
                    }
                    else {
                        System.out.println("Invalid input, please try again.");
                        System.out.println("Please enter 'y' if you want to use an Arcane Boost, or 'n' if you don't want to.");
                    }

                } while (c != 'y');
                if (c == 'y') {
                    arcaneBoost.setStatus(RewardStates.USED);
                }
                break;
            }
        }
        return c == 'y';
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
            } catch (InvalidBonusSelectionException e) {
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
    public boolean handleTimeWarps(TimeWarp[] timewarps) throws ExhaustedResourceException{
        if (timewarps.length == 0)
            return false;
        // System.out.println("are you disatisfied by such rotten luck and would like to get another roll at your fate (this will use one of your aqcuired timewarps becuase nothing in this life is for free)\n (press 'y' or 'y' because no one is satisfied aslan no just kidding ");
        //dummy value initialization for loop entry
        char c = 'a';
        int timeWarpCount = 0;
        for (TimeWarp timeWarp: timewarps) {
            if (timeWarp.getStatus() == RewardStates.ACQUIRED)
                timeWarpCount++;
        }
        if (timeWarpCount == 0)
            throw new ExhaustedResourceException("You have no available Time Warps to use.");
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
                } while (c != 'y');
                timeWarp.setStatus(RewardStates.USED);
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
            throw new NoAvailableMovesException("Hmm, it seems that this set of dice has no possible moves. How unfortunate.");
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
        gameBoard.resetGreenPostColorBonus();
        Move[] playerAllMoves= player.getAllPossibleMoves();
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
        else if (dice instanceof RedDice && ((RedDice)dice).getDragonNumber() != -1) {
            for (int i = 0; i < playerAllMoves.length; i++) {
                if ((playerAllMoves[i].getDice().getRealm() == dice.getRealm() && playerAllMoves[i].getDice().getValue() == dice.getValue() && ((RedDice)playerAllMoves[i].getDice()).getDragonNumber() == ((RedDice)dice).getDragonNumber())){
                    result.add(playerAllMoves[i]);
                }
            }
            Move [] finalResult = new Move[result.size()];
            for (int i=0; i<result.size(); i++) {
                finalResult[i] = result.get(i);
            }
            return finalResult;
        }
        else{
            for (int i = 0; i < playerAllMoves.length; i++) {
                if (playerAllMoves[i].getDice().getRealm() == dice.getRealm() && playerAllMoves[i].getDice().getValue() == dice.getValue()){
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
            else {
                player.updateGameScore();
                player.updateAllPossibleMoves();
                return true;
            }
        } catch (BonusException bException) {
            player.updateGameScore();
            player.updateAllPossibleMoves();
            RealmColor realmColor1 = bException.getRealmColor1();
            Dice chosenDie;
            boolean valid = false;
            while (!valid) {
                try {
                    chosenDie = handleColorBonusException(realmColor1, player);
                } catch (NoAvailableMovesException e) {
                    System.out.println(e.getMessage());
                    return true;
                } catch (InvalidBonusSelectionException e) {
                    System.out.println(e.getMessage());
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
                        System.out.println(e.getMessage());
                        return true;
                    } catch (InvalidBonusSelectionException e) {
                        System.out.println(e.getMessage());
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
            System.out.println(Im.getMessage());
            return false;
        }
    }

    public Dice handleColorBonusException(RealmColor color, Player player) throws NoAvailableMovesException, InvalidBonusSelectionException, InvalidDiceSelectionException{
        gameBoard.resetGreenPostColorBonus();
        Dice finalDie = null;
        String input = "";
        player.getScoreSheet().displayColoredScoreSheet();
        if (color == RealmColor.WHITE) {
            Move[] possibleMoves = getAllPossibleMoves(player);
            if (possibleMoves.length == 0)
                throw new NoAvailableMovesException("How horrible! You cannot use your essence bonus because you have no available moves at all.");
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
                    throw new InvalidBonusSelectionException("This is not a valid Realm...\nI will now give you another chance to select properly.\n");
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
                throw new InvalidBonusSelectionException("Unfortunately you cannot cast your Essence Bonus into this form because it will not have any available moves.\nPlease try again.");
            }
        }
        System.out.println("You have just obtained a " + color + " Bonus (Or you have morphed your Essence Bonus into a " + color + " Bonus)!\n");
        Move[] possibleMoves = getAllPossibleMoves(player);
        boolean canYouUseThisBonus = false;
        for (Move move: possibleMoves) {
            canYouUseThisBonus = canYouUseThisBonus || move.getDice().getRealm().equals(color);
        }
        if (!canYouUseThisBonus) {
            throw new NoAvailableMovesException("Unfortunately, you cannot use this bonus because it has no available moves.");
        }
        if (color == RealmColor.GREEN) {
            System.out.println("Please input a value between 2-12 that you would like to use to attack the GREEN Realm with!");
            input = scanner.next();
            boolean validInput = false;
            for (int i = 2; i <= 12 && !validInput; i++)
                validInput = input.equals("" + i);
            if (!validInput) {
                throw new InvalidDiceSelectionException("This is not a valid input... \nI will give you another chance to select properly.");
            }
            int value = Integer.parseInt(input);
            finalDie = new GreenDice(value);
            gameBoard.setGreenForColorBonus(value);
        }
        else if (color == RealmColor.RED) {
            System.out.println("Please input a value between 1-6 that you would like to use to attack the RED Realm with!");
            input = scanner.next();
            boolean validInput = false;
            for (int i = 1; i <= 6 && !validInput; i++)
                validInput = input.equals("" + i);
            if (!validInput) {
                throw new InvalidDiceSelectionException("This is not a valid input... \nI will give you another chance to select properly.");
            }
            int value = Integer.parseInt(input);
            finalDie = new RedDice(value);
            finalDie = handleRedDice((RedDice)finalDie);
            ((RedDice)finalDie).selectsDragon(Integer.parseInt(input));
        }
        else {
            System.out.println("Please input a value between 1-6 that you would like to use to attack the " + color + " Realm with!");
            input = scanner.next();
            boolean validInput = false;
            for (int i = 1; i <= 6 && !validInput; i++)
                validInput = input.equals("" + i);
            if (!validInput) {
                throw new InvalidDiceSelectionException("This is not a valid input... \nI will give you another chance to select properly.");
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
        gameBoard.resetGreenPostColorBonus();
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
        gameBoard.resetGreenPostColorBonus();
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
        gameBoard.resetGreenPostColorBonus();
        for (Dice die: getAvailableDice()) {
            gameBoard.removeFromAvailable(die);
            gameBoard.moveToForgottenrealm(die);
        }
    }

    public static void printRainbowText(String text) {
        int colorIndex = 0;
        for (char c : text.toCharArray()) {
            // Print each character in the next color, then reset
            System.out.print(COLORS[colorIndex] + c + RESET);
            colorIndex = (colorIndex + 1) % COLORS.length;
        }
        // Move to the next line after printing the text
        System.out.println();
    }

    public String changeToRainbowText(String text) {
        int colorIndex = 0;
        String output = "";
        for (char c : text.toCharArray()) {
            // Print each character in the next color, then reset
            output += COLORS[colorIndex] + c + RESET;
            colorIndex = (colorIndex + 1) % COLORS.length;
        }
        return output;
    }

    public static void main (String[] args) {
        CLIGameController cli = new CLIGameController();
        GameBoard board=new GameBoard();
        Player player=new Player(PlayerStatus.ACTIVE);
        for(Dice die: board.getAllDice()){
            if(!(die instanceof ArcanePrism))
            System.out.println(die.getRealm()+" the value is "+ die.getValue()+" and the eval is " +cli.evaluateDice(player,die));
        }
    }


    //AI PART

    // RULE-BASED
    // TODO special conditions for the last turn

    /*public Dice findSecondLowest(Player player){

        Player pclone=player.clone();       //uhh idk tbh just trying to make a clone again
        Dice[] diceSet = pclone.getPlayerStatus() == PlayerStatus.ACTIVE ? getAvailableDice() : getForgottenRealmDice();
        
        int min=Integer.MAX_VALUE;
        for(Dice dice: diceSet){
            if(dice.getValue()<min){
                    min=dice.getValue();
            }
        }
        boolean flag=false;
        boolean flagPriority=false;
        for(Dice dice: diceSet){
            if(dice.getValue()==min && flag==false){
                flag=true;
            }
            // the following block just checks if theres a dice with the same value in a different realm and if so decides which one to pick based on
            // their respective priorities. if there is no such dice it just returns the first dice with the 2nd lowest value
            else if(dice.getValue()>=min && flag==true){
                int x=dice.getValue();

                for(Dice die: diceSet){
                    if(die.getValue()==x&&die.getRealm()!=dice.getRealm()){
                        flagPriority=true;
                    }
                }
                if(flagPriority==false){
                    return dice;
                }
                else{
                    //TODO PRIORITY HERE
                }

            }
        }
    }*/
    
    /*public Dice findHighestPriority(Dice[] diceSet){

    }*/


    //kinda like rule-based 
    public Move findBestMove(Dice[] diceSet,Player player){
        Move bestMove=null;
        if(diceSet.length==0) return null;
        int bestValue=Integer.MIN_VALUE;
        for(Dice dice: diceSet){
            int value=evaluateDice(player,dice);
            if(value>bestValue){
                bestValue=value;
                bestMove=new Move(dice,player.getScoreSheet().getCreatureByColor(dice.getRealm()));
            }
        }
        return bestMove;
    }










    //MAXMAX STUFF
    public Move findBestMove(Player player,GameBoard board, int depth) {        //add a parameter for the turn number and if its the last turn 
        // then call th
        int bestValue = Integer.MIN_VALUE;
        Move bestMove = null;
        
        Player pclone=player.clone();

        Dice[] diceSet = pclone.getPlayerStatus() == PlayerStatus.ACTIVE ? getAvailableDice() : getForgottenRealmDice();
        Move[] moveSet;

        try {
            moveSet = getAllPossibleMovesForDiceSet(pclone, diceSet);
        } catch (NoAvailableMovesException e) {
            System.out.println("you cant make any moves my guy");
            return null;
        }

        Collections.shuffle(Arrays.asList(moveSet));
        for (Move move : moveSet) {
            Player playerBeforeMove = player.clone();
            GameBoard boardBeforeMove = board.clone();
            try {
                //red got an error so i added the first condition
                if(move.getDice().getRealm()!=RealmColor.RED&&move.getCreature().checkMove(move.getDice())){
                    makeMoveAI(player, move);     //check the invalidmove shit
                    int boardValue = maxmax(player,board, depth - 1);

                    player = playerBeforeMove;
                    board = boardBeforeMove;

                    if (boardValue >= bestValue) {
                    bestValue = boardValue;
                    bestMove = move;

                    }
                }
            } 
            catch (InvalidMoveException e) {
                System.out.println("error in the findbestmove method");
                e.printStackTrace();
            } 

        }
        return bestMove;
    }

    public int maxmax(Player player, GameBoard board, int depth) {
        Player pclone=player.clone();

        Dice[] diceSet = pclone.getPlayerStatus() == PlayerStatus.ACTIVE ? getAvailableDice() : getForgottenRealmDice();
        Move[] moveSet;

        try {
            moveSet = getAllPossibleMovesForDiceSet(pclone, diceSet);
        } catch (NoAvailableMovesException e) {
            return evaluate(player, board);
        }

        if (depth <= 0 || moveSet==null) {
            return evaluate(player,board);
        }
        int maxEval = Integer.MIN_VALUE;
        int eval=0;
        
        Collections.shuffle(Arrays.asList(moveSet));
        for (Move move : moveSet) {    //dfs sum
            Player playerBeforeMove = player.clone();
            GameBoard boardBeforeMove = board.clone();
            try {
                if(move.getDice().getRealm()!=RealmColor.RED &&move.getCreature().checkMove(move.getDice())){
                    makeMoveAI(player,move);                  //should momentarily keep track of the player total score and also the board
                    eval += maxmax(player,board, depth - 1);    //+=?
                    // restore the state
                    player = playerBeforeMove;
                    board = boardBeforeMove;

                    if(eval>=maxEval){
                        maxEval=eval;
                    }
                }
            } catch (InvalidMoveException e) {
                System.out.println("problem in the maxmax method");
                e.printStackTrace();
            }
        }
            return maxEval;
    }

    public int evaluate(Player player,GameBoard board){
        int score =0;
        //score=player.getGameScore().getTotalScore();

        //Dice[] dice=board.getForgottenRealmDice();
        //int forgottenRealmScore=evaluateDiceScore(dice);
        //score-=forgottenRealmScore;

        /*int arcaneBoostCount = 0;
        for (ArcaneBoost arcaneBoost: player.getArcaneBoosts()) {
            if (arcaneBoost.getStatus() == RewardStates.ACQUIRED)
                arcaneBoostCount++;
        }
        score+=arcaneBoostCount*10;

        int timeWarpCount = 0;
        for(TimeWarp timeWarp: player.getTimeWarps()){
            if(timeWarp.getStatus() == RewardStates.ACQUIRED)
                timeWarpCount++;
        }
        score+=timeWarpCount*5;*/

        score+=player.getGameScore().getTotalScore();
        return score;
    }
    public int evaluateDiceScore(Player player,ArrayList<Dice> diceSet){
        int score=0;
        for(Dice die: diceSet){
           score+=evaluateDice(player,die);
        }
        return score;
    }

    public int evaluateDice(Player player,Dice dice){
        if(dice==null) return 0;
        
        //cloning

        /*Player pclone=player.clone();
        ScoreSheet scoreSheet=player.getScoreSheet();
        GameScore GM=player.getGameScore();
        int ogSCore=GM.getTotalScore();

        if(getPossibleMovesForADie(pclone, dice).length!=0){

            if(dice.getRealm()==RealmColor.RED){
                RedDice redDice = new RedDice(dice.getValue());
                for (int k = 4; k>= 1; k--) {
                    redDice.selectsDragon(k);
                    Move[] redDiceDragonMoves=getPossibleMovesForADie(pclone, redDice);
                    if(redDiceDragonMoves.length!=0){
                        Move redAiMove = new Move(redDice, redDiceDragonMoves[0].getCreature());
                        makeMoveAI(pclone, redAiMove);
                        break;
                    }
                }
            }

            else if(dice instanceof ArcanePrism){
                Dice[] whiteDices={new BlueDice(dice.getValue()),new MagentaDice(dice.getValue()),new YellowDice(dice.getValue())};
                Move bestWhiteMove=findBestMove(whiteDices, pclone);
                makeMoveAI(pclone, bestWhiteMove);
             }
            else{
                Move aiMove=new Move(dice, getPossibleMovesForADie(pclone, dice)[0].getCreature());
                makeMoveAI(pclone, aiMove);
            }
        }
        int newScore=pclone.getGameScore().getTotalScore();
        return newScore-ogSCore;*/

        switch (dice.getRealm()) {
            case RED:
                return evaluateRedDice(player,dice);
            case GREEN:
                return evaluateGreenDice(player,dice);
            case BLUE:
                return evaluateBlueDice(player,dice);
            case MAGENTA:
                return evaluateMagentaDice(player,dice);
            case YELLOW:
                return evaluateYellowDice(player,dice);
            case WHITE:
                return evaluateWhiteDice(player,dice);
            default:
            return 0;
        }
    }
    public int evaluateRedDice(Player player,Dice dice){
        //should check if this dice can end a column/row
        int value=dice.getValue();
        dice=new RedDice(dice.getValue());
        /*if(completeRowRed(player,dice)&&completeColumnRed(player,dice)) return 12;
        if(completeColumnRed(player, dice)) return 10;
        if(completeRowRed(player, dice)) return 5;
        if(value==4) return 1;
        if(value==5) return 1;
        if(value==6) return 1;
        if(value==3) return 1;
        if(value==2) return 1;
        if(value==1) return 1;*/


        return -1;
    }
    public boolean completeRowRed(Player player,Dice dice){
        int value=dice.getValue();
        ScoreSheet scoreSheet=player.getScoreSheet();
        Dragon dragon= (Dragon) scoreSheet.getCreatureByColor(RealmColor.RED);
        Dragon[] dragons=dragon.getDragons();
        int firstCounter=0;
        int secondCounter=0;
        int thirdCounter=0;
        int fourthCounter=0;
        int missingFirstValue=0;
        int missingSecondValue=0;
        int missingThirdValue=0;
        int missingFourthValue=0;
        
        for(int i=0;i<4;i++){
            if(dragons[i].getFace()==null){
                firstCounter++;
            }
            else{
                missingFirstValue=dragons[i].getFace();
            }
            if(dragons[i].getWings()==null){
                secondCounter++;
            }
            else{
                missingSecondValue=dragons[i].getWings();
            }
            if(dragons[i].getTail()==null){
                thirdCounter++;
            }
            else{
                missingThirdValue=dragons[i].getTail();
            }
            if(dragons[i].getHeart()==null){
                fourthCounter++;
            }
            else{
                missingFourthValue=dragons[i].getHeart();
            }
        }
        if(firstCounter==3&&value==missingFirstValue){
            return true;
        }
        if(secondCounter==3&&value==missingSecondValue){
            return true;
        }
        if(thirdCounter==3&&value==missingThirdValue){
            return true;
        }
        if(fourthCounter==3&&value==missingFourthValue){
            return true;
        }
        return false;
    }
    public boolean completeColumnRed(Player player,Dice dice){
        int value=dice.getValue();
        ScoreSheet scoreSheet=player.getScoreSheet();
        Dragon dragon= (Dragon) scoreSheet.getCreatureByColor(RealmColor.RED);
        Dragon[] dragons=dragon.getDragons();
        
        for(int i=0;i<4;i++){
            int counter =0;
            int missingValue=0;
            if(dragons[i].getFace()==null){
                counter++;
            }
            else{
                missingValue=dragons[i].getFace();
            }
            if(dragons[i].getWings()==null){
                counter++;
            }
            else{
                missingValue=dragons[i].getWings();
            }
            if(dragons[i].getTail()==null){
                counter++;
            }
            else{
                missingValue=dragons[i].getTail();
            }
            if(dragons[i].getHeart()==null){
                counter++;
            }
            else{
                missingValue=dragons[i].getHeart();
            }

            if(counter==3&&value==missingValue){
                return true;
            }
        }
        return false;
    }

    public int evaluateGreenDice(Player player, Dice dice){ //problem with adding the white dice
        int value=dice.getValue();
        //if(completeRowGreen(player, dice)&&completeColumnGreen(player, dice)) return 200;
        //if(completeRowGreen(player,dice) || completeColumnGreen(player, dice)) return 150;
        if(value==2) return 12;
        if(value==3) return 8;
        if(value==4) return 6;
        if(value==5) return 6;
        if(value==6) return 6;
        if(value==7) return 6;
        if(value==8) return 6;
        if(value==9) return 12;
        if(value==10) return 12;
        if(value==11) return 12;
        if(value==12) return 12;
        return 50;
    }
    public boolean completeRowGreen(Player player,Dice dice){
        int value=dice.getValue();
        ScoreSheet scoreSheet=player.getScoreSheet();
        Gaia gaia=(Gaia) scoreSheet.getCreatureByColor(RealmColor.GREEN);
        Guardians[][] guardians=gaia.getGuardians();
        for(int i=0;i<3;i++){
            int counter=0;
            int missingValue=0;
            for(int j=0;j<4;j++){
                if(!(i==0&&j==0)){
                    if(guardians[i][j].isDead()){
                        counter++;
                    }
                    else{
                        missingValue=i*4+j+1;
                    }
                }
            }
            if(i==0 && counter==2&&value==missingValue){
                return true;
            }
            if(i!=0 &&counter==3&&value==missingValue){
                return true;
            }
        }
        return false;
    }
    public boolean completeColumnGreen(Player player,Dice dice){
        int value=dice.getValue();
        ScoreSheet scoreSheet=player.getScoreSheet();
        Gaia gaia=(Gaia) scoreSheet.getCreatureByColor(RealmColor.GREEN);
        Guardians[][] guardians=gaia.getGuardians();
        for(int i=0;i<4;i++){
            int counter=0;
            int missingValue=0;
            for(int j=0;j<3;j++){
                if(!(i==0&&j==0)){
                    if(guardians[j][i].isDead()){
                        counter++;
                    }
                    else{
                        missingValue=j*4+i+1;
                    }
                }
            }
            if(i==0 && counter==1&&value==missingValue){
                return true;
            }
            if(counter==2&&value==missingValue){
                return true;
            }
        }
        return false;
    }

    public int evaluateBlueDice(Player player,Dice dice){
        int value=dice.getValue();
        Move[] moves=getPossibleMovesForADie(player, dice);
        for(Move move:moves){
            if(move.getDice().getValue()==value){
                return 8;
            }
        }
        return 0;
    }

    public int evaluateMagentaDice(Player player,Dice dice){
        int value=dice.getValue();
        ScoreSheet scoreSheet=player.getScoreSheet();
        Phoenix phoenix=(Phoenix) scoreSheet.getCreatureByColor(RealmColor.MAGENTA);
        int lastHit=phoenix.getLastHit();
        if(value==6) return 8;
        if(lastHit==0) return 4+value;
        if(value>lastHit) return 4+(lastHit-value);//trying to minimize the difference so we dont make a 1 then 5 for example
        return 0;
    }

    public int evaluateYellowDice(Player player, Dice dice){
        return dice.getValue()+2;
    }

    public int evaluateWhiteDice(Player player, Dice dice){//problem with the green dice
        int highestScore=Math.max(evaluateRedDice(player,dice),Math.max(evaluateGreenDice(player,dice),Math.max(evaluateBlueDice(player,dice),
        Math.max(evaluateMagentaDice(player,dice),evaluateYellowDice(player,dice)))))-2;
        return highestScore;
    }

    public boolean makeMoveAI(Player player, Move move)  {
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
            else {
                player.updateGameScore();
                player.updateAllPossibleMoves();
                return true;
            }
        } catch (BonusException bException) {
            player.updateGameScore();
            player.updateAllPossibleMoves();
            RealmColor realmColor1 = bException.getRealmColor1();
            Dice bonusDice=chooseBonusAI(player,realmColor1);
            if(bonusDice==null){
                System.out.println("something wrong with the bonus dice");
            }
            Move finalMove=new Move(bonusDice,player.getScoreSheet().getCreatureByColor(bonusDice.getRealm()));
            makeMoveAI(player, finalMove);

            if (bException.getRealmColor2() != RealmColor.PARENT) {
                RealmColor realmColor2 = bException.getRealmColor2();
                Dice bonusDice2=chooseBonusAI(player, realmColor2);
                if(bonusDice2==null){
                System.out.println("something wrong with the 2nd bonus dice");
                }
                Move finalMove2=new Move(bonusDice2,player.getScoreSheet().getCreatureByColor(bonusDice2.getRealm()));
                makeMoveAI(player, finalMove2);

            }
            return true;
        }
        catch (InvalidMoveException Im){
            System.out.println(Im.getMessage());
            return false;
        }
    }



    public void playRoundAI(Player activePlayer, Player passivePlayer, String reward, int turnCount) {
        gameBoard.resetGreenPostColorBonus();

        if (!reward.equals("skip"))
            handleRoundRewardsAI(activePlayer, reward);

        for (int turn = 0; turn < turnCount && getAvailableDice().length != 0; turn++) {
            boolean valid = playTurnAI(activePlayer, false,turn+1);
            if (!valid)
                break;
        }


        activePlayer.getScoreSheet().displayColoredScoreSheet();
        System.out.println();
        moveAllIntoForgotten();


        //for the human player
        if(!(passivePlayer instanceof AI)){
            playForgottenTurn(passivePlayer);
            boolean usedArcaneBoost = true;
            while (usedArcaneBoost) {
                try {
                    usedArcaneBoost = handleArcaneBoost(getArcaneBoostPowers(passivePlayer), passivePlayer);
                } catch (ExhaustedResourceException e) {
                    System.out.println(e.getMessage());
                    usedArcaneBoost = false;
                }
                if (usedArcaneBoost) {
                    handleArcaneBoostCall(passivePlayer);
                }
            }
        }
        
        //ai2 passive
        else{
            Dice[] avDice = gameBoard.getForgottenRealmDice();
            ScoreSheet scoreSheet = passivePlayer.getScoreSheet();
            boolean flag=false;
            ArrayList<Dice> actuallyAvailableDice = new ArrayList<Dice>();
            for(Dice someDice:avDice){

                if (someDice instanceof RedDice) {
                    RedDice finalDie = new RedDice(someDice.getValue());
                    for (int i = 4; i >= 1; i--) {
                        finalDie.selectsDragon(i);
                        Dragon dragon = (Dragon)scoreSheet.getCreatureByColor(RealmColor.RED);
                        if (dragon.getDragons()[i-1].checkMove(finalDie)) {
                            actuallyAvailableDice.add(finalDie);
                            flag = true;
                            break;
                        }
                    }
                }

                else if(getPossibleMovesForADie(passivePlayer, someDice).length != 0){
                    flag=true;
                    actuallyAvailableDice.add(someDice);
                }
            }
            AI ai=(AI) passivePlayer;
            boolean redMoveDone=false;
            if(flag){
                Dice[] avDiceArray = actuallyAvailableDice.toArray(new Dice[actuallyAvailableDice.size()]);
                Move aiMove=findBestMove(avDiceArray, passivePlayer);
    
                if(aiMove.getDice().getRealm()==RealmColor.RED){
                    RedDice redDice = new RedDice(aiMove.getDice().getValue());
                    for (int i = 4; i>= 1; i--) {
                        redDice.selectsDragon(i);
                        Move[] redDiceDragonMoves=getPossibleMovesForADie(passivePlayer, redDice);
                        if(redDiceDragonMoves.length!=0){
                            aiMove = new Move(redDice, aiMove.getCreature());
                            makeMoveAI(passivePlayer, aiMove);
                            ai.incrementTurnsPlayed();
                            redMoveDone=true;
                            break;
                        }
                    }
    
                if(!redMoveDone) makeMoveAI(passivePlayer, aiMove);
            }            


            //arcane boosts
            //passive ai
            if(passivePlayer.getArcaneBoostsNum()>0){
                AI ai2=(AI) passivePlayer;
                Dice[] abDice=gameBoard.getAllDice();
                Move bestMove=findBestMove(avDice, passivePlayer);
                makeMoveAI(passivePlayer, bestMove);
                ai2.incrementTurnsPlayed();
                passivePlayer.getArcaneBoosts().remove(0);
            }
        }
        //ab for the active ai
        if(activePlayer.getArcaneBoostsNum()>0){
            Dice[] abDice=gameBoard.getAllDice();
            Move bestMove=findBestMove(avDice, activePlayer);
            makeMoveAI(activePlayer, bestMove);
            ai.incrementTurnsPlayed();
            activePlayer.getArcaneBoosts().remove(0);
        }

    }
    }
    
    public void handleRoundRewardsAI(Player player, String reward){
        switch(reward){
            case "ArcaneBoost": player.getArcaneBoosts().add(new ArcaneBoost(RewardStates.ACQUIRED)); break;
            case "TimeWarp":   player.getTimeWarps().add(new TimeWarp(RewardStates.ACQUIRED)); break;
            case "EssenceBonus": handleBonusAI(player, RealmColor.WHITE); break;
            case "RedBonus":    handleBonusAI(player, RealmColor.RED); break;
            case "GreenBonus": handleBonusAI(player, RealmColor.GREEN); break;
            case "BlueBonus": handleBonusAI(player, RealmColor.BLUE); break;
            case "MagentaBonus": handleBonusAI(player,RealmColor.MAGENTA); break;
            case "YellowBonus": handleBonusAI(player, RealmColor.YELLOW); break;
            default: System.out.println("7azak en el round da mafhoosh bonus");
        }
    }
    
    public void playRoundHuman(Player activePlayer,Player passivePlayer,String reward, int turnCount){
        gameBoard.resetGreenPostColorBonus();
        if (!reward.equals("skip"))
            handleRoundRewards(activePlayer, reward);
        for (int turn = 0; turn < turnCount && getAvailableDice().length != 0; turn++) {
            System.out.println();
            System.out.println("IT IS CURRENTLY TURN: " + (turn+1));
            boolean valid = playTurn(activePlayer, false);
            if (!valid)
                break;
        }
        moveAllIntoForgotten();
        //ai passive

        Dice[] avDice = gameBoard.getForgottenRealmDice();
        ScoreSheet scoreSheet = passivePlayer.getScoreSheet();
        boolean flag=false;
        ArrayList<Dice> actuallyAvailableDice = new ArrayList<Dice>();
        for(Dice someDice:avDice){
            if (someDice instanceof RedDice) {
                RedDice finalDie = new RedDice(someDice.getValue());
                for (int i = 4; i >= 1; i--) {
                    finalDie.selectsDragon(i);
                    Dragon dragon = (Dragon)scoreSheet.getCreatureByColor(RealmColor.RED);
                    if (dragon.getDragons()[i-1].checkMove(finalDie)) {
                        actuallyAvailableDice.add(finalDie);
                        flag = true;
                        break;
                    }
                }
            }
            else if(getPossibleMovesForADie(passivePlayer, someDice).length != 0){
                flag=true;
                actuallyAvailableDice.add(someDice);
            }
        }
        AI ai=(AI) passivePlayer;
        boolean redMoveDone=false;
        if(flag){
            Dice[] avDiceArray = actuallyAvailableDice.toArray(new Dice[actuallyAvailableDice.size()]);
            Move aiMove=findBestMove(avDiceArray, passivePlayer);

            if(aiMove.getDice().getRealm()==RealmColor.RED){
                RedDice redDice = new RedDice(aiMove.getDice().getValue());
                for (int i = 4; i>= 1; i--) {
                    redDice.selectsDragon(i);
                    Move[] redDiceDragonMoves=getPossibleMovesForADie(passivePlayer, redDice);
                    if(redDiceDragonMoves.length!=0){
                        aiMove = new Move(redDice, aiMove.getCreature());
                        makeMoveAI(passivePlayer, aiMove);
                        redMoveDone=true;
                        break;
                    }
                }

            if(!redMoveDone) makeMoveAI(passivePlayer, aiMove);
        }

        boolean usedArcaneBoost = true;
        while (usedArcaneBoost) {
            try {
                usedArcaneBoost = handleArcaneBoost(getArcaneBoostPowers(activePlayer), activePlayer);
            } catch (ExhaustedResourceException e) {
                System.out.println(e.getMessage());
                usedArcaneBoost = false;
            }
            if (usedArcaneBoost) {
                handleArcaneBoostCall(activePlayer);
            }
        }

        //ai arcane boost
        if(passivePlayer.getArcaneBoostsNum()>0){
            Dice[] abDice=gameBoard.getAllDice();
            Move bestMove=findBestMove(avDice, passivePlayer);
            makeMoveAI(passivePlayer, bestMove);
            ai.incrementTurnsPlayed();
            passivePlayer.getArcaneBoosts().remove(0);
        }

      }
    }

    public Dice handleBonusAI (Player player,RealmColor realmColor){
        Dice dice=chooseBonusAI(player, realmColor);
        Move move=new Move(dice,player.getScoreSheet().getCreatureByColor(realmColor));
        makeMoveAI(player, move);
        return dice;
    }
    public Dice chooseBonusAI(Player player,RealmColor realmColor){
        Dice bonusDice=null;
        switch(realmColor){
        case RED:
                    boolean flag=false;
                    for(int i=4;i>=1;i--){
                        if(flag) break;
                        for(int j=6;j>=1;j--){
                            RedDice redDice = new RedDice(j);
                            redDice.selectsDragon(i);
                            Move redMove = new Move(redDice,player.getScoreSheet().getCreatureByColor(RealmColor.RED));
                                if(getPossibleMovesForADie(player, redDice).length!=0){
                                    bonusDice=redDice;
                                    flag=true;
                                    break;
                                }
                        }
                    }

                    //temporary bc red doesnt work
                    break;
                case GREEN:
                        Dice dice12 = new GreenDice(12);
                        Dice dice11 = new GreenDice(11);
                        Dice dice10 = new GreenDice(10);
                        Dice dice9 = new GreenDice(9);
                        Dice dice2 = new GreenDice(2);
                        Dice dice3 = new GreenDice(3);
                        Dice dice4 = new GreenDice(4);
                        Dice dice8 = new GreenDice(8);
                        Dice dice5 = new GreenDice(5);
                        Dice dice6 = new GreenDice(6);
                        Dice dice7 = new GreenDice(7);
                        Move move12= new Move(dice12,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move move11= new Move(dice11,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move move10= new Move(dice10,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move move9= new Move(dice9,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move move2= new Move(dice2,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move move3= new Move(dice3,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move move4= new Move(dice4,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move move8= new Move(dice8,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move move5= new Move(dice5,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move move6= new Move(dice6,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move move7= new Move(dice7,player.getScoreSheet().getCreatureByColor(RealmColor.GREEN));
                        Move[] moves = {move12,move11,move10,move9,move2,move3,move4,move8,move5,move6,move7};
                        for(Move m:moves){
                            try {
                                if(m.getCreature().checkMove(m.getDice())){
                                    bonusDice=m.getDice();
                                    break;
                                }
                            } catch (InvalidMoveException e) {
                                System.out.println("error in the makeMoveAI method green part");
                                e.printStackTrace();
                            }
                        }
                        break; 

                case BLUE:
                    bonusDice=new BlueDice(6);
                    break;

                case MAGENTA:
                    bonusDice=new MagentaDice(6);
                    break;

                case YELLOW:
                    bonusDice=new YellowDice(6);
                    break;   

                case WHITE:
                    bonusDice=new MagentaDice(6);
                    break;
                default:
                    bonusDice=new YellowDice(1);//RANDOM SHIT
                    System.out.println("error in the switch case");
                    break;
        }
        return bonusDice;
    }

    public boolean playTurnAI(Player player, boolean isThisATimeWarpRerollCall,int turnCount) {
           
        
       AI ai=(AI) player;
        gameBoard.resetGreenPostColorBonus();
        rollDice();

        ArrayList<Dice> avDice = gameBoard.getAvailableDice();
        boolean madeMove=false;
        boolean canRed=false;
        boolean canGreen=false;
        boolean canBlue=false;
        boolean canMagenta=false;
        boolean canYellow=false;
        boolean canWhite=false;
        
        RedDice redDice1=new RedDice();
        Move redAiMove=null;
        Move bestWhiteMove=null;
        Move aiMove1=null;
        if(turnCount!=3){
            boolean outerFlag=false;
            //outer loop for finding the smallest value in the dice
            for(int i=1;!outerFlag&&i<=6;i++){//break when you find a move

                for(int j=0;j<avDice.size();j++){//to iterate over the dice (dont break if you find a move bc what if another dice has the same value)
                    Dice currDice=avDice.get(j);
                    if(currDice.getValue()==i&& getPossibleMovesForADie(player, currDice).length!=0){

                        if(currDice.getRealm()==RealmColor.RED){
                            redDice1 = new RedDice(currDice.getValue());
                            for (int k = 4; k>= 1; k--) {//to iterate over the red dice only
                                redDice1.selectsDragon(k);
                                Move[] redDiceDragonMoves=getPossibleMovesForADie(player, redDice1);
                                if(redDiceDragonMoves.length!=0){
                                    redAiMove = new Move(redDice1, redDiceDragonMoves[0].getCreature());
                                    canRed=true;
                                    outerFlag=true;
                                    break;
                                }
                            }
                        }

                        else if(currDice instanceof ArcanePrism){
                            Dice[] whiteDices={new BlueDice(currDice.getValue()),new MagentaDice(currDice.getValue()),new YellowDice(currDice.getValue())};
                            bestWhiteMove=findBestMove(whiteDices, player);
                            canWhite=true;
                        }
                        else{
                            RealmColor realm=currDice.getRealm();
                            switch (realm) {
                                case GREEN:
                                    canGreen=true;
                                    break;
                                case BLUE:
                                    canBlue=true;
                                    break;
                                case MAGENTA:
                                    canMagenta=true;
                                    break;
                            }

                            aiMove1=new Move(currDice, getPossibleMovesForADie(player, currDice)[0].getCreature());

                        }
                    }
                }
            }

            if(canGreen){
                makeMoveAI(player, aiMove1);
                selectDice(aiMove1.getDice(), player);
                madeMove=true;
                ai.incrementTurnsPlayed();
                return true;
            }
            if(canBlue){
                makeMoveAI(player, aiMove1);
                selectDice(aiMove1.getDice(), player);
                madeMove=true;
                ai.incrementTurnsPlayed();
                return true;
            }
            if(canYellow){
                makeMoveAI(player, aiMove1);
                selectDice(aiMove1.getDice(), player);
                madeMove=true;
                ai.incrementTurnsPlayed();
                return true;
            }
            if(canMagenta){
                makeMoveAI(player, aiMove1);
                selectDice(aiMove1.getDice(), player);
                madeMove=true;
                ai.incrementTurnsPlayed();
                return true;
            }
            if(canRed){
                makeMoveAI(player, redAiMove);
                selectDice(redDice1, player);
                madeMove=true;
                ai.incrementTurnsPlayed();
                return true;
            }
            if(canWhite){
                makeMoveAI(player, bestWhiteMove);
                madeMove=true;
                ai.incrementTurnsPlayed();
                return true;
            }

        }
        //in case all the moves are invalid
        /*if(!madeMove&&turnCount!=3){
            moveAllIntoForgotten();
            return true;
        }*/
        
        ScoreSheet scoreSheet = player.getScoreSheet();
        boolean flag=false;
        ArrayList<Dice> actuallyAvailableDice = new ArrayList<Dice>();
        ArrayList<Dice> availableWhiteDice=new ArrayList<Dice>();
        for(Dice someDice:avDice){

                if (someDice instanceof RedDice) {
                    someDice = new RedDice(someDice.getValue());
                    RedDice finalDie = new RedDice(someDice.getValue());
                    for (int i = 4; i >= 1; i--) {
                        finalDie.selectsDragon(i);
                        Dragon dragon = (Dragon)scoreSheet.getCreatureByColor(RealmColor.RED);
                        if (dragon.getDragons()[i-1].checkMove(finalDie)) {
                            actuallyAvailableDice.add(finalDie);
                            flag = true;
                            break;
                        }
                    }
                }

                else if(someDice instanceof ArcanePrism){
                    RedDice whiteRedDice=new RedDice(someDice.getValue());
                    BlueDice whiteBlueDice=new BlueDice(someDice.getValue());
                    MagentaDice whiteMagentaDice=new MagentaDice(someDice.getValue());
                    YellowDice whiteYellowDice=new YellowDice(someDice.getValue());

                    for (int i = 4; i >= 1; i--) {
                        whiteRedDice.selectsDragon(i);
                        Dragon dragon = (Dragon)scoreSheet.getCreatureByColor(RealmColor.RED);
                        if (dragon.getDragons()[i-1].checkMove(whiteRedDice)) {
                            availableWhiteDice.add(whiteRedDice);
                            flag = true;
                            break;
                        }
                    }

                    if(getPossibleMovesForADie(player, whiteBlueDice).length!=0){
                        flag=true;
                        availableWhiteDice.add(whiteBlueDice);
                    }
                    if(getPossibleMovesForADie(player, whiteMagentaDice).length!=0){
                        flag=true;
                        availableWhiteDice.add(whiteMagentaDice);
                    }
                    if(getPossibleMovesForADie(player, whiteYellowDice).length!=0){
                        flag=true;
                        availableWhiteDice.add(whiteYellowDice);
                    }   

                }

                else if(getPossibleMovesForADie(player, someDice).length != 0){
                    flag=true;
                    actuallyAvailableDice.add(someDice);
                }
        }
        if(flag){
            actuallyAvailableDice.addAll(availableWhiteDice);
            Dice[] avDiceArray = actuallyAvailableDice.toArray(new Dice[actuallyAvailableDice.size()]);
            Move aiMove=findBestMove(avDiceArray, player);
            if(aiMove.getDice().getRealm()==RealmColor.RED){
                RedDice redDice = new RedDice(aiMove.getDice().getValue());
                for (int i = 4; i>= 1; i--) {
                    redDice.selectsDragon(i);
                    Move[] redDiceDragonMoves=getPossibleMovesForADie(player, redDice);
                    if(redDiceDragonMoves.length!=0){
                        aiMove = new Move(redDice, aiMove.getCreature());
                        makeMoveAI(player, aiMove);
                        return true;
                    }
                }
            }
            boolean whiteFlag=false;
            for(int i=0;i<availableWhiteDice.size();i++){
                if(availableWhiteDice.get(i).getValue()==aiMove.getDice().getValue()&&availableWhiteDice.get(i).getRealm()==aiMove.getDice().getRealm()){
                    ArcanePrism finalWhiteDice=new ArcanePrism(aiMove.getDice().getValue());
                    selectDice(finalWhiteDice, player);
                    whiteFlag=true;
                }
            }
            if(!whiteFlag) selectDice(aiMove.getDice(), player);
            makeMoveAI(player, aiMove);
        }        
        return true;
    }


    public boolean switchPlayerAI(){
        try {
            gameBoard.getPlayer1().switchStatus();
            gameBoard.getAi().switchStatus();
        } catch (Exception e) {
            return false;
        }
        return true;
        }
}

