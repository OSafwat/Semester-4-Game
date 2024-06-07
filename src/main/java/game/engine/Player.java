package game.engine;
import java.util.ArrayList;
import java.util.Objects;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.creatures.Dragon;
import game.creatures.Hydra;
import game.creatures.Lion;
import game.creatures.Phoenix;
import game.creatures.greenclasses.Gaia;
import game.dice.*;
import game.engine.enums.*;

public class Player {
    private String name;
    private PlayerStatus playerStatus;
    private GameScore gameScore;
    private ScoreSheet scoreSheet;
    private ArrayList<ArcaneBoost> arcaneBoosts;
    private ArrayList<TimeWarp> timeWarps;
    private Move[] allPossibleMoves;
    private ArrayList<Dice> playedDice;
    private ArrayList<Dice> usedArcaneDice;

    public Player clone() {
        String name = this.name; //
        PlayerStatus playerStatus = this.playerStatus; //
        GameScore gameScore = getGameScore().clone();
        ScoreSheet scoreSheet = getScoreSheet().clone();
        ArrayList<ArcaneBoost> arcaneBoosts = new ArrayList<>();
        ArrayList<TimeWarp> timeWarps = new ArrayList<>();
        if (!Objects.equals(this.allPossibleMoves, null))
            allPossibleMoves = new Move[this.allPossibleMoves.length];
        else
            allPossibleMoves = null;
        ArrayList<Dice> playedDice = new ArrayList<>();
        ArrayList<Dice> usedArcaneDice = new ArrayList<>();

        if (!Objects.equals(this.arcaneBoosts, null))
            for (ArcaneBoost arcaneBoost: this.arcaneBoosts)
                arcaneBoosts.add(new ArcaneBoost(arcaneBoost.getStatus()));

        if (!Objects.equals(this.timeWarps, null))
            for (TimeWarp timeWarp: this.timeWarps)
                timeWarps.add(new TimeWarp(timeWarp.getStatus()));

        if (!Objects.equals(allPossibleMoves, null)) {
            for (int i = 0; i < allPossibleMoves.length; i++) {
                if (Objects.equals(this.allPossibleMoves[i], null))
                    continue;
                Move move = this.allPossibleMoves[i];
                if (move.getDice() instanceof RedDice) {
                    allPossibleMoves[i] = new Move(new RedDice(move.getDice().getValue(), ((RedDice) move.getDice()).getDragonNumber()), ((Dragon) move.getCreature()).clone());
                }
                if (move.getDice() instanceof GreenDice) {
                    allPossibleMoves[i] = new Move(new GreenDice(move.getDice().getValue()), ((Gaia) move.getCreature()).clone());
                }
                if (move.getDice() instanceof BlueDice) {
                    allPossibleMoves[i] = new Move(new BlueDice(move.getDice().getValue()), ((Hydra) move.getCreature()).clone());
                }
                if (move.getDice() instanceof MagentaDice) {
                    allPossibleMoves[i] = new Move(new MagentaDice(move.getDice().getValue()), ((Phoenix) move.getCreature()).clone());
                }
                if (move.getDice() instanceof YellowDice) {
                    allPossibleMoves[i] = new Move(new YellowDice(move.getDice().getValue()), ((Lion) move.getCreature()).clone());
                }
            }
        }

        if (!Objects.equals(this.playedDice, null))
            for (Dice dice: this.playedDice) {
                if (dice instanceof RedDice)
                    playedDice.add(new RedDice(dice.getValue(), ((RedDice) dice).getDragonNumber()));
                if (dice instanceof GreenDice)
                    playedDice.add(new GreenDice(dice.getValue()));
                if (dice instanceof BlueDice)
                    playedDice.add(new BlueDice(dice.getValue()));
                if (dice instanceof MagentaDice)
                    playedDice.add(new MagentaDice(dice.getValue()));
                if (dice instanceof YellowDice)
                    playedDice.add(new YellowDice(dice.getValue()));
            }

        if (!Objects.equals(this.usedArcaneDice, null))
            for (Dice dice: this.usedArcaneDice) {
                if (dice instanceof RedDice)
                    usedArcaneDice.add(new RedDice(dice.getValue(), ((RedDice) dice).getDragonNumber()));
                if (dice instanceof GreenDice)
                    usedArcaneDice.add(new GreenDice(dice.getValue()));
                if (dice instanceof BlueDice)
                    usedArcaneDice.add(new BlueDice(dice.getValue()));
                if (dice instanceof MagentaDice)
                    usedArcaneDice.add(new MagentaDice(dice.getValue()));
                if (dice instanceof YellowDice)
                    usedArcaneDice.add(new YellowDice(dice.getValue()));
            }


        return new Player(name, playerStatus, gameScore, scoreSheet, arcaneBoosts, timeWarps, allPossibleMoves, playedDice, usedArcaneDice);

    }

    public Player(String name, PlayerStatus playerStatus, GameScore gameScore, ScoreSheet scoreSheet, ArrayList<ArcaneBoost> arcaneBoosts, ArrayList<TimeWarp> timeWarps, Move[] allPossibleMoves, ArrayList<Dice> playedDice, ArrayList<Dice> usedArcaneDice) {
        this.name = name;
        this.playerStatus = playerStatus;
        this.gameScore = gameScore;
        this.scoreSheet = scoreSheet;
        this.arcaneBoosts = arcaneBoosts;
        this.timeWarps = timeWarps;
        this.allPossibleMoves = allPossibleMoves;
        this.usedArcaneDice = usedArcaneDice;
        this.playedDice = playedDice;
    }

    public void setName(String name){
        this.name= name;
    }
    public Player(PlayerStatus status){
        this.scoreSheet= new ScoreSheet();
        this.playerStatus= status;
        this.arcaneBoosts=scoreSheet.getAllArcaneBoosts();
        this.timeWarps=scoreSheet.getAllTimeWarps();
        this.usedArcaneDice = new ArrayList<>();
        this.allPossibleMoves = getAllPossibleMoves();
        gameScore = new GameScore();
        playedDice = new ArrayList<>();
    }

    public Player() {

    }

    public Move[] getAllPossibleMoves(){
        ArrayList<Move> allMoves= new ArrayList<>();
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.RED).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.GREEN).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.BLUE).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.MAGENTA).getAllPossibleMoves());
        allMoves.addAll(scoreSheet.getCreatureByColor(RealmColor.YELLOW).getAllPossibleMoves());
        Move[] res = new Move[allMoves.size()];
        for (int i = 0; i < allMoves.size(); i++) {
            res[i] = allMoves.get(i);
        }
        allPossibleMoves = res;
        return res;
    }

    public void selectDice (Dice dice) {
        playedDice.add(dice);
    }

    public ArrayList<Dice> getPlayedDice () {
        ArrayList<Dice> playedDice = new ArrayList<>(this.playedDice);
        return playedDice;
    }

    public String getName(){
        return this.name;
    }

    public PlayerStatus getPlayerStatus(){
        return this.playerStatus;
    }
    public ScoreSheet getScoreSheet(){
        return this.scoreSheet;
    }
    public void switchStatus(){
        if (this.playerStatus == PlayerStatus.ACTIVE)
            this.playerStatus = PlayerStatus.PASSIVE;
        else
            this.playerStatus = PlayerStatus.ACTIVE;
    }
    public void updateGameScore(){
        gameScore.updateScores(this.scoreSheet.getScores(),this.scoreSheet.getElementalCrests());
    }
    public GameScore getGameScore(){
        return this.gameScore;
    }
    public ArrayList<TimeWarp> getTimeWarps(){
        return this.timeWarps;
    }
    public ArrayList<ArcaneBoost> getArcaneBoosts(){
        return this.arcaneBoosts;
    }
    public void updateAllPossibleMoves(){
        this.allPossibleMoves = getAllPossibleMoves();
    }

    public ArrayList<Dice> getUsedArcaneDice() {
        return usedArcaneDice;
    }
    public void resetUsedArcaneDice() {
        usedArcaneDice.clear();
    }
    public void addToUsedArcaneDice(Dice die) {
        usedArcaneDice.add(die);
    }
    public int getArcaneBoostsNum(){
        int counter=0;
        if(this.arcaneBoosts==null) return 0;
        for (ArcaneBoost tmp : this.arcaneBoosts) {
            if (tmp.getStatus() == RewardStates.ACQUIRED  )
                counter++;
        }
        return counter;
    }
    public int getTimeWarpsNum(){
        int counter=0;
        for (TimeWarp tmp : this.timeWarps) {
            if (tmp.getStatus() == RewardStates.ACQUIRED  )
                counter++;
        }
        return counter;
    }
}
