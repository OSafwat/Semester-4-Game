package game.engine;

import game.collectibles.TimeWarp;
import game.exceptions.BonusException;
import game.dice.*;
import game.creatures.*;
import game.creatures.greenclasses.*;
import game.engine.enums.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
public class CLIGameController {
    GameBoard gameBoard;
    

    //constructor(s):
    public CLIGameController(String player1Name, String player2Name){
        this.gameBoard = new GameBoard(player1Name, player2Name);
    }
    public void startGame(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("please input the name of player 1:");
        String player1Name = scanner.nextLine();
        System.out.println("please input the name of player 2:");
        String player2Name = scanner.nextLine();
        CLIGameController clicontroller = new CLIGameController(player1Name, player2Name);
        
        
        int numberOfRounds;
        int numebrOfTurnsPerRound;
        try{
        FileReader SettingsfileReader = new FileReader("dice-realms-game-dimension/src/main/resources/RoundsSettings.properties");
        BufferedReader settings = new BufferedReader(SettingsfileReader);
        }
        catch(){
       
        //game loop
        String line1=  settings.readLine();
        String [] lineOfRounds = line1.split("="); 
        numberOfRounds = Integer.parseInt(lineOfRounds[1]);

        String line2=  settings.readLine();
        String [] lineOfTurns = line1.split("="); 
        numebrOfTurnsPerRound = Integer.parseInt(lineOfTurns[1]);
        }
    }

    // move methods
    public Move [] getAllPossibleMoves(Player player){
        return player.getAllPossiblMoves();
    }

    //makeMove(new player(), new Move(new RedDice(), new Gaia())) 
    public boolean makeMove(Player player, Move move)throws BonusException{
        try{
            if (move.getCreature() instanceof Dragon ){
                System.out.println("which dragon 7adretak 3aiz temawet (choose from 1 to 4)");
                int dragonIndex = Integer.parseInt(System.console().readLine());
                Dragon dragon = ((Dragon) move.getCreature()).dragonSelector(dragonIndex);
                move.setCreature(dragon);  //should be make move
            }else if (move.getCreature() instanceof Gaia){            
                GreenDice greenDice= (GreenDice)this.gameBoard.getWhite();
                Dice whiteDice= this.gameBoard.getGreen();
                int greenVal= greenDice.getValue();
                int whiteVal= whiteDice.getValue();
                greenDice.setRealValue(greenVal+ whiteVal);
            }
            move.getCreature().makeMove(move.getDice());
            
        }catch (BonusException bException){
            RealmColor theBonusColor= bException.getRealmColor();
            System.out.println("please enter the number to attack the "+theBonusColor + " realm with: ");
            int numberToAttackWith = Integer.parseInt(System.console().readLine());
            Creature creature = player.getScoresheet().getCreatureByColor(theBonusColor);
            Move bonusmove = new Move(new Dice(numberToAttackWith), creature );
            makeMove(player, bonusmove);
        }
        return true;
    }    
    //gameboard getter:
    public GameBoard getGameBoard() {   
        return gameBoard;
    }
    public GameStatus getGameStatus(){
        return this.gameBoard.getGameStatus();
    }
    // dice related methods:
    public void rollDice(){
        this.gameBoard.rollDice();
    }
    public Dice [] getAllDice(){
        return this.gameBoard.getAllDice();
    }
    public Dice [] getAvailableDice(){
        return this.gameBoard.getAvailableDice();
    }
    public Dice [] getForgottenRealmDice(){
        return this.gameBoard.getForgottenRealmDice();
    }
    
    
    //player related methods: 
    public boolean switchPlayer(){
        try{
            this.gameBoard.getPlayer1().switchStatus();
            this.gameBoard.getPlayer2().switchStatus();
            return true;
        } 
        catch(Exception e){
            return false;
        }
    }

    public Player getActivePlayer(){
        if (this.gameBoard.getPlayer1().getPlayerStatus() == PlayerStatus.ACTIVE){
            return this.gameBoard.getPlayer1();
        }else{
            return this.gameBoard.getPlayer2();
        }
    }

    public Player getPassivePlayer(){
        if (this.gameBoard.getPlayer1().getPlayerStatus() == PlayerStatus.PASSIVE){
            return this.gameBoard.getPlayer1();
        }else{
            return this.gameBoard.getPlayer2();
        }
    }

    //player attributes related methods
    public ScoreSheet getScoreSheet(Player player){
        return player.getScoresheet();
    }

    public GameScore getGameScore(Player player){
        return player.getGameScore();
    }

    public  TimeWarp[] getTimeWarpPowers(Player player){
        return player.getTimeWarps();
    }

    




    // public abstract boolean switchPlayer(){
    // }

}
