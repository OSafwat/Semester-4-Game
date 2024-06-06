package game.engine;

import game.collectibles.ArcaneBoost;
import game.collectibles.TimeWarp;
import game.creatures.Creature;
import game.creatures.Dragon;
import game.creatures.Hydra;
import game.creatures.greenclasses.Gaia;
import game.dice.Dice;
import game.dice.GreenDice;
import game.dice.RedDice;
import game.engine.enums.RealmColor;
import game.engine.enums.RewardStates;
import game.exceptions.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Objects;

public class GUIGameController extends CLIGameController {

    int maxRounds;
    int maxTurns;
    int currentRound;
    int currentTurn;
    Exception exception;
    Player currentPlayer;
    Player arcaneBoostPlayer;
    boolean canUseArcaneBoost;

    public GUIGameController() {
        super();
        maxRounds = getSettings()[0];
        maxTurns = getSettings()[1];
        currentRound = 1;
        currentTurn = 1;
        currentPlayer = getPlayer1();
        canUseArcaneBoost = false;
    }
    @Override
    public void startGame() {}

    @Override
    public boolean makeMove(Player player, Move move) {
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
            player.updateGameScore();
            player.updateAllPossibleMoves();
            if (!temp)
                throw new InvalidMoveException();
        } catch (BonusException bException) {
            player.updateGameScore();
            player.updateAllPossibleMoves();
            exception = bException;
            return false;
        }
        catch (InvalidMoveException Im){
            exception = Im;
            return false;
        }
        return true;
    }

    public Player getPlayer1() {
        return getGameBoard().getPlayer1();
    }

    public Player getPlayer2() {
        return getGameBoard().getPlayer2();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public int[] getSettings() {
        return super.getSettings();
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setSelectedDragon(int dragon) {
        ((RedDice)getAllDice()[0]).selectsDragon(dragon);
    }

    public int getSelectedDragon() {
        return ((RedDice)getAllDice()[0]).getDragonNumber()+1;
    }

    public int getValue(String part) {
        int dragonValue = ((RedDice)getAllDice()[0]).getDragonNumber();
        Dragon dragon = ((Dragon)currentPlayer.getScoreSheet().getCreatureByColor(RealmColor.RED)).getDragons()[dragonValue];
        int result = -1;
        switch (part) {
            case "face": result = Objects.equals(dragon.getFace(), null) ? -1 : dragon.getFace(); break;
            case "wings": result = Objects.equals(dragon.getWings(), null) ? -1 : dragon.getWings(); break;
            case "tail": result = Objects.equals(dragon.getTail(), null) ? -1 : dragon.getTail(); break;
            case "heart": result = Objects.equals(dragon.getHeart(), null) ? -1 : dragon.getHeart(); break;
            default: ;
        }
        return result;
    }

    public Exception getException() {
        if (exception instanceof BonusException)
            return new BonusException(((BonusException)exception).getRealmColor1(), ((BonusException)exception).getRealmColor2());
        else
            return new InvalidMoveException();
    }

    public void incrementTurnCount () {
        if (currentTurn == -1) {
            canUseArcaneBoost = true;
            switchPlayer();
            currentPlayer = getActivePlayer();
            if (currentPlayer.getPlayerStatus() == getPlayer1().getPlayerStatus())
                incrementRoundCount();
            currentTurn = 1;
            return;
        }
        canUseArcaneBoost = false;
        currentTurn++;
        if (currentTurn % (maxTurns+1) == 0) {
            currentTurn = -1;
            currentPlayer = getPassivePlayer();
        }
        System.out.print(currentTurn + "   " + currentRound + "    " + currentPlayer.getName());
    }

    public void incrementRoundCount() {
        currentRound++;
    }

    public String[] getDragonPaths() {
        Dragon dragon = (Dragon) currentPlayer.getScoreSheet().getCreatureByColor(RealmColor.RED);
        Dragon[] dragons = dragon.getDragons();
        //images/RedRealmImages/face.png
        String[] paths = new String[4];
        for (int i = 0; i < 4; i++) {
            StringBuilder y = new StringBuilder("/images/RedRealmImages/");
            if (Objects.equals(dragons[i].getFace(), null))
                y.append("face-");
            if (Objects.equals(dragons[i].getWings(), null))
                y.append("wings-");
            if (Objects.equals(dragons[i].getTail(), null))
                y.append("tail-");
            if (Objects.equals(dragons[i].getHeart(),null))
                y.append("heart-");
            paths[i] = y.substring(0,y.length()-1) + ".png";
        }
        return paths;
    }

    public int getGreenCount() {
        Move[] moves = currentPlayer.getAllPossibleMoves();
        int count = 0;
        for (int i = 0; i < moves.length; i++) {
            if (moves[i].getDice().getRealm().equals(RealmColor.GREEN))
                count++;
        }
        return count;
    }

    public int getYellowCount() {
        Move[] moves = currentPlayer.getAllPossibleMoves();
        for (int i = 0; i < moves.length; i++) {
            if (moves[i].getDice().getRealm().equals(RealmColor.YELLOW))
                return 1;
        }
        return 0;
    }

    public int getMagentaCount() {
        Move[] moves = currentPlayer.getAllPossibleMoves();
        for (int i = 0; i < moves.length; i++) {
            if (moves[i].getDice().getRealm().equals(RealmColor.MAGENTA))
                return 1;
        }
        return 0;
    }

    public Pair<Integer, Integer> getHydraData() {
        int killedHeads = ((Hydra)(currentPlayer.getScoreSheet().getCreatureByColor(RealmColor.BLUE))).getHeadsKilled();
        int hydraNumber;
        if (killedHeads < 5)
            hydraNumber = 1;
        else if (killedHeads < 11)
            hydraNumber = 2;
        else
            hydraNumber = 0;
        int requiredCount = killedHeads - 5 < 0 ? killedHeads : killedHeads-5;
        if (hydraNumber == 1)
            requiredCount = 5 - requiredCount;
        else
            requiredCount = 6 - requiredCount;
        Pair<Integer, Integer> data = new Pair<>(hydraNumber, requiredCount);
        return data;
    }

    public boolean handleTimeWarps(Player player) throws ExhaustedResourceException, PlayerActionException{
        ArrayList<TimeWarp> timeWarps = player.getTimeWarps();
        if (currentPlayer.getPlayerStatus().equals(PlayerStatus.PASSIVE))
            throw new PlayerActionException();
        for (TimeWarp timeWarp: timeWarps) {
            if (timeWarp.getStatus() == RewardStates.ACQUIRED) {
                timeWarp.setStatus(RewardStates.USED);
                return true;
            }
        }
        throw new ExhaustedResourceException("No available timewarps!");
    }

    public boolean handleArcaneBoosts(Player player) throws ExhaustedResourceException, PlayerActionException{
        ArrayList<ArcaneBoost> arcaneBoosts = player.getArcaneBoosts();
        if (!canUseArcaneBoost)
            throw new PlayerActionException();
        try {
            Move[] moves = getAllPossibleMovesForDiceSet(player, getArcaneBoostDice(player));
            if (moves.length == 0)
                throw new NoAvailableMovesException("");
        } catch (NoAvailableMovesException e) {
            //handle no moves exception
            return false;
        }
        for (ArcaneBoost arcaneBoost: arcaneBoosts) {
            if (arcaneBoost.getStatus() == RewardStates.ACQUIRED) {
                arcaneBoost.setStatus(RewardStates.USED);
                return true;
            }
        }
        throw new ExhaustedResourceException("No available arcane boosts");
    }

    public void restoreArcaneBoost(Player player) {
        ArrayList<ArcaneBoost> arcaneBoosts = player.getArcaneBoosts();
        for (int i =0 ; i < arcaneBoosts.size(); i++) {
            if (arcaneBoosts.get(i).getStatus().equals(RewardStates.USED))
            {
                arcaneBoosts.get(i).setStatus(RewardStates.ACQUIRED);
                return;
            }
        }
    }

    public void setArcaneBoostPlayer(Player player) {
        arcaneBoostPlayer = player;
    }

    public Player getArcaneBoostPlayer() {
        return arcaneBoostPlayer;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public ArrayList<Integer> getDragons(int value) {
        Dragon[] dragons = ((Dragon)currentPlayer.getScoreSheet().getCreatureByColor(RealmColor.RED)).getDragons();
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < dragons.length; i++) {
            if (Objects.equals(dragons[i].getFace(), value) || Objects.equals(dragons[i].getWings(), value) || Objects.equals(dragons[i].getTail(), value) || Objects.equals(dragons[i].getHeart(), value))
                indices.add(i+1);
        }
        return indices;
    }

    public int getDragonPartForThisDragonAndThisValue(int dragon, int diceValue) {
        Dragon[] dragons = ((Dragon)currentPlayer.getScoreSheet().getCreatureByColor(RealmColor.RED)).getDragons();
        System.out.println("gui here, " + diceValue);
        Dragon requiredDragon = dragons[dragon-1];
        if (Objects.equals(requiredDragon.getFace(), diceValue))
            return 0;
        if (Objects.equals(requiredDragon.getWings(), diceValue))
            return 1;
        if (Objects.equals(requiredDragon.getTail(), diceValue))
            return 2;
        if (Objects.equals(requiredDragon.getHeart(), diceValue))
            return 3;
        return -1;
    }

//    public boolean makeArcaneMove(Player player, Move move) {
//        makeMove(player, move);
//    }

    public Creature getCreature(Player player, RealmColor realmColor) {
        return player.getScoreSheet().getCreatureByColor(realmColor);
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public boolean makeSpecialMove(Dice dice, Player player) throws BonusException, InvalidMoveException {
        Creature creature = player.getScoreSheet().getCreatureByRealm(dice);
        creature.makeMove(dice);
        return true;
    }
}
