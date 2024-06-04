package game.gui;
import game.creatures.Creature;
import game.dice.*;
import game.engine.GUIGameController;
import game.engine.Move;
import game.engine.Player;
import game.engine.enums.RealmColor;
import game.engine.PlayerStatus;
import game.exceptions.BonusException;
import game.exceptions.ExhaustedResourceException;
import game.exceptions.NoAvailableMovesException;
import game.exceptions.PlayerActionException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.util.*;


public class DiceRealms extends Application {
    GUIGameController guiGameController;
    SceneController sceneController;
    Stage primaryStage;
    boolean isForgotten;
    int arcaneValue;
    RealmColor bonusRealmColor;
    int wasEssenceBonus;
    int bonusValue;
    volatile boolean awaitingInput;
    boolean isArcaneBoostPower;
    boolean canReroll;
    @Override
    public void start(Stage primaryStage) {
        guiGameController = new GUIGameController();
        this.primaryStage = primaryStage;
        primaryStage.setY(0);
        primaryStage.setX(0);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/wizard hat.png")));

        sceneController = new SceneController();
        setupGame();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void setupGame() {
        primaryStage.setTitle("Dice Realms Game");
        isArcaneBoostPower = false;
        canReroll = false;
        wasEssenceBonus = 0;
        bonusRealmColor = RealmColor.PARENT;
        awaitingInput = false;
        bonusValue = -1;
        arcaneValue = -1;
        primaryStage.setScene(sceneController.mainMenuScene.createMainScene());
        sceneController.boardScene.makeboardScene(getDicePNGs(guiGameController.getAvailableDice()));
        sceneController.redScene.createScene();
        sceneController.initDragons(guiGameController.getDragonPaths());
        sceneController.greenScene.createScene();
        sceneController.blueScene.createScene();
        sceneController.magentaScene.createScene();
        sceneController.yellowScene.createScene();
        initEventListeners();
        primaryStage.show();
        isForgotten = false;
    }

    public String[] getDicePNGs(Dice[] dice) {
        String[] dicePNGs = new String[6];
        Dice[] allDice = guiGameController.getAllDice();
        Arrays.sort(dice);
        Arrays.sort(allDice);
        int ptr = 0;
        for (int i = 0; i < 6; i++) {
            if (ptr != dice.length && allDice[i].getRealm().equals(dice[ptr].getRealm())) {
                dicePNGs[i] = getColorAsString(dice[ptr]) + " dice " + dice[ptr].getValue() + ".png";
                ptr++;
            }
            else {
                dicePNGs[i] = getColorAsString(allDice[i]) + " dice " + allDice[i].getValue() + ".png123";
            }
        }
        return dicePNGs;
    }

    public String[] getDiceGIFs() {
        Dice[] dice = guiGameController.getAllDice();
        String[] diceGIFs = new String[dice.length];
        // /images/Dice/Dice Animations/ArcanePrismAnimation.gif
        String path = "/images/Dice/Dice Animations/";
        for (int i = 0; i < dice.length; i++) {
            String diceColor;
            switch (dice[i].getRealm()) {
                case RED: diceColor = "redDice"; break;
                case GREEN: diceColor = "greenDice"; break;
                case BLUE: diceColor = "blueDice"; break;
                case MAGENTA: diceColor = "magentaDice"; break;
                case YELLOW: diceColor = "yellowDice"; break;
                default: diceColor = "arcanePrism";
            }
            diceGIFs[i] = path + diceColor + "Animation.gif";
            System.out.println(diceGIFs[i]);
        }
        return diceGIFs;
    }

    public String [] getInformation(Player player){
        String arr []= new String[5];
        arr[0]= "Player Name is: "+player.getName();
        arr[1]= player.getScoreSheet().toString();
        int [] tmp = player.getScoreSheet().getScores();
        arr[2] = "Score in Red: "+tmp[0] + "\nScore in Green: "+ tmp[1]+"\nScore in Blue: "+ tmp[2]+"\nScore in Magenta: "+ tmp[3]+"\nScore in Yellow: "+ tmp[4]+"\n";
        arr[3]="The number of ArcaneBoosts acquired is:"+ player.getArcaneBoostsNum();
        arr[4]="\nThe number of TimeWarps acquired is:"+ player.getTimeWarpsNum();
        return arr;
    
    }   
    public void openLeftGrimoire() {        

        Player player1 = guiGameController.getPlayer1();
        String arr [] =getInformation(player1); 

        TextArea textAreaPlayer1 = new TextArea();
        for (String text : arr) {        //uncomment when the string is being passed
            textAreaPlayer1.appendText(text);
        }
        textAreaPlayer1.setWrapText(true); // Optional: Wrap text to fit width
        textAreaPlayer1.setPrefWidth(700);
        textAreaPlayer1.setPrefHeight(780);
        textAreaPlayer1.setLayoutX(253);
        textAreaPlayer1.setLayoutY(117);
        textAreaPlayer1.setStyle("-fx-font-family: 'Monospaced';");
        textAreaPlayer1.setWrapText(false);
    
        textAreaPlayer1.setEditable(false);// Disable editing in the TextArea
        textAreaPlayer1.getStyleClass().add("grimoire");
       // textArea.setStyle(" -fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent; -fx-text-fill: black; ");
        textAreaPlayer1.getStylesheets().add(getClass().getResource("/MainMenu.css").toExternalForm());


        Player player2 = guiGameController.getPlayer2();
        String arr2 [] =getInformation(player2); 

        TextArea textAreaPlayer2 = new TextArea();
        for (String text : arr2) {        //uncomment when the string is being passed
            textAreaPlayer2.appendText(text);
        }
        textAreaPlayer2.setWrapText(true); // Optional: Wrap text to fit width
        textAreaPlayer2.setPrefWidth(700);
        textAreaPlayer2.setPrefHeight(780);
        textAreaPlayer2.setLayoutX(1000);
        textAreaPlayer2.setLayoutY(118);
        textAreaPlayer2.setStyle("-fx-font-family: 'Monospaced';");
        textAreaPlayer2.setWrapText(false);
    
        textAreaPlayer2.setEditable(false);// Disable editing in the TextArea
        textAreaPlayer2.getStyleClass().add("grimoire");
       // textArea.setStyle(" -fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent; -fx-text-fill: black; ");
        textAreaPlayer2.getStylesheets().add(getClass().getResource("/MainMenu.css").toExternalForm());

                
        ImageView bg = new ImageView(new Image(getClass().getResource("/images/open_book no bg.png").toExternalForm()));
        bg.setFitWidth(2049);
        bg.setFitHeight(1018);
        bg.setLayoutX(-56);
        bg.setLayoutY(18); 
        
        ImageView close = new ImageView(new Image(getClass().getResource("/images/close.png").toExternalForm()));
        close.setFitHeight(120);
        close.setFitWidth(120);
        close.setLayoutX(1575);
        close.setLayoutY(50);
        
        sceneController.boardScene.addToAnchorPane(bg, textAreaPlayer1,textAreaPlayer2, close);

        close.setOnMouseClicked(e -> {
            sceneController.boardScene.removeFromAnchorPane(bg, textAreaPlayer1, textAreaPlayer2, close);
          //  sceneController.boardScene.getLeftGrimoire().setOnMouseClicked(event -> openLeftGrimoire());  
        });
        
        //sceneController.boardScene.anchorPane.getChildren().addAll(close);
            
    }      

    public String getColorAsString(Dice dice) {
        StringBuilder colorString = new StringBuilder("/images/Dice/");
        switch (dice.getRealm()) {
            case RED: colorString.append("Red/red"); break;
            case GREEN: colorString.append("Green/green"); break;
            case BLUE: colorString.append("Blue/blue"); break;
            case MAGENTA: colorString.append("Magenta/magenta"); break;
            case YELLOW: colorString.append("Yellow/yellow"); break;
            default: colorString.append("White/white"); break;
        }
        return colorString.toString();
    }

    public void goBackEvent() {
        primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getActivePlayer().getName() ));
        if (bonusValue != -1) {
            handleBonus(wasEssenceBonus == 1 ? RealmColor.WHITE : bonusRealmColor);
        }
    }
    public void initEventListeners() {
        sceneController.mainMenuScene.getStartGameButton().setOnMouseClicked(e -> startGame());
        sceneController.boardScene.getLeftGrimoire().setOnMouseClicked(e ->  {System.out.println("LeftGrimoire clicked"); openLeftGrimoire();});  
        sceneController.mainMenuScene.getExitButton().setOnMouseClicked(e -> primaryStage.close());  //this should close the game when clicked
        sceneController.mainMenuScene.getPvPButton().setOnMouseClicked(e -> startGame());
        sceneController.mainMenuScene.getExitButton().setOnMouseClicked(e -> primaryStage.close());  //this should close the game when clicked
        initDiceAndRerollButtonEventListeners();
        sceneController.getPhoenix().setOnMouseClicked(e -> handleMove(4, 0, 0, null));
        sceneController.getHydra().setOnMouseClicked(e -> handleMove(3, 0, 0, null));
        sceneController.getGoBackButton().setOnMouseClicked(e -> sceneController.switchToMain());
        sceneController.getStartGameButton().setOnMouseClicked(e -> sceneController.switchFromMain());
        sceneController.getRedRealmGoBackButton().setOnMouseClicked(e -> goBackEvent());
        sceneController.getGreenRealmGoBackButton().setOnMouseClicked(e -> goBackEvent());
        sceneController.getBlueRealmGoBackButton().setOnMouseClicked(e -> goBackEvent());
        sceneController.getMagentaRealmGoBackButton().setOnMouseClicked(e -> goBackEvent());
        sceneController.getYellowRealmGoBackButton().setOnMouseClicked(e -> goBackEvent());
        sceneController.getLion().setOnMouseClicked(e -> handleMove(5, 0, 0, null));
        sceneController.getGaiaGuardian().setOnMouseClicked(e -> handleMove(2, 0, 0, null));
        sceneController.getPlayer1TimeWarpButton().setOnMouseClicked(e -> timeWarpSequence(guiGameController.getPlayer1()));
        sceneController.getPlayer1ArcaneBoostButton().setOnMouseClicked(e -> arcaneBoostSequence(guiGameController.getPlayer1()));
        sceneController.getPlayer2TimeWarpButton().setOnMouseClicked(e -> timeWarpSequence(guiGameController.getPlayer2()));
        sceneController.getPlayer2ArcaneBoostButton().setOnMouseClicked(e -> arcaneBoostSequence(guiGameController.getPlayer2()));
        
        initDragonEventListeners();
        

        sceneController.getFace().setOnMouseClicked(e -> {
            handleMove(1,0, guiGameController.getValue("face"), null);
            sceneController.closeDragonPartSelectionMenu();
        });
        sceneController.getWings().setOnMouseClicked(e -> {
            handleMove(1,0, guiGameController.getValue("wings"), null);
            sceneController.closeDragonPartSelectionMenu();
        });
        sceneController.getTail().setOnMouseClicked(e -> {
            handleMove(1,0, guiGameController.getValue("tail"), null);
            sceneController.closeDragonPartSelectionMenu();
        });
        sceneController.getHeart().setOnMouseClicked(e -> {
            handleMove(1,0, guiGameController.getValue("heart"), null);
            sceneController.closeDragonPartSelectionMenu();
        });

    }

    public void initDragonEventListeners() {
        sceneController.getDragon1().setOnMouseClicked(e -> {
            guiGameController.setSelectedDragon(1);
            sceneController.redScene.showDragonPartSelectionMenu();
            //handle dragon part
        });
        sceneController.getDragon2().setOnMouseClicked(e -> {
            guiGameController.setSelectedDragon(2);
            sceneController.redScene.showDragonPartSelectionMenu();
            //handle dragon part
        });
        sceneController.getDragon3().setOnMouseClicked(e -> {
            guiGameController.setSelectedDragon(3);
            sceneController.redScene.showDragonPartSelectionMenu();
            //handle dragon part
        });
        sceneController.getDragon4().setOnMouseClicked(e -> {
            guiGameController.setSelectedDragon(4);
            sceneController.redScene.showDragonPartSelectionMenu();
            //handle dragon part
        });
    }

    public void initDiceAndRerollButtonEventListeners() {
        Dice[] currentDice = guiGameController.getCurrentPlayer().getPlayerStatus().equals(PlayerStatus.ACTIVE) ? guiGameController.getAvailableDice() : guiGameController.getForgottenRealmDice();
        HashSet<RealmColor> realmColors = new HashSet<>();
        for (Dice dice: currentDice) {
            realmColors.add(dice.getRealm());
        }
        if (realmColors.contains(RealmColor.RED))
            sceneController.getRedDice().setOnMouseClicked(e -> {arcaneValue = -1; setupRealmScene("Red");});
        else
            sceneController.getRedDice().setOnMouseClicked(e -> unavailableDiceAlert());

        if (realmColors.contains(RealmColor.GREEN))
            sceneController.getGreenDice().setOnMouseClicked(e -> {arcaneValue = -1; setupRealmScene("Green");});
        else
            sceneController.getGreenDice().setOnMouseClicked(e -> unavailableDiceAlert());

        if (realmColors.contains(RealmColor.BLUE))
            sceneController.getBlueDice().setOnMouseClicked(e -> {arcaneValue = -1; setupRealmScene("Blue");});
        else
            sceneController.getBlueDice().setOnMouseClicked(e -> unavailableDiceAlert());

        if (realmColors.contains(RealmColor.MAGENTA))
            sceneController.getMagentaDice().setOnMouseClicked(e -> {arcaneValue = -1; setupRealmScene("Magenta");});
        else
            sceneController.getMagentaDice().setOnMouseClicked(e -> unavailableDiceAlert());

        if (realmColors.contains(RealmColor.YELLOW))
            sceneController.getYellowDice().setOnMouseClicked(e -> {arcaneValue = -1; setupRealmScene("Yellow");});
        else
            sceneController.getYellowDice().setOnMouseClicked(e -> unavailableDiceAlert());

        if (realmColors.contains(RealmColor.WHITE))
            sceneController.getArcaneDice().setOnMouseClicked(e -> {
                handleArcanePrism();
                sceneController.changeDragons(guiGameController.getDragonPaths());
                initDragonEventListeners();
                sceneController.initGaiaGuardians(guiGameController.getGreenCount());
                sceneController.initHydra(guiGameController.getHydraData().getKey(), guiGameController.getHydraData().getValue());
                sceneController.initPhoenix(guiGameController.getMagentaCount());
                sceneController.initLions(guiGameController.getYellowCount());
            });
        else
            sceneController.getArcaneDice().setOnMouseClicked(e -> unavailableDiceAlert());

        sceneController.getRollDiceButton().setOnMouseClicked(e -> {
            if (guiGameController.getCurrentPlayer().getPlayerStatus().equals(PlayerStatus.PASSIVE))
                passivePlayerCannotRerollAlert();
            else if (!canReroll)
                needToMakeMoveAlert();
            else {
                handleDiceReroll();
            }
        });
    }

    public void handleArcanePrism() {
        Scene scene;

        int whiteVal = isArcaneBoostPower ? guiGameController.getArcaneBoostDice(guiGameController.getArcaneBoostPlayer())[5].getValue() : guiGameController.getGameBoard().getWhite().getValue();
        Dice [] dietmp= {new RedDice(whiteVal), new GreenDice(guiGameController.getGameBoard().getGreen().getValue()), new BlueDice(whiteVal), new MagentaDice(whiteVal), new YellowDice(whiteVal)};
        String [] tmp = getDicePNGs(dietmp);
        ArrayList<String> dicePaths = new ArrayList<>();
        for (String string: tmp) {
            if (string.toLowerCase().contains("white"))
                continue;
            if (string.contains("123"))
                dicePaths.add(string.substring(0, string.length() - 3));
            else
                dicePaths.add(string);
        }
        Dialog whiteDialog = sceneController.boardScene.handleWhiteDice(dicePaths);
        String result =(String) whiteDialog.showAndWait().get();
        if (result.equals("CLOSED"))
            return;
        String [] resultAsArray= result.split(" ");
        int value = Integer.parseInt(resultAsArray[2].substring(0,1));
        switch (resultAsArray[0]){
            case "red":     scene = sceneController.redScene.getScene();break;
            case "green":   scene = sceneController.greenScene.getScene();break;
            case "blue":    scene = sceneController.blueScene.getScene();break;
            case "magenta": scene = sceneController.magentaScene.getScene();break;
            case "yellow":  scene = sceneController.yellowScene.getScene(); break;
            default:        return;
        }
        arcaneValue = value;

        primaryStage.setScene(scene);
    }

    public boolean handleMove(int num, int indicator, int dragonPart, Player arcanePlayer) {
        //change this later
        if (canReroll) {
            needToRerollDiceAlert();
            primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getCurrentPlayer().getName() ));
            return false;
        }
        RealmColor realmColor;
        Dice currDice = null;
        Creature creature;
        if (isArcaneBoostPower) {
            indicator = -1;
            //1 red, 2 green, 3 blue, 4 magenta, 5 yellow
            Dice[] arcaneBoostDice = guiGameController.getArcaneBoostDice(arcanePlayer);
            for (int i = 0; i < arcaneBoostDice.length; i++) {
                if (num == 1 && arcaneBoostDice[i].getRealm().equals(RealmColor.RED))
                {
                    currDice = new RedDice(arcaneBoostDice[i].getValue(), guiGameController.getSelectedDragon());
                }
                else if (num == 2 && arcaneBoostDice[i].getRealm().equals(RealmColor.GREEN)) {
                    currDice = new GreenDice(arcaneBoostDice[i].getValue());
                }
                else if (num == 3 && arcaneBoostDice[i].getRealm().equals(RealmColor.BLUE)) {
                    currDice = new BlueDice(arcaneBoostDice[i].getValue());
                }
                else if (num == 4 && arcaneBoostDice[i].getRealm().equals(RealmColor.MAGENTA)) {
                    currDice = new MagentaDice(arcaneBoostDice[i].getValue());
                }
                else if (num == 5 && arcaneBoostDice[i].getRealm().equals(RealmColor.YELLOW)) {
                    currDice = new YellowDice(arcaneBoostDice[i].getValue());
                }
            }
        }
        else {
            switch (num) {
                case 1:
                    currDice = new RedDice(guiGameController.getAllDice()[0].getValue(), guiGameController.getSelectedDragon());
                    break;
                case 2:
                    currDice = new GreenDice(guiGameController.getAllDice()[1].getValue());
                    break;
                case 3:
                    currDice = new BlueDice(guiGameController.getAllDice()[2].getValue());
                    break;
                case 4:
                    currDice = new MagentaDice(guiGameController.getAllDice()[3].getValue());
                    break;
                case 5:
                    currDice = new YellowDice(guiGameController.getAllDice()[4].getValue());
                    break;
                default:
                    return false;
            }
        }
        int saveOldWhiteValue = -1;
        int saveOldGreenValue = -1;
        if (arcaneValue != -1 && !currDice.getRealm().equals(RealmColor.GREEN)) {
            currDice.setValue(arcaneValue);
        }
        else if (bonusValue != -1) {
            indicator = -1;
            switch (bonusRealmColor) {
                case RED: currDice = new RedDice(bonusValue, guiGameController.getSelectedDragon()); break;
                case GREEN: currDice = new GreenDice(bonusValue); saveOldWhiteValue = guiGameController.getAllDice()[5].getValue(); saveOldGreenValue = guiGameController.getAllDice()[1].getValue() ; guiGameController.getAllDice()[5].setValue(0); guiGameController.getAllDice()[1].setValue(bonusValue);break;
                case BLUE: currDice = new BlueDice(bonusValue); break;
                case MAGENTA: currDice = new MagentaDice(bonusValue); break;
                case YELLOW: currDice = new YellowDice(bonusValue); break;
                default: return false;
            }
        }
        else if (isArcaneBoostPower && currDice.getRealm().equals(RealmColor.GREEN)) {
            saveOldWhiteValue = guiGameController.getAllDice()[5].getValue();
            saveOldGreenValue = guiGameController.getAllDice()[1].getValue();
            guiGameController.getAllDice()[5].setValue(0);
            guiGameController.getAllDice()[1].setValue(saveOldWhiteValue + saveOldGreenValue);
        }
        realmColor = currDice.getRealm();
        creature = guiGameController.getCurrentPlayer().getScoreSheet().getCreatureByColor(realmColor);
        if (currDice instanceof RedDice) {
            if (currDice.getValue() != dragonPart) {
                illegalMoveAlert();
                primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getCurrentPlayer().getName() ));
                if (bonusValue != -1) {
                    handleBonus(wasEssenceBonus == 1 ? RealmColor.WHITE : bonusRealmColor);
                }
                return false;
            }
        }
        Player player = guiGameController.getCurrentPlayer();
        if (isArcaneBoostPower)
            player = arcanePlayer;
        boolean moveDone = guiGameController.makeMove(player, new Move(currDice, creature));
        primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getCurrentPlayer().getName() ));
        System.out.println(player.getScoreSheet().toString());
        if (saveOldWhiteValue != -1) {
            guiGameController.getAllDice()[5].setValue(saveOldWhiteValue);
            guiGameController.getAllDice()[1].setValue(saveOldGreenValue);
        }
        if (!moveDone) {
            //if we enter here, that means that some sort of exception has been caught
            //either a bonus exception or an invalid move exception
            Exception exception = guiGameController.getException();
            arcaneValue = -1;
            if (exception instanceof BonusException) {
                canReroll = true;
                isArcaneBoostPower = false;
                //put in the bonus make move logic
                handleBonus(((BonusException)exception).getRealmColor1());
                new Thread(() -> {
                    while (awaitingInput) {
                        try {
                            Thread.sleep(100); // Avoid busy-waiting
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                if (!((BonusException)exception).getRealmColor2().equals(RealmColor.PARENT)) {
                    handleBonus(((BonusException)exception).getRealmColor2());

                    new Thread(() -> {
                        while (awaitingInput) {
                            try {
                                Thread.sleep(100); // Avoid busy-waiting
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

                return true;
            }
            else {
                if (isArcaneBoostPower) {
                    guiGameController.restoreArcaneBoost(arcanePlayer);
                }
                isArcaneBoostPower = false;
                //put in a popup that tells the user that he has done an illegal move
                illegalMoveAlert();
                //logic here
                //and go back to the dice board
                if (bonusValue != -1) {
                    handleBonus(wasEssenceBonus == 1 ? RealmColor.WHITE : bonusRealmColor);
                }
                return false;
            }
        }
        if (bonusValue != -1) {
            bonusValue = -1;
            wasEssenceBonus = 0;
            bonusRealmColor = RealmColor.PARENT;
            primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getCurrentPlayer().getName() ));
            return true;
        }
        if (indicator == 0) {
            canReroll = true;
            if (arcaneValue != -1){
                arcaneValue = -1;
                guiGameController.selectDice(guiGameController.getAllDice()[5], guiGameController.getCurrentPlayer());
            }
            else
                guiGameController.selectDice(currDice, guiGameController.getCurrentPlayer());
                if (isForgotten) {
                    isForgotten = false;
                    guiGameController.getGameBoard().resetAllDice();
                    guiGameController.rollDice();
                    guiGameController.incrementTurnCount();
                    sceneController.boardScene.makeboardScene(getDicePNGs(guiGameController.getAvailableDice()));
                    primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getCurrentPlayer().getName()));
                    initDiceAndRerollButtonEventListeners();
                    handleReward(guiGameController.getCurrentRound());
                    return true;
                }
            }
        return true;
    }

    private void loadDiceBoard() {
        sceneController.boardScene.makeboardScene(getDicePNGs(guiGameController.getAvailableDice()));
        primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getCurrentPlayer().getName()));
        initDiceAndRerollButtonEventListeners();
    }
    private void handleReward(int newRoundCount) {
        String[] rewards = guiGameController.getRewards(guiGameController.getMaxRounds());
        String currentReward = rewards[newRoundCount-1];
        System.out.println(currentReward);
        if (currentReward.toLowerCase().contains("bonus")) {
            if (currentReward.toLowerCase().contains("red"))
                handleBonus(RealmColor.RED);
            else if (currentReward.toLowerCase().contains("green"))
                handleBonus(RealmColor.GREEN);
            else if (currentReward.toLowerCase().contains("blue"))
                handleBonus(RealmColor.BLUE);
            else if (currentReward.toLowerCase().contains("magenta"))
                handleBonus(RealmColor.MAGENTA);
            else if (currentReward.toLowerCase().contains("yellow"))
                handleBonus(RealmColor.YELLOW);
            else
                handleBonus(RealmColor.WHITE);
        }
    }

    public void handleForgottenTurn() {
        canReroll = false;
        guiGameController.moveAllIntoForgotten();
        //initiate forgotten realm turn
        sceneController.boardScene.makeboardScene(getDicePNGs(guiGameController.getForgottenRealmDice()));
        primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getCurrentPlayer().getName()));
        isForgotten = true;
        initDiceAndRerollButtonEventListeners();
    }
    public void illegalMoveAlert(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Alert");
        Label label = new Label("You have made an Illegal Move");
        label.setStyle("-fx-font-size: 30px;");
        // Set the Label as the content of the alert
        alert.getDialogPane().setContent(label);
        alert.showAndWait();
    }

    public void needToMakeMoveAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Alert");
        Label label = new Label("You have not played a move yet!");
        label.setStyle("-fx-font-size: 30px;");

        alert.getDialogPane().setContent(label);
        alert.showAndWait();
    }

    public void needToRerollDiceAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Alert");
        Label label = new Label("You have already played a move, and you must reroll your dice!");
        label.setStyle("-fx-font-size: 30px;");

        alert.getDialogPane().setContent(label);
        alert.showAndWait();
    }

    public void passivePlayerCannotRerollAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Alert");
        Label label = new Label("You are the passive player, and so cannot reroll the dice!");
        label.setStyle("-fx-font-size: 30px;");

        alert.getDialogPane().setContent(label);
        alert.showAndWait();
    }

    public void unavailableDiceAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Alert");

        Label label = new Label("This dice is not available, because you have either played with it before, or it is currently in the forgotten realm!");
        label.setStyle("-fx-font-size: 30px;");

        alert.getDialogPane().setContent(label);
        alert.showAndWait();
    }

    public void handleBonus(RealmColor realmColor) {
        if (realmColor.equals(RealmColor.WHITE))
            wasEssenceBonus = 1;
        arcaneValue = -1;
        awaitingInput = true;
        Dialog<String> bonusDialog = new Dialog<>();
        FlowPane buttonBox = new FlowPane(20,20);
        buttonBox.setPrefWrapLength(1200);
        if (realmColor.equals(RealmColor.WHITE)) {
            RealmColor[] realmColors = RealmColor.values();
            for (int i = 0; i <= 4; i++) {
                ArrayList<Button> buttons = bonusDialogFill(realmColors[i], bonusDialog);
                for (Button dialogButton : buttons) {
                    buttonBox.getChildren().add(dialogButton);
                }
            }
        }
        else {
            ArrayList<Button> buttons = bonusDialogFill(realmColor, bonusDialog);
            for (Button dialogButton : buttons) {
                buttonBox.getChildren().add(dialogButton);
            }
        }
        bonusDialog.getDialogPane().setContent(buttonBox);
        bonusDialog.showAndWait();
        String result = bonusDialog.getResult();
        bonusValue = result.charAt(result.length()-2) == ' ' ? Integer.parseInt(result.substring(result.length()-1)) : Integer.parseInt(result.substring(result.length()-2));
        if (result.contains("Red")) {
            bonusRealmColor = RealmColor.RED;
            setupRealmScene("Red");
        }
        else if (result.contains("Blue")) {
            bonusRealmColor = RealmColor.BLUE;
            setupRealmScene("Blue");
        }
        else if (result.contains("Green")) {
            bonusRealmColor = RealmColor.GREEN;
            setupRealmScene("Green");
        }
        else if (result.contains("Magenta")) {
            bonusRealmColor = RealmColor.MAGENTA;
            setupRealmScene("Magenta");
        }
        else if (result.contains("Yellow")) {
            bonusRealmColor = RealmColor.YELLOW;
            setupRealmScene("Yellow");
        }

    }

    public ArrayList<Button> bonusDialogFill(RealmColor realmColor, Dialog<String> bonusDialog) {
        //fix green later
        String color;
        switch (realmColor) {
            case RED: color = "Red"; break;
            case GREEN: color = "Green"; break;
            case BLUE: color = "Blue"; break;
            case MAGENTA: color = "Magenta"; break;
            case YELLOW: color = "Yellow";break;
            default: return null;
        }
        ArrayList<Button> buttons = new ArrayList<>();
        int lowerLimit = realmColor.equals(RealmColor.GREEN) ? 2 : 1;
        int upperLimit = realmColor.equals(RealmColor.GREEN) ? 12 : 6;
        //   Color/color dice value.png
        String path = "/images/Dice/";
        String greenBonus = realmColor.equals(RealmColor.GREEN) ? "Green Bonus/" : "";
        for (int i = lowerLimit; i <= upperLimit; i++) {
            Button tmp = new Button();
            ImageView tempImage = new ImageView(new Image(getClass().getResourceAsStream(path + color + "/" + greenBonus + color.toLowerCase() + " dice " + i + ".png")));

            tempImage.setFitHeight(150);
            tempImage.setFitWidth(150);
            tmp.setGraphic(tempImage);
            String value = " " + i;
            tmp.setOnAction(event -> bonusDialog.setResult(color + value));
            buttons.add(tmp);
        }
        return buttons;
    }

    public boolean checkRealmValidity(String realm) {
        realm = realm.toLowerCase();
        return realm.equals("red") || realm.equals("blue") || realm.equals("green") || realm.equals("yellow") || realm.equals("magenta");
    }

    public void startGame() {
        handlePlayerNameInputs();
        primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getActivePlayer().getName() ));
        String[] rewards = guiGameController.getRewards(guiGameController.getMaxRounds());
        String currentReward = rewards[0];
        System.out.println(currentReward);
        if (currentReward.toLowerCase().contains("bonus")) {
            if (currentReward.toLowerCase().contains("red"))
                handleBonus(RealmColor.RED);
            else if (currentReward.toLowerCase().contains("green"))
                handleBonus(RealmColor.GREEN);
            else if (currentReward.toLowerCase().contains("blue"))
                handleBonus(RealmColor.BLUE);
            else if (currentReward.toLowerCase().contains("magenta"))
                handleBonus(RealmColor.MAGENTA);
            else if (currentReward.toLowerCase().contains("yellow"))
                handleBonus(RealmColor.YELLOW);
            else
                handleBonus(RealmColor.WHITE);
        }
        else
            guiGameController.handleRoundRewards(guiGameController.getCurrentPlayer(), currentReward);
    }

    public void handlePlayerNameInputs() {
        TextInputDialog textInputDialog = new TextInputDialog();

        textInputDialog.setTitle("Player Name Input");
        textInputDialog.setHeaderText("Please enter Player 1's name");

        Optional<String> result = textInputDialog.showAndWait();
        String player1Name;

        try {
            player1Name = handleNames(result.get());
        } catch (NoSuchElementException e) {
            player1Name = handleNames("");
        }
        textInputDialog = new TextInputDialog();

        textInputDialog.setTitle("Player Name Input");
        textInputDialog.setHeaderText("Please enter Player 2's name");

        result = textInputDialog.showAndWait();
        String player2Name;

        try {
            player2Name = handleNames(result.get());
        } catch (NoSuchElementException e) {
            player2Name = handleNames("");
        }

        guiGameController.getPlayer1().setName(player1Name);
        guiGameController.getPlayer2().setName(player2Name);
    }

    public String handleNames(String playerName) {
        String[] magicNames = {
                "Akiramenai", "Clown", "Zephyrion", "Luminara", "Amrosgy", "Elandor", "Celestia", "Drakonis",
                "Seraphina", "Faelan", "Azura", "Eldric", "Isilme", "Badawayyy", "Aelar", "Lyra", "Vesper",
                "Dumbbelldoor", "CNC", "Boring", "Sylphine", "Zeus", "Adolf", "Arion", "Liora", "Valerian",
                "Esmeray", "Adolf", "Amara", "Kael", "MONSTER...THE DRINK", "Oberon", "Elara", "Utopia", "Morrigan",
                "Za3bola", "Kaelen", "REWE", "Dumbledore", "Fenris", "Gandalf", "Dimension6", "Arwen", "Serapis",
                "ACE", "Sixfold", "Marianna", "El Le3ba", "Za3bola", "Square Moustache guy", "Hooba"
        };
        if (playerName.trim().isEmpty()) {
            Random random = new Random();

            // Get a random index between 0 and the length of the array
            int randomIndex = random.nextInt(magicNames.length);

            // Get the random name from the array
            String randomName = magicNames[randomIndex];

            playerName = randomName;
        }

        switch(playerName.toLowerCase()) {
            case "dimension6":
                playerName = guiGameController.changeToRainbowText(playerName);
                break;

            case "slmat":
            case "doctor":
            case "dr":
            case "dr.":
            case "doc":
            case "ahmed hussein":
                playerName = guiGameController.changeToRainbowText("slmat27");
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
                playerName = playerName;
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
                playerName = (playerName);
                break;

            case "sharazad":
                playerName = playerName;
                break;

            case "giu":
                playerName = playerName;
                break;
            case "guc":
                playerName = playerName;
            case "meow":
                playerName = playerName;

            default:
                break;
        }
        return playerName;
    }

    public void setupRealmScene(String realmColor) {
        Scene scene;
        switch (realmColor.toLowerCase()) {
            case "red": sceneController.changeDragons(guiGameController.getDragonPaths()); initDragonEventListeners(); scene = sceneController.redScene.getScene();break;
            case "green": sceneController.initGaiaGuardians(guiGameController.getGreenCount()); ;scene = sceneController.greenScene.getScene(); break;
            case "blue": sceneController.initHydra(guiGameController.getHydraData().getKey(), guiGameController.getHydraData().getValue()); ; scene = sceneController.blueScene.getScene(); break;
            case "magenta": sceneController.initPhoenix(guiGameController.getMagentaCount()); ;scene = sceneController.magentaScene.getScene(); break;
            case "yellow": sceneController.initLions(guiGameController.getYellowCount()); ;scene = sceneController.yellowScene.getScene(); break;


            default: scene = null;
        }

        System.out.println(guiGameController.getHydraData());
        System.out.println(sceneController.blueScene.getScene());
        primaryStage.setScene(scene);
    }

    public void timeWarpSequence(Player player) {
        if (isArcaneBoostPower)
            return;
        Dialog<String> dialog = new Dialog<>();
        Button accept = new Button();
        accept.setText("Yes");
        Button decline = new Button();
        decline.setText("No");
        accept.setOnMouseClicked(e -> dialog.setResult("YES"));
        decline.setOnMouseClicked(e -> dialog.setResult("NO"));
        dialog.setContentText("Would you like to use one of your time warps?");
        FlowPane buttons = new FlowPane();
        buttons.getChildren().add(accept);
        buttons.getChildren().add(decline);
        dialog.getDialogPane().setContent(buttons);
        boolean proceed = dialog.getResult().equals("Yes");
        if (proceed) {
            try {
                guiGameController.handleTimeWarps(player);
            } catch (ExhaustedResourceException e) {
                //display error
                return;
            } catch (PlayerActionException e) {
                //display error
                return;
            }
            handleDiceReroll();
        }
    }

    public void arcaneBoostSequence(Player player) {
        if (isArcaneBoostPower)
            return;
        Dialog<String> dialog = new Dialog<>();
        Button accept = new Button();
        accept.setText("Yes");
        Button decline = new Button();
        decline.setText("No");
        accept.setOnMouseClicked(e -> dialog.setResult("YES"));
        decline.setOnMouseClicked(e -> dialog.setResult("NO"));
        dialog.setContentText("Would you like to use one of your arcane boosts?");
        FlowPane buttons = new FlowPane();
        buttons.getChildren().add(accept);
        buttons.getChildren().add(decline);
        dialog.getDialogPane().setContent(buttons);
        boolean proceed = dialog.getResult().equals("Yes");
        if (proceed) {
            try {
                guiGameController.handleArcaneBoosts(player);
            } catch (ExhaustedResourceException e) {
                //display error
                return;
            } catch (PlayerActionException e) {
                //display error
                return;
            }
            Dice[] arcaneBoostDice = guiGameController.getArcaneBoostDice(player);
            //display the scene with the arcaneBoostDice
            sceneController.boardScene.makeboardScene(getDicePNGs(arcaneBoostDice));
            initDiceAndRerollButtonEventListeners();
            primaryStage.setScene(sceneController.boardScene.getBoardScene(guiGameController.getCurrentRound(), guiGameController.getCurrentTurn(), player.getName()));

            //setup is done, leave the rest to the player
            isArcaneBoostPower = true;
            guiGameController.setArcaneBoostPlayer(player);
        }
    }

    private void handleDiceReroll() {
        guiGameController.rollDice();
        guiGameController.incrementTurnCount();
        if (guiGameController.getCurrentPlayer().getPlayerStatus().equals(PlayerStatus.PASSIVE)) {
            handleForgottenTurn();
            return;
        }
        canReroll = false;
        sceneController.boardScene.makeboardScene(getDiceGIFs());
        primaryStage.setScene(sceneController.boardScene.getBoardScene(this.guiGameController.getCurrentRound(), this.guiGameController.getCurrentTurn(), this.guiGameController.getCurrentPlayer().getName()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Sleep for 1 second (1000 milliseconds)
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loadDiceBoard();
                    }
                });
            }
        }).start();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
